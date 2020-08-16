package mooc.vandy.java4android.diamonds.firstapp.local.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import mooc.vandy.java4android.diamonds.firstapp.model.entity.Account;

@Dao
public interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Account account);

    @Query("select * from accounts ")
    List<Account> getAll();

    @Query("delete from accounts")
    void deleteAll();

    @Query("select id from accounts where email = :email")
    String getId(String email);

    @Query("select password from accounts where email = :email")
    String getPassword(String email);

    @Query("select email from accounts where email = :email")
    String getEmail(String email);

    @Query("select name from accounts where id = :id")
    String getName(String id);



    @Update
    void update(Account account);

    @Delete
    void delete(Account account);
}
