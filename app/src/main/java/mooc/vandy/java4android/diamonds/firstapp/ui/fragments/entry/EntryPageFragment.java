package mooc.vandy.java4android.diamonds.firstapp.ui.fragments.entry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;


import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import mooc.vandy.java4android.diamonds.firstapp.R;

public class EntryPageFragment extends Fragment {

    private NavController navController;

    private MaterialButton buttonNavigateToLoginPage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        navController = NavHostFragment.findNavController(this);

        return inflater.inflate(R.layout.fragment_entry_page,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonNavigateToLoginPage = view.findViewById(R.id.button_entry_log_in);
        setOnClickListeners();
    }

    public void setOnClickListeners(){

        buttonNavigateToLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.loginPageFragment);
            }
        });
    }
}
