package lb.edu.ul.khayme_w_nar.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Activities",foreignKeys = @ForeignKey(entity = CampPlace.class,parentColumns = "campId",childColumns = "camp_Id",onDelete = ForeignKey.CASCADE))
public class Activities {
    @PrimaryKey(autoGenerate = true)
    private int activityId;
    @ColumnInfo(name = "camp_Id")
    private int camp_Id;
    @ColumnInfo(name = "activityName")
    private String activityName;
    @ColumnInfo(name = "activityDesc")
    private String activityDesc;
    @ColumnInfo(name = "actIcon")
    private String actIcon;

    public void setCamp_Id(int camp_Id) {
        this.camp_Id = camp_Id;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setActivityDesc(String activityDesc) {
        this.activityDesc = activityDesc;
    }

    public void setActIcon(String actIcon) {
        this.actIcon = actIcon;
    }

    public int getActivityId() {
        return this.activityId;
    }

    public int getCamp_Id() {
        return camp_Id;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getActivityDesc() {
        return activityDesc;
    }

    public String getActIcon() {
        return actIcon;
    }
}
