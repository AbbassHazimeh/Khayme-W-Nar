package lb.edu.ul.khayme_w_nar.ui.booked_camps;

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

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import lb.edu.ul.khayme_w_nar.R;
import lb.edu.ul.khayme_w_nar.databinding.FragmentChecklistBinding;
import lb.edu.ul.khayme_w_nar.databinding.FragmentChecklistInfoLayoutBinding;
import lb.edu.ul.khayme_w_nar.db.AppDatabase;
import lb.edu.ul.khayme_w_nar.db.CampPlace;
import lb.edu.ul.khayme_w_nar.db.CheckList;
import lb.edu.ul.khayme_w_nar.db.DatabaseManager;
import lb.edu.ul.khayme_w_nar.db.Reservations;


public class ChecklistInfoFragment extends Fragment {
    private Executor executor;
    private FragmentChecklistInfoLayoutBinding binding;
    public static AppDatabase db;
    private Button addtask;
    private int campId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChecklistInfoLayoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        db = DatabaseManager.getInstance(requireContext());
        executor = Executors.newSingleThreadExecutor();
        Bundle arguments = getArguments();
        if (arguments != null) {
            campId = arguments.getInt("campId", -1);
        }
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addtask = getView().findViewById(R.id.addButton);

        addtask.setOnClickListener(v -> {
            EditText tasktxt = getView().findViewById(R.id.edit_text_taskId);
            String task = tasktxt.getText().toString();

            if (TextUtils.isEmpty(task)) {
                tasktxt.setError("Please enter a task");
                return;
            }
            executor.execute(() -> {
                int count = db.checklistDao().taskExists(campId, task);
                if (count > 0) {
                    requireActivity().runOnUiThread(() ->
                            Toast.makeText(getContext(), "Task already exists!", Toast.LENGTH_SHORT).show()
                    );
                } else {
                    CheckList checkList = new CheckList();
                    checkList.setTask(task);
                    checkList.setCampId(campId);
                    db.checklistDao().Insert(checkList);
                    requireActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Task saved successfully!", Toast.LENGTH_SHORT).show();
                        NavController navController = Navigation.findNavController(requireView());
                        if (navController.getPreviousBackStackEntry() != null) {
                            navController.getPreviousBackStackEntry().getSavedStateHandle().set("campId", campId);
                            navController.navigateUp();
                        }
                    });
                }
            });
        });
    }
}
