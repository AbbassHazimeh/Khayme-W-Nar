package lb.edu.ul.khayme_w_nar.ui.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import lb.edu.ul.khayme_w_nar.CustomAdapter;
import lb.edu.ul.khayme_w_nar.databinding.FragmentActivitiesCampsBinding;
import lb.edu.ul.khayme_w_nar.db.Activities;
import lb.edu.ul.khayme_w_nar.db.AppDatabase;
import lb.edu.ul.khayme_w_nar.db.DatabaseManager;

public class ActivitiesFragment extends Fragment {
    private FragmentActivitiesCampsBinding binding;
    public static AppDatabase db;
    List<Activities> allActivites;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentActivitiesCampsBinding.inflate(inflater,container,false);
        db = DatabaseManager.getInstance(requireContext());
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle B = getArguments();
        int campId = B.getInt("campId");
        new Thread(() ->{
           allActivites = db.actDao().getAllActivities(campId);
           requireActivity().runOnUiThread(new Runnable() {
               @Override
               public void run() {
                   CustomAdapter adapter = new CustomAdapter(getContext(),allActivites);
                   binding.activitiesListView.setAdapter(adapter);
               }
           });
        }).start();
    }
}
