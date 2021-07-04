package com.example.newpeopletest1

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.update_nick).setOnClickListener {
            AlertDialog.Builder(this).let {
                it.setTitle("修改昵称")
                it.setMessage("你确定要修改昵称吗？")
                it.setCancelable(true)
                it.setPositiveButton("确定"){diaog,which->
                    AlertDialog.Builder(this).let {
                        val editText = EditText(this)
                        it.setTitle("修改昵称")
//                        it.setMessage("你确定要修改昵称吗？")
                        it.setView(editText)
                        it.setCancelable(true)
                        it.setPositiveButton("确定"){diaog,which->
                            findViewById<TextView>(R.id.nickname).text = editText.text.toString()
                            Toast.makeText(this,"昵称修改成功",Toast.LENGTH_SHORT).show()
                        }
                        it.setNegativeButton("取消"){diaog,which->}
                        it
                    }.show()
                }
                it.setNegativeButton("取消"){diaog,which->}
                it
            }.show()
        }
    }
}