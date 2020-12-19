package com.example.chef_louiardie

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASE_NAME = "MyDb"
val TABLE_NAME="favorites"
val COL_NAME = "recipeid"
val COL_ID = "id"

class DataBaseHandler(var context : Context) : SQLiteOpenHelper(context, DATABASE_NAME, null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME +" (" +
                COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " VARCHAR(20))"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
    fun insertData(recipeid : String){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_NAME, recipeid)
        var result = db.insert(TABLE_NAME, null, cv)
        if(result == -1.toLong()){
            Toast.makeText(context, "SOM TING WONG", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
        }
    }
    fun readData() : MutableList<String>{
        var list : MutableList<String> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME
        val result = db.rawQuery(query,null)
        if(result.moveToFirst()){
            do {
                var id = result.getString(result.getColumnIndex(COL_NAME)).toString()
                list.add(id)
            }while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }
    fun deleteRecipe(id: String){
        val db = this.readableDatabase
        val query = "DELETE FROM $TABLE_NAME WHERE $COL_NAME = $id"
        db?.execSQL(query)
        Toast.makeText(context, "Deleted from favorites", Toast.LENGTH_SHORT).show()
    }
    fun deleteData(){
        val db = this.writableDatabase
        db.delete(TABLE_NAME,null,null)
        db.close()
    }
    fun contains(recipeid: String) : Boolean{
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COL_NAME = $recipeid"
        val result = db.rawQuery(query,null)
        return result.moveToFirst()
    }
}