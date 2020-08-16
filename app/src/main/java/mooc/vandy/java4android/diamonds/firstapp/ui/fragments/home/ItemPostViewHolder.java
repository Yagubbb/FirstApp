package mooc.vandy.java4android.diamonds.firstapp.ui.fragments.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;

import mooc.vandy.java4android.diamonds.firstapp.R;
import mooc.vandy.java4android.diamonds.firstapp.local.AppDatabase;
import mooc.vandy.java4android.diamonds.firstapp.local.dao.AccountDao;
import mooc.vandy.java4android.diamonds.firstapp.model.entity.Post;

public class ItemPostViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewName;
    private ImageView imageViewSharedPhoto;
    private ImageView imageViewProfilePhoto;



    public ItemPostViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewName = itemView.findViewById(R.id.text_view_account_name);
        imageViewSharedPhoto = itemView.findViewById(R.id.image_view_shared_photo);
        imageViewProfilePhoto = itemView.findViewById(R.id.image_view_profile_photo);

        loadProfilePhoto();

    }

    public void bindItem(Post post, File filesDir) {

        AccountDao accountDao = AppDatabase.getDatabase(imageViewSharedPhoto.getContext()).getAccountDao();
        textViewName.setText(accountDao.getName(post.getAccountID()));

        Glide.with(imageViewSharedPhoto.getContext()).load(new File(filesDir,post.getId() + ".png")).into(imageViewSharedPhoto);

    }

    private void loadProfilePhoto() {

        Glide.with(imageViewProfilePhoto.getContext()).load("https://images.pexels.com/photos/771742/pexels-photo-771742.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500").circleCrop().into(imageViewProfilePhoto);

    }
}
