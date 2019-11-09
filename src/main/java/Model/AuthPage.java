package Model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AuthPage {

    public static String encryptPassword(String password) {
        String passwordHash;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            passwordHash = Base64.getEncoder().encodeToString(hash);

            return passwordHash;
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e);
        }
        return null;
    }

}
