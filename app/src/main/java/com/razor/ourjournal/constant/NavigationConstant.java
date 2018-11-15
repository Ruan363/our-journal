package com.razor.ourjournal.constant;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.razor.ourjournal.constant.NavigationConstant.IDENTIFIER.DOWNLOAD_URL;
import static com.razor.ourjournal.constant.NavigationConstant.IDENTIFIER.FROM;
import static com.razor.ourjournal.constant.NavigationConstant.NAME.POST_REPO;
import static com.razor.ourjournal.constant.NavigationConstant.NAME.UPLOAD_REPO;

@Retention(RetentionPolicy.SOURCE)
@StringDef({FROM, DOWNLOAD_URL, POST_REPO, UPLOAD_REPO})
public @interface NavigationConstant {

    @interface IDENTIFIER {
        String FROM = "From";
        String DOWNLOAD_URL = "Download Url";
    }

    @interface NAME {
        String POST_REPO = "Post Repository";
        String UPLOAD_REPO = "Upload Repository";
    }
}
