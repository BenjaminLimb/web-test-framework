package org.syftkog.web.test.framework;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * COPIED FROM
 * http://stackoverflow.com/questions/20227/how-do-i-use-3des-encryption-decryption-in-java
 * * Consider
 * http://www.jkstack.com/2014/02/encryption-decryption-using-desede-triple-des-algorithm-padding-modes-java.html
 */
public class TrippleDES {

  private static final String UNICODE_FORMAT = "UTF8";

  /**
   *
   */
  public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
  private KeySpec ks;
  private SecretKeyFactory skf;
  private Cipher cipher;
  byte[] arrayBytes;

  private String myEncryptionScheme;
  SecretKey key;

  /**
   *
   * @param myEncryptionKey
   */
  public TrippleDES(String myEncryptionKey) {
    try {
      myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
      arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
      ks = new DESedeKeySpec(arrayBytes);
      skf = SecretKeyFactory.getInstance(myEncryptionScheme);
      cipher = Cipher.getInstance(myEncryptionScheme);
      key = skf.generateSecret(ks);
    } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException ex) {
      throw new RuntimeException(ex);
    }

  }

  /**
   *
   * @param unencryptedString
   * @return
   */
  public String encrypt(String unencryptedString) {
    String encryptedString = null;
    try {
      cipher.init(Cipher.ENCRYPT_MODE, key);
      byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
      byte[] encryptedText = cipher.doFinal(plainText);
      encryptedString = new String(Base64.encodeBase64(encryptedText));
    } catch (InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException ex) {
      throw new RuntimeException(ex);
    }
    return encryptedString;
  }

  /**
   *
   * @param encryptedString
   * @return
   */
  public String decrypt(String encryptedString) {
    String decryptedText = null;
    try {
      cipher.init(Cipher.DECRYPT_MODE, key);
      byte[] encryptedText = Base64.decodeBase64(encryptedString);
      byte[] plainText = cipher.doFinal(encryptedText);
      decryptedText = new String(plainText);
    } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
      throw new RuntimeException(ex);
    }
    return decryptedText;
  }

}
