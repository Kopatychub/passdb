package com.example.passdb.DB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface mainDao {

    // passwords
    @Query("select * from passwords")
    fun getAllPass() : LiveData<List<PassItem>>

    @Update
    fun updatePass(todoItem: PassItem)

    @Insert
    fun addPass(todoItem: PassItem)

    @Delete
    fun deleteTodo(todoItem: PassItem)

    // user
    @Query("select * from user where id = 1")
    suspend fun getUser(): UserItem?

    @Insert
    fun addUser(todoItem: UserItem)

    @Update
    fun updateUser(todoItem: UserItem)

    @Query("delete from user where id = 1")
    fun deleteUser()
}