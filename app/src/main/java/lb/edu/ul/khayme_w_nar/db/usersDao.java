package lb.edu.ul.khayme_w_nar.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface usersDao {

    @Query("Select * From Users Where userId = :id")
    List<Users> getUserInfo(long id);

    @Query("SELECT * FROM Users WHERE username = :username")
    Users findUserByUsername(String username);

    @Query("Select password From Users Where userId = :id")
    String getPassword(long id);
    @Query("Select username From Users Where userId = :id")
    String getuserName(long id);

    @Query("Select isLoogedIn From Users Where userId = :id")
    boolean getIsLoggedInById(long id);
    @Insert
    long Insert(Users user);

    @Query("SELECT COUNT(*) FROM Users WHERE email = :email")
    int checkEmailExists(String email);
    @Query("SELECT COUNT(*) FROM Users WHERE username = :username")
    int checkUsernameExists(String username);

    @Query("UPDATE Users SET isLoogedIn = :loginStatus WHERE userId = :userId")
    void updateLoginStatus(long userId, boolean loginStatus);
 @Query("Select userId From Users Where username = :username")
    long getUserIdByUsername(String username);

    @Query("UPDATE Users SET profileImageUri = :imageUri WHERE userId = :userId")
    void updateProfileImage(long userId, String imageUri);

    @Query("SELECT profileImageUri FROM Users WHERE userId = :userId")
    String getProfileImageUri(long userId);

    @Query("UPDATE users SET fullname = :fullname, email = :email, username = :username, password = :password WHERE userId = :userId")
    void updateUser(int userId, String fullname, String email, String username, String password);
}
