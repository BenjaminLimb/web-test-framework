package org.syftkog.web.test.framework;

import com.saucelabs.saucerest.SecurityUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.DateUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.*;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;
import org.apache.commons.codec.binary.Base64;



import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.UnexpectedException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Java API that invokes the Sauce REST API. Adapted https://github.com/saucelabs/saucerest-java/blob/master/src/main/java/com/saucelabs/saucerest/SauceREST.java
 * 
 */
public class SauceRESTApi {

  private static final Logger logger = Logger.getLogger(SauceRESTApi.class.getName());
  private static final long HTTP_READ_TIMEOUT_SECONDS = TimeUnit.SECONDS.toMillis(10);
  private static final long HTTP_CONNECT_TIMEOUT_SECONDS = TimeUnit.SECONDS.toMillis(10);

  /**
   *
   */
  protected String username;

  /**
   *
   */
  protected String accessKey;

  /**
   *
   */
  public static final String RESTURL = "https://saucelabs.com/rest/v1/%1$s";
  private static final String USER_RESULT_FORMAT = RESTURL + "/%2$s";
  private static final String JOBS_FORMAT = RESTURL + "/jobs?limit=%2$s";
  private static final String JOB_RESULT_FORMAT = RESTURL + "/jobs/%2$s";
  private static final String STOP_JOB_FORMAT = JOB_RESULT_FORMAT + "/stop";
  private static final String DOWNLOAD_VIDEO_FORMAT = JOB_RESULT_FORMAT + "/assets/video.flv";
  private static final String DOWNLOAD_LOG_FORMAT = JOB_RESULT_FORMAT + "/assets/selenium-server.log";

  private static final String GET_TUNNEL_FORMAT = RESTURL + "/tunnels";
  private static final String DELETE_TUNNEL_FORMAT = GET_TUNNEL_FORMAT + "/%2$s";
  private static final String DATE_FORMAT = "yyyyMMdd_HHmmSS";

  /**
   *
   * @param username
   * @param accessKey
   */
  public SauceRESTApi(String username, String accessKey) {
    this.username = username;
    this.accessKey = accessKey;
  }

  /**
   * Marks a Sauce Job as 'passed'.
   *
   * @param jobId the Sauce Job Id, typically equal to the Selenium/WebDriver
   * sessionId
   */
  public void jobPassed(String jobId) {
    Map<String, Object> updates = new HashMap<String, Object>();
    updates.put("passed", true);
    updateJobInfo(jobId, updates);
  }

  /**
   * Marks a Sauce Job as 'failed'.
   *
   * @param jobId the Sauce Job Id, typically equal to the Selenium/WebDriver
   * sessionId   
   */
  public void jobFailed(String jobId) {
    Map<String, Object> updates = new HashMap<String, Object>();
    updates.put("passed", false);
    updateJobInfo(jobId, updates);
  }

  /**
   * Downloads the video for a Sauce Job to the filesystem. The file will be
   * stored in a directory specified by the <code>location</code> field.
   *
   * @param jobId the Sauce Job Id, typically equal to the Selenium/WebDriver
   * sessionId
   * @param location
   */
  public void downloadVideo(String jobId, String location) {
    URL restEndpoint = null;
    try {
      restEndpoint = new URL(String.format(DOWNLOAD_VIDEO_FORMAT, username, jobId));
    } catch (MalformedURLException e) {
      logger.log(Level.WARNING, "Error constructing Sauce URL", e);
    }
    downloadFile(jobId, location, restEndpoint);
  }

  /**
   * Downloads the log file for a Sauce Job to the filesystem. The file will be
   * stored in a directory specified by the <code>location</code> field.
   *
   * @param jobId the Sauce Job Id, typically equal to the Selenium/WebDriver
   * sessionId
   * @param location
   */
  public void downloadLog(String jobId, String location) {
    URL restEndpoint = null;
    try {
      restEndpoint = new URL(String.format(DOWNLOAD_LOG_FORMAT, username, jobId));
    } catch (MalformedURLException e) {
      logger.log(Level.WARNING, "Error constructing Sauce URL", e);
    }
    downloadFile(jobId, location, restEndpoint);
  }

  /**
   *
   * @param path
   * @return
   */
  public String retrieveResults(String path) {
    URL restEndpoint = null;
    try {
      restEndpoint = new URL(String.format(USER_RESULT_FORMAT, username, path));
    } catch (MalformedURLException e) {
      logger.log(Level.WARNING, "Error constructing Sauce URL", e);
    }
    return retrieveResults(restEndpoint);
  }

  /**
   *
   * @param jobId
   * @return
   */
  public String getJobInfo(String jobId) {
    URL restEndpoint = null;
    try {
      restEndpoint = new URL(String.format(JOB_RESULT_FORMAT, username, jobId));
    } catch (MalformedURLException e) {
      logger.log(Level.WARNING, "Error constructing Sauce URL", e);
    }
    return retrieveResults(restEndpoint);
  }

  /**
   *
   * @param restEndpoint
   * @return
   */
  public String retrieveResults(URL restEndpoint) {
    BufferedReader reader = null;
    StringBuilder builder = new StringBuilder();
    try {

      HttpURLConnection connection = openConnection(restEndpoint);
      connection.setDoOutput(true);
      addAuthenticationProperty(connection);
      reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

      String inputLine;
      while ((inputLine = reader.readLine()) != null) {
        builder.append(inputLine);
      }
    } catch (IOException e) {
      logger.log(Level.WARNING, "Error retrieving Sauce Results", e);
    }
    try {
      if (reader != null) {
        reader.close();
      }
    } catch (IOException e) {
      logger.log(Level.WARNING, "Error closing Sauce input stream", e);
    }
    return builder.toString();
  }

  private void downloadFile(String jobId, String location, URL restEndpoint) {
    try {
      HttpURLConnection connection = openConnection(restEndpoint);

      connection.setDoOutput(true);
      connection.setRequestMethod("GET");
      addAuthenticationProperty(connection);

      InputStream stream = connection.getInputStream();
      BufferedInputStream in = new BufferedInputStream(stream);
      SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
      String saveName = jobId + format.format(new Date());
      if (restEndpoint.getPath().endsWith(".flv")) {
        saveName = saveName + ".flv";
      } else {
        saveName = saveName + ".log";
      }
      FileOutputStream file = new FileOutputStream(new File(location, saveName));
      BufferedOutputStream out = new BufferedOutputStream(file);
      int i;
      while ((i = in.read()) != -1) {
        out.write(i);
      }
      out.flush();
    } catch (IOException e) {
      logger.log(Level.WARNING, "Error downloading Sauce Results");
    }
  }

  /**
   *
   * @param connection
   */
  protected void addAuthenticationProperty(HttpURLConnection connection) {
    if (username != null && accessKey != null) {
      String auth = encodeAuthentication();
      connection.setRequestProperty("Authorization", auth);
    }

  }

  /**
   *
   * @return
   */
  public String getJSON() {
    try {
      URL url = new URL("http://localhost:8080/RESTfulExample/json/product/get");
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Accept", "application/json");

      if (conn.getResponseCode() != 200) {
        throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
      }

      BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

      String output;
      System.out.println("Output from Server .... \n");
      while ((output = br.readLine()) != null) {
        System.out.println(output);
      }

      conn.disconnect();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;

  }

  /**
   *
   * @param jobId
   * @return
   */
  public boolean deleteJob(String jobId) {
    HttpURLConnection conn = null;

    try {
      URL restEndpoint = new URL(String.format(JOB_RESULT_FORMAT, username, jobId));
      conn = openConnection(restEndpoint);
      conn.setDoOutput(true);
      conn.setRequestMethod("DELETE");
      addAuthenticationProperty(conn);
      int responseCode = conn.getResponseCode();
      return responseCode == 200;
    } catch (IOException e) {
      logger.log(Level.WARNING, "Error deleting Sauce Job", e);
    }

    try {
      if (conn != null) {
        conn.getInputStream().close();
      }
    } catch (IOException e) {
      logger.log(Level.WARNING, "Error deleting result stream", e);
    }
    return false;
  }

  /**
   *
   * @param limit
   * @return
   */
  public JSONArray getJobs(int limit) {

    HttpURLConnection conn = null;
    try {

      URL restEndpoint = new URL(String.format(JOBS_FORMAT, username, limit));
      conn = openConnection(restEndpoint);
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Accept", "application/json");
      addAuthenticationProperty(conn);

      if (conn.getResponseCode() != 200) {
        throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
      }

      BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

      // String output;
      //System.out.println("Output from Server .... \n");
      // while ((output = br.readLine()) != null) {
      //   System.out.println(output);
      // }
      JSONParser parser = new JSONParser();
      //Object obj = parser.parse(br);
      JSONArray list = (JSONArray) parser.parse(br);

      return list;

    } catch (IOException | ParseException e) {
      logger.log(Level.WARNING, "Error reading Sauce Jobs", e);
    }

    try {
      if (conn != null) {
        conn.getInputStream().close();
      }
    } catch (IOException e) {
      logger.log(Level.WARNING, "Error closing result stream", e);
    }
    return null;
  }

  /**
   *
   * @param jobId
   * @param updates
   */
  public void updateJobInfo(String jobId, Map<String, Object> updates) {
    HttpURLConnection postBack = null;
    try {
      URL restEndpoint = new URL(String.format(JOB_RESULT_FORMAT, username, jobId));
      postBack = openConnection(restEndpoint);
      postBack.setDoOutput(true);
      postBack.setRequestMethod("PUT");
      addAuthenticationProperty(postBack);
      String jsonText = JSONValue.toJSONString(updates);
      postBack.getOutputStream().write(jsonText.getBytes());

    } catch (IOException e) {
      logger.log(Level.WARNING, "Error updating Sauce Results", e);
    }

    try {
      if (postBack != null) {
        postBack.getInputStream().close();
      }
    } catch (IOException e) {
      logger.log(Level.WARNING, "Error closing result stream", e);
    }

  }

  /**
   *
   * @param jobId
   */
  public void stopJob(String jobId) {
    HttpURLConnection postBack = null;

    try {
      URL restEndpoint = new URL(String.format(STOP_JOB_FORMAT, username, jobId));
      postBack = openConnection(restEndpoint);
      postBack.setDoOutput(true);
      postBack.setRequestMethod("PUT");
      addAuthenticationProperty(postBack);
      postBack.getOutputStream().write("".getBytes());
    } catch (IOException e) {
      logger.log(Level.WARNING, "Error stopping Sauce Job", e);
    }

    try {
      if (postBack != null) {
        postBack.getInputStream().close();
      }
    } catch (IOException e) {
      logger.log(Level.WARNING, "Error closing result stream", e);
    }

  }

  /**
   *
   * @param url
   * @return
   * @throws IOException
   */
  public HttpURLConnection openConnection(URL url) throws IOException {
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setReadTimeout((int) HTTP_READ_TIMEOUT_SECONDS);
    con.setConnectTimeout((int) HTTP_CONNECT_TIMEOUT_SECONDS);
    return con;
  }

  /**
   * Uploads a file to Sauce storage.
   *
   * @param file the file to upload -param fileName uses file.getName() to store
   * in sauce -param overwrite set to true
   * @return the md5 hash returned by sauce of the file
   * @throws IOException
   */
  public String uploadFile(File file) throws IOException {
    return uploadFile(file, file.getName());
  }

  /**
   * Uploads a file to Sauce storage.
   *
   * @param file the file to upload
   * @param fileName name of the file in sauce storage -param overwrite set to
   * true
   * @return the md5 hash returned by sauce of the file
   * @throws IOException
   */
  public String uploadFile(File file, String fileName) throws IOException {
    return uploadFile(file, fileName, true);
  }

  /**
   * Uploads a file to Sauce storage.
   *
   * @param file the file to upload
   * @param fileName name of the file in sauce storage
   * @param overwrite boolean flag to overwrite file in sauce storage if it
   * exists
   * @return the md5 hash returned by sauce of the file
   * @throws IOException
   */
  public String uploadFile(File file, String fileName, Boolean overwrite) throws IOException {

    CookieSpecProvider customSpecProvider = new CookieSpecProvider() {
      public CookieSpec create(HttpContext context) {
        return new BrowserCompatSpec(new String[]{DateUtils.PATTERN_RFC1123,
          DateUtils.PATTERN_RFC1036,
          DateUtils.PATTERN_ASCTIME,
          "\"EEE, dd-MMM-yyyy HH:mm:ss z\""});
      }

    };
    Registry<CookieSpecProvider> r = RegistryBuilder.<CookieSpecProvider>create()
            .register(CookieSpecs.BEST_MATCH,
                    new BestMatchSpecFactory())
            .register("custom", customSpecProvider)
            .build();

    RequestConfig requestConfig = RequestConfig.custom()
            .setCookieSpec("custom")
            .build();

    CloseableHttpClient client = HttpClients.custom()
            .setDefaultCookieSpecRegistry(r)
            .setDefaultRequestConfig(requestConfig)
            .build();

    HttpClientContext context = HttpClientContext.create();
    context.setCookieSpecRegistry(r);

    HttpPost post = new HttpPost("http://saucelabs.com/rest/v1/storage/"
            + username + "/" + fileName + "?overwrite=" + overwrite.toString());

    FileEntity entity = new FileEntity(file);
    entity.setContentType(new BasicHeader("Content-Type",
            "application/octet-stream"));
    post.setEntity(entity);

    post.setHeader("Content-Type", "application/octet-stream");
    post.setHeader("Authorization", encodeAuthentication());
    HttpResponse response = client.execute(post, context);
    BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
    String line;
    StringBuilder builder = new StringBuilder();
    while ((line = rd.readLine()) != null) {
      builder.append(line);
    }

    try {
      JSONObject sauceUploadResponse = new JSONObject(builder.toString());
      if (sauceUploadResponse.has("error")) {
        throw new UnexpectedException("Failed to upload to sauce-storage: "
                + sauceUploadResponse.getString("error"));
      }
      return sauceUploadResponse.getString("md5");
    } catch (JSONException j) {
      throw new UnexpectedException("Failed to parse json response.", j);
    }

  }

  /**
   * Generates a link to the job page on Saucelabs.com, which can be accessed
   * without the user's credentials. Auth token is HMAC/MD5 of the job ID with
   * the key <username>:<api key>
   * (see {@link http://saucelabs.com/docs/integration#public-job-links}).
   *
   * @param jobId the Sauce Job Id, typically equal to the Selenium/WebDriver
   * sessionId
   * @return link to the job page with authorization token
   */
  public String getPublicJobLink(String jobId) {
    try {
      String key = username + ":" + accessKey;
      String auth_token = SecurityUtils.hmacEncode("HmacMD5", jobId, key);
      String link = "https://saucelabs.com/jobs/" + jobId + "?auth=" + auth_token;

      return link;
    } catch (IllegalArgumentException ex) {
      // someone messed up on the algorithm to hmacEncode
      // For available algorithms see {@link http://docs.oracle.com/javase/7/docs/api/javax/crypto/Mac.html}
      // we only want to use 'HmacMD5'
      System.err.println("Unable to create an authenticated public link to job:");
      System.err.println(ex);
      return "";
    }
  }

  // TODO: Test this
  //http://stackoverflow.com/questions/13109588/base64-encoding-in-java

  /**
   *
   * @return
   */
    protected String encodeAuthentication() {
    String auth = username + ":" + accessKey;
    auth = "Basic " + new String(Base64.encodeBase64(auth.getBytes()));
    return auth;
  }

  /**
   *
   * @param tunnelId
   */
  public void deleteTunnel(String tunnelId) {

    HttpURLConnection postBack = null;
    try {
      URL restEndpoint = new URL(String.format(DELETE_TUNNEL_FORMAT, username, tunnelId));
      postBack = openConnection(restEndpoint);
      postBack.setDoOutput(true);
      postBack.setRequestMethod("DELETE");
      addAuthenticationProperty(postBack);
      postBack.getOutputStream().write("".getBytes());
    } catch (IOException e) {
      logger.log(Level.WARNING, "Error stopping Sauce Job", e);
    }

    try {
      if (postBack != null) {
        postBack.getInputStream().close();
      }
    } catch (IOException e) {
      logger.log(Level.WARNING, "Error closing result stream", e);
    }
  }

  /**
   *
   * @return
   */
  public String getTunnels() {
    URL restEndpoint = null;
    try {
      restEndpoint = new URL(String.format(GET_TUNNEL_FORMAT, username));
    } catch (MalformedURLException e) {
      logger.log(Level.WARNING, "Error constructing Sauce URL", e);
    }
    return retrieveResults(restEndpoint);
  }

}
