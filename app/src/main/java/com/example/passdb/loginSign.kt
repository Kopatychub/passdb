package com.example.passdb

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.passdb.DB.MainDataBase
import com.example.passdb.DB.UserItem
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class loginSign : AppCompatActivity() {

    lateinit var topText: TextView
    lateinit var checkBtn: Button
    lateinit var passLine: TextInputEditText
    lateinit var errText: TextView
    private lateinit var db: MainDataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_sign)

        var topText = findViewById<TextView>(R.id.textView)
        var checkBtn = findViewById<Button>(R.id.checkBtn)
        var passLine = findViewById<TextInputEditText>(R.id.passw)
        var errText = findViewById<TextView>(R.id.errText)

        db = MainDataBase.getDataBase(this)

        lifecycleScope.launch(Dispatchers.IO) {
//            db.mainDao().deleteUser()
            val pass = db.mainDao().getUser()
            if (db.mainDao().getUser() == null){
                topText.setText("Задайте пароль")
                checkBtn.setText("Задать")
                checkBtn.setOnClickListener{
                    if(passLine.text.toString().length < 8){
                        errText.setText("Должно быть не менее 8 символов")
                    } else {
                        val user: UserItem = UserItem(id = 1 ,password = passLine.text.toString())
                        lifecycleScope.launch(Dispatchers.IO) {
                            db.mainDao().addUser(user)
                        }
                        reg()
                    }
                }
            } else {
                checkBtn.setOnClickListener {

                if (passLine.text?.toString()?.trim()?.length!! >= 8 &&
                    pass!!.password == passLine.text?.toString()?.trim()) {
                    passLine.text?.clear()
                    reg()


                } else {
                    showDailog()
                    }
                }
            }
        }
    }
    private fun showDailog(){
        MaterialAlertDialogBuilder(this)
            .setTitle("Ошибка")
            .setMessage("Введен неверный пароль")
            .show()
    }

    fun reg(){
        startActivity(Intent(this, MainActivity::class.java))
    }

}
