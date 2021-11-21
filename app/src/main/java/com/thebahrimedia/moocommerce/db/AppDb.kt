package com.thebahrimedia.moocommerce.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = arrayOf(Item::class), version = 3)
abstract class AppDb: RoomDatabase() {
    abstract fun itemCRUD(): ItemCRUD
    companion object {
        private  var INSTANCE:AppDb?= null
        fun getInstance(context: Context): AppDb {
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDb::class.java, "item.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE!!
        }
        fun destroyInstance() {
            INSTANCE = null
        }

    }
}