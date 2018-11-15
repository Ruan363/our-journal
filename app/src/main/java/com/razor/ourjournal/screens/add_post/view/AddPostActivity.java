package com.razor.ourjournal.screens.add_post.view;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.asksira.bsimagepicker.BSImagePicker;
import com.razor.ourjournal.R;
import com.razor.ourjournal.repository.UploadRepository;
import com.razor.ourjournal.screens.add_post.adapter.PostAttachmentsAdapter;
import com.razor.ourjournal.screens.add_post.viewmodel.AddPostActivityViewModel;
import com.razor.ourjournal.repository.IRepositoryCallback;
import com.razor.ourjournal.repository.PostRepository;
import com.razor.ourjournal.repository.SharedPreferencesRepository;
import com.razor.ourjournal.repository.UserRepository;
import com.razor.ourjournal.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.razor.ourjournal.constant.NavigationConstant.IDENTIFIER.DOWNLOAD_URL;
import static com.razor.ourjournal.constant.NavigationConstant.IDENTIFIER.FROM;
import static com.razor.ourjournal.constant.NavigationConstant.NAME.POST_REPO;
import static com.razor.ourjournal.constant.NavigationConstant.NAME.UPLOAD_REPO;

public class AddPostActivity extends AppCompatActivity implements AddPostActivityView, IRepositoryCallback, BSImagePicker.OnMultiImageSelectedListener {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PERMISSION_CAMERA = 2;

    @BindView(R.id.edtPostTitle)
    TextInputEditText titleEditText;
    @BindView(R.id.edtPostDetail)
    TextInputEditText detailEditText;
    @BindView(R.id.progPost)
    ProgressBar progPost;
    @BindView(R.id.btnPost)
    Button postButton;
    @BindView(R.id.attachments_bar)
    RecyclerView attachmentBarRecyclerView;
    private AddPostActivityViewModel viewModel;
    private BSImagePicker imagePicker;
    private RecyclerView.LayoutManager layoutManager;
    private PostAttachmentsAdapter adapter;
    private String currentPhotoPath;
    private boolean postUploadSuccessful;
    private boolean imageUploadSuccessful;
    private int amountImagesSuccessfullyUploaded = 0;
    private List<String> downloadUrlList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        ButterKnife.bind(this);

        initUiElements();

        buildImagePicker();

        viewModel = new AddPostActivityViewModel(this, new PostRepository(this, getApplicationContext()),
                new SharedPreferencesRepository(this), new UserRepository(), new UploadRepository(this));
    }

    private void buildImagePicker() {
        imagePicker = new BSImagePicker.Builder("com.razor.ourjournal.fileprovider")
                .isMultiSelect() //Set this if you want to use multi selection mode.
                .setMaximumMultiSelectCount(6) //Default: Integer.MAX_VALUE (i.e. User can select as many images as he/she wants)
                .setMultiSelectBarBgColor(android.R.color.white) //Default: #FFFFFF. You can also set it to a translucent color.
                .setMultiSelectTextColor(R.color.primary_text) //Default: #212121(Dark grey). This is the message in the multi-select bottom bar.
                .setMultiSelectDoneTextColor(R.color.colorAccent) //Default: #388e3c(Green). This is the color of the "Done" TextView.
                .setOverSelectTextColor(R.color.error_text) //Default: #b71c1c. This is the color of the message shown when user tries to select more than maximum select count.
                .build();
    }

    private void initUiElements() {
        titleEditText.addTextChangedListener(titleTextWatcher);
        detailEditText.addTextChangedListener(detailTextWatcher);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        attachmentBarRecyclerView.setLayoutManager(layoutManager);
    }

    @OnClick(R.id.gallery_btn)
    public void galleryClick() {
        imagePicker.show(getSupportFragmentManager(), "picker");
    }

    @OnClick(R.id.camera_btn)
    public void cameraClick() {
        if (ContextCompat.checkSelfPermission(AddPostActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            launchCameraIntent();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(AddPostActivity.this,
                    Manifest.permission.CAMERA)) {
                showPermissionRationaleDialog();
            } else {
                ActivityCompat.requestPermissions(AddPostActivity.this, new String[]{Manifest.permission.CAMERA},
                        PERMISSION_CAMERA);

            }
        }
    }

    private void showPermissionRationaleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage(R.string.camera_permission_explanation)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.request_permission, requestPermissionClickListener);

        builder.show();
    }

    DialogInterface.OnClickListener requestPermissionClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            ActivityCompat.requestPermissions(AddPostActivity.this, new String[]{Manifest.permission.CAMERA},
                    PERMISSION_CAMERA);
        }
    };

    private void launchCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = FileUtils.createImageFile(getApplicationContext());
                currentPhotoPath = photoFile.getAbsolutePath();
            } catch (IOException ex) {
                Log.e(AddPostActivity.class.getName(), "Failed to create file", ex);
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.razor.ourjournal.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @OnClick(R.id.btnPost)
    public void postClick() {
        List<Uri> imageList = null;
        if (adapter != null) {
            imageList = adapter.getData();
        }
        viewModel.generatePost();
        viewModel.postClicked();
        if (imageList == null || imageList.isEmpty()) {
            viewModel.uploadPost(null);
        } else {
            viewModel.uploadAttachments(imageList);
        }
    }

    TextWatcher titleTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            viewModel.titleChanged(editable.toString());
        }
    };

    TextWatcher detailTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            viewModel.descriptionChanged(editable.toString());
        }
    };

    @Override
    public void showPostButton() {
        postButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePostButton() {
        postButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showProgressLoader() {
        progPost.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressLoader() {
        progPost.setVisibility(View.GONE);
    }

    @Override
    public void closeScreen() {
        finish();
    }

    @Override
    public void disableUiElements() {
        titleEditText.setEnabled(false);
        detailEditText.setEnabled(false);
        postButton.setEnabled(false);
    }

    @Override
    public void enableUiElements() {
        titleEditText.setEnabled(true);
        detailEditText.setEnabled(true);
        postButton.setEnabled(true);
    }

    @Override
    public void onSuccess(@NonNull Bundle bundle) {
        String from = bundle.getString(FROM);
        if (from == null) {
            return;
        }
        if (from.equals(POST_REPO)) {
            postUploadSuccessful = true;
        } else if (from.equals(UPLOAD_REPO)) {
            String downloadUrl = bundle.getString(DOWNLOAD_URL);
            if (downloadUrlList == null) {
                downloadUrlList = new ArrayList<>();
            }
            downloadUrlList.add(downloadUrl);
            amountImagesSuccessfullyUploaded += 1;
            Toast.makeText(AddPostActivity.this, "Images Successfully uploaded: " + amountImagesSuccessfullyUploaded, Toast.LENGTH_SHORT).show();
            if (adapter.getItemCount() == amountImagesSuccessfullyUploaded) {
                imageUploadSuccessful = true;
            }
        }

        if (!postUploadSuccessful && imageUploadSuccessful) {
            viewModel.uploadPost(downloadUrlList);
        }

        if (postUploadSuccessful && (imageUploadSuccessful || adapter == null || adapter.getItemCount() == 0)) {
            viewModel.postOnSuccess();
        }
    }

    @Override
    public void onCancelled(@NonNull Bundle bundle) {
        String from = bundle.getString(FROM);
        if (from == null) {
            return;
        }
        if (from.equals(POST_REPO)) {
            postUploadSuccessful = false;
        }
    }

    @Override
    public void onMultiImageSelected(List<Uri> uriList) {
        handleAttachmentsSelected(uriList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            FileUtils.galleryAddFile(getApplicationContext(), currentPhotoPath);
            Uri contentUri = Uri.fromFile(new File(currentPhotoPath));
            List<Uri> cameraList = new ArrayList<>();
            cameraList.add(contentUri);
            handleAttachmentsSelected(cameraList);
        }
    }

    private void handleAttachmentsSelected(List<Uri> uriList) {
        attachmentBarRecyclerView.setVisibility(View.VISIBLE);
        if (adapter == null) {
            adapter = new PostAttachmentsAdapter(uriList);
            attachmentBarRecyclerView.setAdapter(adapter);
        } else {
            adapter.addAttachments(uriList);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    launchCameraIntent();
                }
            }
        }
    }
}
