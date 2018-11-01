package com.razor.ourjournal.screens.link_partner.viewmodel;


import com.razor.ourjournal.screens.link_partner.view.LinkPartnerActivityView;
import com.razor.ourjournal.repository.ISharedPreferencesRepository;
import com.razor.ourjournal.repository.IUserRepository;

import static com.razor.ourjournal.utils.ValidationUtils.isValidEmail;

public class LinkPartnerActivityViewModel {

    private LinkPartnerActivityView view;
    private IUserRepository userRepository;
    private ISharedPreferencesRepository sharedPreferencesRepository;
    private String partnerEmail = null;

    public LinkPartnerActivityViewModel(LinkPartnerActivityView view, IUserRepository userRepository, ISharedPreferencesRepository sharedPreferencesRepository) {
        this.view = view;
        this.userRepository = userRepository;
        this.sharedPreferencesRepository = sharedPreferencesRepository;
    }

    public void handleEmailTyped(String email) {
        partnerEmail = email;
        if (isValidEmail(email)) {
            view.showShareButton();
        } else {
            view.hideShareButton();
        }
    }

    public void linkUserWithPartner() {
        view.showProgress();
        userRepository.linkWithPartner(partnerEmail);
    }

    public void linkPartnerOnSuccess() {
        sharedPreferencesRepository.setPartnerEmail(partnerEmail);
        view.hideProgress();
        view.navigateToTimeline();
    }
}
