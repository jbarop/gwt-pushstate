package de.barop.gwt.client;

/**
 * Class which helps to convert the history token to a format which is suitable for the current
 * browser.
 * 
 * @author <a href="mailto:jb@barop.de">Johannes Barop</a>
 * 
 */
public interface HistoryConverter {

  /**
   * If needed convert the current history token to a suitable format.
   * 
   * <p>
   * Does nothing if no converting is needed.
   * <p>
   */
  void convertHistoryToken();

}
