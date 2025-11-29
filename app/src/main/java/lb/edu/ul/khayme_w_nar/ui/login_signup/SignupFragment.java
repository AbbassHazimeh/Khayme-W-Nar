package lb.edu.ul.khayme_w_nar.ui.login_signup;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import lb.edu.ul.khayme_w_nar.R;
import lb.edu.ul.khayme_w_nar.databinding.FragmentSignupBinding;
import lb.edu.ul.khayme_w_nar.db.AppDatabase;
import lb.edu.ul.khayme_w_nar.db.DatabaseManager;
import lb.edu.ul.khayme_w_nar.db.Users;
import lb.edu.ul.khayme_w_nar.ui.booked_camps.BookedCampsFragment;
import lb.edu.ul.khayme_w_nar.ui.profile.ProfileFragment;

public class SignupFragment extends Fragment {
    private FragmentSignupBinding binding;
    private static AppDatabase db;
    private EditText fullname;
    private EditText email;
    private EditText username;
    private EditText password;
    private Button signupBtn;

    public SignupFragment() {
        super(R.layout.fragment_signup);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSignupBinding.inflate(inflater, container, false);
        db = DatabaseManager.getInstance(requireContext());
        fullname = binding.fullnameEditText;
        email = binding.emailEditText;
        password = binding.passwordEditText;
        username = binding.usernameEditText;
        signupBtn = binding.btnsignup;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);

        signupBtn.setOnClickListener(v -> {
            if (!validateInputs()) {
                return;
            }
            new Thread(() -> {
                String emailText = email.getText().toString();
                String usernameText = username.getText().toString();
                int emailCount = db.usersDao().checkEmailExists(emailText);
                int usernameCount = db.usersDao().checkUsernameExists(usernameText);
                requireActivity().runOnUiThread(() -> {
                    if (emailCount > 0) {
                        email.setError("This email is already registered");
                        return;
                    }
                    if (usernameCount > 0) {
                        username.setError("This username is already taken");
                        return;
                    }
                    performSignup(navController);
                });
            }).start();
        });
    }
    private boolean validateInputs() {
        boolean isValid = true;
        if (TextUtils.isEmpty(fullname.getText().toString())) {
            fullname.setError("You should provide your full name");
            isValid = false;
        }
        if (TextUtils.isEmpty(email.getText().toString())) {
            email.setError("You should provide your email");
            isValid = false;
        }
        if (TextUtils.isEmpty(username.getText().toString())) {
            username.setError("You should provide a username");
            isValid = false;
        }
        if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError("You should set a password");
            isValid = false;
        }
        return isValid;
    }

    private void performSignup(NavController navController) {
        new Thread(() -> {
            Users user = new Users();
            user.setEmail(email.getText().toString());
            user.setFullname(fullname.getText().toString());
            user.setPassword(password.getText().toString());
            user.setUsername(username.getText().toString());
            user.setLoggedIn(true);
            long id = db.usersDao().Insert(user);
            requireActivity().runOnUiThread(() -> {
                Bundle idBundle = new Bundle();
                idBundle.putLong("userId", id);

                Log.d("userID Debug", "User Id from sign up: " + id);
                navController.navigate(R.id.action_signupFragment_to_loginFragment, idBundle);
            });
        }).start();
    }
}