package mooc.vandy.java4android.diamonds.firstapp.local.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import mooc.vandy.java4android.diamonds.firstapp.model.entity.Post;

@Dao
public interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Post post);

    @Query("select * from posts")
    List<Post> getAllPosts();
}
