/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.user.client.impl;

import com.google.gwt.core.client.JavaScriptObject;
import com.googlecode.mgwt.ui.client.MGWT;


/**
 * IE10 implementation of {@link com.google.gwt.user.client.impl.DOMImplStandard}.
 */
public class DOMImplIE10 extends DOMImplIE9 {
  
  static
  {
    DOMImplStandard.addCaptureEventDispatchers(getCaptureEventDispatchers());
    DOMImplStandard.addBitlessEventDispatchers(getBitlessEventDispatchers());
    if (MGWT.getOsDetection().isWindowsPhone()) {
      capturePointerEvents();
    }
    else {
      // for desktop we need a focus fix
      capturePointerEventsWithFocusFix();
    }
  }
  
  /**
   * Lets have the same behaviour as IOS where the target element continues to receive Pointer events
   * even when the pointer has moved off the element up until MSPointerUp has occurred. 
   */
  private native static void capturePointerEvents() /*-{
    $wnd.addEventListener('MSPointerDown',
      function(evt) {
        evt.target.msSetPointerCapture(evt.pointerId);
      }, true);
  }-*/;

  /**
   * On desktop, for some reason when you do pointer capture input elements fail to get the focus
   */
  private native static void capturePointerEventsWithFocusFix() /*-{
    var getFocus = function(evt) {
      this.focus()
    };
    $wnd.addEventListener('MSPointerDown',
      function(evt) {
        evt.target.msSetPointerCapture(evt.pointerId);
        if (evt.target.focus) {
          evt.target.addEventListener('MSPointerUp',getFocus);
        }
      }, true);
  }-*/;
  
  public static native JavaScriptObject getCaptureEventDispatchers() /*-{
    return {
      MSPointerDown:   @com.google.gwt.user.client.impl.DOMImplStandard::dispatchCapturedMouseEvent(*),
      MSPointerUp:     @com.google.gwt.user.client.impl.DOMImplStandard::dispatchCapturedMouseEvent(*),
      MSPointerMove:   @com.google.gwt.user.client.impl.DOMImplStandard::dispatchCapturedMouseEvent(*),
      MSPointerCancel: @com.google.gwt.user.client.impl.DOMImplStandard::dispatchCapturedMouseEvent(*)
    };
  }-*/;

  public static native JavaScriptObject getBitlessEventDispatchers() /*-{
    return {
      MSPointerDown:   @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent(*),
      MSPointerUp:     @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent(*),
      MSPointerMove:   @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent(*),
      MSPointerCancel: @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent(*)
    };
  }-*/;

}
