package com.sonikpalms.testingarage.sqllite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.sonikpalms.testingarage.pojo.Contact

class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE $TABLE_NAME (ID INTEGER PRIMARY KEY " +
                    "AUTOINCREMENT, IMAGE INTEGER,NAME TEXT,LAST_NAME TEXT,EMAIL TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(contact: Contact) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_2, contact.user_image)
        contentValues.put(COL_3, contact.user_name)
        contentValues.put(COL_4, contact.last_name)
        contentValues.put(COL_5, contact.email)
        db.insert(TABLE_NAME, null, contentValues)
    }

    fun updateData(
        id: String,
        image: Int,
        name: String,
        last_name: String,
        email: String
    ): Boolean {

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

    fun deleteData(id: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "ID = ?", arrayOf(id))
    }

    val allData: Cursor
        get() {
            val db = this.writableDatabase
            return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
        }

    companion object {
        val DATABASE_NAME = "contacts.db"
        val TABLE_NAME = "contacts_table"
        val COL_1 = "ID"
        val COL_2 = "IMAGE"
        val COL_3 = "NAME"
        val COL_4 = "LAST_NAME"
        val COL_5 = "EMAIL"
    }

    fun dropDb(context: Context) {
        context.deleteDatabase("contacts.db")

    }

    fun proverka(): Int {

        val db = this.writableDatabase
        val cursor: Cursor = db.rawQuery("select * from" + TABLE_NAME, null)
        Log.e("a", "" + cursor.count)
        return cursor.count
    }

    fun readData(): MutableList<Contact> {
        val list: MutableList<Contact> = ArrayList()
        val databaseHelper = this.writableDatabase
        val query = "Select * from " + TABLE_NAME
        val result = databaseHelper.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                var contact = Contact()
                contact.user_id = result.getString(result.getColumnIndex(COL_1))
                contact.user_image = result.getInt(result.getColumnIndex(COL_2))
                contact.user_name = result.getString(result.getColumnIndex(COL_3))
                contact.last_name = result.getString(result.getColumnIndex(COL_4))
                contact.email = result.getString(result.getColumnIndex(COL_5))
                list.add(contact)
            } while (result.moveToNext())
        }

        result.close()
        databaseHelper.close()
        return list
    }

}