package com.blucor.thecontractor.database;

import com.blucor.thecontractor.models.User;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;

@Dao

public interface UniqaCustomDao extends BaseDao<User> {

    @Query("SELECT * FROM " + TableNames.LOGIN)
    List<User> getAllFlowableCodes();

    @Query("SELECT COUNT(id) FROM " + TableNames.LOGIN)
    int getRowCount();


    @Query("DELETE FROM "  + TableNames.LOGIN)
    void nukeTable();

    @Query("UPDATE " + TableNames.LOGIN+" SET password = :password WHERE server_id =:id")
    void updatePassword(String password, int id);

    @Query("UPDATE " + TableNames.LOGIN+" SET image_name = :image_name WHERE server_id =:id")
    void updateImageName(String image_name, int id);
}
