package mooc.vandy.java4android.diamonds.firstapp.ui.activities;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import mooc.vandy.java4android.diamonds.firstapp.R;
import mooc.vandy.java4android.diamonds.firstapp.local.AppDatabase;
import mooc.vandy.java4android.diamonds.firstapp.local.dao.AccountDao;

public class MainActivity extends AppCompatActivity {


    private AccountDao accountDao;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureDatabase();
    }

    public void configureDatabase(){
        AppDatabase appDatabase = AppDatabase.getDatabase(getApplicationContext());
        this.accountDao = appDatabase.getAccountDao();
    }

}