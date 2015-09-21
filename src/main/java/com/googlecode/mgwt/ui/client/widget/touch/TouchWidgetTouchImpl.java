package com.googlecode.mgwt.ui.client.widget.touch;

import com.google.gwt.event.dom.client.TouchCancelEvent;
import com.google.gwt.event.dom.client.TouchCancelHandler;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.mouse.HandlerRegistrationCollection;
import com.googlecode.mgwt.dom.client.event.touch.TouchHandler;

public class TouchWidgetTouchImpl implements TouchWidgetImpl
{
  @Override
  public HandlerRegistration addTouchStartHandler(Widget w, TouchStartHandler handler) {
    return w.addDomHandler(handler, TouchStartEvent.getType());
  }

  @Override
  public HandlerRegistration addTouchMoveHandler(Widget w, TouchMoveHandler handler) {
    return w.addDomHandler(handler, TouchMoveEvent.getType());
  }

  @Override
  public HandlerRegistration addTouchCancelHandler(Widget w, TouchCancelHandler handler) {
    return w.addDomHandler(handler, TouchCancelEvent.getType());
  }

  @Override
  public HandlerRegistration addTouchEndHandler(Widget w, TouchEndHandler handler) {
    return w.addDomHandler(handler, TouchEndEvent.getType());
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
