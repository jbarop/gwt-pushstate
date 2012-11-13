/*
 * Copyright 2012 Johannes Barop
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package de.barop.gwt.client;

import java.util.logging.Logger;

import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.impl.HistoryImpl;

/**
 * Extends GWT's {@link HistoryImpl} and adds HTML5 pushState support.
 * 
 * <p>
 * The complete path is treated as history token.
 * </p>
 * 
 * <p>
 * The leading '/' is hidden from GWTs History API, so that the path '/' is returned as an empty
 * history token ('').
 * </p>
 * 
 * @author <a href="mailto:jb@barop.de">Johannes Barop</a>
 * 
 */
public class HistoryImplPushState extends HistoryImpl {

  private static final Logger LOG = Logger.getLogger(HistoryImplPushState.class.getName());

  @Override
  public boolean init() {
    retrieveHistoryToken(); // initialize HistoryImpl with the current path
    nativeUpdate(getToken()); // initialize the empty state with the current history token
    initPopStateHandler();

    return true;
  }

  @Override
  protected void nativeUpdate(final String historyToken) {
    String newPushStateToken = CodeServerParameterHelper.append(encodeFragment(historyToken));
    if (!newPushStateToken.startsWith("/")) {
      newPushStateToken = "/" + newPushStateToken;
    }

    pushState(newPushStateToken);
    if (LogConfiguration.loggingIsEnabled()) {
      LOG.fine("Pushed '" + newPushStateToken + "' (" + historyToken + ")");
    }
  }

  /**
   * Set the current path as GWT History token which can later retrieved with
   * {@link History#getToken()}.
   */
  private void retrieveHistoryToken() {
    String token = Window.Location.getPath();
    token = (token.length() > 0) ? decodeFragment(token) : "";
    token = (token.startsWith("/")) ? token.substring(1) : token;

    String queryString = CodeServerParameterHelper.remove(Window.Location.getQueryString());
    if (queryString != null && !queryString.trim().isEmpty() && !queryString.equals("?")) {
      token += queryString;
    }

    if (LogConfiguration.loggingIsEnabled()) {
      LOG.fine("Set token to '" + token + "'");
    }
    setToken(token);
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
   * Called from native JavaScript when an old history state was popped.
   */
  private void onPopState(final String historyToken) {
    if (LogConfiguration.loggingIsEnabled()) {
      LOG.fine("Popped '" + historyToken + "'");
    }
    retrieveHistoryToken();
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
