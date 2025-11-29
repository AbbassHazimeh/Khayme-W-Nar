package lb.edu.ul.khayme_w_nar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import lb.edu.ul.khayme_w_nar.db.Activities;

public class CustomAdapter extends BaseAdapter {
    Context C;
    List<Activities> activitiesList;
    LayoutInflater inflater;

    public CustomAdapter(Context C,List<Activities> activitiesList){
        this.C = C;
        this.activitiesList = activitiesList;
        inflater = (LayoutInflater)C.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return activitiesList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class Holder{
        public ImageView imageView;
        public TextView activtyTitle;
        public TextView activityDes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.activities_layout,parent,false);
        }
        Holder H = new Holder();
        H.activityDes = convertView.findViewById(R.id.activityDescription);
        H.activtyTitle = convertView.findViewById(R.id.activityTitle);
        H.imageView = convertView.findViewById(R.id.imageActivity);
        String imageName = activitiesList.get(position).getActIcon();
        int imageRes = C.getResources().getIdentifier(imageName,"drawable",C.getOpPackageName());
        H.activtyTitle.setText(activitiesList.get(position).getActivityName());
        H.activityDes.setText(activitiesList.get(position).getActivityDesc());
        H.imageView.setImageResource(imageRes);
        return convertView;
    }
}
