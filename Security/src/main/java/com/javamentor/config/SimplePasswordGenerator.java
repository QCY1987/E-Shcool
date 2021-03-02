package com.javamentor.config;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

/**
 * Класс, который содержит метод генерации простого пароля.
 */

@Component
public class SimplePasswordGenerator {

    /**
     * Метод, который генерирует пароль из случайных символов заданной длины
     *
     * @param length - кол-во генерируемых символов в пароле
     */
    public String randomPassword(int length) {
        SecureRandom secureRandom = new SecureRandom();
        String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789!@#$%^&*()№%";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomSequence = secureRandom.nextInt(symbols.length());
            result.append(symbols.charAt(randomSequence));
        }
        return result.toString();
    }
}