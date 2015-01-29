package org.syftkog.web.test.framework;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author BenjaminLimb
 */
public class GeneralUtils {

    private GeneralUtils() {
    }

  /**
   *
   * @param <T>
   * @param input
   * @param valueIfNull
   * @return
   */
  public static <T> T replaceNull(T input, T valueIfNull) {
        return input == null ? valueIfNull : input;
    }

  /**
   *
   * @param urlString
   * @return
   */
  public static int getResponseCode(String urlString) {
        try {
            URL u = new URL(urlString);
            HttpURLConnection huc = (HttpURLConnection) u.openConnection();
            huc.setRequestMethod("GET");
            huc.connect();
            return huc.getResponseCode();
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

  /**
   *
   * @param a
   * @param separator
   * @return
   */
  public static String arrayToString(Object[] a, String separator) {
        StringBuilder result = new StringBuilder();
        if (a.length > 0) {
            result.append(a[0]);
            for (int i = 1; i < a.length; i++) {
                result.append(separator);
                result.append(a[i]);
            }
        }
        return result.toString();
    }

  /**
   *
   * @param throwable
   * @return
   */
  public static String throwableToString(Throwable throwable) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        throwable.printStackTrace(printWriter);
        return result.toString();
    }
  
  
  /**
   * GET the java working path for unit testing.
   * @return 
   */
   public static String getPath() {
      try {
        return new java.io.File(".").getCanonicalPath();
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    }

  

}
