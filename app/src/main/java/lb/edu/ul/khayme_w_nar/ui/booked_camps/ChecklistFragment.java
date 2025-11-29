package lb.edu.ul.khayme_w_nar.ui.booked_camps;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import lb.edu.ul.khayme_w_nar.ClsChecklistAdapater;
import lb.edu.ul.khayme_w_nar.CustomAdapter;
import lb.edu.ul.khayme_w_nar.R;
import lb.edu.ul.khayme_w_nar.databinding.FragmentBookedCampsBinding;
import lb.edu.ul.khayme_w_nar.databinding.FragmentChecklistBinding;
import lb.edu.ul.khayme_w_nar.db.AppDatabase;
import lb.edu.ul.khayme_w_nar.db.CheckList;
import lb.edu.ul.khayme_w_nar.db.DatabaseManager;
public class ChecklistFragment extends Fragment {
    private FragmentChecklistBinding binding;
    public static AppDatabase db;
    private ClsChecklistAdapater adapter;
    private ListView lv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChecklistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        db = DatabaseManager.getInstance(requireContext());
        lv = binding.clsChecklist;
        Bundle arguments = getArguments();
        if (arguments != null) {
            int campId = arguments.getInt("campId", -1);
            if (campId != -1) {
                loadChecklistItems(campId);
            } else {
                Toast.makeText(requireContext(), "Camp ID is missing or invalid!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(requireContext(), "Arguments are missing!", Toast.LENGTH_SHORT).show();
        }
        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(v -> {
            if (getArguments() != null) {
                int campId = getArguments().getInt("campId", -1);
                if (campId != -1) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("campId", campId); // Add campId to the bundle
                    NavController navController = Navigation.findNavController(requireView());
                    navController.navigate(R.id.checklist_to_checklistInfo, bundle);
                }
            }
        });
        return root;
    }
    private void loadChecklistItems(int campId) {
        new Thread(() -> {
            List<CheckList> checkLists = db.checklistDao().getChecklistsForCamp(campId);
            requireActivity().runOnUiThread(() -> {
                adapter = new ClsChecklistAdapater(checkLists);
                lv.setAdapter(adapter);
            });
        }).start();
    }


}
