package mooc.vandy.java4android.diamonds.firstapp.ui.fragments.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.textfield.TextInputEditText;

import mooc.vandy.java4android.diamonds.firstapp.R;
import mooc.vandy.java4android.diamonds.firstapp.local.AppDatabase;
import mooc.vandy.java4android.diamonds.firstapp.local.dao.AccountDao;

public class LoginPageFragment extends Fragment {

    private NavController navController;

    private ImageView imageViewBack;
    private TextView textViewSignUpp;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogIn;

    private AccountDao accountDao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        navController = NavHostFragment.findNavController(this);

        return inflater.inflate(R.layout.fragment_login_page, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViewSignUpp = view.findViewById(R.id.text_view_sign_up);
        imageViewBack = view.findViewById(R.id.image_view_login_page_back);
        buttonLogIn = view.findViewById(R.id.button_log_in);
        editTextEmail = view.findViewById(R.id.edit_text_email);
        editTextPassword = view.findViewById(R.id.edit_text_password);
        setOnClickListeners();
    }

    public void setOnClickListeners() {

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.entryPageFragment);

            }
        });

        textViewSignUpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.registerPage1Fragment);
            }
        });
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase database = AppDatabase.getDatabase(getContext());
                accountDao = database.getAccountDao();

                String enteredEmail = editTextEmail.getText().toString();
                String enteredPassword = editTextPassword.getText().toString();

                if (isValidEmail(enteredEmail)){
                    if (!enteredEmail.equals("")){
                        if (accountDao.getEmail(enteredEmail) != null) {
                            if (!enteredPassword.isEmpty()){
                                if (enteredPassword.equals(accountDao.getPassword(enteredEmail))){
                                    navController.navigate(R.id.containerFragment);
                                } else Toast.makeText(getContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                            } else Toast.makeText(getContext(), "Enter the password", Toast.LENGTH_SHORT).show();
                        } else Toast.makeText(getContext(), "Email not found", Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(getContext(), "Enter the email", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(getContext(), "Invalid email", Toast.LENGTH_SHORT).show();

            }
        });
    }


    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
