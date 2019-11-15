package com.example.servicelist;


import android.app.Activity;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import java.io.File;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestFragment extends Fragment {

    UploadDataForm uploadDataForm;

    public static String TAG = "RequestFragment";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap bitmap;
    ImageButton imageButton;
    RxValidationForm rxValidationForm ;
    MenuItem item;
    EditText name;
    EditText comment;

   private int STATUS_RESPONSE_CODE_OK = 201;

    String currentPhotoPath;
    Uri uriLastImage = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: RequestFragment ");
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request, container, false);

        this.imageButton = view.findViewById(R.id.PhotoButton);
        this.name = view.findViewById(R.id.person_name);
        this.comment = view.findViewById(R.id.comment);

        this.uploadDataForm = new UploadDataForm(getContext(), name, comment, imageButton);
        this.rxValidationForm =  new RxValidationForm(name, comment);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        rxValidationForm.init();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult: requestCode "+ requestCode +" "+ resultCode);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // "/storage/emulated/0/Android/data/com.example.servicelist/files/Pictures/photo.jpg"
            Log.i(TAG, "onActivityResult: bitmap" + bitmap);

            imageButton.setBackground(Drawable.createFromPath(currentPhotoPath));
           /* Glide.with(this)
                    .load(currentPhotoPath)
                    .into((ImageButton)getView().findViewById(R.id.PhotoButton));*/
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.i(TAG, "dispatchTakePictureIntent: photoFile Ex " + ex);
                // Error occurred while creating the File
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.servicelist",
                        photoFile);

                this.uriLastImage = photoURI;

                Log.i(TAG, "dispatchTakePictureIntent: "+photoURI);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }

    private File createImageFile() throws IOException {
        String imageFileName = "photo.jpg";

        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir, imageFileName);
       // File image = File.createTempFile( imageFileName, ".jpg", storageDir);
        this.currentPhotoPath = image.getAbsolutePath();
        Log.i(TAG, "createImageFile:currentPhotoPath "+ image.getName()+ " --- " +image.getAbsolutePath());
        return image;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
        this.item = menu.findItem(R.id.menu_send);

        String nameStr = name.getText().toString();
        String com = comment.getText().toString();

        this.rxValidationForm =  new RxValidationForm(item);
        rxValidationForm.onDisableMenuBtn(nameStr, com, item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_send:

              int sts = uploadDataForm.sendData(uriLastImage, currentPhotoPath);
              boolean t = sts==STATUS_RESPONSE_CODE_OK;
              boolean r = uriLastImage!= null;

                Log.i(TAG, "onOptionsItemSelected: " +sts +" "+ t + " " +r );
                if (sts==STATUS_RESPONSE_CODE_OK & uriLastImage!= null){

                    Log.i(TAG, "onOptionsItemSelected: clear");
                    this.uriLastImage = null;
                }
                break;
        }
            return true;
    }

    @Override
    public void onPrepareOptionsMenu (Menu menu) {
        Log.i(TAG, "onPrepareOptionsMenu: ");

    }




}
