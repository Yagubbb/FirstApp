package mooc.vandy.java4android.diamonds.firstapp.ui.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import mooc.vandy.java4android.diamonds.firstapp.R;
import mooc.vandy.java4android.diamonds.firstapp.local.AppDatabase;
import mooc.vandy.java4android.diamonds.firstapp.local.dao.PostDao;

public class HomePageFragment extends Fragment {

    private RecyclerView recyclerView;

    private PostDao postDao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        postDao = AppDatabase.getDatabase(getContext()).getPostDao();
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view_posts);
        configureRecyclerView();
   }

   private void configureRecyclerView(){
        PostsRecyclerViewAdapter adapter = new PostsRecyclerViewAdapter(requireActivity().getFilesDir());
        recyclerView.setAdapter(adapter);
        adapter.submitList(postDao.getAllPosts());


   }
}
