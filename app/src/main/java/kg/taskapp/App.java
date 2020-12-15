package kg.taskapp;

import android.app.Application;

import androidx.room.Room;

import kg.taskapp.room.AppDatabase;

public class App extends Application {
    public static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room
                .databaseBuilder(this, AppDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
    }


}
