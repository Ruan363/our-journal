package com.razor.ourjournal.utils.validator;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;

import com.razor.ourjournal.utils.StringUtils;

public class OtpValidator extends InputValidator {

    private final String invalidFormatMessage;
    private final String invalidLengthMessage;
    private final int otpCharCount;

    public OtpValidator(@NonNull TextInputLayout textInputLayout,
                        String invalidFormatMessage, String invalidLengthMessage,
                        int otpCharCount) {
        super(textInputLayout);
        this.invalidLengthMessage = invalidLengthMessage;
        this.invalidFormatMessage = invalidFormatMessage;
        this.otpCharCount = otpCharCount;
    }

    @Override
    protected boolean validate(@NonNull String text) {
        if (!isInputFormatValid(text)) {
            textInputLayout.setError(invalidFormatMessage);
        } else if (!isInputLengthValid(text)) {
            textInputLayout.setError(invalidLengthMessage);
        } else {
            textInputLayout.setError(null);
        }
        return textInputLayout.getError() == null;
    }

    private boolean isInputFormatValid(String input) {
        return !input.isEmpty() && StringUtils.isNumeric(input);
    }

    private boolean isInputLengthValid(String input) {
        return otpCharCount == input.length();
    }
}
