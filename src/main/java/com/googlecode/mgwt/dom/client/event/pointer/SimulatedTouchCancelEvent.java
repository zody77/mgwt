package com.googlecode.mgwt.dom.client.event.pointer;

import com.google.gwt.event.dom.client.TouchCancelEvent;

public class SimulatedTouchCancelEvent extends TouchCancelEvent
{
  public SimulatedTouchCancelEvent(MsPointerCancelEvent event) {
    setNativeEvent(event.getNativeEvent());
    setSource(event.getSource());
  }

}
