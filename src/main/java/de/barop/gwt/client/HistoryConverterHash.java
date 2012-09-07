package de.barop.gwt.client;

import java.util.logging.Logger;

import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.Window;

/**
 * {@link HistoryConverter} implementation for browsers without HTML5 pushState support and
 * therefore expect the history token inside the hash.
 * 
 * <p>
 * Incoming links like <code>http://www.barop.de/impressum</code> are redirected to
 * <code>http://www.barop.de/#/impressum</code>.
 * </p>
 * 
 * @author <a href="mailto:jb@barop.de">Johannes Barop</a>
 * 
 */
public class HistoryConverterHash implements HistoryConverter {

  private static final Logger LOG = Logger.getLogger(HistoryConverterHash.class.getName());

  @Override
  public void convertHistoryToken() {
    String path = Window.Location.getPath();

    if (path.isEmpty() || path.equals("/")) {
      if (LogConfiguration.loggingIsEnabled()) {
        LOG.fine("Path is '" + path + "'. Nothing to do.");
      }
      return;
    }

    String newUrl = Window.Location.getProtocol() + "//" + Window.Location.getHost() + "/";
    newUrl = CodeServerParameterHelper.append(newUrl);
    newUrl = newUrl + "#" + path;

    if (LogConfiguration.loggingIsEnabled()) {
      LOG.fine("Path is '" + path + "'. Redirecting to '" + newUrl + "'...");
    }

    Window.Location.assign(newUrl); // This will cause a complete reload :(
  }

}
