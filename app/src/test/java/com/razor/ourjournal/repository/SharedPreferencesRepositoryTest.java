package com.razor.ourjournal.repository;

import android.content.Context;

import com.razor.ourjournal.BuildConfig;
import com.razor.ourjournal.login.view.LoginActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SharedPreferencesRepositoryTest {

    @Test
    public void verify_partnerEmail_isSet() throws Exception {
        Context context = Robolectric.buildActivity(LoginActivity.class).create().get();
        SharedPreferencesRepository repository = new SharedPreferencesRepository(context);

        repository.setPartnerEmail("test@test.com");

        assertTrue(repository.hasPartner());
    }
}