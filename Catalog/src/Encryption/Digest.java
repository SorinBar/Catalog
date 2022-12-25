package Encryption;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Digest {
     public static String SHA256(String password) {
         try {
             MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
             try {
                 byte [] bytes = messageDigest.digest(password.getBytes("UTF-8"));
                 return Base64.getEncoder().encodeToString(bytes);
             } catch (UnsupportedEncodingException e) {
                 System.out.println("Wrong Encoding");
             }
         } catch (NoSuchAlgorithmException e) {
             System.out.println("Invalid Algorithm");
         }
         return null;
    }
}
