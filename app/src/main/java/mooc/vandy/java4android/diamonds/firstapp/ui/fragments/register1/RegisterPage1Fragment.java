package mooc.vandy.java4android.diamonds.firstapp.ui.fragments.register1;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Patterns;
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
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;

import mooc.vandy.java4android.diamonds.firstapp.R;
import mooc.vandy.java4android.diamonds.firstapp.local.AppDatabase;
import mooc.vandy.java4android.diamonds.firstapp.local.dao.AccountDao;

public class RegisterPage1Fragment extends Fragment {

    private AccountDao accountDao;

    private ImageView imageViewBack;
    private TextView textViewFirstStep;
    private TextView textViewLogIn;
    private MaterialButton buttonNext;
    private EditText editTextName;
    private EditText editTextEmail;

    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        navController = NavHostFragment.findNavController(this);

        return inflater.inflate(R.layout.fragment_register_page_1,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewFirstStep = view.findViewById(R.id.text_view_steps_2);
        textViewLogIn = view.findViewById(R.id.text_view_back_to_log_in);
        imageViewBack = view.findViewById(R.id.image_view_register_page_1_back);
        buttonNext = view.findViewById(R.id.button_register_page_2_sign_up);
        editTextName = view.findViewById(R.id.edit_text_new_password);
        editTextEmail = view.findViewById(R.id.edit_text_new_password_confirm);
         configureDatabase();
         setStepFirstText();
        setOnClickListeners();
    }

    public void configureDatabase(){
        AppDatabase appDatabase = AppDatabase.getDatabase(getContext());
        this.accountDao = appDatabase.getAccountDao();
    }


    public void setOnClickListeners(){

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.popBackStack();

            }
        });

        textViewLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.loginPageFragment);

            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredEmail = editTextEmail.getText().toString();
                String enteredName = editTextName.getText().toString();
                if (checkInputs(enteredName,enteredEmail)){
                    Bundle info = new Bundle();
                    info.putString("name",enteredName);
                    info.putString("email", enteredEmail);
                    getParentFragmentManager().setFragmentResult("infoKey",info);
                    navController.navigate(R.id.registerPage2Fragment);
                }

            }
        });
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public boolean checkInputs(String enteredName,String enteredEmail){
        if (!"".equals(enteredName)){
            if (isValidEmail(enteredEmail)){
                if (accountDao.getEmail(enteredEmail) == null )return true;
                else Toast.makeText(getContext(), "These email already used", Toast.LENGTH_SHORT).show();
            }else Toast.makeText(getContext(), "Invalid email", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(getContext(), "Enter your name", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void setStepFirstText() {

        String string = "1 /2\nsteps";

        SpannableString spannableString = new SpannableString(string);

        AbsoluteSizeSpan stringTwenty = new AbsoluteSizeSpan(20, true);
        AbsoluteSizeSpan stringTwentyFour = new AbsoluteSizeSpan(24, true);

        spannableString.setSpan(stringTwenty, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(stringTwentyFour, 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textViewFirstStep.setText(spannableString);
    }
}
