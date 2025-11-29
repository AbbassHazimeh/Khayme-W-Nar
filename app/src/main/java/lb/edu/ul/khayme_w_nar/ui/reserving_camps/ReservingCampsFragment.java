package lb.edu.ul.khayme_w_nar.ui.reserving_camps;

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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.List;

import lb.edu.ul.khayme_w_nar.R;
import lb.edu.ul.khayme_w_nar.databinding.FragmentReservingCampsBinding;
import lb.edu.ul.khayme_w_nar.db.AppDatabase;
import lb.edu.ul.khayme_w_nar.db.CampPlace;
import lb.edu.ul.khayme_w_nar.db.DatabaseManager;
import lb.edu.ul.khayme_w_nar.db.Reservations;
import lb.edu.ul.khayme_w_nar.db.Users;

public class ReservingCampsFragment extends Fragment {
    private FragmentReservingCampsBinding binding;
    public static AppDatabase db;
    private EditText nameEditText;
    private EditText fromDateEd;
    private EditText toDateEd;
    private EditText numberOfGuestsEd;
    private Button addResHandler;
    private Button resetResHandler;
    String fullName;
    String fromDate;
    String toDate;
    int nbOfGuests;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentReservingCampsBinding.inflate(inflater, container, false);
        db = DatabaseManager.getInstance(requireContext());
        View root = binding.getRoot();
        nameEditText = binding.yourName;
        fromDateEd = binding.fromDateText;
        toDateEd = binding.toDateText;
        numberOfGuestsEd = binding.numberOfGuests;
        addResHandler = binding.submitRes;
        resetResHandler = binding.resetRes;
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null || !bundle.containsKey("campId")) {
            throw new IllegalArgumentException("campId is required to reserve a camp");
        }
        int campId = bundle.getInt("campId");

        SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        long userId = prefs.getLong("current_user_id", -1);

        if (userId != -1) {
            new Thread(() -> {
                List<Users> userInfo = db.usersDao().getUserInfo((int)userId);
                if (!userInfo.isEmpty()) {
                    Users user = userInfo.get(0);
                    requireActivity().runOnUiThread(() -> {
                        nameEditText.setText(user.getFullname());
                        // Optionally disable editing of the name field since it's pulled from profile
                        nameEditText.setEnabled(false);
                    });
                }
            }).start();
        }

        addResHandler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateInputs()) {
                    return;
                }

                new Thread(() -> {
                    SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                    long userId = prefs.getLong("current_user_id", -1);

                    if (userId == -1) {
                        requireActivity().runOnUiThread(() -> {
                            // Handle error - user not logged in
                            return;
                        });
                    }

                    Reservations Res = new Reservations();
                    Res.setCamp_Id(campId);
                    Res.setFromDate(fromDate);
                    Res.setToDate(toDate);
                    Res.setUser_Id((int)userId);

                    db.ResDAO().insertRes(Res);

                    requireActivity().runOnUiThread(() -> {
                        Log.d("idDebug", "This is the id in the reserving camps" + campId);

                        new Thread(() -> {
                            db.CampDAO().updateColumn(campId, true);
                            db.CampDAO().updateUser(campId, userId);
                            CampPlace campselected = db.CampDAO().getCampById(campId);
                            boolean b = campselected.isIsbooked();
                            Log.d("isBookedDebug", "" + b);
                        }).start();

                        NavController navController = Navigation.findNavController(requireView());
                        if (navController.getPreviousBackStackEntry() != null) {
                            navController.getPreviousBackStackEntry().getSavedStateHandle();
                            navController.navigateUp();
                            navController.navigateUp();
                        }
                    });
                }).start();
            }
        });

        resetResHandler.setOnClickListener(v -> resetFields());
    }

    private boolean validateInputs() {
        fullName = nameEditText.getText().toString();
        if (TextUtils.isEmpty(fullName)) {
            nameEditText.setError("you should provide your name");
            return false;
        }

        fromDate = fromDateEd.getText().toString();
        if (TextUtils.isEmpty(fromDate)) {
            fromDateEd.setError("you should set up you're starting date of camp");
            return false;
        }

        toDate = toDateEd.getText().toString();
        if (TextUtils.isEmpty(toDate)) {
            toDateEd.setError("you should set up you're ending date of camp");
            return false;
        }

        String guestsNb = numberOfGuestsEd.getText().toString();
        if (TextUtils.isEmpty(guestsNb)) {
            numberOfGuestsEd.setError("enter how many participants will be");
            return false;
        }

        nbOfGuests = Integer.parseInt(guestsNb);
        return true;
    }

    private void resetFields() {
        fromDateEd.setText("");
        toDateEd.setText("");
        numberOfGuestsEd.setText("");
    }
}