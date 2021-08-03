package com.blucor.tcthecontractor.database;

import android.content.Context;

import com.blucor.tcthecontractor.models.User;

import java.util.List;


public class DatabaseUtil {
    /**
     * Fields
     */
    private static DatabaseUtil sInstance;
    private UniqaCustomDao mCodeDao;

    private DatabaseUtil() {
        setCodeDao(UniqaDatabase.on().codeDao());
    }

    /**
     * This method builds an instance
     */
    public static void init(Context context) {
        UniqaDatabase.init(context);

        if (sInstance == null) {
            sInstance = new DatabaseUtil();
        }
    }

    public static DatabaseUtil on() {
        if (sInstance == null) {
            sInstance = new DatabaseUtil();
        }

        return sInstance;
    }

    private UniqaCustomDao getCodeDao() {
        return mCodeDao;
    }

    private void setCodeDao(UniqaCustomDao codeDao) {
        mCodeDao = codeDao;
    }

    public long[] insertUser(User user) {
        return getCodeDao().insert(user);
    }

    public List<User> getAllUser() {
        return getCodeDao().getAllFlowableCodes();
    }

    public int deleteEntity(User code) {
        return getCodeDao().delete(code);
    }

    public int getItemCount() {
        return getCodeDao().getRowCount();
    }

    public void deleteAll() {
        getCodeDao().nukeTable();
    }

    public void updatePassword(String password, int id) {
        getCodeDao().updatePassword(password,id);
    }

    public void updateImageName(String image_name, int id) {
        getCodeDao().updateImageName(image_name,id);
    }

    public boolean hasLogin() {
        return getAllUser().size() > 0;
    }
}
