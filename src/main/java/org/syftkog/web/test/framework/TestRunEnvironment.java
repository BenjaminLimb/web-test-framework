package org.syftkog.web.test.framework;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author BenjaminLimb
 */
public class TestRunEnvironment {

    private static Calendar cal = Calendar.getInstance();
    private static String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
    private static String dateTimeStamp = sdf.format(cal.getTime());

    private static String hostName = "";
    private static String hostaddr = "";
    private static Long time = System.currentTimeMillis();

  /**
   *
   * @param jobNameKey
   * @param displayNameKey
   * @return
   */
  public static String getJenkinsRunId(String jobNameKey, String displayNameKey) {
        String jenkinsRunId = null;
        String jobName = getSystemValue(jobNameKey);
        if ((null != jobName) && (!jobName.equals(""))) {
            jenkinsRunId = jobName;
        }
        String displayName = getSystemValue(displayNameKey);
        if ((null != displayName) && (!displayName.equals(""))) {
            jenkinsRunId = jenkinsRunId + displayName;
        }
        return jenkinsRunId;
    }

  /**
   *
   * @param key
   * @return
   */
  public static String getSystemValue(String key) {
        String val = System.getProperty(key);
        if (val != null) {
            return val;
        }
        return System.getenv(key);
    }

    private static void setInet() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostName = addr.getHostName();
            hostaddr = addr.getHostAddress();
        } catch (UnknownHostException e) {
            //Swallow it and return "".
        }
    }

  /**
   *
   * @return
   */
  public static String getHostName() {

        if (hostName.equals("")) {
            setInet();
        }
        return hostName;
    }

  /**
   *
   * @return
   */
  public static String getHostAddr() {

        if (hostaddr.equals("")) {
            setInet();
        }
        return hostaddr;
    }

  /**
   *
   * @return
   */
  public static String getRunId() {
        String id1 = getSystemValue("PROMOTED_ID");
        String id2 = getSystemValue("BUILD_ID");
        String id3 = getJenkinsRunId("JOB_NAME", "BUILD_DISPLAY_NAME");        
        String id4 = getSystemValue("COMMANDER_JOBID");
        return (id1 != null) ? id1 : (id2 != null) ? id2 : (id3 != null) ? id3 : (id4 != null) ? id4 : dateTimeStamp;
    }
}
