package lb.edu.ul.khayme_w_nar.ui.login_signup;

import android.content.Context;
import android.content.SharedPreferences;
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
import lb.edu.ul.khayme_w_nar.databinding.FragmentLoginBinding;
import lb.edu.ul.khayme_w_nar.db.AppDatabase;
import lb.edu.ul.khayme_w_nar.db.DatabaseManager;
import lb.edu.ul.khayme_w_nar.db.Users;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private static AppDatabase db;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private NavController navController;

    public LoginFragment() {
        super(R.layout.fragment_login);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        db = DatabaseManager.getInstance(requireContext());
        usernameEditText = binding.usernameEditText;
        passwordEditText = binding.passwordEditText;
        loginButton = binding.btnlogin;
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        loginButton.setOnClickListener(v -> performLogin());
        binding.signuptxt.setOnClickListener(v ->
                navController.navigate(R.id.action_loginFragment_to_signupFragment)
        );
    }
    private void performLogin() {
        loginButton.setEnabled(false);
        if (!validateInputs()) {
            loginButton.setEnabled(true);
            return;
        }
        String inputUsername = usernameEditText.getText().toString().trim();
        String inputPassword = passwordEditText.getText().toString().trim();
        new Thread(() -> {
            try {
                Users user = authenticateUser(inputUsername, inputPassword);
                requireActivity().runOnUiThread(() -> {
                    if (user != null) {
                        navigateToMainScreen(user);
                    } else {
                        showLoginError();
                    }
                    loginButton.setEnabled(true);
                });
            } catch (Exception e) {
                requireActivity().runOnUiThread(() -> {
                    Log.e("LoginError", "Authentication error", e);
                    Toast.makeText(requireContext(), "An error occurred during login", Toast.LENGTH_SHORT).show();
                    loginButton.setEnabled(true);
                });
            }
        }).start();
    }
    private boolean validateInputs() {
        boolean isValid = true;
        if (TextUtils.isEmpty(usernameEditText.getText().toString().trim())) {
            usernameEditText.setError("Username cannot be empty");
            isValid = false;
        }
        if (TextUtils.isEmpty(passwordEditText.getText().toString().trim())) {
            passwordEditText.setError("Password cannot be empty");
            isValid = false;
        }
        return isValid;
    }
    private Users authenticateUser(String username, String password) {
        Users user = db.usersDao().findUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            long userId = db.usersDao().getUserIdByUsername(username);
            SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            prefs.edit().putLong("current_user_id", userId).apply();
//     for debugging
            Log.d("id from authentication", "id from authentication: "+userId);
            db.usersDao().updateLoginStatus(userId, true);
            Log.d("isLoggedInStatus", "is logged in status after log in "+db.usersDao().getIsLoggedInById(userId));
            return user;
        }
        return null;
    }
    private void navigateToMainScreen(Users user) {
        Bundle userBundle = new Bundle();
        userBundle.putLong("userId", user.getUserId());
        userBundle.putBoolean("isLoggedIn", true);
        navController.navigate(R.id.action_loginFragment_to_availablecamps, userBundle);
    }
    private void showLoginError() {
        Toast.makeText(requireContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
        passwordEditText.setText(""); // Clear password field
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}