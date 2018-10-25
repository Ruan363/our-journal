package com.razor.ourjournal.repository;

import android.content.Context;

import com.razor.ourjournal.screens.login.view.LoginActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class SharedPreferencesRepositoryTest {

    @Test
    public void verify_partnerEmail_isSet() throws Exception {
        Context context = Robolectric.buildActivity(LoginActivity.class).create().get();
        SharedPreferencesRepository repository = new SharedPreferencesRepository(context);

        repository.setPartnerEmail("test@test.com");

        assertTrue(repository.hasPartner());
    }
}