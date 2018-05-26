package com.razor.ourjournal.constant;


import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.razor.ourjournal.constant.TimelineConstant.BUNDLE_IDENTIFIER.ERROR_MESSAGE;
import static com.razor.ourjournal.constant.TimelineConstant.BUNDLE_IDENTIFIER.RESULT;

@Retention(RetentionPolicy.SOURCE)
@StringDef({RESULT, ERROR_MESSAGE})
public @interface TimelineConstant {

    @interface BUNDLE_IDENTIFIER {
        String RESULT = "result";
        String ERROR_MESSAGE = "errorMessage";
    }
}