package com.springboot.demo.bookstore.utils;

import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * The class provides static methods for generating ISBN-13 numbers based on a book's title, including methods for creating various components of the ISBN and calculating its check digit
 */
public class IsbnGeneratorServiceUtil {

    private static final Random RANDOM = new Random();

    public static String generatorIsbn13(String title) {
        String prefix = generateEanPrefix();
        String registrationGroup = generateRegistrationGroup();
        String registrant = generateRegistrantElement();
        String publication = generatePublicationElement(title);

        String stringWithoutCheckDigits = String.format("%s%s%s%s", prefix, registrationGroup, registrant, publication);
        String check3digits = calculateCheckDigit(stringWithoutCheckDigits);

        return String.format("%s%s", stringWithoutCheckDigits, check3digits);
    }

    /**
     * EAN Prefix : The first 3 digits of all ISBNs are always 978 or 979.
     * @return 978 or 979
     */
    private static String generateEanPrefix() {
        return (978 + RANDOM.nextInt(2)) +"";
    }

    /**
     * Registration Group : This number can be 1-5 digits long and tells you the country or region where the ISBN is registered.
     * We are assuming this to be 0065 here (4 digits)
     * @return 0065
     */
    private static String generateRegistrationGroup() {
        return "0065";
    }

    /**
     * Registrant Element : The registrant element tells you which publisher created this book.
     * There are 74 book publishes in singapore, hence random.nextInt(74+1)
     * @return 0 to 74 (2digits)
     */
    private static String generateRegistrantElement() {
        int registrantElement = RANDOM.nextInt(75);
        return registrantElement < 10 ?
                "0" + registrantElement :
                registrantElement + "";
    }

    /**
     * Publication Element : This number tells you the title of the book, format, and edition of the book.
     * I will add all the unicode code points of the title of the book
     * I will allocate the remaining 3 digits for this number
     */
    private static String generatePublicationElement(String title) {
        int result = 0;
        for (int i = 0; i < title.length(); i++) {

            char ch = title.charAt(i);

            if (Character.isLetter(ch)) {
                result +=ch;
            } else result = result + ch;
        }

        // makes sure there are only 3 digits
        while(result>=1000){
            result/=10;
        }
        while(result<100){
            result+=RANDOM.nextInt(999);
        }
        return result + "";
    }

    private static String calculateCheckDigit(String firstTenDigits) {
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            int digit = Character.getNumericValue(firstTenDigits.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        int remainder = sum % 10;
        return (remainder == 0) ? 0 + "": 10 - remainder + "";
    }
}
