package com.googlecode.mgwt.ui.client.widget.panel.flex;

import com.google.gwt.dom.client.Element;

/**
 * Unbelievable - IE10 does not obey the camel case rule correctly
 * @author pfrench
 *
 */
public class FlexPropertyHelperIE10 extends FlexPropertyHelper {

  @Override
  public void _setAlignmentProperty(Element el, Alignment alignment) {
    String value;
    switch (alignment) {
      case START: {
        value = "start";
        break;
      }
      case END: {
        value = "end";
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
        value = "";
      }
    }
    setStyleProperty(el, "msFlexAlign", value);
  }

  @Override
  public void _setOrientationProperty(Element el, Orientation orientation) {
    String value;
    switch (orientation) {
      case HORIZONTAL: {
        value = "row";
        break;
      }
      case VERTICAL: {
        value = "column";
        break;
      }
      default: {
        value = "";
        break;
      }
    }
    setStyleProperty(el, "msFlexDirection", value);
  }

  @Override
  public void _setJustificationProperty(Element el, Justification justification) {
    String value;
    switch (justification) {
      case START: {
        value = "start";
        break;
      }
      case END: {
        value = "end";
        break;
      }
      case CENTER: {
        value = "center";
        break;
      }
      case SPACE_AROUND: {
        value = "distribute";
        break;
      }
      case SPACE_BETWEEN: {
        value = "justify";
        break;
      }
      default: {
        value = "";
      }
    }
    setStyleProperty(el, "msFlexPack", value);
  }

  @Override
  protected void _setAlignmentSelfProperty(Element el, AlignmentSelf alignmentSelf)
  {
    String value;
    switch (alignmentSelf) {
      case START: {
        value = "start";
        break;
      }
      case END: {
        value = "end";
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
    setStyleProperty(el, "msFlexItemAlign", value);
  }

  @Override
  protected void _setFlex(Element el, double grow, String basis) {
    setStyleProperty(el,"msFlex", Double.toString(grow)+" "+(basis == null ? "0%" : basis));
  }

  @Override
  protected void _setFlex(Element el, double grow, double shrink, String basis) {
    setStyleProperty(el,"msFlex", Double.toString(grow)+" "+Double.toString(shrink)+" "+(basis == null ? "0%" : basis));
  }

  @Override
  public void _setFlexOrder(Element el, int order) {
    setStyleProperty(el,"msFlexOrder", Integer.toString(order));
  }

  @Override
  protected void _setElementAsFlexContainer(Element el, Orientation orientation) {
    setStyleProperty(el,"display", "-ms-flexbox");
    _setOrientationProperty(el,orientation);
  }

}
