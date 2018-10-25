package com.razor.ourjournal.utils.validator;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;

public class TextValidator extends InputValidator {
    private final String validationErrorMessage;

    public TextValidator(@NonNull TextInputLayout textInputLayout, String validationErrorMessage) {
        super(textInputLayout);
        this.validationErrorMessage = validationErrorMessage;
    }

    @Override
    protected boolean validate(@NonNull String text) {
        if (TextUtils.isEmpty(text)) {
            textInputLayout.setError(validationErrorMessage);
        } else {
            textInputLayout.setError(null);
        }
        return textInputLayout.getError() == null;
    }
}
