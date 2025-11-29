package lb.edu.ul.khayme_w_nar.ui.profile;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lb.edu.ul.khayme_w_nar.R;
import lb.edu.ul.khayme_w_nar.databinding.FragmentProfileBinding;
import lb.edu.ul.khayme_w_nar.db.AppDatabase;
import lb.edu.ul.khayme_w_nar.db.CampPlace;
import lb.edu.ul.khayme_w_nar.db.DatabaseManager;
import lb.edu.ul.khayme_w_nar.db.Reservations;
import lb.edu.ul.khayme_w_nar.db.Users;
import lb.edu.ul.khayme_w_nar.utils.CircularImageView;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private static AppDatabase db;
    private Button logoutBtn;
    private Button editProfileButton;
    private TextView usernameText;
    private TextView emailText;
    private TextView reservationDetailsText;
    private CircularImageView profileImage;
    private long userId;
    private ActivityResultLauncher<String[]> multiplePermissionsLauncher;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerImagePicker();
        registerMultiplePermissionsLauncher();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        userId = prefs.getLong("current_user_id", -1);

        // Redirect to login if not logged in
        if (userId == -1) {
            new Handler(Looper.getMainLooper()).post(() -> {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_main_activity);
                navController.navigate(R.id.action_profileFragment_to_loginFragment);
            });
            return new View(requireContext());
        }

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        db = DatabaseManager.getInstance(requireContext());
        logoutBtn = binding.logoutButton;
        editProfileButton = binding.editProfileButton;
        usernameText = binding.username;
        emailText = binding.email;
        profileImage = binding.profileImage;
        reservationDetailsText = binding.reservationDetails;
        profileImage.setOnClickListener(v -> checkLoginAndPerformAction(this::checkStoragePermissionAndPickImage));

        setupUI();
        return root;
    }

    private void setupUI() {
        loadUserProfile();
        loadProfileImage();
        loadUserReservations();
        logoutBtn.setText("Logout");
        editProfileButton.setVisibility(View.VISIBLE);
        logoutBtn.setOnClickListener(v -> performLogout());
        editProfileButton.setOnClickListener(v -> {
            try {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_main_activity);
                navController.navigate(R.id.action_profileFragment_to_editProfileFragment);
            } catch (Exception e) {
                Log.e("NavigationError", "Navigation failed", e);
                Toast.makeText(requireContext(), "Cannot navigate to edit profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerImagePicker() {
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri selectedImage = result.getData().getData();
                        saveProfileImage(selectedImage);
                    }
                }
        );
    }

    private void registerMultiplePermissionsLauncher() {
        multiplePermissionsLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                permissions -> {
                    boolean allGranted = true;
                    for (Boolean isGranted : permissions.values()) {
                        if (!isGranted) {
                            allGranted = false;
                            break;
                        }
                    }

                    if (allGranted) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        imagePickerLauncher.launch(intent);
                    } else {
                        new AlertDialog.Builder(requireContext())
                                .setTitle("Permissions Required")
                                .setMessage("Storage permissions are required to set a profile picture. Would you like to grant them now?")
                                .setPositiveButton("Settings", (dialog, which) -> {
                                    Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", requireContext().getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                })
                                .setNegativeButton("Cancel", null)
                                .show();
                    }
                }
        );
    }

    private void checkStoragePermissionAndPickImage() {
        String[] permissions = {Manifest.permission.READ_MEDIA_IMAGES};
        boolean allPermissionsGranted = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(requireContext(), permission)
                    != PackageManager.PERMISSION_GRANTED) {
                allPermissionsGranted = false;
                break;
            }
        }

        if (allPermissionsGranted) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imagePickerLauncher.launch(intent);
        } else {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Permission Required")
                    .setMessage("This app needs storage permissions to set your profile picture.")
                    .setPositiveButton("OK", (dialog, which) -> {
                        multiplePermissionsLauncher.launch(permissions);
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
    }

    private void loadUserProfile() {
        new Thread(() -> {
            List<Users> userInfo = db.usersDao().getUserInfo(userId);
            if (!userInfo.isEmpty()) {
                Users user = userInfo.get(0);
                requireActivity().runOnUiThread(() -> {
                    usernameText.setText(user.getUsername());
                    emailText.setText(user.getEmail());
                });
            }
        }).start();
    }

    private void loadUserReservations() {
        new Thread(() -> {
            List<Reservations> reservations = db.ResDAO().getResByUserId(userId);
            List<CampPlace> bookedCamps = db.CampDAO().getCampPlacesWithReservations(userId);
            requireActivity().runOnUiThread(() -> {
                if (reservations != null && !reservations.isEmpty()) {
                    StringBuilder reservationText = new StringBuilder();
                    reservationText.append("Your Reservations:\n\n");
                    Map<Integer, CampPlace> campMap = new HashMap<>();
                    for (CampPlace camp : bookedCamps) {
                        campMap.put(camp.getCampId(), camp);
                    }
                    for (int i = 0; i < reservations.size(); i++) {
                        Reservations res = reservations.get(i);
                        CampPlace camp = campMap.get(res.getCamp_Id());
                        reservationText.append("Reservation ").append(i + 1).append(":\n");
                        if (camp != null) {
                            reservationText.append("Camp: ").append(camp.getCampName()).append("\n");
                            reservationText.append("Location: ").append(camp.getCampLocation()).append("\n");
                        }
                        reservationText.append("From: ").append(res.getFromDate()).append("\n");
                        reservationText.append("To: ").append(res.getToDate()).append("\n");
                        reservationText.append("\n");
                    }
                    reservationDetailsText.setText(reservationText.toString());
                } else {
                    reservationDetailsText.setText("No reservations yet");
                }
            });
        }).start();
    }

    private void saveProfileImage(Uri imageUri) {
        try {
            InputStream inputStream = requireContext().getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            File outputDir = requireContext().getFilesDir();
            File outputFile = new File(outputDir, "profile_" + userId + ".jpg");
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
            new Thread(() -> {
                db.usersDao().updateProfileImage(userId, outputFile.getAbsolutePath());
                requireActivity().runOnUiThread(this::loadProfileImage);
            }).start();
        } catch (IOException e) {
            Toast.makeText(requireContext(), "Failed to save image", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void loadProfileImage() {
        File imageFile = new File(requireContext().getFilesDir(), "profile_" + userId + ".jpg");
        if (imageFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            profileImage.setImageBitmap(bitmap);
        } else {
            profileImage.setImageResource(R.drawable.rounded_profile_background);
        }
    }

    private void performLogout() {
        new Thread(() -> {
            db.usersDao().updateLoginStatus(userId, false);
            requireActivity().runOnUiThread(() -> {
                SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                prefs.edit().remove("current_user_id").apply();
                NavController navController = Navigation.findNavController(requireView());
                navController.navigate(R.id.action_profileFragment_to_loginFragment);
            });
        }).start();
    }

    private void checkLoginAndPerformAction(Runnable action) {
        action.run();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}