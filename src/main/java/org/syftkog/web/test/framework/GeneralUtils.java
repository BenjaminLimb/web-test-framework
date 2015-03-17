package org.syftkog.web.test.framework;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import org.testng.ITestResult;

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

   /**
    * 
    * @param text
    * @return 
    */
   public String urlEncode(String text) {
    try {
      text = URLEncoder.encode(text, "UTF-8");
      text = text.replaceAll("\\+", "%20");
      return text;
    } catch (UnsupportedEncodingException ex) {
      throw new RuntimeException(ex);
    }
  }
   
   public static String statusToString(int status) {
    switch (status) {
      case ITestResult.SUCCESS:
        return "SUCCESS";
      case ITestResult.FAILURE:
        return "FAILURE";
      case ITestResult.SKIP:
        return "SKIP";
      case ITestResult.SUCCESS_PERCENTAGE_FAILURE:
        return "SUCCESS WITHIN PERCENTAGE";
      case ITestResult.STARTED:
        return "STARTED";
      default:
        throw new RuntimeException("Could not interpret Result Status.");
    }
  }
   
   
   
 
  

}
