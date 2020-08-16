package mooc.vandy.java4android.diamonds.firstapp.ui.fragments.share;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import mooc.vandy.java4android.diamonds.firstapp.R;
import mooc.vandy.java4android.diamonds.firstapp.local.AppDatabase;
import mooc.vandy.java4android.diamonds.firstapp.local.dao.AccountDao;
import mooc.vandy.java4android.diamonds.firstapp.local.dao.PostDao;
import mooc.vandy.java4android.diamonds.firstapp.model.entity.Post;

import static android.app.Activity.RESULT_OK;

public class SharePageFragment extends Fragment {

    private ImageView imageViewSharedPhoto;
    private ImageView imageViewProfilePhoto;
    private TextView textViewName;
    private Switch switchComments;
    private TextInputLayout textInputLayoutDescription;
    private Button buttonShare;

    private PostDao postDao;

    private boolean isImageChosen;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_page, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageViewSharedPhoto = view.findViewById(R.id.image_view_shared_photo);
        imageViewProfilePhoto = view.findViewById(R.id.image_view_profile_photo);
        textViewName = view.findViewById(R.id.text_view_account_name_share_page);
        switchComments = view.findViewById(R.id.switch_comments);
        textInputLayoutDescription = view.findViewById(R.id.text_input_layout);
        buttonShare = view.findViewById(R.id.button_share);


        AccountDao accountDao = AppDatabase.getDatabase(textViewName.getContext()).getAccountDao();
        textViewName.setText(accountDao.getName(AppDatabase.LOGGED_IN_USER_ID));

        postDao = AppDatabase.getDatabase(getContext()).getPostDao();

        setOnClickListeners();
        loadProfilePhoto();

    }


    private void setOnClickListeners() {

        imageViewSharedPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(requireContext(), SharePageFragment.this);
            }
        });

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isImageChosen) {
                    String id = UUID.randomUUID().toString();

                    postDao.insert(new Post(id, Objects.requireNonNull(textInputLayoutDescription.getEditText()).getText().toString(), AppDatabase.LOGGED_IN_USER_ID, switchComments.isSelected()));
                    writeImageToInternalStorage(((BitmapDrawable) imageViewSharedPhoto.getDrawable()).getBitmap(), id);
                    Toast.makeText(getContext(), "Post shared!", Toast.LENGTH_SHORT).show();

                } else Toast.makeText(getContext(), "Invalid photo", Toast.LENGTH_SHORT).show();


            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult activityResult = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                assert activityResult != null;
                Uri resultUri = activityResult.getUri();
                imageViewSharedPhoto.setImageURI(resultUri);
                imageViewSharedPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);

                isImageChosen = true;
            }
        }
    }

    private void writeImageToInternalStorage(Bitmap bitmap, String id) {

        File file = new File(requireActivity().getFilesDir(), id + ".png");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert fos != null;
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void loadProfilePhoto() {

        Glide.with(this).load("https://images.pexels.com/photos/771742/pexels-photo-771742.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500").centerCrop().into(imageViewProfilePhoto);

    }
}


