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

import java.util.Stack;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.ExporterUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.History;

/**
 * Base class for GWT unit tests with mocked pushState support.
 * 
 * <p>
 * The embedded HtmlUnit FF3 browser does not have pushState support. In order to unit test it
 * easily the pushState API is mocked.
 * </p>
 * 
 * @author <a href="mailto:jb@barop.de">Johannes Barop</a>
 * 
 */
public abstract class AbstractPushStateTest extends GWTTestCase {

  /**
   * A pushed history state.
   */
  static class State {

    final JavaScriptObject data;
    final String title;
    final String url;

    State(final JavaScriptObject data, final String title, final String url) {
      this.data = data;
      this.title = title;
      this.url = url;
    }

    @Override
    public String toString() {
      return "State [data=" + data + ", title=" + title + ", url=" + url + "]";
    }

  }

  /**
   * The pushState implementation which gets exported to JavaScript.
   */
  public static class PushStateApi implements Exportable {

    @Export("$wnd.history.pushState")
    public static void pushState(final JavaScriptObject data, final String title, final String url) {
      states.push(new State(data, title, url));
    }

  }

  /**
   * All pushed states.
   */
  protected static Stack<State> states;

  /**
   * The number of the items in {@link #states} at test start.
   */
  protected int statesOnTestStart;

  @Override
  public String getModuleName() {
    return "de.barop.gwt.PushStateTest";
  }

  @Override
  protected void gwtSetUp() throws Exception {
    super.gwtSetUp();

    if (states == null) {
      ExporterUtil.exportAll();
      states = new Stack<State>();
      GWT.create(History.class);
    }

    statesOnTestStart = states.size();
  }

  /**
   * Set's the hash part of the browsers address bar.
   */
  protected native void setHash(String hash) /*-{
    $wnd.location.hash = hash;
  }-*/;

  /**
   * Pop the previous history state.
   */
  protected void popState() {
    assert states.size() > 1;
    states.pop(); // go back
    callOnPopState(states.peek().data); // pop the current state
  }

  /**
   * Native JavaScript which calls the onpopstate handler with the given state.
   */
  private native void callOnPopState(JavaScriptObject data) /*-{
    var event = {
      state : data
    };
    
    $wnd.onpopstate(event);
  }-*/;

  /**
   * Assert that the test runs with pushState support.
   */
  public void testNoPushStateSupport() {
    assertTrue(GWT.create(HistoryConverter.class) instanceof HistoryConverterPushState);
  }

}
