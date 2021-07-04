package com.example.newpeopletest2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StrDatabaseHelper(context: Context,name: String, version:Int): SQLiteOpenHelper(context,name,null,version) {

    private val createStrTable = "create table stringt(" +
            "id integer primary key autoincrement," +
            "value text," +
            "year integer," +
            "month integer," +
            "day integer)"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createStrTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}