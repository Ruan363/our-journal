package com.razor.ourjournal.screens.add_post.view;


public interface AddPostActivityView {
    void showPostButton();

    void hidePostButton();

    void showProgressLoader();

    void disableUiElements();

    void hideProgressLoader();

    void closeScreen();

    void enableUiElements();
}
