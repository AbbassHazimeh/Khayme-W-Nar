package lb.edu.ul.khayme_w_nar.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface activitiesDao {
    @Insert
    void insertAct(Activities A);
    @Query("Select * from Activities where camp_Id = :campId")
    List<Activities> getAllActivities(int campId);
    @Query("Delete from Activities")
    void deleteAllAct();
}
