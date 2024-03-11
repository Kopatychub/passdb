package com.example.passdb

import android.R.attr.key
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.passdb.DB.MainDataBase
import com.example.passdb.DB.PassItem
import com.example.passdb.databinding.ActivityAddPassBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class addPass : AppCompatActivity() {

    lateinit var binding: ActivityAddPassBinding
    lateinit var db: MainDataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MainDataBase.getDataBase(this)
        binding.btnAdd.setOnClickListener {

            val site = binding.etSite.text.toString()
            val login = binding.etLogin.text.toString()
            var password = binding.etPassword.text.toString()
            if (site.trim() != "" && login.trim() != "" && password.trim() != "") {
                password = encryptText(password)
                val todo = PassItem(name = site, login = login, password = password)

                lifecycleScope.launch(Dispatchers.IO) {
                    db.mainDao().addPass(todo)
                    finish()
                }
            } else {showDailog()}
        }
    }

    fun encryptText(text: String): String {
        val textCharMass = text.toCharArray()
        val encryptedText = CharArray(textCharMass.size)
        for (i in textCharMass.indices) {
            encryptedText[i] = (textCharMass[i].code + key).toChar()
        }
        return String(encryptedText)
    }

    private fun showDailog(){
        MaterialAlertDialogBuilder(this)
            .setTitle("Ошибка")
            .setMessage("Не корректный ввод")
            .show()
    }

}