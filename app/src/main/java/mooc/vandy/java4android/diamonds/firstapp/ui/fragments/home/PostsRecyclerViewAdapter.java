package mooc.vandy.java4android.diamonds.firstapp.ui.fragments.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import mooc.vandy.java4android.diamonds.firstapp.R;
import mooc.vandy.java4android.diamonds.firstapp.model.entity.Post;

public class PostsRecyclerViewAdapter extends RecyclerView.Adapter<ItemPostViewHolder> {

    private List<Post> listOfPosts = new ArrayList<>();
    private File filesDir;

    public PostsRecyclerViewAdapter(File filesDir){this.filesDir = filesDir;}

    @NonNull
    @Override
    public ItemPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_post,parent,false);
        return new ItemPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemPostViewHolder holder, int position) {

        holder.bindItem(listOfPosts.get(position),filesDir);

    }

    @Override
    public int getItemCount() {
        return listOfPosts.size();
    }

    public void submitList(List<Post> posts){
        listOfPosts.clear();
        listOfPosts.addAll(posts);
        notifyDataSetChanged();

    }
}
