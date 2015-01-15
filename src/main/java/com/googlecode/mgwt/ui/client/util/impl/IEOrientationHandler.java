package com.googlecode.mgwt.ui.client.util.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.dom.client.event.orientation.OrientationChangeEvent;
import com.googlecode.mgwt.dom.client.event.orientation.OrientationChangeEvent.ORIENTATION;
import com.googlecode.mgwt.ui.client.util.OrientationHandler;
import com.googlecode.mgwt.ui.client.widget.main.MainResourceAppearance.UtilCss;
import com.googlecode.mgwt.ui.client.widget.main.MainResourceHolder;

/**
 * IE11 on windows 8 or windows phone 8.1 supports orientation events but via
 * the Screen object, IE10 on windows phone 8 does not support orientation events.
 * IE10 on windows phone/desktop does support resize events but they do not appear to
 * fire on wp8 when the viewport is set to device-width. We fallback to resize events anyhow.
 */
public class IEOrientationHandler implements OrientationHandler {
	private static native JavaScriptObject setupOrientation0(
  		IEOrientationHandler handler)/*-{
  	var func = $entry(function(evt) {
  		handler.@com.googlecode.mgwt.ui.client.util.impl.IEOrientationHandler::onorientationChange(Ljava/lang/String;)(evt.target.msOrientation);
  	});
  	$wnd.screen.onmsorientationchange = func;
  	return func;
  }-*/;

  private static native void destroyOrientation(JavaScriptObject o)/*-{
  	$wnd.screen.onmsorientationchange = null;
  }-*/;

  // update styles on body
  	private static void setClasses(ORIENTATION o) {

  	  UtilCss utilCss = MainResourceHolder.getUtilCss();
  		switch (o) {

  		case PORTRAIT:
  			Document.get().getBody().addClassName(utilCss.portrait());
  			Document.get().getBody().removeClassName(utilCss.landscape());
  			break;
  		case LANDSCAPE:
  			Document.get().getBody().addClassName(utilCss.landscape());
  			Document.get().getBody().removeClassName(utilCss.portrait());
  			break;

  		default:
  			break;
  		}
  	}

  protected static ORIENTATION currentOrientation;
	protected static boolean orientationInitialized;
	protected JavaScriptObject nativeJsFunction;

	private EventBus manager;

  @Override
	public final void maybeSetupOrientation(EventBus manager) {
		this.manager = manager;
		if (orientationInitialized)
			return;
		if (!GWT.isClient()) {
			return;
		}
		doSetupOrientation();
		orientationInitialized = true;
		setClasses(getOrientation());
	}

	protected void setupNativeBrowerOrientationHandler() {
	  Window.alert("Settting up native browser orientation handler");
		nativeJsFunction = setupOrientation0(this);
		Window.addCloseHandler(new CloseHandler<Window>() {

			@Override
			public void onClose(CloseEvent<Window> event) {
				destroyOrientation(nativeJsFunction);
			}
		});
	}

	protected static native String getOrientation0()/*-{
		if (typeof ($wnd.screen.msOrientation) == 'undefined') {
			return "portrait-primary";
		}
		return $wnd.screen.msOrientation;
	}-*/;

  protected static ORIENTATION getBrowserOrientation() {
    String orientation = getOrientation0();

    ORIENTATION o;
    if ("landscape-primary".equals(orientation) || "landscape-secondary".equals(orientation)) {
      o = ORIENTATION.LANDSCAPE;
    }
    else {
      o = ORIENTATION.PORTRAIT;
    }
    return o;
  }

  void fireOrientationChangedEvent(ORIENTATION orientation) {
  	setClasses(orientation);
  	manager.fireEvent(new OrientationChangeEvent(orientation));
  }

  private void onorientationChange(String orientation) {
    ORIENTATION o;
    if ("landscape-primary".equals(orientation) || "landscape-secondary".equals(orientation)) {
      o = ORIENTATION.LANDSCAPE;
    }
    else {
      o = ORIENTATION.PORTRAIT;
    }
  	currentOrientation = o;
  	fireOrientationChangedEvent(o);
  }
  
  public void doSetupOrientation() {

    if (!orientationSupported()) {
      Window.addResizeHandler(new ResizeHandler() {

        @Override
        public void onResize(ResizeEvent event) {
          ORIENTATION orientation = getOrientation();
          if (orientation != currentOrientation) {
            currentOrientation = orientation;
            fireOrientationChangedEvent(orientation);
          }
        }
      });
    } else {
      setupNativeBrowerOrientationHandler();
    }

  }

  /**
   * Get the current orientation of the device
   *
   * @return the current orientation of the device
   */
  public ORIENTATION getOrientation() {
    if (!orientationSupported()) {
      int height = Window.getClientHeight();
      int width = Window.getClientWidth();

      if (width > height) {
        return ORIENTATION.LANDSCAPE;
      } else {
        return ORIENTATION.PORTRAIT;
      }
    } else {
      return getBrowserOrientation();
    }
  }

  private static native boolean orientationSupported() /*-{
    return "msOrientation" in $wnd.screen;
  }-*/;

}
