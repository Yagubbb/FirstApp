package mooc.vandy.java4android.diamonds.firstapp.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import mooc.vandy.java4android.diamonds.firstapp.local.dao.AccountDao;
import mooc.vandy.java4android.diamonds.firstapp.local.dao.PostDao;
import mooc.vandy.java4android.diamonds.firstapp.model.entity.Account;
import mooc.vandy.java4android.diamonds.firstapp.model.entity.Post;

@Database(entities = {Account.class, Post.class}, version = 2, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {

    public static String LOGGED_IN_USER_ID;

    public static final String DATABASE_NAME = "AppDatabase";

    public static AppDatabase instance;

    public static AppDatabase getDatabase(Context context){

    synchronized (AppDatabase.class){
        if (instance == null){

            instance = Room.databaseBuilder(context, AppDatabase.class,DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    }

    public abstract AccountDao getAccountDao();

    public abstract PostDao getPostDao();


}
