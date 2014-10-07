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


import static com.google.gwt.user.client.Window.Location.getQueryString;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;

/**
 * 
 * @author <a href="mailto:jb@barop.de">Johannes Barop</a>
 * 
 */
public class HistoryImplPushStateGwtTest extends AbstractPushStateTest {

  String startPath;

  @Override
  protected void gwtSetUp() throws Exception {
    super.gwtSetUp();
    startPath = Window.Location.getPath() + getQueryString();
  }

  public void testInit() {
    assertEquals(statesOnTestStart, states.size());
    assertEquals(startPath, states.peek().url);
    // remove the query string with the codeserver
    String expectedHistoryToken = states.peek().url.substring(1).split("\\?")[0];
    assertEquals(expectedHistoryToken, History.getToken());
  }

  public void testOnPopState() {
    History.newItem("historyToken_1");
    assertEquals(statesOnTestStart + 1, states.size());
    assertEquals("/historyToken_1" + getQueryString(), states.peek().url);
    assertEquals("historyToken_1", History.getToken());

    History.newItem("historyToken_2");
    assertEquals(statesOnTestStart + 2, states.size());
    assertEquals("/historyToken_2" + getQueryString(), states.peek().url);
    assertEquals("historyToken_2", History.getToken());

    popState();

    History.newItem("historyToken_1");
    assertEquals(statesOnTestStart + 1, states.size());
    assertEquals("/historyToken_1" + getQueryString(), states.peek().url);
    assertEquals("historyToken_1", History.getToken());
  }

}
