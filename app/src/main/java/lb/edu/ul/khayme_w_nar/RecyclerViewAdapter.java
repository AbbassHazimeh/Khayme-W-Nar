package lb.edu.ul.khayme_w_nar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import lb.edu.ul.khayme_w_nar.db.CampPlace;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<CampPlace> availableCamps;
    private Context context;
    private final OnItemClickListener listener;
    public RecyclerViewAdapter(Context C, List<CampPlace> camps, OnItemClickListener listener) {
        availableCamps = camps;
        this.context = C;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTextView().setText(availableCamps.get(position).getCampName());
        String campImage = availableCamps.get(position).getCampImage();
        int imageCampId = context.getResources().getIdentifier(campImage,"drawable",context.getOpPackageName());
        holder.getImgView().setImageResource(imageCampId);
        holder.bind(availableCamps.get(position));
    }

    @Override
    public int getItemCount() {
        return availableCamps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageView imgView;
        private final Button buttonView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recycler_view_text);
            imgView = itemView.findViewById(R.id.recycler_view_image);
            buttonView = itemView.findViewById(R.id.activitiesButton);
        }

        public void bind(CampPlace camp){
            itemView.setOnClickListener(v -> {
                if(listener != null){
                    listener.onItemClick(camp);
                }
            });
            buttonView.setOnClickListener(v->{
                if(listener!= null){
                    listener.onButtonClick(camp.getCampId());
                }
            });
        }

        public TextView getTextView() {
            return textView;
        }
        public ImageView getImgView() {
            return imgView;
        }
        public Button getButtonView(){
            return buttonView;
        }
    }
    public interface OnItemClickListener{
        void onItemClick(CampPlace camp);
        void onButtonClick(int campId);
    }

}