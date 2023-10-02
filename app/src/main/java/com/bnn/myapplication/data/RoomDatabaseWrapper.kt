package com.bnn.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bnn.myapplication.R
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream


@Database(
	entities = [Company::class, Career::class, Details::class, Developer::class],
	version = 1,
	exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RoomDatabaseWrapper : RoomDatabase() {

	//	abstract fun roomDao(): RoomDao
	abstract fun daoWrapper(): RoomDao


	companion object {
		@Volatile
		private var INSTANCE: RoomDatabaseWrapper? = null

		fun getDatabase(
			context: Context, scope: CoroutineScope
		): RoomDatabaseWrapper {
			// if the INSTANCE is not null, then return it,
			// if it is, then create the database
			return INSTANCE ?: synchronized(this) {
				val instance = Room.databaseBuilder(
					context.applicationContext, RoomDatabaseWrapper::class.java, "data_database"
				).fallbackToDestructiveMigration().addCallback(
						RoomDatabaseWrapperCallback(
							context, scope
						)
					).build()
				INSTANCE = instance
				instance
			}
		}

		private class RoomDatabaseWrapperCallback(
			private val context: Context, private val scope: CoroutineScope
		) : RoomDatabase.Callback() {
			override fun onOpen(db: SupportSQLiteDatabase) {
				super.onOpen(db)
				// If you want to keep the data through app restarts,
				// comment out the following line.
				INSTANCE?.let { database ->
					scope.launch(Dispatchers.IO) {
						val jsonObj = JsonParser().parse(
							readJSONFromAsset(
								context
							)
						).asJsonObject

						val companyType = object : TypeToken<Company>() {}.type
						val company: Company = Gson().fromJson(jsonObj, companyType)

						populateDatabase(
							database, company
						)
					}
				}
			}
		}

		// Populate the database from data.json file - needs to be in a new coroutine
		// If needed, you can parse the fields you want from the database and use them
		// This example shows a list by the Developer object/class

		fun populateDatabase(database: RoomDatabaseWrapper, company: Company) {
			val roomDao = database.daoWrapper()

			// Empty database on first load
			roomDao.deleteAll()

			val developerList = company.career?.developers
			developerList?.forEach {
				roomDao.insert(Developer(it.developer))
			}
		}

		fun readJSONFromAsset(context: Context): String {
			val json: String
			try {
				val inputStream: InputStream = context.assets.open(
					context.getString(
						R.string.companyRes
					)
				)
				json = inputStream.bufferedReader().use {
					it.readText()
				}
			} catch (ex: Exception) {
				ex.localizedMessage
				return ""
			}
			return json
		}
	}
}/*class RoomDatabaseWrapper  : RoomDatabaseWrapper()  {
	override fun clearAllTables() {
		var data: String? = null
	}

	override fun createInvalidationTracker(): InvalidationTracker {
		var data: String? = null
		return
	}

	override fun createOpenHelper(config: DatabaseConfiguration): SupportSQLiteOpenHelper {
		var data: String? = null
		return
	}
	}*/

