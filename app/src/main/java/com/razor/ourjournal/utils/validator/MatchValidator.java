package com.razor.ourjournal.utils.validator;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import com.razor.ourjournal.utils.validator.InputValidator;

public class MatchValidator extends InputValidator {

    private final EditText input;
    private final EditText inputToMatch;
    private final String noMatchMessage;

    public MatchValidator(@NonNull final TextInputLayout textInputLayout, String noMatchMessage, @NonNull EditText inputToMatch) {
        super(textInputLayout);
        this.noMatchMessage = noMatchMessage;
        this.input = textInputLayout.getEditText();
        this.inputToMatch = inputToMatch;

        if (input != null) {
            inputToMatch.addTextChangedListener(new InputValidator(textInputLayout) {
                @Override
                public boolean validate(@NonNull String text) {
                    String matchInput = input.getText().toString();
                    if (!matchInput.isEmpty()) {
                        return MatchValidator.this.validate(matchInput);
                    }
                    return false;
                }
            });
        }
    }

    @Override
    protected boolean validate(@NonNull String text) {
        if (!doFieldsMatch(text)) {
            textInputLayout.setError(noMatchMessage);
        } else {
            textInputLayout.setError(null);
        }

        return textInputLayout.getError() == null;
    }

    private boolean doFieldsMatch(String input) {
        return input.equals(inputToMatch.getText().toString());
    }
}
