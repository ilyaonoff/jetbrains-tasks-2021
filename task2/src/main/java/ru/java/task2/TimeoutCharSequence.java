package ru.java.task2;

public class TimeoutCharSequence implements CharSequence {
    public static final String INTERRUPTED_MESSAGE = "Interrupted";
    private final CharSequence consumingCharSeq;
    private final long timeout;

    public TimeoutCharSequence(CharSequence charSeq, long timeoutLimit) {
        consumingCharSeq = charSeq;
        timeout = System.currentTimeMillis() + (timeoutLimit < 0 ? 0 : timeoutLimit);
    }

    @Override
    public int length() {
        return consumingCharSeq.length();
    }

    @Override
    public char charAt(int index) {
        if (System.currentTimeMillis() > timeout) {
            throw new RuntimeException(INTERRUPTED_MESSAGE);
        }
        return consumingCharSeq.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return new TimeoutCharSequence(consumingCharSeq.subSequence(start, end),
                timeout - System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return consumingCharSeq.toString();
    }

}
