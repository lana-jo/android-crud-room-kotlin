package com.bnn.myapplication.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao;
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RoomDao {
	@Query("SELECT * from data_table")
	fun dataList(): LiveData<List<Developer>>

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	fun insert(developer: Developer)

	@Query("DELETE FROM data_table")
	fun deleteAll()

	@Delete
	fun deleteDeveloper(developer: Developer)

	@Query("SELECT * FROM data_table where employeeId = :employeeId")
}
