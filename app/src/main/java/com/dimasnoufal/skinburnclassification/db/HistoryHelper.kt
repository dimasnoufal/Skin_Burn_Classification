//package com.dimasnoufal.skinburnclassification.db
//
//import android.content.ContentValues
//import android.content.Context
//import android.database.Cursor
//import android.database.SQLException
//import android.database.sqlite.SQLiteDatabase
//import com.dimasnoufal.skinburnclassification.db.DatabaseContract.HistoryColumns.Companion.TABLE_NAME
//import com.dimasnoufal.skinburnclassification.db.DatabaseContract.HistoryColumns.Companion._ID
//
//class HistoryHelper(context: Context) {
//
//    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
//    private lateinit var database: SQLiteDatabase
//
//    @Throws(SQLException::class)
//    fun open() {
//        database = dataBaseHelper.writableDatabase
//    }
//    fun close() {
//        dataBaseHelper.close()
//        if (database.isOpen)
//            database.close()
//    }
//
//    fun queryAll(): Cursor {
//        return database.query(
//            DATABASE_TABLE,
//            null,
//            null,
//            null,
//            null,
//            null,
//            "$_ID ASC")
//    }
//
//    fun insert(values: ContentValues?): Long {
//        return database.insert(DATABASE_TABLE, null, values)
//    }
//
//    fun update(id: String, values: ContentValues?): Int {
//        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
//    }
//
//    fun deleteById(id: String): Int {
//        return database.delete(DATABASE_TABLE, "$_ID = '$id'", null)
//    }
//
//    companion object {
//        private const val DATABASE_TABLE = TABLE_NAME
//        private var INSTANCE: HistoryHelper? = null
//        fun getInstance(context: Context): HistoryHelper =
//            INSTANCE ?: synchronized(this) {
//                INSTANCE ?: HistoryHelper(context)
//            }
//    }
//}