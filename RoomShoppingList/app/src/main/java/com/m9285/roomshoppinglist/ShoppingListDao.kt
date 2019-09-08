package com.m9285.roomshoppinglist

/*
This file contains the methods used for accessing the database.
All shopping list items can be queried with getAll() function.
This function will return MutableList with ShoppingListItems.
A new item can be added with insert() function.
This function will return the auto increment id (Long) to the caller application.
One item can be deleted with a delete() function.
This function id parameter will be used inside "DELETE FROM" query.
 */

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface ShoppingListDao {

    @Query("SELECT * from shopping_list_table")
    fun getAll(): MutableList<ShoppingListItem>

    @Insert
    fun insert(item: ShoppingListItem) : Long

    @Query("DELETE FROM shopping_list_table WHERE id = :itemId")
    fun delete(itemId: Int)

}