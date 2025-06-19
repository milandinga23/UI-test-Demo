package com.example.tests.data;

import java.util.Random;

public class TestDataGenerator {

    private static final String[] FIRST_NAMES = {
            "John", "Jane", "Alice", "Bob", "Emily", "Tom", "Nina", "David"
    };
    private static final String[] LAST_NAMES = {
            "Smith", "Johnson", "Brown", "Williams", "Jones", "Miller", "Taylor", "Davis"
    };

    private static final Random random = new Random();

    public static String randomFirstName() {
        return FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
    }

    public static String randomLastName() {
        return LAST_NAMES[random.nextInt(LAST_NAMES.length)];
    }

    public static String randomEmployeeId() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }
}
