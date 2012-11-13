package de.barop.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

/**
 * Utility class with methods to extract and add the <code>gwt.codesvr</code> to the URL.
 * 
 * <p>
 * This is needed so that we not leave the development mode after pushing a history state.
 * </p>
 * 
 * @author <a href="mailto:jb@barop.de">Johannes Barop</a>
 * 
 */
public final class CodeServerParameterHelper {

  /**
   * Hidden constructor as this class is not meant to be instantiated.
   */
  private CodeServerParameterHelper() {
  }

  /**
   * Append the <code>gwt.codesvr</code> parameter to the token when needed.
   */
  public static String append(final String token) {
    String result = token;

    /*
     * This gets compiled out in production mode!
     */
    if (!GWT.isProdMode() && GWT.isClient()) {
      String gwtCodesvr = Window.Location.getParameter("gwt.codesvr");
      if (gwtCodesvr != null) {
        if (token.contains("?")) {
          result += "&";
        } else {
          result += "?";
        }
        result += "gwt.codesvr=" + gwtCodesvr;
      }
    }

    return result;
  }

  /**
   * Removes the <code>gwt.codesvr</code> parameter from the given string.
   */
  public static String remove(final String queryString) {
    String result = queryString;

    /*
     * This gets compiled out in production mode!
     */
    if (!GWT.isProdMode() && GWT.isClient() && queryString != null) {
      StringBuilder builder = new StringBuilder();
      builder.append("?");

      String separator = "";
      for (String keyValue : queryString.split("&")) {
        if (keyValue.startsWith("?")) {
          keyValue = keyValue.substring(1);
        }
        if (!keyValue.matches("gwt\\.codesvr=.*")) {
          builder.append(separator + keyValue.trim());
          separator = "&";
        }
      }
      result = builder.toString();
    }

    return result;
  }

}
