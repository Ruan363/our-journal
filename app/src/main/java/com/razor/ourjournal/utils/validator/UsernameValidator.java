package com.razor.ourjournal.utils.validator;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;

public class UsernameValidator extends InputValidator {
    private static final String USERNAME_EXPRESSION = "^[\\w.]+$";

    private final String invalidFormatMessage;
    private final String tooShortMessage;
    private final String tooLongMessage;
    private final Integer minLength;
    private final Integer maxLength;

    public UsernameValidator(@NonNull TextInputLayout textInputLayout, String invalidFormatMessage) {
        this(textInputLayout, invalidFormatMessage, null, null, null, null);
    }

    public UsernameValidator(@NonNull TextInputLayout textInputLayout,
                             String invalidFormatMessage, String tooShortMessage, String tooLongMessage,
                             Integer minLength, Integer maxLength) {
        super(textInputLayout);
        this.invalidFormatMessage = invalidFormatMessage;
        this.tooShortMessage = tooShortMessage;
        this.tooLongMessage = tooLongMessage;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    protected boolean validate(@NonNull String text) {
        if (!isInputFormatValid(text)) {
            textInputLayout.setError(invalidFormatMessage);
        } else if (!satisfiesMinLength(text)) {
            textInputLayout.setError(tooShortMessage);
        } else if (!satisfiesMaxLength(text)) {
            textInputLayout.setError(tooLongMessage);
        } else {
            textInputLayout.setError(null);
        }
        return textInputLayout.getError() == null;
    }

    private boolean isInputFormatValid(String input) {
        return input.matches(USERNAME_EXPRESSION);
    }

    private boolean satisfiesMinLength(String input) {
        return (minLength == null || input.length() >= minLength);
    }

    private boolean satisfiesMaxLength(String input) {
        return (maxLength == null || input.length() <= maxLength);
    }
}
