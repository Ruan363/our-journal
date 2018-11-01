package com.razor.ourjournal.timeline.viewmodel;

import android.os.Bundle;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseUser;
import com.razor.ourjournal.repository.PostRepository;
import com.razor.ourjournal.repository.SharedPreferencesRepository;
import com.razor.ourjournal.repository.UserRepository;
import com.razor.ourjournal.screens.timeline.model.Post;
import com.razor.ourjournal.screens.timeline.view.TimelineFragmentView;
import com.razor.ourjournal.screens.timeline.viewmodel.TimelineFragmentViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static com.razor.ourjournal.constant.TimelineConstant.BUNDLE_IDENTIFIER.ERROR_MESSAGE;
import static com.razor.ourjournal.constant.TimelineConstant.BUNDLE_IDENTIFIER.RESULT;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TimelineFragmentViewModelTest {

    @Mock
    TimelineFragmentView view;
    @Mock
    PostRepository postRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    SharedPreferencesRepository sharedPreferencesRepository;
    @Mock
    FirebaseUser firebaseUser;
    @Mock
    Bundle bundle;

    TimelineFragmentViewModel viewModel;
    ArrayList<Post> testPosts;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        viewModel = new TimelineFragmentViewModel(view, postRepository, sharedPreferencesRepository, userRepository);
    }

    @Test
    public void getPosts_when_viewModelCreated() throws Exception {
        String userEmail = "test.email@test.com";
        String partnerEmail = "partner.email@test.com";
        when(userRepository.getFirebaseUser()).thenReturn(firebaseUser);
        when(firebaseUser.getEmail()).thenReturn(userEmail);
        when(sharedPreferencesRepository.getPartnerEmail()).thenReturn(partnerEmail);

        viewModel.fetchPosts();

        verify(postRepository).getPosts("partneremail@testcom_testemail@testcom");
    }

    @Test
    public void onCancelled_showSnackbar_errorMessage() throws Exception {
        String errorMessage = "Test error message";
        when(bundle.getString(ERROR_MESSAGE)).thenReturn(errorMessage);

        viewModel.onFetchPostsCancelled(bundle);

        verify(view).showSnackbar(errorMessage);
    }

    @Test
    public void onCancelled_navigateToErrorFragment() throws Exception {
        String errorMessage = "Test error message";
        when(bundle.getString(ERROR_MESSAGE)).thenReturn(errorMessage);

        viewModel.onFetchPostsCancelled(bundle);

        verify(view).navigateToErrorFragment(errorMessage);
    }

    @Test
    public void onSuccess_displayPosts() throws Exception {
        setupPosts();

        viewModel.onFetchPostsSuccess(bundle);

        verify(view).displayPosts(testPosts);
    }

    private void setupPosts() {
        testPosts = new ArrayList<>();
        testPosts.add(new Post("Test A", "Description A", "userEmail", "partnerEmail"));
        testPosts.add(new Post("Test B", "Description B", "userEmail", "partnerEmail"));

        when(bundle.getParcelableArrayList(RESULT)).thenReturn(new ArrayList<Parcelable>(testPosts));
    }

    @Test
    public void goToAddPostActivity_when_addPost() throws Exception {
        viewModel.addPost();

        view.navigateToAddPostActivity();
    }
}