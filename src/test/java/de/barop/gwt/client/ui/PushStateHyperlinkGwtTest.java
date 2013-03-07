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

import de.barop.gwt.client.AbstractPushStateTest;

/**
 * Tests {@link HyperlinkPushState} and {@link InlineHyperlinkPushState} when
 * the browser does support pushState.
 * 
 * @author <a href="mailto:jb@barop.de">Johannes Barop</a>
 * 
 */
public class PushStateHyperlinkGwtTest extends AbstractPushStateTest {

  public void testDereferedBinding() {
    assertTrue(GWT.create(Hyperlink.class) instanceof HyperlinkPushState);
    assertTrue(GWT.create(InlineHyperlink.class) instanceof InlineHyperlinkPushState);
  }

  public void testHyperlink() {
    // Given
    String text = "a link";
    String historyToken = "historyToken";

    // When
    Hyperlink noSlashHyperlink = new HyperlinkPushState(text, historyToken);
    Hyperlink slashHyperlinkSlash = new HyperlinkPushState(text, "/" + historyToken);

    // Then
    String[] noSlashLinkHref = ((Element) noSlashHyperlink.getElement().getChild(0)).getAttribute(
        "href").split("\\?");
    String noSlashPath = noSlashLinkHref[0];
    String noSlashQueryString = noSlashLinkHref[1];

    String[] slashLinkHref = ((Element) slashHyperlinkSlash.getElement().getChild(0)).getAttribute(
        "href").split("\\?");
    String slashPath = slashLinkHref[0];
    String slashQueryString = slashLinkHref[1];

    assertEquals(historyToken, noSlashHyperlink.getTargetHistoryToken());
    assertEquals("/" + historyToken, noSlashPath);
    assertTrue(noSlashQueryString.matches("gwt\\.codesvr=[^&]*"));

    assertEquals("/" + historyToken, slashHyperlinkSlash.getTargetHistoryToken());
    assertEquals("/" + historyToken, slashPath);
    assertTrue(slashQueryString.matches("gwt\\.codesvr=[^&]*"));
  }

  public void testInlineHyperlink() {
    // Given
    String text = "a link";
    String historyToken = "historyToken";

    // When
    Hyperlink noSlashHyperlink = new InlineHyperlinkPushState(text, historyToken);
    Hyperlink slashHyperlinkSlash = new InlineHyperlinkPushState(text, "/" + historyToken);

    // Then
    String[] noSlashLinkHref = ((Element) noSlashHyperlink.getElement()).getAttribute("href").split(
        "\\?");
    String noSlashPath = noSlashLinkHref[0];
    String noSlashQueryString = noSlashLinkHref[1];

    String[] slashLinkHref = ((Element) slashHyperlinkSlash.getElement()).getAttribute("href").split(
        "\\?");
    String slashPath = slashLinkHref[0];
    String slashQueryString = slashLinkHref[1];

    assertEquals(historyToken, noSlashHyperlink.getTargetHistoryToken());
    assertEquals("/" + historyToken, noSlashPath);
    assertTrue(noSlashQueryString.matches("gwt\\.codesvr=[^&]*"));

    assertEquals("/" + historyToken, slashHyperlinkSlash.getTargetHistoryToken());
    assertEquals("/" + historyToken, slashPath);
    assertTrue(slashQueryString.matches("gwt\\.codesvr=[^&]*"));
  }

}
