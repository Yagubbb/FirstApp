package mooc.vandy.java4android.diamonds.firstapp.ui.fragments.register2;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mooc.vandy.java4android.diamonds.firstapp.R;
import mooc.vandy.java4android.diamonds.firstapp.local.AppDatabase;
import mooc.vandy.java4android.diamonds.firstapp.local.dao.AccountDao;
import mooc.vandy.java4android.diamonds.firstapp.model.entity.Account;

public class RegisterPage2Fragment extends Fragment {

    private AccountDao accountDao;

    private ImageView imageViewBack;
    private TextView textViewSecondStep;
    private MaterialButton buttonSignUp;
    private EditText editTextNewPassword;
    private EditText editTextConfirmNewPassword;
    private String newName;
    private String newEmail;

    private NavController navController;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        navController = NavHostFragment.findNavController(this);
        return inflater.inflate(R.layout.fragment_register_page_2, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("infoKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String receivedName = result.getString("name");
                String receivedEmail = result.getString("email");
                newName = receivedName;
                newEmail = receivedEmail;
            }
        });
        textViewSecondStep = view.findViewById(R.id.text_view_steps_2);
        imageViewBack = view.findViewById(R.id.image_view_register_page_2_back);
        buttonSignUp = view.findViewById(R.id.button_register_page_2_sign_up);
        editTextNewPassword = view.findViewById(R.id.edit_text_new_password);
        editTextConfirmNewPassword = view.findViewById(R.id.edit_text_new_password_confirm);
        configureDatabase();
        setStepSecondText();
        setOnClickListeners();
    }

    public void setOnClickListeners() {
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navController.popBackStack();
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword = editTextNewPassword.getText().toString();
                String confirmPassword = editTextConfirmNewPassword.getText().toString();
                if (checkInputs(newPassword,confirmPassword)){

                    AppDatabase.LOGGED_IN_USER_ID = accountDao.getId(newEmail);
                    Account newAccount = new Account(UUID.randomUUID().toString(), newName, newPassword, newEmail);
                    accountDao.insert(newAccount);
                    navController.navigate(R.id.containerFragment);
                }
            }
        });
    }

    public void configureDatabase() {
        AppDatabase appDatabase = AppDatabase.getDatabase(getContext());
        this.accountDao = appDatabase.getAccountDao();
    }

    public boolean validationOfInputs(String newPassword, String confirmPassword) {
        return !confirmPassword.isEmpty() && !newPassword.isEmpty();


    }

    public boolean checkPasswords(String newPassword, String confirmPassword) {
        return newPassword.equals(confirmPassword);
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public boolean checkInputs(String enteredPassword, String enteredConfirmPassword) {

        if (validationOfInputs(enteredPassword, enteredConfirmPassword)) {
            if (isValidPassword(enteredPassword)) {
                if (checkPasswords(enteredPassword, enteredConfirmPassword)) {
                    return true;
                } else
                    Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();

            }
            Toast.makeText(getContext(), "Invalid password\n" +
                    "Password should contain capital letter(at least 1),number,  special characters longer and longer than 8 letters ", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getContext(), "Enter and confirm password", Toast.LENGTH_SHORT).show();
        return false;
    }
    private void setStepSecondText() {

        String string = "2 /2\nsteps";

        SpannableString spannableString = new SpannableString(string);

        AbsoluteSizeSpan stringTwenty = new AbsoluteSizeSpan(20, true);
        AbsoluteSizeSpan stringTwentyFour = new AbsoluteSizeSpan(24, true);

        spannableString.setSpan(stringTwenty, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(stringTwentyFour, 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textViewSecondStep.setText(spannableString);
    }
}

