package org.syftkog.web.test.framework;

import java.text.DecimalFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author BenjaminLimb
 */
public class StepLogger {

    //ERROR, WARN, INFO, DEBUG, TRACE

  /**
   *
   */
      public static final int ERROR = 0;

  /**
   *
   */
  public static final int WARN = 1;

  /**
   *
   */
  public static final int INFO = 2;

  /**
   *
   */
  public static final int DEBUG = 3;

  /**
   *
   */
  public static final int TRACE = 4;

  /**
   *
   */
  public final Logger LOG = LoggerFactory.getLogger(StepLogger.class);

    private StringBuilder logStringBuilder;
    private int currentStep;
    private long startTime;

  /**
   *
   */
  public StepLogger() {
        logStringBuilder = new StringBuilder();
        currentStep = 0;
    }

  /**
   *
   * @param level
   * @param text
   */
  public void log(int level, String text) {
        if (currentStep == 0) {
            startTime = System.currentTimeMillis();
        }
        StringBuilder stepText = new StringBuilder();
        long currentTime = System.currentTimeMillis();
        DecimalFormat df = new DecimalFormat("#0.00");

        double durationSeconds = ((double) (currentTime - startTime)) / 1000.0;
        String timeString = df.format(durationSeconds);

        stepText.append(currentStep++).append(".")
                .append(" ").append("Time:").append(timeString).append("s")
                .append(" ").append("Thread:").append(Thread.currentThread().getId())
                .append(" ")
                .append(text);

        switch (level) {
            case ERROR:
                LOG.error(stepText.toString());
                break;
            case WARN:
                LOG.warn(stepText.toString());
                break;
            case INFO:
                LOG.info(stepText.toString());
                break;
            case DEBUG:
                LOG.debug(stepText.toString());
                break;
            case TRACE:
                LOG.trace(stepText.toString());
                break;
        }

        stepText.append("\n");

        logStringBuilder.append(stepText);

    }

  /**
   *
   * @param text
   */
  public void log(String text) {
        log(INFO, text);

    }

  /**
   *
   * @return
   */
  public String getText() {
        return logStringBuilder.toString();
    }

  /**
   *
   * @return
   */
  public String clear() {
        String output = getText();
        init();
        return output;
    }

  /**
   *
   */
  public void init() {
        this.logStringBuilder = new StringBuilder();
        currentStep = 0;
    }
}
