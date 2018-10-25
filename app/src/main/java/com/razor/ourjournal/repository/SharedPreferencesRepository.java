package com.razor.ourjournal.repository;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.razor.ourjournal.R;

public class SharedPreferencesRepository implements ISharedPreferencesRepository {

    SharedPreferences sharedPreferences;
    Context context;

    public SharedPreferencesRepository(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.preferences_file_key), Context.MODE_PRIVATE);
        Log.i("", "");
    }

    @Override
    public boolean hasPartner() {
        String partnerEmail = sharedPreferences.getString(context.getString(R.string.shared_pref_key_partner_email), null);
        return partnerEmail != null;
    }

    @Override
    public void setPartnerEmail(String partnerEmail) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.shared_pref_key_partner_email), partnerEmail);
        editor.apply();
    }

    @Override
    public String getPartnerEmail() {
        return sharedPreferences.getString(context.getString(R.string.shared_pref_key_partner_email), null);
    }
}
