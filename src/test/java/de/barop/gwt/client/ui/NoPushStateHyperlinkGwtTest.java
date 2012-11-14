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

package de.barop.gwt.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.InlineHyperlink;

import de.barop.gwt.client.AbstractNoPushStateTest;

/**
 * Tests {@link HyperlinkPushState} and {@link InlineHyperlinkPushState} when the browser does not
 * support pushState.
 * 
 * @author <a href="mailto:jb@barop.de">Johannes Barop</a>
 * 
 */
public class NoPushStateHyperlinkGwtTest extends AbstractNoPushStateTest {

  public void testDereferedBinding() {
    assertTrue(GWT.create(Hyperlink.class) instanceof HyperlinkPushState);
    assertTrue(GWT.create(InlineHyperlink.class) instanceof InlineHyperlinkPushState);
  }

  public void testHyperlink() {
    Hyperlink hyperlink = new HyperlinkPushState("link", "linkToken");
    assertEquals("linkToken", hyperlink.getTargetHistoryToken());

    String[] linkHref = ((Element) hyperlink.getElement().getChild(0)).getAttribute("href").split("\\?");
    String path = linkHref[0];
    String queryString = linkHref[1];

    assertEquals("/linkToken", path);
    assertTrue(queryString.matches("gwt\\.codesvr=[^&]*"));
  }

  public void testInlineHyperlink() {
    Hyperlink hyperlink = new InlineHyperlinkPushState("link", "linkToken");
    assertEquals("linkToken", hyperlink.getTargetHistoryToken());

    String[] linkHref = ((Element) hyperlink.getElement()).getAttribute("href").split("\\?");
    String path = linkHref[0];
    String queryString = linkHref[1];

    assertEquals("/linkToken", path);
    assertTrue(queryString.matches("gwt\\.codesvr=[^&]*"));
  }

}
