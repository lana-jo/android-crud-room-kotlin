package com.bnn.myapplication.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DataRepo(private val roomDao: RoomDao) {

	// LiveData observer will notify when the data has changed
	// Room executes all the queries on a separate threads
	val dataList: LiveData<List<Developer>> = roomDao.dataList()

	//Insertion has to be on a non-UI thread - in opposite applicaiton will crash. So we're making this a
	// suspend function so the caller methods know this.

	// For insertion here we're using "suspend" keyword for Coroutines
	// This way the caller methods are aware of using of non-UI threads

	// AsyncTask is also one of alternatives

	@Suppress("RedundantSuspendModifier")
	@WorkerThread
	suspend fun insert(developer: Developer) {
		roomDao.insert(developer)
	}
	suspend fun delete(developer: Developer) {
		roomDao.deleteDeveloper(developer)
	}
	suspend fun deleteAll() {
		roomDao.deleteAll()
	}
}