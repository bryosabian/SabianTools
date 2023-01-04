package com.sabiantools.extensions

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

fun SQLiteDatabase.queryAll(
    table: String, selection: String? = null,
    selectionArgs: Array<String>? = null,
    columns: Array<String>? = null,
    limit: String? = null,
    orderBy: String? = null
): Cursor {
    return query(table, columns, selection, selectionArgs, null, null, orderBy, limit);
}