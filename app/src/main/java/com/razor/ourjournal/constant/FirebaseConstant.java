package com.razor.ourjournal.constant;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.razor.ourjournal.constant.FirebaseConstant.PATHS.POSTS;
import static com.razor.ourjournal.constant.FirebaseConstant.PATHS.USERS;

@Retention(RetentionPolicy.SOURCE)
@StringDef({POSTS,
USERS})
public @interface FirebaseConstant {
    @interface PATHS {
        String POSTS = "posts";
        String USERS = "users";
    }
}
