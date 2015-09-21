package com.googlecode.mgwt.ui.client.widget.touch;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.TouchCancelHandler;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.mouse.HandlerRegistrationCollection;
import com.googlecode.mgwt.dom.client.event.mouse.TouchEndToMouseUpHandler;
import com.googlecode.mgwt.dom.client.event.mouse.TouchMoveToMouseMoveHandler;
import com.googlecode.mgwt.dom.client.event.mouse.TouchStartToMouseDownHandler;
import com.googlecode.mgwt.dom.client.event.touch.TouchHandler;
import com.googlecode.mgwt.ui.client.util.NoopHandlerRegistration;

public class TouchWidgetStandardImpl implements TouchWidgetImpl
{
  private static boolean hasTouchSupport;
  private static TouchWidgetImpl delegate;

  static {
    hasTouchSupport = hasTouch();
    if (hasTouchSupport) {
      delegate = new TouchWidgetTouchImpl();
    }
  }

  private static native boolean hasTouch() /*-{
    return 'ontouchstart' in $doc.documentElement;
  }-*/;


  @Override
  public HandlerRegistration addTouchStartHandler(Widget w, TouchStartHandler handler) {
    if (hasTouchSupport) {
      return delegate.addTouchStartHandler(w, handler);
    }
    return w.addDomHandler(new TouchStartToMouseDownHandler(handler), MouseDownEvent.getType());
  }

  @Override
  public HandlerRegistration addTouchMoveHandler(Widget w, TouchMoveHandler handler) {
    if (hasTouchSupport) {
      return delegate.addTouchMoveHandler(w, handler);
    }
    TouchMoveToMouseMoveHandler touchMoveToMouseMoveHandler = new TouchMoveToMouseMoveHandler(handler);
    HandlerRegistrationCollection handlerRegistrationCollection = new HandlerRegistrationCollection();
    handlerRegistrationCollection.addHandlerRegistration(w.addDomHandler(touchMoveToMouseMoveHandler, MouseDownEvent.getType()));
    handlerRegistrationCollection.addHandlerRegistration(w.addDomHandler(touchMoveToMouseMoveHandler, MouseUpEvent.getType()));
    handlerRegistrationCollection.addHandlerRegistration(w.addDomHandler(touchMoveToMouseMoveHandler, MouseMoveEvent.getType()));
    return handlerRegistrationCollection;
  }

  @Override
  public HandlerRegistration addTouchCancelHandler(Widget w, TouchCancelHandler handler) {
    if (hasTouchSupport) {
      return delegate.addTouchCancelHandler(w, handler);
    }
    return new NoopHandlerRegistration();
  }

  @Override
  public HandlerRegistration addTouchEndHandler(Widget w, TouchEndHandler handler) {
    if (hasTouchSupport) {
      return delegate.addTouchEndHandler(w, handler);
    }
    return w.addDomHandler(new TouchEndToMouseUpHandler(handler), MouseUpEvent.getType());
  }

  @Override
  public HandlerRegistration addTouchHandler(Widget w, TouchHandler handler) {
    if (hasTouchSupport) {
      return delegate.addTouchHandler(w, handler);
    }
    HandlerRegistrationCollection hrc = new HandlerRegistrationCollection();
    hrc.addHandlerRegistration(addTouchStartHandler(w, handler));
    hrc.addHandlerRegistration(addTouchMoveHandler(w, handler));
    hrc.addHandlerRegistration(addTouchEndHandler(w, handler));
    hrc.addHandlerRegistration(addTouchCancelHandler(w, handler));
    return hrc;
  }

}
