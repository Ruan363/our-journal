package com.razor.ourjournal.utils.validator;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;

import com.razor.ourjournal.utils.StringUtils;

public class AlphaNumericValidator extends InputValidator {

    private final String invalidFormatMessage;

    public AlphaNumericValidator(@NonNull TextInputLayout textInputLayout, String invalidFormatMessage) {
        super(textInputLayout);
        this.invalidFormatMessage = invalidFormatMessage;
    }

    @Override
    protected boolean validate(@NonNull String text) {
        if (!isInputFormatValid(text)) {
            textInputLayout.setError(invalidFormatMessage);
        } else {
            textInputLayout.setError(null);
        }
        return textInputLayout.getError() == null;
    }

    private boolean isInputFormatValid(String input) {
        return !input.isEmpty() && StringUtils.isAlphanumericSpace(input);
    }
}
