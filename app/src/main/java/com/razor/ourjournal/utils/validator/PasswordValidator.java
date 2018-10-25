package com.razor.ourjournal.utils.validator;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;

import com.razor.ourjournal.utils.StringUtils;

public class PasswordValidator extends InputValidator {

    private final String invalidFormatMessage;
    private final String tooShortMessage;
    private final Integer minLength;

    public PasswordValidator(@NonNull TextInputLayout textInputLayout, String invalidFormatMessage) {
        this(textInputLayout, invalidFormatMessage, null, null);
    }

    public PasswordValidator(@NonNull TextInputLayout textInputLayout,
                             String invalidFormatMessage, String tooShortMessage,
                             Integer minLength) {
        super(textInputLayout);
        this.invalidFormatMessage = invalidFormatMessage;
        this.tooShortMessage = tooShortMessage;
        this.minLength = minLength;
    }

    @Override
    protected boolean validate(@NonNull String text) {
        if (!isInputFormatValid(text)) {
            textInputLayout.setError(invalidFormatMessage);
        } else if (!satisfiesMinLength(text)) {
            textInputLayout.setError(tooShortMessage);
        } else {
            textInputLayout.setError(null);
        }
        return textInputLayout.getError() == null;
    }

    private boolean isInputFormatValid(String input) {
        return !StringUtils.isBlank(input);
    }

    private boolean satisfiesMinLength(String input) {
        return (minLength == null || input.length() >= minLength);
    }
}
