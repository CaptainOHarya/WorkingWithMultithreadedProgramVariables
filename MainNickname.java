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
    //static int numberOfWords = 10;

    public static void main(String[] args) throws InterruptedException {
        Thread threadThreeLetters;
        Thread threadFourLetters;
        Thread threadFiveLetters;

        // generate 100_000 short words
        Random random = new Random();
        String[] texts = new String[numberOfWords];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        threadThreeLetters = new Thread(() -> {
            counter1 = count(texts, 3);
        });
        threadFourLetters = new Thread(() -> {
            counter2 = count(texts, 4);
        });
        threadFiveLetters = new Thread(() -> {
            counter3 = count(texts, 5);
        });

        threadThreeLetters.start();
        threadFourLetters.start();
        threadFiveLetters.start();

        threadThreeLetters.join();
        threadFourLetters.join();
        threadFiveLetters.join();

        System.out.println("Красивых слов с длиной 3: " + counter1 + " шт");
        System.out.println("Красивых слов с длиной 4: " + counter2 + " шт");
        System.out.println("Красивых слов с длиной 5: " + counter3 + " шт");
        System.out.println("Main ends!!!");
    }

    public static AtomicInteger count(String texts[], int number) {
        AtomicInteger counter = new AtomicInteger();
        for (String text : texts) {
            if (text.length() == number) {

                if (isPalindrome(text)) {
                    counter.incrementAndGet();
                }
                if (checkAllRepeated(text)) {
                    counter.incrementAndGet();
                }
                if (isAscendingOrdering(text)) {
                    counter.incrementAndGet();
                }
            }
        }
        return counter;
    }

    // is the word a palindrome
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




