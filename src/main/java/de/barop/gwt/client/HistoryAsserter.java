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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

/**
 * Helper class which asserts during startup that the history token is suitable for the current
 * browser and converts it if needed.
 * 
 * <p>
 * This is needed when a browser without HTML5 pushState support is opening a pushState URL and vice
 * versa.
 * </p>
 * 
 * @author <a href="mailto:jb@barop.de">Johannes Barop</a>
 * 
 */
public class HistoryAsserter implements EntryPoint {

  @Override
  public void onModuleLoad() {
    HistoryConverter converter = GWT.create(HistoryConverter.class);
    converter.convertHistoryToken();
  }

}
