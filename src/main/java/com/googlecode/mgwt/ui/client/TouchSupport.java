package com.googlecode.mgwt.ui.client;

import com.google.gwt.core.shared.GWT;

public abstract class TouchSupport {
  
  private static TouchSupport impl = GWT.create(TouchSupport.class);

  protected abstract boolean _isTouchEventsEmulatedUsingMouseEvents();

  protected abstract boolean _isTouchEventsEmulatedUsingPointerEvents();

  protected abstract boolean _isTouchEventsSupported();
  
  public static boolean isTouchEventsEmulatedUsingMouseEvents() {
    return impl._isTouchEventsEmulatedUsingMouseEvents();
  }

  public static boolean isTouchEventsEmulatedUsingPointerEvents() {
    return impl._isTouchEventsEmulatedUsingPointerEvents();
  }

  public static boolean isTouchEventsSupported() {
    return impl._isTouchEventsSupported();
  }
  
  public static class TouchSupportStandard extends TouchSupport {

    private static boolean hasTouchSupport;
    private static TouchSupport delegate;
    
    static {
      hasTouchSupport = hasTouch();
      if (hasTouchSupport) {
        delegate = new TouchSupportNative();
      }
    }

    private static native boolean hasTouch() /*-{
      return 'ontouchstart' in $doc.documentElement;
    }-*/;


    @Override
    protected boolean _isTouchEventsEmulatedUsingMouseEvents() {
      if (hasTouchSupport) {
        return delegate._isTouchEventsEmulatedUsingMouseEvents();
      }
      return true;
    }

    @Override
    protected boolean _isTouchEventsEmulatedUsingPointerEvents() {
      return false;
    }

    @Override
    protected boolean _isTouchEventsSupported() {
      if (hasTouchSupport) {
        return delegate._isTouchEventsSupported();
      }
      return false;
    }
  }

  public static class TouchSupportEmulatedPointer extends TouchSupport {
    @Override
    protected boolean _isTouchEventsEmulatedUsingMouseEvents() {
      return false;
    }

    @Override
    protected boolean _isTouchEventsEmulatedUsingPointerEvents() {
      return true;
    }

    @Override
    protected boolean _isTouchEventsSupported() {
      return false;
    }
  }

  public static class TouchSupportNative extends TouchSupport {
    @Override
    protected boolean _isTouchEventsEmulatedUsingMouseEvents() {
      return false;
    }

    @Override
    protected boolean _isTouchEventsEmulatedUsingPointerEvents() {
      return false;
    }

    @Override
    protected boolean _isTouchEventsSupported() {
      return true;
    }
  }

}
