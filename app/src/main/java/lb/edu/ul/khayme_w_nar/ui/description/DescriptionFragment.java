package lb.edu.ul.khayme_w_nar.ui.description;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.List;

import lb.edu.ul.khayme_w_nar.R;
import lb.edu.ul.khayme_w_nar.databinding.ActivityMainBinding;
import lb.edu.ul.khayme_w_nar.databinding.FragmentCampDescriptionBinding;
import lb.edu.ul.khayme_w_nar.db.AppDatabase;
import lb.edu.ul.khayme_w_nar.db.CampPlace;
import lb.edu.ul.khayme_w_nar.db.DatabaseManager;
import lb.edu.ul.khayme_w_nar.db.Reservations;
import lb.edu.ul.khayme_w_nar.db.Users;

//public class DescriptionFragment extends Fragment {
//
//    private FragmentCampDescriptionBinding binding;
//
//
//    public static AppDatabase db;
//    private ImageView imageView;
//    private TextView campNameTv;
//    private TextView campDesTv;
//    private TextView campLocation;
//    private TextView campResTv;
//    private TextView campPriceTv;
//    private Button addReservationButton;
//
//    private int campId;
//
//
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        binding = FragmentCampDescriptionBinding.inflate(inflater, container, false);
//        db = DatabaseManager.getInstance(requireContext());
//        View root = binding.getRoot();
//        imageView = binding.campImageView;
//        campNameTv = binding.campName;
//        campDesTv = binding.campDescription;
//        campLocation = binding.campLocation;
//        campResTv = binding.campReservation;
//        campPriceTv = binding.campPrice;
//        addReservationButton = binding.reservingButton;
//        return root;
//    }
//
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        Bundle bundle = getArguments();
//        if(bundle != null) {
//            campId = bundle.getInt("campId");
//        }
//        new Thread(() -> {
//            CampPlace campPlaceById = db.CampDAO().getCampById(campId);
//            List<Reservations> R = db.ResDAO().getResById(campId);
//            requireActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    String campImage = campPlaceById.getCampImage();
//                    int campImageId = getResources().getIdentifier(campImage, "drawable", getActivity().getPackageName());
//                    imageView.setImageResource(campImageId);
//                    campNameTv.setText(campPlaceById.getCampName());
//                    campDesTv.setText(campPlaceById.getCampDescription());
//                    int campPrice = campPlaceById.getCampPrice();
//                    campLocation.setText(campPlaceById.getCampLocation());
//                    String campPriceStr = Integer.toString(campPrice);
//                    campPriceTv.setText(campPriceStr+" $");
//                    if(R == null || R.isEmpty()){
//                        campResTv.setText("No Reservations Up Till Now! Book Yours");
//                    }
//                    else{
//                        StringBuilder reservations = new StringBuilder();
//                        for(int i = 0;i < R.size();i++){
//                            reservations.append(R.get(i).getFromDate()).append(" To ").append(R.get(i).getToDate()).append("\n");
//                        }
//                        campResTv.setText(reservations.toString());
//                    }
//                }
//            });
//        }).start();
//        new Thread(()->{
//
//
//        }).start();
//        addReservationButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Bundle B = new Bundle();
//                B.putInt("campId",campId);
//                Bundle loginBundle = getArguments();
//                boolean isLoggedIn = loginBundle != null
//                        ? loginBundle.getBoolean("isLoggedInFromDb", false)
//                        : false;
//                Log.d("boolean Debug", "Boolean from description"+" "+ isLoggedIn);
//                if(isLoggedIn){
//                    NavController navController = Navigation.findNavController(requireView());
//                    navController.navigate(R.id.action_to_bookCamp,B);
//                }else{
//                    NavController navController = Navigation.findNavController(requireView());
//                    navController.navigate(R.id.action_to_loginFragment);
//                }
//            }
//        });
////        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
////            @Override
////            public void handleOnBackPressed() {
////                requireActivity().getSupportFragmentManager().popBackStack();
////            }
////        };
////        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(),callback);
//    }
//
//
//
//}



public class DescriptionFragment extends Fragment {

    private FragmentCampDescriptionBinding binding;
    public static AppDatabase db;
    private ImageView imageView;
    private TextView campNameTv;
    private TextView campDesTv;
    private TextView campLocation;
    private TextView campResTv;
    private TextView campPriceTv;
    private Button addReservationButton;

    private int campId;
    private boolean isLoggedIn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCampDescriptionBinding.inflate(inflater, container, false);
        db = DatabaseManager.getInstance(requireContext());
        View root = binding.getRoot();
        imageView = binding.campImageView;
        campNameTv = binding.campName;
        campDesTv = binding.campDescription;
        campLocation = binding.campLocation;
        campResTv = binding.campReservation;
        campPriceTv = binding.campPrice;
        addReservationButton = binding.reservingButton;
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        long userId = prefs.getLong("current_user_id", -1);
        if (bundle != null) {
            campId = bundle.getInt("campId");
            Log.d("DescriptionFragment", "isLoggedIn from Bundle: " + isLoggedIn);
        }
        new Thread(() -> {
            CampPlace campPlaceById = db.CampDAO().getCampById(campId);
            List<Reservations> reservations = db.ResDAO().getResById(campId);
            isLoggedIn = db.usersDao().getIsLoggedInById(userId);
            Log.d("isLoggedInStatus", "is logged in status after log in "+db.usersDao().getIsLoggedInById(userId));
            requireActivity().runOnUiThread(() -> {
                String campImage = campPlaceById.getCampImage();
                int campImageId = getResources().getIdentifier(campImage, "drawable", requireActivity().getPackageName());
                imageView.setImageResource(campImageId);
                campNameTv.setText(campPlaceById.getCampName());
                campDesTv.setText(campPlaceById.getCampDescription());
                campLocation.setText(campPlaceById.getCampLocation());
                int campPrice = campPlaceById.getCampPrice();
                String campPriceStr = Integer.toString(campPrice);
                campPriceTv.setText(campPriceStr + " $");

                if (reservations == null || reservations.isEmpty()) {
                    campResTv.setText("No Reservations Up Till Now! Book Yours");
                } else {
                    StringBuilder reservationsText = new StringBuilder();
                    for (Reservations res : reservations) {
                        reservationsText.append(res.getFromDate())
                                .append(" To ")
                                .append(res.getToDate())
                                .append("\n");
                    }
                    campResTv.setText(reservationsText.toString());
                }
            });
        }).start();

        // Handle reservation button click
        addReservationButton.setOnClickListener(v -> {
            Bundle reservationBundle = new Bundle();
            reservationBundle.putInt("campId", campId);
//            reservationBundle.putBoolean("isLoggedIn", isLoggedIn); // Pass isLoggedIn status

            NavController navController = Navigation.findNavController(requireView());




            if (isLoggedIn) {
                // Navigate to the booking fragment if the user is logged in
                navController.navigate(R.id.action_to_bookCamp, reservationBundle);
            } else {
                // Navigate to the login fragment if the user is not logged in
                navController.navigate(R.id.action_to_loginFragment);
            }
        });
    }
}