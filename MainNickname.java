package working_with_multithreaded_program_variables.home_work03;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Leonid Zulin
 * @date 29.01.2023 13:20
 */
public class MainNickname {
    static AtomicInteger counter1 = new AtomicInteger();
    static AtomicInteger counter2 = new AtomicInteger();
    static AtomicInteger counter3 = new AtomicInteger();
    static int numberOfWords = 100_000;

    public static void main(String[] args) throws InterruptedException {
        Thread threadPalindrome;
        Thread threadSameLetters;
        Thread threadLettersAscendingOrder;

        // generate 100_000 short words
        Random random = new Random();
        String[] texts = new String[numberOfWords];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));

        }

        // First thread checks if the word is a palindrome
        threadPalindrome = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    count(text);
                }
            }

        });

        // Second thread checks that all letters in the word are the same
        threadSameLetters = new Thread(() -> {
            for (String text : texts) {
                if (checkAllRepeated(text)) {
                    count(text);
                }
            }
        });

        // Third thread checks letters in ascending order
        threadLettersAscendingOrder = new Thread(() -> {
            for (String text : texts) {
                if (isAscendingOrdering(text)) {
                    count(text);
                }
            }
        });

        threadPalindrome.start();
        threadSameLetters.start();
        threadLettersAscendingOrder.start();

        threadPalindrome.join();
        threadSameLetters.join();
        threadLettersAscendingOrder.join();

        System.out.println("Красивых слов с длиной 3: " + counter1 + " шт");
        System.out.println("Красивых слов с длиной 4: " + counter2 + " шт");
        System.out.println("Красивых слов с длиной 5: " + counter3 + " шт");
        System.out.println("Main ends!!!");

    }

    public static void count(String text) {

        if (text.length() == 3) {
            counter1.incrementAndGet();
        }
        if (text.length() == 4) {
            counter2.incrementAndGet();
        }
        if (text.length() == 5) {
            counter3.incrementAndGet();
        }

    }

    // is the word is a palindrome
    public static boolean isPalindrome(String text) {
        StringBuilder plain = new StringBuilder(text);
        StringBuilder reverse = plain.reverse();
        if (reverse.toString().equals(text) && (!checkAllRepeated(text))) {
            return true;
        }
        return false;
    }

    // checking that all letters are the word the same
    public static boolean checkAllRepeated(String text) {
        // create array of char
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != chars[0]) {
                return false;
            }
        }
        return true;

    }

    // checking order ascending
    public static boolean isAscendingOrdering(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) > text.charAt(i + 1) || checkAllRepeated(text)) {
                return false;
            }
        }
        return true;
    }

    // Generate random text
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}




