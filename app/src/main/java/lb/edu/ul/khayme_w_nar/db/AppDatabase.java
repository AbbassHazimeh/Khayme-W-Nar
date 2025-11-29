package lb.edu.ul.khayme_w_nar.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Experience.class,CampPlace.class,Reservations.class,Activities.class,Users.class,CheckList.class}, version =2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ExperienceDao experienceDao();
    public abstract campDao CampDAO();
    public abstract resDao ResDAO();
    public abstract activitiesDao actDao();
    public abstract checklistDao checklistDao();
    public abstract usersDao usersDao();
    private static volatile AppDatabase INSTANCE;
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "experience_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}