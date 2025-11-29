package lb.edu.ul.khayme_w_nar.db;

import androidx.room.ColumnInfo;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "ChecklistTable")

public class CheckList {

    @PrimaryKey(autoGenerate = true)
    private int checklistId;

    @ColumnInfo(name = "campId")
    private int campId;

    @ColumnInfo(name = "task")
    private String task;



    public int getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(int checklistId) {
        this.checklistId = checklistId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getCampId() {
        return campId;
    }

    public void setCampId(int campId) {
        this.campId = campId;
    }


}
