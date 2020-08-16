package mooc.vandy.java4android.diamonds.firstapp.ui.fragments.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import mooc.vandy.java4android.diamonds.firstapp.R;
import mooc.vandy.java4android.diamonds.firstapp.local.AppDatabase;

public class ProfilePageFragment extends Fragment {

    private TextView textViewName;
    private TextView textViewFullName;
    private ImageView imageViewProfilePhoto;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_page,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewName = view.findViewById(R.id.text_view_account_name_profile);
        textViewFullName = view.findViewById(R.id.text_view_full_name);
        imageViewProfilePhoto = view.findViewById(R.id.image_view_profile_photo);
        loadProfilePhoto();

        textViewFullName.setText(AppDatabase.LOGGED_IN_USER_ID);
        textViewName.setText(AppDatabase.LOGGED_IN_USER_ID);
    }

    private void loadProfilePhoto() {

        Glide.with(imageViewProfilePhoto.getContext()).load("https://images.pexels.com/photos/771742/pexels-photo-771742.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500").circleCrop().into(imageViewProfilePhoto);

    }
}
