package com.m9285.appwidgetexercise

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/*
This file contains the database holder and serves as the main access point
for the underlying connection to your app's persisted, relational data.
Above dao funtions can be called with ListDao() function.
 */

@Database(entities = [ListItem::class], version = 1)
abstract class ListRoomDatabase : RoomDatabase() {
    abstract fun ListDao(): ListDao
}