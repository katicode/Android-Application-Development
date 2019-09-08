package com.m9285.appwidgetexercise

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


/*
This file represents a table within the Room database.
Table name will be list_table, id will be autoGenerate id and other variables describes a list data.
 */

@Entity(tableName = "list_table")
data class ListItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var asia: String?
    //var pvm: String?
)