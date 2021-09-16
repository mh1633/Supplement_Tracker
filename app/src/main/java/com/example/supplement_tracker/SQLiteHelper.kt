package com.example.supplement_tracker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

data class ITEM_LIST(
    var ID_Num: Long,
    var Name: String,
    var Item_desc: String, // 1 = True , 0 = False
    var Alarm_Enable: Int,
    var Item_Enable: Int,
    var Item_Image_URI: String,
    var Item_Cycle: String,
    var Item_Times: Int
)

data class ITEM_RECORD(
    var Record_No: Long?,
    var ID_Num: Long,
    var Name: String,
    var Datetime: Long?
)

class SQLiteHelper(context: Context, name: String, version: Int) : SQLiteOpenHelper(context, name, null, version) {
    lateinit var database: SQLiteDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        val create_Item_List = "CREATE TABLE ITEM_LIST (" +
                "ID_NUM INTEGER PRIMARY KEY, " +
                "Name TEXT, " +
                "Item_desc TEXT, " +
                "Alarm_Enable INTEGER, " +
                "Item_Enable INTEGER," +
                "Item_Image_URI text, " +
                "Item_Cycle TEXT, " +
                "Item_Times INTEGER" +
                ")"
        val create_Item_Record = "CREATE TABLE ITEM_RECORD (" +
                "Record_No INTEGER PRIMARY KEY, " +
                "ID_Num INTEGER, " +
                "Name TEXT, " +
                "Datetime INTEGER " +
                ")"
        db?.execSQL(create_Item_List)
        db?.execSQL(create_Item_Record)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun insertItem_List(Item_list: ITEM_LIST) {
        val values = ContentValues()
        values.put("Name", Item_list.Name)
        values.put("Item_desc", Item_list.Item_desc)
        values.put("Alarm_Enable", Item_list.Alarm_Enable)
        values.put("Item_Enable", Item_list.Item_Enable)
        values.put("Item_Image_URI", Item_list.Item_Image_URI)
        values.put("Item_Cycle", Item_list.Item_Cycle)
        values.put("Item_Times", Item_list.Item_Times)
        val wd = writableDatabase
        wd.insert("ITEM_LIST", null, values)
        wd.close()
    }

    fun select_AllItem_List(): MutableList<ITEM_LIST>{
        val list = mutableListOf<ITEM_LIST>()
        val select = "select * from ITEM_LIST WHERE Item_Enable = ?"
        //rawQuery("SELECT id, name FROM people WHERE name = ? AND id = ?", new String[] {"David", "2"});
        val rd = readableDatabase
        val cursor = rd.rawQuery(select, arrayOf("1"))
        while(cursor.moveToNext()){
            val id_num = cursor.getLong(cursor.getColumnIndex("ID_NUM"))
            val name = cursor.getString(cursor.getColumnIndex("Name"))
            val item_desc = cursor.getString(cursor.getColumnIndex("Item_desc"))
            val alarm_enable = cursor.getInt(cursor.getColumnIndex("Alarm_Enable"))
            val item_enable = cursor.getInt(cursor.getColumnIndex("Item_Enable"))
            val item_image_uri = cursor.getString(cursor.getColumnIndex("Item_Image_URI"))
            val item_cycle = cursor.getString(cursor.getColumnIndex("Item_Cycle"))
            val item_times = cursor.getInt(cursor.getColumnIndex("Item_Times"))
            list.add(ITEM_LIST(id_num, name, item_desc, alarm_enable, item_enable, item_image_uri, item_cycle, item_times))
        }
        cursor.close()
        rd.close()
        return list
    }

    fun update_Item_list(Item_list: ITEM_LIST){
        val values = ContentValues()
        values.put("Name", Item_list.Name)
        values.put("Item_desc", Item_list.Item_desc)
        values.put("Alarm_Enable", Item_list.Alarm_Enable)
        values.put("Item_Enable", Item_list.Item_Enable)
        values.put("Item_Image_URI", Item_list.Item_Image_URI)
        values.put("Item_Cycle", Item_list.Item_Cycle)
        values.put("Item_Times", Item_list.Item_Times)

        val wd = writableDatabase
        wd.update("ITEM_LIST", values, "ID_NUM = ${Item_list.ID_Num}", null)
        wd.close()
    }

    fun delete_Item_List(Item_list: ITEM_LIST){
        val delete = "DELETE FROM ITEM_LIST WHERE ID_NUM = ${Item_list.ID_Num}"
        val db = writableDatabase
        db.execSQL(delete)
        db.close()
    }

    fun insert_Item_Record(db: SQLiteDatabase? ,Item_Record: ITEM_RECORD){
        val insert_item_record = "INSERT INTO ITEM_RECORD (ID_Num, Name, Datetime) " +
                "values ('${Item_Record.ID_Num}', '${Item_Record.Name}', DATETIME(\"NOW\", \"LOCALTIME\"))"
        db?.execSQL(insert_item_record)
        db?.close()
    }
}