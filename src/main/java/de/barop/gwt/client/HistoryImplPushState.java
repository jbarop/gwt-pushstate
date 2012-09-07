package de.barop.gwt.client;

import java.util.logging.Logger;

import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.impl.HistoryImpl;

/**
 * Extends GWT's {@link HistoryImpl} and adds HTML5 pushState support.
 * 
 * @author <a href="mailto:jb@barop.de">Johannes Barop</a>
 * 
 */
public class HistoryImplPushState extends HistoryImpl {

  private static final Logger LOG = Logger.getLogger(HistoryImplPushState.class.getName());

  @Override
  public boolean init() {
    setTokenFromPath(); // initialize HistoryImpl with the current path
    nativeUpdate(getToken()); // initialize the empty state with the current history token
    initPopStateHandler();

    return true;
  }

  @Override
  protected void nativeUpdate(final String historyToken) {
    pushState(CodeServerParameterHelper.append(encodeFragment(historyToken)));
    if (LogConfiguration.loggingIsEnabled()) {
      LOG.fine("Pushed '" + historyToken + "'");
    }
  }

  /**
   * Set the current path as token.
   */
  private void setTokenFromPath() {
    String token = Window.Location.getPath();
    String queryString = CodeServerParameterHelper.remove(Window.Location.getQueryString());

    if (queryString != null && !queryString.trim().isEmpty() && !queryString.equals("?")) {
      token += queryString;
    }

    String newToken = (token.length() > 0) ? decodeFragment(token) : "";
    if (LogConfiguration.loggingIsEnabled()) {
      LOG.fine("Set token to '" + newToken + "'");
    }
    setToken(newToken);
  }

  /**
   * Initialize an event handler that gets executed when the token changes.
   */
  private native void initPopStateHandler() /*-{
    var that = this;
    var oldHandler = $wnd.onpopstate;
    $wnd.onpopstate = $entry(function(e) {
      if (e.state && e.state.historyToken) {
        that.@de.barop.gwt.client.HistoryImplPushState::onPopState(Ljava/lang/String;)(e.state.historyToken);
      }
      if (oldHandler) {
        oldHandler();
      }
    });
  }-*/;

  /**
   * Called from native code when an old history state was popped.
   */
  private void onPopState(final String historyToken) {
    if (LogConfiguration.loggingIsEnabled()) {
      LOG.fine("Popped '" + historyToken + "'");
    }
    setTokenFromPath();
    fireHistoryChangedImpl(getToken());
  }

  /**
   * Add the given token to the history using pushState.
   */
  private static native void pushState(final String token) /*-{
    var state = {
      historyToken : token
    };
    $wnd.history.pushState(state, $doc.title, token);
  }-*/;

}
