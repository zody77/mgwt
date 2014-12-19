package com.googlecode.mgwt.ui.client.widget.panel.flex;

import com.google.gwt.dom.client.Element;

public class FlexPropertyHelperWebkit extends FlexPropertyHelper {

  @Override
  public void _setAlignmentProperty(Element el, Alignment alignment) {
    String alignItemOldSyntax, alignItemNewSyntax;
    switch (alignment) {
      case START: {
        alignItemOldSyntax = "start";
        alignItemNewSyntax = "flex-start";
        break;
      }
      case END: {
        alignItemOldSyntax = "end";
        alignItemNewSyntax = "flex-end";
        break;
      }
      case CENTER: {
        alignItemOldSyntax = "center";
        alignItemNewSyntax = "center";
        break;
      }
      case STRETCH: {
        alignItemOldSyntax = ""; // not implemented
        alignItemNewSyntax = "stretch";
        break;
      }
      case BASELINE: {
        alignItemOldSyntax = "";  // not implemented
        alignItemNewSyntax = "baseline";
        break;
      }
      default: {
        alignItemOldSyntax = "";
        alignItemNewSyntax = "";
      }
    }
    setStyleProperty(el, "WebkitBoxAlign", alignItemOldSyntax);
    setStyleProperty(el, "WebkitAlignItems", alignItemNewSyntax);
  }

  @Override
  public void _setOrientationProperty(Element el, Orientation orientation) {
    String orientationOldSyntax, orientationNewSyntax;
    boolean reverse = false;
    switch (orientation) {
      case HORIZONTAL: {
        orientationOldSyntax = "horizontal";
        orientationNewSyntax = "row";
        break;
      }
      case VERTICAL: {
        orientationOldSyntax = "vertical";
        orientationNewSyntax = "column";
        break;
      }
      case HORIZONTAL_REVERSE: {
        orientationOldSyntax = "horizontal"; reverse = true;
        orientationNewSyntax = "row-reverse";
        break;
      }
      case VERTICAL_REVERSE: {
        orientationOldSyntax = "vertical"; reverse = true;
        orientationNewSyntax = "column-reverse";
        break;
      }
      default: {
        orientationOldSyntax = "";
        orientationNewSyntax = "";
        break;
      }
    }
    setStyleProperty(el, "WebkitBoxOrient", orientationOldSyntax);
    setStyleProperty(el, "WebkitBoxDirection", reverse ? "reverse" : "normal");
    setStyleProperty(el, "WebkitFlexDirection", orientationNewSyntax);
  }

  @Override
  public void _setJustificationProperty(Element el, Justification justification) {
    String justificationOldSyntax, justificationNewSyntax;
    switch (justification) {
      case START: {
        justificationOldSyntax = "start";
        justificationNewSyntax = "flex-start";
        break;
      }
      case END: {
        justificationOldSyntax = "end";
        justificationNewSyntax = "flex-end";
        break;
      }
      case CENTER: {
        justificationOldSyntax = "center";
        justificationNewSyntax = "center";
        break;
      }
      case SPACE_AROUND: {
        justificationOldSyntax = ""; // not implemented
        justificationNewSyntax = "space-around";
        break;
      }
      case SPACE_BETWEEN: {
        justificationOldSyntax = "justify";
        justificationNewSyntax = "space-between";
        break;
      }
      default: {
        justificationOldSyntax = "";
        justificationNewSyntax = "";
      }
    }
    setStyleProperty(el, "WebkitBoxPack", justificationOldSyntax);
    setStyleProperty(el, "WebkitJustifyContent", justificationNewSyntax);
  }

  @Override
  protected void _setAlignmentSelfProperty(Element el, AlignmentSelf alignmentSelf)
  {
    String value;
    switch (alignmentSelf) {
      case START: {
        value = "flex-start";
        break;
      }
      case END: {
        value = "flex-end";
        break;
      }
      case CENTER: {
        value = "center";
        break;
      }
      case STRETCH: {
        value = "stretch";
        break;
      }
      case BASELINE: {
        value = "baseline";
        break;
      }
      default: {
        value = "auto";
      }
    }
    setStyleProperty(el, "WebkitAlignSelf", value);
  }

  @Override
  protected void _setFlexWrapProperty(Element el, FlexWrap flexWrap)
  {
    String flexWrapOldSyntax, flexWrapNewSyntax;
    switch (flexWrap) {
      case NOWRAP: {
        flexWrapOldSyntax = "single";
        flexWrapNewSyntax = "nowrap";
        break;
      }
      case WRAP: {
        flexWrapOldSyntax = "multiple";
        flexWrapNewSyntax = "wrap";
        break;
      }
      case WRAP_REVERSE: {
        flexWrapOldSyntax = "multiple";
        flexWrapNewSyntax = "wrap";
        break;
      }
      default: {
        flexWrapOldSyntax = "single";
        flexWrapNewSyntax = "nowrap";
        break;
      }
    }
    setStyleProperty(el, "WebkitBoxLines", flexWrapOldSyntax);
    setStyleProperty(el, "WebkitFlexWrap", flexWrapNewSyntax);
  }

  @Override
  public void _setFlex(Element el, double grow, String basis) {
    setStyleProperty(el,"WebkitBoxFlex", Double.toString(grow));
    setStyleProperty(el,"WebkitFlex", Double.toString(grow)+(basis == null ? "0%" : basis));
  }

  @Override
  protected void _setFlex(Element el, double grow, double shrink, String basis) {
    setStyleProperty(el,"WebkitBoxFlex", Double.toString(grow)); // shrink and basis not supported
    setStyleProperty(el,"WebkitFlex", Double.toString(grow)+" "+Double.toString(shrink)+" "+(basis == null ? "0%" : basis));
  }

  @Override
  public void _setFlexOrder(Element el, int order) {
    setStyleProperty(el,"WebkitBoxOrdinalGroup", Integer.toString(order));
    setStyleProperty(el,"WebkitOrder", Integer.toString(order));
  }


  @Override
  protected void _setElementAsFlexContainer(Element el, Orientation orientation) {
    setStyleProperty(el,"display", "-webkit-box");
    setStyleProperty(el,"display", "-webkit-flex");
    _setOrientationProperty(el,orientation);
  }

}
