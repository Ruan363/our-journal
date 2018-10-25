package com.razor.ourjournal.add_post.viewmodel;

import com.google.firebase.auth.FirebaseUser;
import com.razor.ourjournal.BuildConfig;
import com.razor.ourjournal.screens.add_post.view.AddPostActivityView;
import com.razor.ourjournal.repository.IPostRepository;
import com.razor.ourjournal.repository.ISharedPreferencesRepository;
import com.razor.ourjournal.repository.IUserRepository;
import com.razor.ourjournal.screens.add_post.viewmodel.AddPostActivityViewModel;
import com.razor.ourjournal.screens.timeline.model.Post;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class AddPostActivityViewModelTest {

    @Mock
    private AddPostActivityView view;
    @Mock
    private IPostRepository postRepository;
    @Mock
    private ISharedPreferencesRepository sharedPreferencesRepository;
    @Mock
    private IUserRepository userRepository;
    @Mock
    private FirebaseUser firebaseUser;

    private AddPostActivityViewModel viewModel;

    private String testDescription = "Test Description";
    private String testTitle = "Test Title";
    private String testUserEmail = "user@email.com";
    private String testPartnerEmail = "partner@email.com";

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        when(userRepository.getFirebaseUser()).thenReturn(firebaseUser);
        viewModel = new AddPostActivityViewModel(view, postRepository, sharedPreferencesRepository, userRepository);
    }

    @Test
    public void showPostButton_when_titleChanged_if_postTitleAndDescriptionNotEmpty() throws Exception {
        viewModel.descriptionChanged(testDescription);

        verify(view, never()).showPostButton();

        viewModel.titleChanged(testTitle);

        verify(view).showPostButton();
    }

    @Test
    public void hidePostButton_when_titleChanged_if_titleOrDescriptionEmpty() throws Exception {
        viewModel.titleChanged("");

        verify(view).hidePostButton();
    }

    @Test
    public void showPostButton_when_DescriptionChanged_if_postTitleAndDescriptionNotEmpty() throws Exception {
        viewModel.titleChanged(testTitle);

        verify(view, never()).showPostButton();

        viewModel.descriptionChanged(testDescription);

        verify(view).showPostButton();
    }

    @Test
    public void hidePostButton_when_descriptionChanged_if_titleOrDescriptionEmpty() throws Exception {
        viewModel.descriptionChanged("");

        verify(view).hidePostButton();
    }

    @Test
    public void showProgressLoader_when_postButtonClicked() throws Exception {
        viewModel.postClicked();

        verify(view).showProgressLoader();
    }

    @Test
    public void disableUiElements_when_postButtonClicked() throws Exception {
        viewModel.postClicked();

        verify(view).disableUiElements();
    }

    @Test
    public void postRepoAddPost_when_postButtonClicked() throws Exception {
        FirebaseUser firebaseUser = mock(FirebaseUser.class);
        when(userRepository.getFirebaseUser()).thenReturn(firebaseUser);
        when(firebaseUser.getEmail()).thenReturn(testUserEmail);
        when(sharedPreferencesRepository.getPartnerEmail()).thenReturn(testPartnerEmail);

        viewModel = new AddPostActivityViewModel(view, postRepository, sharedPreferencesRepository, userRepository);

        viewModel.titleChanged(testTitle);
        viewModel.descriptionChanged(testDescription);

        viewModel.postClicked();

        Post post = new Post(testTitle, testDescription, testUserEmail, testPartnerEmail);
        verify(postRepository).addPost(post);
    }

    @Test
    public void hideProgressLoader_closeScreen_when_postsUpdated() throws Exception {
        viewModel.postOnSuccess();

        InOrder inOrder = inOrder(view);
        inOrder.verify(view).hideProgressLoader();
        inOrder.verify(view).enableUiElements();
        inOrder.verify(view).closeScreen();
    }
}