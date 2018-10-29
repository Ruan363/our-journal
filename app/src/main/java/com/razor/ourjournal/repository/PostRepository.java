package com.razor.ourjournal.repository;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razor.ourjournal.R;
import com.razor.ourjournal.screens.timeline.model.Post;
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
    }

    @Override
    public void getPosts(String postId) {
        postDatabaseReference = FirebaseDatabase.getInstance().getReference(String.format("%s/%s", POSTS, postId));
        if (ConnectivityUtils.isNetworkAvailable(context)) {
            postDatabaseReference.orderByChild("date").addValueEventListener(getPostsListener);
        }
        else {
            callBackOnCancelled(context.getString(R.string.error_connection_failed));
        }
    }

    @Override
    public void addPost(Post post) {
        postDatabaseReference = FirebaseDatabase.getInstance().getReference(POSTS);
        if (ConnectivityUtils.isNetworkAvailable(context)) {
            postDatabaseReference.child(post.getPostId()).child(String.valueOf(post.hashCode())).setValue(post, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError == null) {
                        repositoryCallback.onSuccess(new Bundle());
                    } else {
                        callBackOnCancelled("Failed to add posts");
                    }
                }
            });

        }
        else {
            callBackOnCancelled(context.getString(R.string.error_connection_failed));
        }
    }

    private ValueEventListener getPostsListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            ArrayList<Post> posts = new ArrayList<>();
            try {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Post postSnapshotValue = postSnapshot.getValue(Post.class);
                    posts.add(postSnapshotValue);
                }
            } catch (Exception exception) {
                Log.e(PostRepository.class.getName(), "Failed to convert to post", exception.getCause());
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
