package com.razor.ourjournal.splash.viewmodel;

import com.razor.ourjournal.BuildConfig;
import com.razor.ourjournal.screens.splash.view.SplashActivityView;
import com.razor.ourjournal.screens.splash.viewmodel.SplashActivityViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLooper;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SplashActivityViewModelTest {

    @Mock
    SplashActivityView view;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void verify_navigateToLogin() throws Exception {
        new SplashActivityViewModel(view);

        ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

        verify(view).navigateToLogin();
    }
}