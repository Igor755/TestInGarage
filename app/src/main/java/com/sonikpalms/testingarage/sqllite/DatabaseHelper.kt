package com.sonikpalms.testingarage.sqllite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NAME (ID INTEGER PRIMARY KEY " +
        "AUTOINCREMENT, IMAGE INTEGER,NAME TEXT,LAST_NAME TEXT,EMAIL TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
    fun insertData(image : Int, name: String, last_name : String, email: String){
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_2, image)
        contentValues.put(COL_3, name)
        contentValues.put(COL_4, last_name)
        contentValues.put(COL_5, email)
        db.insert(TABLE_NAME, null, contentValues)
    }
    fun updateData(id : String, image : Int, name: String, last_name : String, email: String): Boolean {

            val db = this.writableDatabase
            val contentValues = ContentValues()
            contentValues.put(COL_1, id)
            contentValues.put(COL_2, image)
            contentValues.put(COL_3, name)
            contentValues.put(COL_4, last_name)
            contentValues.put(COL_5, email)
            db.update(TABLE_NAME, contentValues, "ID = ?", arrayOf(id))
            return true

    }
    fun deleteData(id : String) : Int{
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "ID = ?", arrayOf(id))
    }
    val allData : Cursor
    get() {
        val db = this.writableDatabase
        val res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
        return res
    }
    companion object{
        val DATABASE_NAME = "contacts.db"
        val TABLE_NAME = "contacts_table"
        val COL_1 = "ID"
        val COL_2 = "IMAGE"
        val COL_3 = "NAME"
        val COL_4 = "LAST_NAME"
        val COL_5 = "EMAIL"
    }

}