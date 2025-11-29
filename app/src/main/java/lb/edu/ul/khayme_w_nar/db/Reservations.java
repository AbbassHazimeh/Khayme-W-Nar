package lb.edu.ul.khayme_w_nar.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Reservations",
        foreignKeys = {
                @ForeignKey(entity = CampPlace.class,
                        parentColumns = "campId",
                        childColumns = "camp_Id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Users.class,
                        parentColumns = "userId",
                        childColumns = "user_Id",
                        onDelete = ForeignKey.CASCADE)
        })

public class Reservations {
    @PrimaryKey(autoGenerate = true)
    private int resId;
    @ColumnInfo(name = "camp_Id")
    private int camp_Id;
    @ColumnInfo(name = "fromDate")
    private String fromDate;
    @ColumnInfo(name = "toDate")
    private String toDate;
    @ColumnInfo(name = "user_Id")
    private int user_Id;


    public int getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    public void setResId(int resId){
        this.resId = resId;
    }
    public void setCamp_Id(int camp_Id){
        this.camp_Id = camp_Id;
    }
    public void setFromDate(String fromDate){
        this.fromDate = fromDate;
    }
    public void setToDate(String toDate){
        this.toDate = toDate;
    }
    public int getResId(){
        return this.resId;
    }
    public int getCamp_Id(){
        return this.camp_Id;
    }
    public String getFromDate(){
        return this.fromDate;
    }
    public String getToDate(){
        return this.toDate;
    }
}
