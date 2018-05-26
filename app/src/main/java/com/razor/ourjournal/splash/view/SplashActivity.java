package com.razor.ourjournal.splash.view;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.razor.ourjournal.R;
import com.razor.ourjournal.login.view.LoginActivity;
import com.razor.ourjournal.splash.viewmodel.SplashActivityViewModel;
import com.razor.ourjournal.timeline.view.TimelineActivity;

public class SplashActivity extends AppCompatActivity implements SplashActivityView {

    SplashActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_splash);

        viewModel = new SplashActivityViewModel(this);
    }

    @Override
    public void navigateToLogin() {
        Intent destination = new Intent(this, LoginActivity.class);
        startActivity(destination);
        finish();
    }
}
