package com.m9285.roomwordsample

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/*
Each @Entity class represents a table. Annotate your class declaration to indicate that it's an entity.
Specify the name of the table if you want it to be different from the name of the class.
 */
@Entity(tableName = "word_table")
data class Word(@PrimaryKey @ColumnInfo(name = "word") val word: String)