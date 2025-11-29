package lb.edu.ul.khayme_w_nar.ui.booked_camps;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import lb.edu.ul.khayme_w_nar.BookedCampsRvAdapter;
import lb.edu.ul.khayme_w_nar.R;
import lb.edu.ul.khayme_w_nar.RecyclerViewAdapter;
import lb.edu.ul.khayme_w_nar.databinding.FragmentBookedCampsBinding;
import lb.edu.ul.khayme_w_nar.db.AppDatabase;
import lb.edu.ul.khayme_w_nar.db.CampPlace;
import lb.edu.ul.khayme_w_nar.db.DatabaseManager;

public class BookedCampsFragment extends Fragment implements BookedCampsRvAdapter.OnItemClickListener {
    private FragmentBookedCampsBinding binding;
    public static AppDatabase db;
    List<CampPlace> bookedCamps = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBookedCampsBinding.inflate(inflater, container,false);
        db = DatabaseManager.getInstance(requireContext());
        View root = binding.getRoot();
        return root;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        long userId = prefs.getLong("current_user_id", -1);
        Log.d("userID Debug", "user id in booked camps"+ userId);
        new Thread(() ->{
            bookedCamps.clear();
            bookedCamps.addAll(db.CampDAO().getBookedCampsByUser(userId));
            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    BookedCampsRvAdapter adapter = new BookedCampsRvAdapter(requireContext(),bookedCamps, BookedCampsFragment.this);
                    binding.bookedCampsRv.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.bookedCampsRv.setAdapter(adapter);
                }
            });
        }).start();
    }
    public void onItemClickBookedCamps(CampPlace camp){
        Bundle B = new Bundle();
        B.putInt("campId",camp.getCampId());
        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.action_booked_camps_to_checklistFragment,B);
    }
}

