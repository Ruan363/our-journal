package com.razor.ourjournal.utils.validator;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;

import com.razor.ourjournal.utils.StringUtils;

public class CellValidator extends InputValidator {
    private final String validationErrorMessage;

    public CellValidator(@NonNull TextInputLayout textInputLayout, String validationErrorMessage) {
        super(textInputLayout);
        this.validationErrorMessage = validationErrorMessage;
    }

    @Override
    protected boolean validate(@NonNull String text) {

        if (StringUtils.isCellphoneNumber(text)) {
            textInputLayout.setError(null);
        } else {
            textInputLayout.setError(validationErrorMessage);
        }

        return textInputLayout.getError() == null;
    }
}