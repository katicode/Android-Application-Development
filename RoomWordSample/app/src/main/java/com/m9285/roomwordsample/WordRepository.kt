package com.m9285.roomwordsample

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

//create repository
class WordRepository(private val wordDao: WordDao) {

    val allWords: LiveData<List<Word>> = wordDao.getAllWords()

    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}