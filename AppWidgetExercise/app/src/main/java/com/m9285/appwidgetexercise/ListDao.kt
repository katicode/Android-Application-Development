package com.m9285.appwidgetexercise

/*
This file contains the methods used for accessing the database.
All list items can be queried with getAll() function.
This function will return MutableList with ListItems.
A new item can be added with insert() function.
This function will return the auto increment id (Long) to the caller application.
One item can be deleted with a delete() function. This function id parameter will be used inside "DELETE FROM" query.
 */

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface ListDao {

    @Query("SELECT * from list_table")
    fun getAll(): MutableList<ListItem>

    @Insert
    fun insert(item: ListItem) : Long

    @Query("DELETE FROM list_table WHERE id = :itemId")
    fun delete(itemId: Int)

}