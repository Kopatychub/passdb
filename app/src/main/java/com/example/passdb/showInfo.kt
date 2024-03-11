package com.example.passdb

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.passdb.DB.MainDataBase
import com.example.passdb.DB.PassItem
import com.example.passdb.databinding.ActivityShowInfoBinding

class showInfo : AppCompatActivity() {
    private lateinit var binding: ActivityShowInfoBinding
    private lateinit var db: MainDataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = MainDataBase.getDataBase(this)
        val todoItem = detOldItem()
        binding.shName.setText(todoItem.name)
        binding.shLogin.setText(todoItem.login)
        binding.shPassword.setText(decryptText(todoItem.password))
    }



    fun detOldItem(): PassItem {
        val id = intent.getIntExtra("id", -1)
        val site = intent.getStringExtra("site") ?: ""
        val login = intent.getStringExtra("login") ?: ""
        val password = intent.getStringExtra("password") ?: ""
        return PassItem(id,site,login,password)
    }

    fun decryptText(text: String): String? {
        val textCharMass = text.toCharArray()
        val decryptedText = CharArray(textCharMass.size)
        for (i in textCharMass.indices) {
            decryptedText[i] = (textCharMass[i].code - R.attr.key).toChar()
        }
        return String(decryptedText)
    }
}