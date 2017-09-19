package tech.babashnik.olimp.inventory.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.*

class DataBase(context: Context) {
    private var db: SQLiteDatabase? = null

    init {
        val dbHelper = DBHelper(context)
        db = dbHelper.writableDatabase
    }

    //Добавляет элемент в таблицу
    fun insert(table: String, sm: Map<*, *>): Long {
        val cv = ContentValues()
        for (o in sm.entries) {
            val pair = o
            if (pair.value.toString().isNotEmpty())
                cv.put(pair.key as String, pair.value.toString())
        }
        return db!!.insert(table, null, cv)
    }

    //Обновляет элемент таблицы по ID
    fun update(table: String, id: Int, sm: Map<*, *>): Long {
        val cv = ContentValues()
        for (o in sm.entries) {
            val pair = o
            if (pair.value.toString().isNotEmpty())
                cv.put(pair.key as String, pair.value.toString())
        }
        return db!!.update(table, cv, "id=" + id, null).toLong()
    }

    //Добавляет или обновляет элемент в таблицу
    fun insertOrUpdate(table: String, where: String, map: HashMap<String, String>) {
        val bd_item = query(table, null, where, null, null, null, null)
        if (bd_item != null && bd_item.moveToFirst()) {//Элемент в таблице есть - обновляем данные
            update(table, bd_item.getInt(bd_item.getColumnIndex("id")), map)
            bd_item.close()
        } else {//Элемента в таблиц нет - добавляем его
            insert(table, map)
        }
    }

    //Удаляет по запросу элемент из таблицы
    fun delete(table: String, whereClause: String, whereArgs: Array<String>): Int {
        return db!!.delete(table, whereClause, whereArgs)
    }

    //Поучает Cursor по запросу из таблицы
    fun query(table: String, columns: Array<String>?, selection: String,
              selectionArgs: Array<String>?, groupBy: String?, having: String?,
              orderBy: String?): Cursor? {
        var orderBy = orderBy
        if (orderBy == null)
            orderBy = "id DESC"
        return db!!.query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    //Получает кол-во элементов в таблице по запросу
    fun getCount(table: String, where: String): Int {
        val c = db!!.query(table, null, where, null, null, null, null)
        val ret = c.count
        c.close()
        return ret
    }

    //Удаляет элемент по ID из таблицы
    fun removeById(table: String, id: Int): Int {
        return db!!.delete(table, "id = " + id, null)
    }

    fun close() {
        try {
            db!!.endTransaction()
        } catch (ignored: IllegalStateException) {
        } finally {
            db!!.close()
        }

        db = null
    }

    private inner class DBHelper internal constructor(context: Context) : SQLiteOpenHelper(context, "appOlimp", null, 1) {

        //При создании базы данных
        override fun onCreate(db: SQLiteDatabase) {
            Log.d(LOG_TAG, "--- onCreate database ---")
            ////////////////////
            db.execSQL("CREATE TABLE olimp_inventory_items (" +
                    "name TEXT PRIMARY KEY," +
                    "title TEXT DEFAULT NULL," +
                    "description TEXT DEFAULT NULL," +
                    "href TEXT DEFAULT NULL);"
            )

        }

        //При обновлении приложения
        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS appOlimp")
            onCreate(db)
        }
    }

    companion object {

        private val LOG_TAG = "DataBase"
    }


}