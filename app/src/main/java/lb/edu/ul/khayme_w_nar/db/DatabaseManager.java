package lb.edu.ul.khayme_w_nar.db;

import android.content.Context;

import androidx.room.Room;

public class DatabaseManager {
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "experience_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
