package lb.edu.ul.khayme_w_nar.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface checklistDao {

    @Query("SELECT * FROM ChecklistTable WHERE campId = :campId")
    List<CheckList> getTasksForCamp(int campId);

    @Query("SELECT COUNT(*) FROM ChecklistTable WHERE campId = :campId AND task = :task")
    int taskExists(int campId, String task);

    @Query("SELECT * FROM ChecklistTable WHERE campId = :campId")
    List<CheckList> getChecklistsForCamp(int campId);

    @Delete
    void Delete(CheckList checkList);

    @Insert
    void Insert(CheckList... checkList);

}
