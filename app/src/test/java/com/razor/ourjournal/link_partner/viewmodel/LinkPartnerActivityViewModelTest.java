package com.razor.ourjournal.link_partner.viewmodel;

import com.razor.ourjournal.link_partner.view.LinkPartnerActivityView;
import com.razor.ourjournal.repository.ISharedPreferencesRepository;
import com.razor.ourjournal.repository.IUserRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class LinkPartnerActivityViewModelTest {

    @Mock
    LinkPartnerActivityView view;
    @Mock
    IUserRepository userRepository;
    @Mock
    ISharedPreferencesRepository sharedPreferencesRepository;

    LinkPartnerActivityViewModel viewModel;
    String partnerEmail;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        partnerEmail = "partner@test.com";

        viewModel = new LinkPartnerActivityViewModel(view, userRepository, sharedPreferencesRepository);
    }

    @Test
    public void showShareButton_when_emailValid() throws Exception {
        viewModel.handleEmailTyped(partnerEmail);

        verify(view).showShareButton();
    }

    @Test
    public void hideShareButton_when_emailNotValid() throws Exception {
        viewModel.handleEmailTyped("test");

        verify(view).hideShareButton();
    }

    @Test
    public void linkPartner_when_shareButtonPressed() throws Exception {
        viewModel.handleEmailTyped(partnerEmail);

        viewModel.linkUserWithPartner();

        verify(userRepository).linkWithPartner(partnerEmail);
    }

    @Test
    public void savePartnerEmail_inSharedPreffs_then_navigateToTimeline_when_linkPartnerOnSuccess() throws Exception {
        viewModel.handleEmailTyped(partnerEmail);

        viewModel.linkPartnerOnSuccess();

        InOrder inOrder = inOrder(sharedPreferencesRepository, view);
        inOrder.verify(sharedPreferencesRepository).setPartnerEmail(partnerEmail);
        inOrder.verify(view).navigateToTimeline();
    }
}