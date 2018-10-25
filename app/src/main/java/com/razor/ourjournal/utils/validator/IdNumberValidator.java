package com.razor.ourjournal.utils.validator;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;

import com.razor.ourjournal.utils.StringUtils;

public class IdNumberValidator extends InputValidator {

    private final String invalidFormatMessage;

    public IdNumberValidator(@NonNull TextInputLayout textInputLayout, String invalidFormatMessage) {
        super(textInputLayout);
        this.invalidFormatMessage = invalidFormatMessage;
    }

    @Override
    protected boolean validate(@NonNull String text) {
        if (!StringUtils.isValidId(text)) {
            textInputLayout.setError(invalidFormatMessage);
            return false;
        } else {
            textInputLayout.setError(null);
            return true;
        }
    }
}
