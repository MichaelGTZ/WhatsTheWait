package com.example.whatsthewait.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.whatsthewait.BitmapScalar;
import com.example.whatsthewait.LoginActivity;
import com.example.whatsthewait.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";

    // Items on the fragment_profile.xml layout
    private ImageView ivProfilePic;
    private TextView tvUsername;
    private TextView tvChangeProfilePic;
    private EditText etChangeUsername;
    private EditText etChangePassword;
    private TextView tvLogOut;

    // For using camera to take and set profile picture
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1134;
    public String profilePhotoFileName = "profile_%d.jpg";
    private File profilePicFile;
    private Bitmap resizedProfilePicBitmap;

    // For picking a profile picture from gallery
    public final static int PICK_PHOTO_CODE = 1136;

    public ProfileFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialization of each layout item
        ivProfilePic = view.findViewById(R.id.ivprofilePic);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser.has("profilePicture")) {
            ParseFile profilePicFile = (ParseFile) currentUser.get("profilePicture");
            try {
                File profilePic = profilePicFile.getFile();
                Bitmap profilePicBitmap = BitmapFactory.decodeFile(profilePic.getAbsolutePath());
                Glide.with(getContext())
                        .load(profilePicBitmap)
                        .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(10)))
                        .placeholder(R.color.colorAccent)
                        .error(R.drawable.ic_launcher_background)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(ivProfilePic);
            } catch (ParseException e) {
                Log.e(TAG, "onViewCreated: Failed to load profile pic", e);
            }
        } else {
            ivProfilePic.setImageDrawable(getResources().getDrawable(R.drawable.user_unselected));
        }

        tvUsername = view.findViewById(R.id.tvUsername);
        tvUsername.setText(currentUser.getUsername());

        tvChangeProfilePic = view.findViewById(R.id.tvChangeProfilePic);
        registerForContextMenu(tvChangeProfilePic);

        tvChangeProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvChangeProfilePic.showContextMenu();
            }
        });

        etChangeUsername = view.findViewById(R.id.etChangeUsername);
        etChangeUsername.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    // Call password change method
                    currentUser.setUsername(String.valueOf(etChangeUsername.getText()));
                    currentUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Log.i(TAG, "done: Username successfully changed");
                            Toast.makeText(getContext(), "Username successfully changed", Toast.LENGTH_SHORT).show();
                            etChangeUsername.setText("");
                            tvUsername.setText(currentUser.getUsername());
                        }
                    });
                    return true;
                }
                return false;
            }
        });

        etChangePassword = view.findViewById(R.id.etChangePassword);
        etChangePassword.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    currentUser.setPassword(String.valueOf(etChangePassword.getText()));
                    currentUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Log.i(TAG, "done: password successfully changed");
                            Toast.makeText(getContext(), "Password successfully changed", Toast.LENGTH_SHORT).show();
                            etChangePassword.setText("");
                        }
                    });
                    return true;
                }
                return false;
            }
        });

        tvLogOut = view.findViewById(R.id.tvLogOut);
        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick log out button");
                logoutUser();
            }
        });
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        ((Activity) getContext()).getMenuInflater().inflate(R.menu.set_profile_picture, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.takePhoto:
                launchCamera(getView());
                return true;
            case R.id.chooseFromLibrary:
                onPickPhoto(getView());
                return true;
            case R.id.removeCurrentPhoto:
                Toast.makeText(getContext(), "Remove photo selected", Toast.LENGTH_SHORT).show(); // Change to actually remove photo from preview and from parse
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void launchCamera(View view) {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access using current timestamp
        profilePicFile = getPhotoFileUri(String.format(profilePhotoFileName, System.currentTimeMillis()));

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.whatsthewait.fileprovider", profilePicFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE); // Calls onActivityResult() upon completion
        }
    }

    // Trigger gallery selection for a photo
    public void onPickPhoto(View view) {
        // Create intent for picking a photo from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            // Bring up gallery to select a photo
            startActivityForResult(intent, PICK_PHOTO_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                try {
                    saveProfilePic(currentUser, profilePicFile);
                } catch (IOException e) {
                    Log.e(TAG, "Save Post failed from button click listener", e);
                }
                //Bitmap rawTakenImage = BitmapFactory.decodeFile(profilePicFile.getAbsolutePath());
                // Load the taken image into a preview
                //ivProfilePic.setImageBitmap(rawTakenImage);
            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
        if ((data != null) && requestCode == PICK_PHOTO_CODE) {
            ParseUser currentUser = ParseUser.getCurrentUser();
            Uri photoUri = data.getData();
            profilePicFile = new File(String.valueOf(photoUri));
            try {
                savePickedProfilePic(currentUser, profilePicFile, photoUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveProfilePic(ParseUser currentUser, File profilePicFile) throws IOException {
        // See code above
        Uri takenPhotoUri = Uri.fromFile(getPhotoFileUri(profilePicFile.getName()));
        // by this point we have the camera photo on disk
        Bitmap rawTakenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());
        // See BitmapScaler.java: https://gist.github.com/nesquena/3885707fd3773c09f1bb
        resizedProfilePicBitmap = BitmapScalar.scaleToFitWidth(rawTakenImage, 1080);
        // Configure byte output stream
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        // Compress the image further
        resizedProfilePicBitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
        // Create a new file for the resized bitmap (`getPhotoFileUri` defined above)
        File resizedFile = getPhotoFileUri("resized_" + profilePicFile.getName());
        resizedFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(resizedFile);
        // Write the bytes of the bitmap to file
        fos.write(bytes.toByteArray());
        fos.close();

        currentUser.put("profilePicture", new ParseFile(resizedFile));

        currentUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getContext(), "Error while saving", Toast.LENGTH_SHORT);
                }
                Log.i(TAG, "Post save was successful");
                // Load the selected image into a preview
                ivProfilePic.setImageBitmap(resizedProfilePicBitmap);
            }
        });
    }

    private void savePickedProfilePic(ParseUser currentUser, File profilePicFile, Uri photoUri) throws IOException {
        // Load the image located at photoUri into selectedImage
        Bitmap selectedImage = loadFromUri(photoUri);
        // See BitmapScaler.java: https://gist.github.com/nesquena/3885707fd3773c09f1bb
        resizedProfilePicBitmap = BitmapScalar.scaleToFitWidth(selectedImage, 1080);
        // Configure byte output stream
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        // Compress the image further
        resizedProfilePicBitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
        // Create a new file for the resized bitmap (`getPhotoFileUri` defined above)
        File resizedFile = getPhotoFileUri("resized2_" + profilePicFile.getName());
        resizedFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(resizedFile);
        // Write the bytes of the bitmap to file
        fos.write(bytes.toByteArray());
        fos.close();

        currentUser.put("profilePicture", new ParseFile(resizedFile));

        currentUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getContext(), "Error while saving", Toast.LENGTH_SHORT);
                }
                Log.i(TAG, "Post save was successful");
                // Load the selected image into a preview
                ivProfilePic.setImageBitmap(selectedImage);
            }
        });
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    public Bitmap loadFromUri(Uri photoUri) {
        Bitmap image = null;
        try {
            // check version of Android on device
            if(Build.VERSION.SDK_INT > 27){
                // on newer versions of Android, use the new decodeBitmap method
                ImageDecoder.Source source = ImageDecoder.createSource(getContext().getContentResolver(), photoUri);
                image = ImageDecoder.decodeBitmap(source);
            } else {
                // support older versions of Android by using getBitmap
                image = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), photoUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private void logoutUser() {
        ParseUser.logOut();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        if (getActivity() != null) {
            getActivity().finish();
        }
    }
}
