package lb.edu.ul.khayme_w_nar.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface resDao {
    @Insert
    void insertRes(Reservations R);
    @Query("Select * From Reservations where camp_Id = :campID")
    List<Reservations> getResById(int campID);

    @Query("SELECT * FROM Reservations WHERE user_Id = :userId")
    List<Reservations> getResByUserId(long userId);


}
