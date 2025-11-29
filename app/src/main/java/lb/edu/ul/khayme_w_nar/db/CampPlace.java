package lb.edu.ul.khayme_w_nar.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CampingPlaces")
public class CampPlace{
    @PrimaryKey(autoGenerate = false)
    private int campId;
    @ColumnInfo(name = "campName")
    private String campName;
    @ColumnInfo(name = "campDescription")
    private String campDescription;
    @ColumnInfo(name = "campLocation")
    private String campLocation;
    @ColumnInfo(name = "campPrice")
    private int campPrice;
    @ColumnInfo(name = "campImage") //I'll Store in this field the path of each image
    private String campImage;
    @ColumnInfo(name = "userId")
    private long userId;  // This will store the ID of the user who booked the camp

    @ColumnInfo(name = "isbooked")
    private boolean isbooked;


    public void setCampId(int campId){
        this.campId = campId;
    }
    public void setCampName(String campName){
        this.campName = campName;
    }
    public void setCampDescription(String campDescription){
        this.campDescription = campDescription;
    }
    public void setCampLocation(String campLocation){
        this.campLocation = campLocation;
    }
    public void setCampPrice(int campPrice){
        this.campPrice = campPrice;
    }
    public void setCampImage(String campImage){
        this.campImage = campImage;
    }
    public int getCampId(){
        return this.campId;
    }
    public String getCampName(){
        return this.campName;
    }
    public String getCampDescription(){
        return this.campDescription;
    }
    public String getCampLocation(){
        return this.campLocation;
    }
    public String getCampImage(){
        return this.campImage;
    }
    public int getCampPrice(){
        return this.campPrice;
    }
    public boolean isIsbooked() {
        return isbooked;
    }
    public void setIsbooked(boolean isbooked) {
        this.isbooked = isbooked;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
