package com.example.passdb.DB

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("user")
data class UserItem(
    @PrimaryKey(false) val id: Int? = null,
    val password: String
)
