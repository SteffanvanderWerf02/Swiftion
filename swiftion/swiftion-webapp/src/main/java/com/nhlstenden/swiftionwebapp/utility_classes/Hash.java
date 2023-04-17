package com.nhlstenden.swiftionwebapp.utility_classes;

import org.mindrot.jbcrypt.BCrypt;

/*****************************************************************
 * This class is the hash class. It generates a hash and checks
 * if the password is correct
 ****************************************************************/
public class Hash {

    public static final int DEFAULT_ITERATIONS = 5;
    public static String generateBcryptHash(String password) {
        String salt = BCrypt.gensalt(DEFAULT_ITERATIONS);
        return BCrypt.hashpw(password, salt);
    }

    public static Boolean checkPassword(String dbPwHashed, String plainText) {
        return BCrypt.checkpw(plainText, dbPwHashed);
    }
}