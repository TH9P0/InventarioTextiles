package com.example.inventariotextiles.data.local

import android.content.ContentValues
import android.content.Context
import com.example.inventariotextiles.data.local.tables.ProductoTable
import com.example.inventariotextiles.domain.model.Producto
import kotlin.also
import kotlin.apply
import kotlin.io.use

class ProductoLocalDataSource(context: Context) {
    private val dbHelper = DBHelper(context)

    fun addProduct(name:String, price:String, description:String?): Long{
        val db = dbHelper.writableDatabase
        val date = System.currentTimeMillis() / 1000

        val cv = ContentValues().apply {
            put(ProductoTable.COLUMN_NAME, name)
            put(ProductoTable.COLUMN_PRICE, price)
            put(ProductoTable.COLUMN_DESCRIPTION, description)
            put(ProductoTable.COLUMN_DATEMODIFIED, date)
            put(ProductoTable.COLUMN_DELETED, 0)
        }

        val result = db.insert(ProductoTable.TABLE_NAME, "${ProductoTable.COLUMN_ID}=?", cv)
        db.close()
        return result
    }

    fun deleteProduct(id:String): Boolean{
        val db = dbHelper.writableDatabase
        return try {
            val rows = db.delete(ProductoTable.TABLE_NAME, "${ProductoTable.COLUMN_ID}=?",
                arrayOf(id)
            )
            rows > 0
        } catch (   e: Exception) {
            false
        } finally {
            db.close()
        }
    }

    fun updateProduct(id: String, name: String, price: String, description:String?): Int{
        val db = dbHelper.writableDatabase
        val date = System.currentTimeMillis() / 1000

        val cv = ContentValues().apply {
            put(ProductoTable.COLUMN_NAME,name)
            put(ProductoTable.COLUMN_PRICE,price)
            put(ProductoTable.COLUMN_DESCRIPTION,description)
            put(ProductoTable.COLUMN_DATEMODIFIED,date)
        }
        val result = db.update(ProductoTable.TABLE_NAME,cv,"${ProductoTable.COLUMN_ID}=?",
            arrayOf(id)
        )
        db.close()
        return result
    }

    fun getProductById(id:String): Producto?{
        val db = dbHelper.readableDatabase
        val cursor = db.query(ProductoTable.TABLE_NAME,
            arrayOf(
                ProductoTable.COLUMN_ID,
                ProductoTable.COLUMN_NAME,
                ProductoTable.COLUMN_PRICE,
                ProductoTable.COLUMN_DESCRIPTION
            ),"${ProductoTable.COLUMN_ID}=?",
            arrayOf(id),null,null,null)

        return if (cursor.moveToFirst()) {
            Producto(
                id = cursor.getString(0),
                name = cursor.getString(1),
                price = cursor.getString(2),
                description = cursor.getString(3) ?: "Sin descripcion"
            )
        } else {
            null
        }.also {
            cursor.close()
            db.close()
        }
    }

    fun getAllProducts(): List<Producto>{
        val db = dbHelper.readableDatabase
        val productList = mutableListOf<Producto>()

        val cursor = db.query(
            ProductoTable.TABLE_NAME,
            arrayOf(
                ProductoTable.COLUMN_ID,
                ProductoTable.COLUMN_NAME,
                ProductoTable.COLUMN_PRICE,
                ProductoTable.COLUMN_DESCRIPTION
            ),
            "${ ProductoTable.COLUMN_DELETED} =?",
            arrayOf("0"),
            null,
            null,
            "${ProductoTable.COLUMN_DATEMODIFIED} DESC"
        )

        cursor.use {
            while (it.moveToNext()){
                val product = Producto(
                    id = it.getString(0),
                    name = it.getString(1),
                    price = it.getString(2),
                    description = it.getString(3)?:"Sin descripcion"
                )
                productList.add(product)
            }
        }

        db.close()
        return productList
    }
}