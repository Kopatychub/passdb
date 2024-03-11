package com.example.passdb.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database([UserItem::class, PassItem::class], version = 1)
abstract class MainDataBase : RoomDatabase() {
    abstract fun mainDao(): mainDao

    companion object{
        var db: MainDataBase? = null

        fun getDataBase(context: Context) :MainDataBase {
            if (db == null){
                db = Room.databaseBuilder(context, MainDataBase::class.java, "passDB").build()
            }
            return db!!
        }
    }

}