package com.razor.ourjournal.utils.validator;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * @author izak
 * @since 2018/07/31
 */
public abstract class BaseTextWatcher implements TextWatcher {

    @Override
    public final void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Don't care */ }

    @Override
    public final void onTextChanged(CharSequence s, int start, int before, int count) { /* Don't care */ }

    @Override
    public void afterTextChanged(Editable input) {
        textChanged(input);
    }

    protected abstract void textChanged(Editable input);
}
