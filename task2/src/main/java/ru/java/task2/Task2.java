package ru.java.task2;

import java.util.regex.Pattern;

public class Task2 {
    static final int MAX_RUNNING_TIME_IN_MS = 3000;

    public static boolean matches(String text, String regex) {
        class BooleanHolder {
            boolean bool = false;
        }

        Pattern pattern = Pattern.compile(regex);

        BooleanHolder booleanHolder = new BooleanHolder();

        Runnable runnableForMatching =
                () -> booleanHolder.bool = pattern.matcher(new InterruptibleCharSequence(text)).matches();

        Thread threadForMatching = new Thread(runnableForMatching);

        threadForMatching.start();
        try {
            threadForMatching.join(MAX_RUNNING_TIME_IN_MS);
        } catch (InterruptedException ignored) {
        }
        threadForMatching.interrupt();

        return booleanHolder.bool;
    }
}
