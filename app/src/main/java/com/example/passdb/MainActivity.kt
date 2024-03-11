package com.example.passdb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.passdb.DB.MainDataBase
import com.example.passdb.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var db: MainDataBase
    lateinit var passAdapter: PassAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MainDataBase.getDataBase(this)
        passAdapter = PassAdapter(
            onClickItem = {
                val intent = Intent(this, showInfo::class.java)
                intent.putExtra("id", it.id)
                intent.putExtra("site", it.name)
                intent.putExtra("login", it.login)
                intent.putExtra("password", it.password)
                startActivity(intent)
            },
            onDeleteClickItem = {
                lifecycleScope.launch(Dispatchers.IO){
                    db.mainDao().deleteTodo(it)
                }
            },

            onChangeItem = {
                val intent = Intent(this, changePass::class.java)
                intent.putExtra("id", it.id)
                intent.putExtra("site", it.name)
                intent.putExtra("login", it.login)
                intent.putExtra("password", it.password)
                startActivity(intent)
            }
        )
        binding.todoItems.layoutManager = LinearLayoutManager(this)
        binding.todoItems.adapter = passAdapter

        db.mainDao().getAllPass().observe(this) {
            passAdapter.update(it)
        }

        binding.addBtn.setOnClickListener{
            startActivity(Intent(this, addPass::class.java))
//            println("11111111111111111111")
        }
    }
    }
