package com.m9285.roomshoppinglist

/*
This file contains the database holder and serves as the main access point
for the underlying connection to your app's persisted, relational data.
Above dao funtions can be called with shoppingListDao() function.
 */

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [ShoppingListItem::class], version = 1)
abstract class ShoppingListRoomDatabase : RoomDatabase() {
    abstract fun shoppingListDao(): ShoppingListDao
}