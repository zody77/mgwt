package com.googlecode.mgwt.ui.client.widget.touch;

import com.google.gwt.event.dom.client.TouchCancelHandler;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.mouse.HandlerRegistrationCollection;
import com.googlecode.mgwt.dom.client.event.pointer.MsPointerCancelEvent;
import com.googlecode.mgwt.dom.client.event.pointer.MsPointerDownEvent;
import com.googlecode.mgwt.dom.client.event.pointer.MsPointerMoveEvent;
import com.googlecode.mgwt.dom.client.event.pointer.MsPointerUpEvent;
import com.googlecode.mgwt.dom.client.event.pointer.TouchCancelToMsPointerCancelHandler;
import com.googlecode.mgwt.dom.client.event.pointer.TouchEndToMsPointerUpHandler;
import com.googlecode.mgwt.dom.client.event.pointer.TouchMoveToMsPointerMoveHandler;
import com.googlecode.mgwt.dom.client.event.pointer.TouchStartToMsPointerDownHandler;
import com.googlecode.mgwt.dom.client.event.touch.TouchHandler;

public class TouchWidgetPointerImpl implements TouchWidgetImpl
{
  @Override
  public HandlerRegistration addTouchStartHandler(Widget w, TouchStartHandler handler) {
    return w.addBitlessDomHandler(new TouchStartToMsPointerDownHandler(handler), MsPointerDownEvent.getType());
  }

  @Override
  public HandlerRegistration addTouchMoveHandler(Widget w, TouchMoveHandler handler) {
    TouchMoveToMsPointerMoveHandler touchMoveToMsPointerMoveHandler = new TouchMoveToMsPointerMoveHandler(handler);
    HandlerRegistrationCollection handlerRegistrationCollection = new HandlerRegistrationCollection();
    handlerRegistrationCollection.addHandlerRegistration(w.addBitlessDomHandler(touchMoveToMsPointerMoveHandler, MsPointerDownEvent.getType()));
    handlerRegistrationCollection.addHandlerRegistration(w.addBitlessDomHandler(touchMoveToMsPointerMoveHandler, MsPointerUpEvent.getType()));
    handlerRegistrationCollection.addHandlerRegistration(w.addBitlessDomHandler(touchMoveToMsPointerMoveHandler, MsPointerMoveEvent.getType()));
    return handlerRegistrationCollection;
  }

  @Override
  public HandlerRegistration addTouchCancelHandler(Widget w, TouchCancelHandler handler) {
    return w.addBitlessDomHandler(new TouchCancelToMsPointerCancelHandler(handler), MsPointerCancelEvent.getType());
  }

  @Override
  public HandlerRegistration addTouchEndHandler(Widget w, TouchEndHandler handler) {
    return w.addBitlessDomHandler(new TouchEndToMsPointerUpHandler(handler), MsPointerUpEvent.getType());
  }

  @Override
  public HandlerRegistration addTouchHandler(Widget w, TouchHandler handler) {
    HandlerRegistrationCollection hrc = new HandlerRegistrationCollection();
    hrc.addHandlerRegistration(addTouchStartHandler(w, handler));
    hrc.addHandlerRegistration(addTouchMoveHandler(w, handler));
    hrc.addHandlerRegistration(addTouchEndHandler(w, handler));
    hrc.addHandlerRegistration(addTouchCancelHandler(w, handler));
    return hrc;
  }

}
