package com.razor.ourjournal.splash.viewmodel;


import android.os.Handler;

import com.razor.ourjournal.splash.view.SplashActivityView;

public class SplashActivityViewModel {

    SplashActivityView view;

    public SplashActivityViewModel(SplashActivityView view) {
        this.view = view;

        startCounterToNavigate();
    }

    private void startCounterToNavigate() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.navigateToLogin();
            }
        }, 3000);
    }
}
