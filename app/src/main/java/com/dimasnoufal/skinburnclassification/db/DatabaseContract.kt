package com.dimasnoufal.skinburnclassification.db

import android.provider.BaseColumns

internal class DatabaseContract {
    internal class HistoryColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "history"
            const val _ID = "_id"
            const val TITLE = "title"
            const val DESCRIPTION = "description"
            const val DATE = "date"
            const val IMAGE = "image"
        }
    }
}