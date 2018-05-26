package com.razor.ourjournal.repository;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razor.ourjournal.R;
import com.razor.ourjournal.timeline.model.Post;
import com.razor.ourjournal.utils.ConnectivityUtils;

import java.util.ArrayList;

import static com.razor.ourjournal.constant.FirebaseConstant.PATHS.POSTS;
import static com.razor.ourjournal.constant.TimelineConstant.BUNDLE_IDENTIFIER.ERROR_MESSAGE;
import static com.razor.ourjournal.constant.TimelineConstant.BUNDLE_IDENTIFIER.RESULT;

public class PostRepository implements IPostRepository {

    private DatabaseReference postDatabaseReference;
    private IRepositoryCallback repositoryCallback;
    private Context context;

    public PostRepository(IRepositoryCallback repositoryCallback, Context context) {
        this.repositoryCallback = repositoryCallback;
        this.context = context;

        postDatabaseReference = FirebaseDatabase.getInstance().getReference().child(POSTS);
    }

    @Override
    public void getPosts() {
        if (ConnectivityUtils.isNetworkAvailable(context)) {
            postDatabaseReference.addValueEventListener(getPostsListener);
        }
        else {
            callBackOnCancelled(context.getString(R.string.error_connection_failed));
        }
    }

    @Override
    public void addPost(Post post) {
        if (ConnectivityUtils.isNetworkAvailable(context)) {
            postDatabaseReference.removeEventListener(getPostsListener);
            postDatabaseReference.setValue(post);
        }
        else {
            callBackOnCancelled(context.getString(R.string.error_connection_failed));
        }
    }

    private ValueEventListener getPostsListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            ArrayList<Post> posts = new ArrayList<>();
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                posts.add(postSnapshot.getValue(Post.class));
            }

            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(RESULT, posts);

            repositoryCallback.onSuccess(bundle);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            callBackOnCancelled(databaseError.getMessage());
        }
    };

    private void callBackOnCancelled(String errorMessage) {
        Bundle bundle = new Bundle();
        bundle.putString(ERROR_MESSAGE, errorMessage);

        repositoryCallback.onCancelled(bundle);
    }
}
