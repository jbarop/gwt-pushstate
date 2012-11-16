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

import static com.google.gwt.user.client.Window.Location.getHost;
import static com.google.gwt.user.client.Window.Location.getHref;
import static com.google.gwt.user.client.Window.Location.getPath;
import static com.google.gwt.user.client.Window.Location.getQueryString;

import com.google.gwt.core.client.GWT;

/**
 * Test the {@link HistoryConverter} for browsers without pushState support.
 * 
 * @author <a href="mailto:jb@barop.de">Johannes Barop</a>
 * 
 */
public class HistoryConverterHashGwtTest extends AbstractNoPushStateTest {

  private HistoryConverter historyConverter;

  @Override
  protected void gwtSetUp() throws Exception {
    super.gwtSetUp();

    historyConverter = GWT.create(HistoryConverter.class);
  }

  public void testConvertHistory() {
    String path = getPath().substring(1);
    historyConverter.convertHistoryToken();
    assertEquals("http://" + getHost() + "/" + getQueryString() + "#" + path, getHref());
  }

}
