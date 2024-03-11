package com.example.passdb.DB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("passwords")
data class PassItem(
    @PrimaryKey(true) val id: Int? = null,
    val name: String,
    val login: String,
    val password: String
)
