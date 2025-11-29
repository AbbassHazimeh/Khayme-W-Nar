package lb.edu.ul.khayme_w_nar.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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
import lb.edu.ul.khayme_w_nar.databinding.FragmentEditProfileBinding;
import lb.edu.ul.khayme_w_nar.db.AppDatabase;
import lb.edu.ul.khayme_w_nar.db.DatabaseManager;
import lb.edu.ul.khayme_w_nar.db.Users;

public class EditProfileFragment extends Fragment {
    private FragmentEditProfileBinding binding;
    private static AppDatabase db;
    private int userId;
    private EditText fullnameEditText;
    private EditText emailEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button saveButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        userId = (int) prefs.getLong("current_user_id", -1);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        db = DatabaseManager.getInstance(requireContext());
        fullnameEditText = binding.fullnameEditText;
        emailEditText = binding.emailEditText;
        usernameEditText = binding.usernameEditText;
        passwordEditText = binding.passwordEditText;
        saveButton = binding.saveButton;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadCurrentUserDetails();
        saveButton.setOnClickListener(v -> {
            if (validateInputs()) {
                updateUserProfile();
            }
        });
    }
    private void loadCurrentUserDetails() {
        new Thread(() -> {
            Users user = db.usersDao().getUserInfo(userId).get(0);
            requireActivity().runOnUiThread(() -> {
                fullnameEditText.setText(user.getFullname());
                emailEditText.setText(user.getEmail());
                usernameEditText.setText(user.getUsername());
            });
        }).start();
    }
    private boolean validateInputs() {
        boolean isValid = true;

        if (TextUtils.isEmpty(fullnameEditText.getText().toString().trim())) {
            fullnameEditText.setError("Full name cannot be empty");
            isValid = false;
        }

        if (TextUtils.isEmpty(emailEditText.getText().toString().trim())) {
            emailEditText.setError("Email cannot be empty");
            isValid = false;
        }

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

    private void updateUserProfile() {
        new Thread(() -> {
            String newFullname = fullnameEditText.getText().toString().trim();
            String newEmail = emailEditText.getText().toString().trim();
            String newUsername = usernameEditText.getText().toString().trim();
            String newPassword = passwordEditText.getText().toString().trim();
            requireActivity().runOnUiThread(() -> {
                new Thread(() -> {
                    int emailCount = db.usersDao().checkEmailExists(newEmail);
                    int usernameCount = db.usersDao().checkUsernameExists(newUsername);
                    requireActivity().runOnUiThread(() -> {
                        boolean proceedWithUpdate = true;
                        if (proceedWithUpdate) {
                            new Thread(() -> {
                                db.usersDao().updateUser(userId, newFullname, newEmail, newUsername, newPassword);
                                requireActivity().runOnUiThread(() -> {
                                    Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
                                    NavController navController = Navigation.findNavController(requireView());
                                    navController.navigateUp();
                                });
                            }).start();
                        }
                    });
                }).start();
            });
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}