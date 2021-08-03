package com.blucor.tcthecontractor.database;

import android.content.Context;

import com.blucor.tcthecontractor.R;
import com.blucor.tcthecontractor.models.User;

import androidx.room.Database;

@Database(entities = {User.class},
        version = 1, exportSchema = false)
public abstract class UniqaDatabase extends AppDatabase {

    private static volatile UniqaDatabase sInstance;

    // Get a database instance
    public static synchronized UniqaDatabase on() {
        return sInstance;
    }

    public static synchronized void init(Context context) {

        if (sInstance == null) {
            synchronized (UniqaDatabase.class) {
                sInstance = createDb(context, context.getString(R.string.app_name), UniqaDatabase.class);
            }
        }
    }

    public abstract UniqaCustomDao codeDao();
}
