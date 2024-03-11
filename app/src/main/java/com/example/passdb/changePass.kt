package com.example.passdb

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.passdb.DB.MainDataBase
import com.example.passdb.DB.PassItem
import com.example.passdb.databinding.ActivityChangePassBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class changePass : AppCompatActivity() {

    private lateinit var binding: ActivityChangePassBinding
    private lateinit var db: MainDataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePassBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = MainDataBase.getDataBase(this)
        val todoItem = detOldItem()
        binding.ettSite.setText(todoItem.name)
        binding.ettLogin.setText(todoItem.login)
        binding.ettPassword.setText(decryptText(todoItem.password))
        binding.btnSave.setOnClickListener{
            lifecycleScope.launch(Dispatchers.IO){

                val site = binding.ettSite.text.toString()
                val login = binding.ettLogin.text.toString()
                var password = binding.ettPassword.text.toString()
                if (site.trim() != "" && login.trim() != "" && password.trim() != "") {
                    password = encryptText(password)
                    db.mainDao().updatePass(todoItem.copy(name = site, login = login, password = password))
                    finish()
                }
            }
        }
    }

    fun detOldItem(): PassItem{
        val id = intent.getIntExtra("id", -1)
        val site = intent.getStringExtra("site") ?: ""
        val login = intent.getStringExtra("login") ?: ""
        val password = intent.getStringExtra("password") ?: ""
        return PassItem(id,site,login,password)
    }

    fun encryptText(text: String): String {
        val textCharMass = text.toCharArray()
        val encryptedText = CharArray(textCharMass.size)
        for (i in textCharMass.indices) {
            encryptedText[i] = (textCharMass[i].code + R.attr.key).toChar()
        }
        return String(encryptedText)
    }
    fun decryptText(text: String): String {
        val textCharMass = text.toCharArray()
        val decryptedText = CharArray(textCharMass.size)
        for (i in textCharMass.indices) {
            decryptedText[i] = (textCharMass[i].code - R.attr.key).toChar()
        }
        return String(decryptedText)
    }
    private fun showDailog(){
        MaterialAlertDialogBuilder(this)
            .setTitle("Ошибка")
            .setMessage("Не корректный ввод")
            .show()
    }


}