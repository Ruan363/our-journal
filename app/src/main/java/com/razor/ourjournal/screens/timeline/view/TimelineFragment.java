package com.razor.ourjournal.screens.timeline.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.razor.ourjournal.R;
import com.razor.ourjournal.repository.SharedPreferencesRepository;
import com.razor.ourjournal.repository.UserRepository;
import com.razor.ourjournal.screens.add_post.view.AddPostActivity;
import com.razor.ourjournal.screens.error.view.ErrorFragment;
import com.razor.ourjournal.repository.IRepositoryCallback;
import com.razor.ourjournal.repository.PostRepository;
import com.razor.ourjournal.screens.timeline.adapter.TimelineAdapter;
import com.razor.ourjournal.screens.timeline.model.Post;
import com.razor.ourjournal.screens.timeline.viewmodel.TimelineFragmentViewModel;

import java.util.List;

public class TimelineFragment extends Fragment implements TimelineFragmentView, IRepositoryCallback, View.OnClickListener {

    public static final String TAG = TimelineFragment.class.getSimpleName();
    TimelineFragmentViewModel viewModel;

    View view;
    RecyclerView postsRecyclerView;
    FloatingActionButton addPostButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new TimelineFragmentViewModel(this, new PostRepository(this, getContext()),
                new SharedPreferencesRepository(getContext()), new UserRepository());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_timeline, container, false);
        initUIElements();
        return view;
    }

    private void initUIElements() {
        postsRecyclerView = view.findViewById(R.id.timelineRecyclerView);
        addPostButton = view.findViewById(R.id.fabAdd);
        addPostButton.setOnClickListener(this);

        configureRecyclerView();
    }

    private void configureRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        postsRecyclerView.setLayoutManager(layoutManager);
        postsRecyclerView.setHasFixedSize(false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.fetchPosts();
    }

    @Override
    public void onSuccess(@NonNull Bundle bundle) {
        viewModel.onFetchPostsSuccess(bundle);
    }

    @Override
    public void onCancelled(@NonNull Bundle bundle) {
        viewModel.onFetchPostsCancelled(bundle);
    }

    @Override
    public void navigateToErrorFragment(String errorMessage) {
        ErrorFragment errorFragment = new ErrorFragment();

        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.content_container, errorFragment).commit();
    }

    @Override
    public void displayPosts(List<Post> posts) {
        TimelineAdapter adapter = new TimelineAdapter(posts);

        postsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showSnackbar(String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void navigateToAddPostActivity() {
        Intent destination = new Intent(getContext(), AddPostActivity.class);
        startActivity(destination);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fabAdd) {
            viewModel.addPost();
        }
    }
}
