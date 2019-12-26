package com.password;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author cj
 * @date 2019/12/26
 */
public class Main {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String result = encoder.encode("123");
        System.out.println("-----debug: result = " + result);
    }
}
