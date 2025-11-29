package lb.edu.ul.khayme_w_nar.ui.community;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import lb.edu.ul.khayme_w_nar.R;
import lb.edu.ul.khayme_w_nar.db.AppDatabase;
import lb.edu.ul.khayme_w_nar.db.DatabaseManager;
import lb.edu.ul.khayme_w_nar.db.Experience;

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.ViewHolder> {
    private List<Experience> experienceList;
    private Executor executor = Executors.newSingleThreadExecutor();
    public ExperienceAdapter(List<Experience> experienceList) {
        this.experienceList = experienceList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Experience experience = experienceList.get(position);
        holder.bind(experience);
        holder.editButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("fullName", experience.getFullName());
            bundle.putString("phoneNumber", experience.getPhoneNumber());
            bundle.putString("paragraph", experience.getParagraph());
            bundle.putInt("experienceId", experience.getId());
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_to_addExperience, bundle);

        });
    }

    @Override
    public int getItemCount() {
        return experienceList.size();
    }
    private void deleteExperience(int position, Context context) {
        Experience experience = experienceList.get(position);
        executor.execute(() -> {
            AppDatabase db = DatabaseManager.getInstance(context);
            db.experienceDao().deleteExperience(experience);
            new Handler(Looper.getMainLooper()).post(() -> {
                experienceList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, experienceList.size());
            });
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTv, phoneTv, experienceTv;
        private ImageButton deleteButton, dialButton, editButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.nameTv);
            phoneTv = itemView.findViewById(R.id.phoneTv);
            experienceTv = itemView.findViewById(R.id.experienceTv);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            dialButton = itemView.findViewById(R.id.dialButton);
            editButton = itemView.findViewById(R.id.editButton);

            deleteButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Context context = v.getContext();
                    new AlertDialog.Builder(context)
                            .setTitle("Delete Experience")
                            .setMessage("Are you sure you want to delete this experience?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    deleteExperience(position, context);
                                    Toast.makeText(context, "Experience Deleted", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            });
        }

        public void bind(Experience experience) {
            nameTv.setText(experience.getFullName());
            phoneTv.setText(experience.getPhoneNumber());
            experienceTv.setText(experience.getParagraph());
            dialButton.setOnClickListener(v -> {
                Context context = v.getContext();
                String phoneNumber = experience.getPhoneNumber();
                if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phoneNumber));
                    try {
                        context.startActivity(intent); // Open dialer app
                    } catch (Exception e) {
                        Toast.makeText(context, "No dialer app found!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Invalid phone number!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
