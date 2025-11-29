package lb.edu.ul.khayme_w_nar;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lb.edu.ul.khayme_w_nar.db.AppDatabase;
import lb.edu.ul.khayme_w_nar.db.CheckList;
import lb.edu.ul.khayme_w_nar.db.DatabaseManager;
import lb.edu.ul.khayme_w_nar.ui.booked_camps.ChecklistFragment;


public class ClsChecklistAdapater extends BaseAdapter {
    private List<CheckList> checkLists;
    private LayoutInflater inflater;

    public ClsChecklistAdapater(List<CheckList> checkLists) {
        this.checkLists = checkLists;
    }
    @Override
    public int getCount() {
        return checkLists.size();
    }
    @Override
    public Object getItem(int position) {
        return checkLists.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    private static class ViewHolder {
        TextView taskTv;
        ImageButton deleteButton;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.clv_checklist_layout, parent, false);
            holder = new ViewHolder();
            holder.taskTv = convertView.findViewById(R.id.typeTv);
            holder.deleteButton = convertView.findViewById(R.id.deleteButton);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CheckList currentCheckList = checkLists.get(position);
        holder.taskTv.setText(currentCheckList.getTask());
        holder.deleteButton.setOnClickListener(v -> {
            CheckList itemToDelete = checkLists.get(position);
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                ChecklistFragment.db.checklistDao().Delete(itemToDelete);
                checkLists.remove(position);
                ((Activity) parent.getContext()).runOnUiThread(() -> notifyDataSetChanged());
            });
        });
        return convertView;
    }
}
