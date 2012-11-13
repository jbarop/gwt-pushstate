package de.barop.gwt.client.ui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.InlineHyperlink;

import de.barop.gwt.client.CodeServerParameterHelper;

/**
 * Widget that is an internal hyperlink and supports HTML5 pushState.
 * 
 * <p>
 * It extends GWT's original {@link InlineHyperlink} and moves the history token from the hash to
 * the path.
 * <p>
 * 
 * <p>
 * This has the advantage that no-pushState browsers see the correct nice link while still using a
 * {@link History} implementation which does not cause reloads.
 * <p>
 * 
 * @author <a href="mailto:jb@barop.de">Johannes Barop</a>
 * 
 */
public class HyperlinkPushState extends Hyperlink {

  private String targetHistoryToken;

  /**
   * Calls {@link InlineHyperlink(text, targetHistoryToken)}
   * 
   * @param text
   * @param targetHistoryToken
   */
  public HyperlinkPushState(final String text, final String targetHistoryToken) {
    super(text, targetHistoryToken);
  }

  /**
   * No arg constructor, calls {@link InlineHyperlink()}  
   */
  public HyperlinkPushState() {
  }

  @Override
  public void setTargetHistoryToken(final String targetHistoryToken) {
    assert targetHistoryToken != null : "New history item cannot be null!";

    this.targetHistoryToken = targetHistoryToken;

    DOM.setElementProperty((Element) getElement().getChild(0), "href",
        CodeServerParameterHelper.append("/" + targetHistoryToken));
  }

  @Override
  public String getTargetHistoryToken() {
    return targetHistoryToken;
  }

}
