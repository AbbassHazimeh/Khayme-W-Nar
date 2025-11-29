package lb.edu.ul.khayme_w_nar.ui.community;

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
import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.room.Room;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import lb.edu.ul.khayme_w_nar.R;
import lb.edu.ul.khayme_w_nar.db.AppDatabase;
import lb.edu.ul.khayme_w_nar.db.DatabaseManager;
import lb.edu.ul.khayme_w_nar.db.Experience;

public class AddExperienceFragment extends Fragment {
    private AppDatabase db;
    private Executor executor;
    private int experienceId = -1; // To store the experience ID (for editing)

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_experience, container, false);
        db = DatabaseManager.getInstance(requireContext());
        executor = Executors.newSingleThreadExecutor();
        if (getArguments() != null) {
            String fullName = getArguments().getString("fullName");
            String phoneNumber = getArguments().getString("phoneNumber");
            String paragraph = getArguments().getString("paragraph");
            experienceId = getArguments().getInt("experienceId", -1); // Get the experience ID if available
            EditText etFullName = view.findViewById(R.id.et_full_name);
            EditText etPhoneNumber = view.findViewById(R.id.et_phone_number);
            EditText etParagraph = view.findViewById(R.id.et_paragraph);
            etFullName.setText(fullName);
            etPhoneNumber.setText(phoneNumber);
            etParagraph.setText(paragraph);
            Button btnSave = view.findViewById(R.id.btn_save);
            btnSave.setText("Edit Experience");
        }
        Button btnSave = view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(v -> saveOrEditUser(view));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavController navController = Navigation.findNavController(view);
                navController.popBackStack();
            }
        });
    }

    private void saveOrEditUser(View view) {
        EditText etFullName = view.findViewById(R.id.et_full_name);
        EditText etPhoneNumber = view.findViewById(R.id.et_phone_number);
        EditText etParagraph = view.findViewById(R.id.et_paragraph);
        String fullName = etFullName.getText().toString();
        if(TextUtils.isEmpty(fullName)){
            etFullName.setError("Can't be empty!");
        }

        String phoneNumber = etPhoneNumber.getText().toString();
        if(TextUtils.isEmpty(phoneNumber)){
            etPhoneNumber.setError("Can't be empty!");
        }

        String paragraph = etParagraph.getText().toString();
        if(TextUtils.isEmpty(paragraph)){
            etParagraph.setError("Can't be empty!");
        }
        if (!fullName.isEmpty() && !phoneNumber.isEmpty() && !paragraph.isEmpty()) {
            Experience experience = new Experience(fullName, phoneNumber, paragraph);
            executor.execute(() -> {
                if (experienceId == -1) {
                    db.experienceDao().insert(experience);
                } else {
                    experience.setId(experienceId);
                    db.experienceDao().update(experience);
                }
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Experience saved successfully!", Toast.LENGTH_SHORT).show();
                    NavController navController = Navigation.findNavController(view);
                    navController.popBackStack(); // Navigate back to previous fragment
                });
            });
        } else {
            Toast.makeText(getContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
        }
    }
}