package com.razor.ourjournal.utils.validator;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;

import com.razor.ourjournal.utils.validator.BaseTextWatcher;

public abstract class InputValidator extends BaseTextWatcher {

    protected final TextInputLayout textInputLayout;

    public InputValidator(@NonNull TextInputLayout textInputLayout) {
        if (textInputLayout.getEditText() != null) {
            this.textInputLayout = textInputLayout;
            textInputLayout.setErrorEnabled(true);
            textInputLayout.getEditText().addTextChangedListener(this);
        } else {
            throw new IllegalArgumentException("TextInputLayout must wrap an EditText");
        }
    }

    @Override
    protected void textChanged(Editable input) {
        if (input != null) {
            validate(input.toString());
        }
    }

    /**
     * Used to perform explicit validation such as when the user presses `Next`
     * @param focusOnError - Whether or not to set focus to the EditText if validation failed
     * @return - Value indicating whether or not validation passed
     */
    public boolean validate(boolean focusOnError) {
        return validate(focusOnError, false);
    }

    public boolean validate(boolean focusOnError, boolean hideError) {
        if (textInputLayout.getEditText() != null) {
            boolean isValid = validate(textInputLayout.getEditText().getText().toString());

            if (hideError) {
                textInputLayout.setError(null);
                textInputLayout.getEditText().setError(null);
            }

            if (!isValid && focusOnError) {
                focusInput();
            }
            return isValid;
        }

        return false;
    }

    /**
     * Automatic validation that will be triggered by TextChange.
     * This method must be overridden in any inheriting class to describe how validity will be evaluated.
     * @param text - The input to be validated
     * @return - Value indicating whether or not validation passed
     */
    protected abstract boolean validate(@NonNull String text);

    private void focusInput() {
        textInputLayout.getParent().requestChildFocus(textInputLayout, textInputLayout);
        textInputLayout.requestFocus();
    }
}