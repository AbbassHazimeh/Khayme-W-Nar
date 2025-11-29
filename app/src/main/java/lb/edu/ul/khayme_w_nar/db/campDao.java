package lb.edu.ul.khayme_w_nar.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface campDao {
    @Query("Select * From campingplaces")
    List<CampPlace> getAllCamps();
    // Get camps booked by a specific user




    // Update booking status and assign to user
    @Query("UPDATE campingplaces SET isbooked = :newValue, userId = :userId WHERE campId = :id")
    void updateBookingStatus(int id, boolean newValue, long userId);

    // Clear user assignment when unbooking
    @Query("UPDATE campingplaces SET isbooked = false, userId = NULL WHERE campId = :id")
    void unBookCamp(int id);
    @Insert
    void insertCamp(CampPlace Camp);
    @Query("Select * From campingplaces where campId = :campID")
    CampPlace getCampById(int campID);
    @Query("Delete From campingplaces")
    void deleteAllCamping();
    @Query("UPDATE campingplaces SET isbooked = :newValue WHERE campId = :id")
    void updateColumn(int id, boolean newValue);
    @Query("SELECT * FROM campingplaces WHERE isBooked = true ")
    List<CampPlace> getAllBookedCamps();

    @Query("SELECT * FROM campingplaces WHERE isBooked = true AND userId = :userId")
    List<CampPlace> getBookedCampsByUser(long userId);

    @Query("UPDATE campingplaces SET userId = :newValue WHERE campId = :id")
    void updateUser(int id, long newValue);

    @Query("SELECT cp.* FROM CampingPlaces cp " +
            "INNER JOIN Reservations r ON cp.campId = r.camp_Id " +
            "WHERE r.user_Id = :userId")
    List<CampPlace> getCampPlacesWithReservations(long userId);
}
