package lb.edu.ul.khayme_w_nar.ui.community;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import lb.edu.ul.khayme_w_nar.R;
import lb.edu.ul.khayme_w_nar.databinding.FragmentCommunityBinding;
import lb.edu.ul.khayme_w_nar.db.AppDatabase;
import lb.edu.ul.khayme_w_nar.db.DatabaseManager;
import lb.edu.ul.khayme_w_nar.db.Experience;

public class CommunityFragment extends Fragment {
    private FragmentCommunityBinding binding;
    private RecyclerView recyclerView;
    private ExperienceAdapter experienceAdapter;
    public static AppDatabase db;
    private Executor executor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCommunityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.rvCommunity;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
        db = DatabaseManager.getInstance(requireContext());
        executor = Executors.newSingleThreadExecutor();

        FloatingActionButton fabAddUser = root.findViewById(R.id.fab);
        fabAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_to_addExperience);
            }
        });

        db.experienceDao().getAllExperiences().observe(getViewLifecycleOwner(), new Observer<List<Experience>>() {
            @Override
            public void onChanged(List<Experience> experiences) {
                experienceAdapter = new ExperienceAdapter(experiences);
                recyclerView.setAdapter(experienceAdapter);
            }
        });
        return root;
    }

    private void clearAllData() {
        executor.execute(() -> {
            db.experienceDao().deleteAllExperiences();
        });
    }
}