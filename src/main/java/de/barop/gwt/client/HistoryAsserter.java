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
