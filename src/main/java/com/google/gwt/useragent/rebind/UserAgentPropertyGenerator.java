
package com.google.gwt.useragent.rebind;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.linker.ConfigurationProperty;
import com.google.gwt.core.ext.linker.PropertyProviderGenerator;
import com.google.gwt.user.rebind.SourceWriter;
import com.google.gwt.user.rebind.StringSourceWriter;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

/**
 * Generator which writes out the JavaScript for determining the value of the
 * <code>user.agent</code> selection property.
 */
public class UserAgentPropertyGenerator implements PropertyProviderGenerator {

  /**
   * The list of {@code user.agent} values listed here should be kept in sync with
   * {@code UserAgent.gwt.xml}.
   * <p>Note that the order of enums matter as the script selection is based on running
   * these predicates in order and matching the first one that returns {@code true}.
   * <p> Also note that, {@code docMode < 11} in predicates for older IEs exists to
   * ensures we never choose them for IE11 (we know that they will not work for IE11).
   */
  private enum UserAgent {
    safari("return ((ua.indexOf('webkit') != -1) && !(ua.indexOf('trident') != -1));"),
    ie10("return (ua.indexOf('msie') != -1 && (docMode >= 10 && docMode < 11)) || "
              + "(ua.indexOf('iemobile') != -1 && (docMode >= 10 && docMode < 11))"),
    ie9("return (ua.indexOf('msie') != -1 && (docMode >= 9 && docMode < 11));"),
    ie8("return (ua.indexOf('msie') != -1 && (docMode >= 8 && docMode < 11));"),
    gecko1_8("return (ua.indexOf('gecko') != -1 || docMode >= 11);");

    private final String predicateBlock;

    private UserAgent(String predicateBlock) {
      this.predicateBlock = predicateBlock;
    }

    private static Set<String> getKnownAgents() {
      HashSet<String> userAgents = new HashSet<String>();
      for (UserAgent userAgent : values()) {
        userAgents.add(userAgent.name());
      }
      return userAgents;
    }
  }

  /**
   * Writes out the JavaScript function body for determining the value of the
   * <code>user.agent</code> selection property. This method is used to create
   * the selection script and by {@link UserAgentGenerator} to assert at runtime
   * that the correct user agent permutation is executing.
   */
  static void writeUserAgentPropertyJavaScript(SourceWriter body,
      SortedSet<String> possibleValues, String fallback) {

    // write preamble
    body.println("var ua = navigator.userAgent.toLowerCase();");
    body.println("var docMode = $doc.documentMode;");

    for (UserAgent userAgent : UserAgent.values()) {
      // write only selected user agents
      if (possibleValues.contains(userAgent.name())) {
        body.println("if ((function() { ");
        body.indentln(userAgent.predicateBlock);
        body.println("})()) return '%s';", userAgent.name());
      }
    }

    // default return
    if (fallback == null) {
      fallback = "unknown";
    }
    body.println("return '" + fallback + "';");
  }

  @Override
  public String generate(TreeLogger logger, SortedSet<String> possibleValues, String fallback,
      SortedSet<ConfigurationProperty> configProperties) {
    assertUserAgents(logger, possibleValues);

    StringSourceWriter body = new StringSourceWriter();
    body.println("{");
    body.indent();
    writeUserAgentPropertyJavaScript(body, possibleValues, fallback);
    body.outdent();
    body.println("}");

    return body.toString();
  }

  private static void assertUserAgents(TreeLogger logger, SortedSet<String> possibleValues) {
    HashSet<String> unknownValues = new HashSet<String>(possibleValues);
    unknownValues.removeAll(UserAgent.getKnownAgents());
    if (!unknownValues.isEmpty()) {
      logger.log(TreeLogger.WARN, "Unrecognized " + UserAgentGenerator.PROPERTY_USER_AGENT
          + " values " + unknownValues + ", possibly due to UserAgent.gwt.xml and "
          + UserAgentPropertyGenerator.class.getName() + " being out of sync.");
    }
  }
}
