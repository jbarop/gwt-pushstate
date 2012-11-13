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

import java.util.logging.Logger;

import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;

/**
 * {@link HistoryConverter} implementation for browsers which support HTML5 pushState and therefore
 * expect the history token in the path.
 * 
 * <p>
 * Incoming links like <code>http://www.barop.de/#/impressum</code> are rewritten to
 * <code>http://www.barop.de/impressum</code>.
 * </p>
 * 
 * @author <a href="mailto:jb@barop.de">Johannes Barop</a>
 * 
 */
public class HistoryConverterPushState implements HistoryConverter {

  private static final Logger LOG = Logger.getLogger(HistoryConverterPushState.class.getName());

  @Override
  public void convertHistoryToken() {
    String hash = Window.Location.getHash();

    if (hash == null || hash.isEmpty()) {
      if (LogConfiguration.loggingIsEnabled()) {
        LOG.fine("Hash is '" + hash + "'. Nothing to do.");
      }
      return;
    }

    // remove the '#' character
    if (hash.startsWith("#")) {
      hash = hash.substring(1);
    }

    // navigate to the token using pushState
    if (LogConfiguration.loggingIsEnabled()) {
      LOG.fine("Hash is '" + hash + "'. Navigating to '" + hash + "'.");
    }
    History.newItem(hash, false);
  }

}
