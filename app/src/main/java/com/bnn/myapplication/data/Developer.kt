package com.bnn.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_table")
data class Developer constructor(
	@PrimaryKey
	@ColumnInfo(name = "developer")
	var developer: String,
	@ColumnInfo(name = "employeeId")
	var employeeId: String,
	@ColumnInfo(name = "personality")
	var employeePersonality: String,
	@ColumnInfo(name = "deservesBonus")
	var employeeBonus: String
)