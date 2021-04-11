package ru.java.task2;

public class InterruptibleCharSequence implements CharSequence {
    public static final String INTERRUPTED_MESSAGE = "Interrupted";
    private final CharSequence consumingCharSeq;

    public InterruptibleCharSequence(CharSequence charSeq) {
        consumingCharSeq = charSeq;
    }

    @Override
    public int length() {
        return consumingCharSeq.length();
    }

    @Override
    public char charAt(int index) {
        if (Thread.interrupted()) {
            throw new RuntimeException(INTERRUPTED_MESSAGE);
        }
        return consumingCharSeq.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return new InterruptibleCharSequence(consumingCharSeq.subSequence(start, end));
    }

    @Override
    public String toString() {
        return consumingCharSeq.toString();
    }
}
