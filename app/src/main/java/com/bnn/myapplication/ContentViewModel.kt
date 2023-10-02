package com.bnn.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bnn.myapplication.data.DataRepo
import com.bnn.myapplication.data.Developer
import com.bnn.myapplication.data.RoomDatabaseWrapper

class ContentViewModel (application: Application) : AndroidViewModel(application) {
	private val repository: DataRepo
	val dataList: LiveData<List<Developer>>

	init {
		//serverside with courotines component viewmodelScope
		val wordsDao = RoomDatabaseWrapper.getDatabase(application, viewModelScope).daoWrapper()
		repository = DataRepo(wordsDao)
		dataList = repository.dataList
	}

	fun insertData(){

	}
}