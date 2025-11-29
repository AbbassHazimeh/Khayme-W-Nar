package lb.edu.ul.khayme_w_nar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


import lb.edu.ul.khayme_w_nar.db.CampPlace;

public class BookedCampsRvAdapter extends RecyclerView.Adapter<BookedCampsRvAdapter.ViewHolder>{

    private List<CampPlace> bookedCamps;
    private Context context;
    private final OnItemClickListener listener;
    public BookedCampsRvAdapter(Context C, List<CampPlace> camps, OnItemClickListener listener){
        bookedCamps = camps;
        this.context = C;
        this.listener = listener;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_booked_camps_layout, parent, false);
        return new BookedCampsRvAdapter.ViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull BookedCampsRvAdapter.ViewHolder holder, int position) {
        holder.getTextView().setText(bookedCamps.get(position).getCampName());
        String campImage = bookedCamps.get(position).getCampImage();
        int imageCampId = context.getResources().getIdentifier(campImage,"drawable",context.getOpPackageName());
        holder.getImgView().setImageResource(imageCampId);
        holder.bind(bookedCamps.get(position));
    }

    @Override
    public int getItemCount() {
        return bookedCamps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageView imgView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recycler_view_text);
            imgView = itemView.findViewById(R.id.recycler_view_image);

        }

        public void bind(CampPlace camp){
            itemView.setOnClickListener(v -> {
                if(listener != null){
                    listener.onItemClickBookedCamps(camp);
                }
            });

        }

        public TextView getTextView() {
            return textView;
        }
        public ImageView getImgView() {
            return imgView;
        }

    }
    public interface OnItemClickListener{
        void onItemClickBookedCamps(CampPlace camp);

    }

}
