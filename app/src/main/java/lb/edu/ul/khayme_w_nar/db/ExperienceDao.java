package lb.edu.ul.khayme_w_nar.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExperienceDao {

    @Insert
    void insert(Experience experience);

    @Query("SELECT * FROM experience_table")
    LiveData<List<Experience>> getAllExperiences();

    @Query("DELETE FROM experience_table")
    void deleteAllExperiences(); // Add this method

    @Delete
    void deleteExperience(Experience experience);

    @Update
    void update(Experience experience);

}
