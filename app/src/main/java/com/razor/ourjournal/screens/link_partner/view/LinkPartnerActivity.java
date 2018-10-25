package com.razor.ourjournal.screens.link_partner.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.razor.ourjournal.R;
import com.razor.ourjournal.screens.link_partner.viewmodel.LinkPartnerActivityViewModel;
import com.razor.ourjournal.repository.IRepositoryCallback;
import com.razor.ourjournal.repository.SharedPreferencesRepository;
import com.razor.ourjournal.repository.UserRepository;
import com.razor.ourjournal.screens.timeline.view.TimelineActivity;

public class LinkPartnerActivity extends AppCompatActivity implements LinkPartnerActivityView, View.OnClickListener, IRepositoryCallback {

    Button shareButton;
    TextInputEditText emailAddressInput;
    LinkPartnerActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_partner);

        initUiElements();

        viewModel = new LinkPartnerActivityViewModel(this, new UserRepository(this), new SharedPreferencesRepository(getApplicationContext()));
    }

    private void initUiElements() {
        shareButton = (Button) findViewById(R.id.btnShare);
        emailAddressInput = (TextInputEditText) findViewById(R.id.edtEmailAddress);

        emailAddressInput.addTextChangedListener(emailAddressInputTextChangedListener);
        shareButton.setOnClickListener(this);
    }

    TextWatcher emailAddressInputTextChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            viewModel.handleEmailTyped(editable.toString());
        }
    };

    @Override
    public void showShareButton() {
        shareButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideShareButton() {
        shareButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void navigateToTimeline() {
        Intent destination = new Intent(this, TimelineActivity.class);
        startActivity(destination);
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnShare) {
            viewModel.linkUserWithPartner();
        }
    }

    @Override
    public void onSuccess(@NonNull Bundle bundle) {
        viewModel.linkPartnerOnSuccess();
    }

    @Override
    public void onCancelled(@NonNull Bundle bundle) {

    }
}
