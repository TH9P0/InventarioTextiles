package com.example.inventariotextiles.data.local.tables

object ProductoTable {
    const val TABLE_NAME = "product"
    const val COLUMN_ID = "id_product"
    const val COLUMN_NAME = "name"
    const val COLUMN_PRICE = "price"
    const val COLUMN_DESCRIPTION = "description"
    const val COLUMN_DATEMODIFIED = "date_modified"
    const val COLUMN_DELETED = "eliminated"

    const val CREATE_TABLE="""
        CREATE TABLE $TABLE_NAME (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
            $COLUMN_NAME TEXT NOT NULL,
            $COLUMN_PRICE DOUBLE NOT NULL,
            $COLUMN_DESCRIPTION TEXT,
            $COLUMN_DATEMODIFIED INTEGER NOT NULL,
            $COLUMN_DELETED BOOL NOT NULL
        )
    """
}