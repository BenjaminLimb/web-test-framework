package org.syftkog.web.test.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author BenjaminLimb
 */
@XmlType(name = "langauge")
@XmlEnum
public enum Language {

  /**
   *
   */
  DE("Deutsch", "German", "de", "deu"),

  /**
   *
   */
  PT("Português", "Portuguese", "pt", "por"),

  /**
   *
   */
  EN("English", "English", "en", "eng"),

  /**
   *
   */
  RU("Русский", "Russian", "ru", "rus"),

  /**
   *
   */
  ES("Español", "Spanish", "es", "spa"),

  /**
   *
   */
  JA("日本語", "Japanese", "ja", "jpn"),

  /**
   *
   */
  FR("Français", "French", "fr", "fra"),

  /**
   *
   */
  KO("한국어", "Korean", "ko", "kor"),

  /**
   *
   */
  IT("Italiano", "Italian", "it", "ita"),

  /**
   *
   */
  ZH("中文", "Chinese", "zh", "zho"),

  /**
   *
   */
  EO("Psuedo Loc", "Psudo Loc", "eo", "eo");
    private static final Map<String, Language> twoLetterCodeMap = new HashMap<>();
    private static final Map<String, Language> threeLetterCodeMap = new HashMap<>();

    static {
        for (Language lan : Language.values()) {
            twoLetterCodeMap.put(lan.twoLetterCode, lan);
            threeLetterCodeMap.put(lan.threeLetterCode, lan);
        }
    }

    private Language(final String name, final String englishName, final String twoLetterCode, String threeLetterCode) {
        this.name = name;
        this.englishName = englishName;
        this.twoLetterCode = twoLetterCode;
        this.threeLetterCode = threeLetterCode;
    }
    private final String name;
    private final String englishName;
    private final String twoLetterCode;
    private final String threeLetterCode;

    @Override
    public String toString() {
        return englishName;
    }

  /**
   *
   * @return
   */
  public String getName() {
        return name;
    }

  /**
   *
   * @return
   */
  public String getEnglishName() {
        return englishName;
    }

  /**
   *
   * @return
   */
  public String getTwoLetterCode() {
        return twoLetterCode;
    }

  /**
   *
   * @return
   */
  public String getThreeLetterCode() {
        return threeLetterCode;
    }

  /**
   *
   * @return
   */
  public Boolean isCJK() {
        return this.equals(JA) || this.equals(KO) || this.equals(ZH);
    }

  /**
   *
   * @param twoLetterCode
   * @return
   */
  private static Language getFromTwoLetterCode(String twoLetterCode) {
        return twoLetterCodeMap.get(twoLetterCode.toLowerCase());
    }

  /**
   *
   * @param twoLetterCodes
   * @return
   */
  private static List<Language> getFromTwoletterCodes(String... twoLetterCodes) {
        ArrayList<Language> results = new ArrayList<Language>();
        for (String code : twoLetterCodes) {
            results.add(getFromTwoLetterCode(code.toLowerCase()));
        }
        return results;
    }

  /**
   *
   * @param threeLetterCode
   * @return
   */
  private static Language getFromThreeLetterCode(String threeLetterCode) {
        return threeLetterCodeMap.get(threeLetterCode.toLowerCase());
    }

  /**
   *
   * @param code
   * @return
   */
  public static Language getFromCode(String code) {
        Language lang = twoLetterCodeMap.get(code);
        if (lang == null) {
            lang = threeLetterCodeMap.get(code);
        }
        return lang;
    }

  
}
