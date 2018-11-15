package com.razor.ourjournal.repository;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.razor.ourjournal.screens.timeline.model.Post;

import java.util.List;

import static com.razor.ourjournal.constant.NavigationConstant.IDENTIFIER.DOWNLOAD_URL;
import static com.razor.ourjournal.constant.NavigationConstant.IDENTIFIER.FROM;
import static com.razor.ourjournal.constant.NavigationConstant.NAME.UPLOAD_REPO;
import static com.razor.ourjournal.constant.TimelineConstant.BUNDLE_IDENTIFIER.ERROR_MESSAGE;

public class UploadRepository implements IUploadRepository {

    private IRepositoryCallback repositoryCallback;

    public UploadRepository(IRepositoryCallback repositoryCallback) {
        this.repositoryCallback = repositoryCallback;
    }

    @Override
    public void uploadImageUriList(String postGroupId, String postId, List<Uri> imageUriList) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference attachmentsReference = storageRef.child(String.format("attachments/%s/%s/", postGroupId, postId));
        for (Uri uri : imageUriList) {
            uploadImageUri(attachmentsReference, uri);
        }
    }

    @Override
    public StorageReference getDownloadUrlPost(String postId, String userEmailPostedBy, String userEmailPostedFor) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference attachmentsReference = storageRef.child(String.format("attachments/%s/%s/",
                Post.getPostId(userEmailPostedBy, userEmailPostedFor), postId));
        return null;
    }

    private void uploadImageUri(StorageReference attachmentsReference, Uri uri) {
        final StorageReference imageReference = attachmentsReference.child(uri.getLastPathSegment());
        UploadTask uploadTask = imageReference.putFile(uri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Bundle bundle = new Bundle();
                bundle.putString(FROM, UPLOAD_REPO);
                bundle.putString(ERROR_MESSAGE, exception.getMessage());
                repositoryCallback.onCancelled(bundle);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Bundle bundle = new Bundle();
                bundle.putString(FROM, UPLOAD_REPO);
                repositoryCallback.onSuccess(bundle);
            }
        });
    }
}
