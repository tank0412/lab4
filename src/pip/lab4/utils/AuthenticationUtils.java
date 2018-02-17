package pip.lab4.utils;

import jdk.nashorn.internal.runtime.regexp.RegExp;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthenticationUtils {

    public static String encodeSHA256(String password)
        throws UnsupportedEncodingException, NoSuchAlgorithmException{
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(password.getBytes("UTF-8"));
        byte[] digest = messageDigest.digest();
        return DatatypeConverter.printBase64Binary(digest).toString();
    }
}
