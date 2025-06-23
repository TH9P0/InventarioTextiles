package com.example.inventariotextiles.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.core.graphics.scale
import com.example.inventariotextiles.R
import com.example.inventariotextiles.domain.model.Prenda
import java.io.ByteArrayOutputStream
import java.util.UUID

class DBHelper(context: Context) : SQLiteOpenHelper(context, "inventario.db", null, 1) {
    private val context: Context = context

    // Definición de tablas y columnas
    object Tablas {
        const val PRENDAS = "prendas"
        const val CARGOS = "cargos"
        const val PACKING_LISTS = "packing_lists"
        const val USUARIOS = "usuarios"
        const val ALMACENES = "almacenes"
        const val DETALLE_INVENTARIO = "detalle_inventario_almacenes"
        const val DETALLE_PACKING = "detalle_packing_lists"
    }

    object Columnas {
        // Comunes
        const val ID = "id"
        const val NOMBRE = "Nombre"

        // Prendas
        const val CODIGO_BARRAS = "Codigo_Barras"
        const val TALLA = "Talla"
        const val COLOR = "Color"
        const val DESCRIPCION = "Descripcion"
        const val IMAGEN = "imagen"

        // Cargos
        const val ID_CARGO = "ID_Cargo"
        const val NOMBRE_CARGO = "Nombre_Cargo"
        const val NIVEL_CARGO = "Nivel_Cargo"

        // Packing Lists
        const val FOLIO = "Folio"
        const val ENVIA = "Envia"
        const val RECIBE = "Recibe"
        const val TRANSPORTE = "Transporte"
        const val CODIGO_CONTENEDOR = "Codigo_Contenedor"

        // Usuarios
        const val USUARIO = "User"
        const val PASSWORD = "Password"
        const val CARGO = "Cargo"

        // Almacenes
        const val UBICACION = "Ubicacion"

        // Detalles
        const val CODIGO_ALMACEN = "Codigo_Almacen"
        const val PRENDAS = "Prendas"
        const val CANTIDAD = "Cantidad"
        const val PACKING_LIST_ID = "PackingList_id"
    }

    private fun insertarPrendasDePrueba(db: SQLiteDatabase) {
        val prendas = listOf(
            ContentValues().apply {
                put(Columnas.ID, UUID.randomUUID().toString())
                put(Columnas.CODIGO_BARRAS, "123456789")
                put(Columnas.NOMBRE, "Camiseta Blanca")
                put(Columnas.TALLA, "M")
                put(Columnas.COLOR, "Blanco")
                put(Columnas.DESCRIPCION, "Camiseta de algodón 100%")
                put(Columnas.IMAGEN, convertImageToBase64(context, R.drawable.camiseta_ejemplo))
            },
            ContentValues().apply {
                put(Columnas.ID, UUID.randomUUID().toString())
                put(Columnas.CODIGO_BARRAS, "987654321")
                put(Columnas.NOMBRE, "Pantalón Vaquero")
                put(Columnas.TALLA, "32")
                put(Columnas.COLOR, "Azul")
                put(Columnas.DESCRIPCION, "Pantalón de mezclilla resistente")
                put(Columnas.IMAGEN, convertImageToBase64(context, R.drawable.pantalon_ejemplo))
            }
        )

        prendas.forEach { values ->
            db.insert(Tablas.PRENDAS, null, values)
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Crear tablas
        crearTablaPrendas(db)
        crearTablaCargos(db)
        crearTablaPackingLists(db)
        crearTablaUsuarios(db)
        crearTablaAlmacenes(db)
        crearTablaDetalleInventario(db)
        crearTablaDetallePacking(db)

        // Insertar datos iniciales
        insertarCargosIniciales(db)
        insertarUsuarioAdmin(db)
        insertarPrendasDePrueba(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Eliminar tablas en orden inverso (por dependencias de FK)
        db.execSQL("DROP TABLE IF EXISTS ${Tablas.DETALLE_PACKING}")
        db.execSQL("DROP TABLE IF EXISTS ${Tablas.DETALLE_INVENTARIO}")
        db.execSQL("DROP TABLE IF EXISTS ${Tablas.USUARIOS}")
        db.execSQL("DROP TABLE IF EXISTS ${Tablas.PACKING_LISTS}")
        db.execSQL("DROP TABLE IF EXISTS ${Tablas.ALMACENES}")
        db.execSQL("DROP TABLE IF EXISTS ${Tablas.PRENDAS}")
        db.execSQL("DROP TABLE IF EXISTS ${Tablas.CARGOS}")
        onCreate(db)
    }

    private fun crearTablaPrendas(db: SQLiteDatabase) {
        val query = """
            CREATE TABLE ${Tablas.PRENDAS} (
                ${Columnas.ID} TEXT PRIMARY KEY,
                ${Columnas.CODIGO_BARRAS} TEXT UNIQUE NOT NULL,
                ${Columnas.NOMBRE} TEXT NOT NULL,
                ${Columnas.TALLA} TEXT NOT NULL,
                ${Columnas.COLOR} TEXT NOT NULL,
                ${Columnas.DESCRIPCION} TEXT,
                ${Columnas.IMAGEN} TEXT 
            );
        """.trimIndent()
        db.execSQL(query)
    }

    private fun crearTablaCargos(db: SQLiteDatabase) {
        val query = """
            CREATE TABLE ${Tablas.CARGOS} (
                ${Columnas.ID_CARGO} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${Columnas.NOMBRE_CARGO} TEXT NOT NULL,
                ${Columnas.NIVEL_CARGO} INTEGER NOT NULL
            );
        """.trimIndent()
        db.execSQL(query)
    }

    private fun crearTablaPackingLists(db: SQLiteDatabase) {
        val query = """
            CREATE TABLE ${Tablas.PACKING_LISTS} (
                ${Columnas.ID} TEXT PRIMARY KEY,
                ${Columnas.FOLIO} TEXT UNIQUE NOT NULL,
                ${Columnas.ENVIA} TEXT NOT NULL,
                ${Columnas.RECIBE} TEXT NOT NULL,
                ${Columnas.TRANSPORTE} TEXT NOT NULL,
                ${Columnas.CODIGO_CONTENEDOR} TEXT NOT NULL
            );
        """.trimIndent()
        db.execSQL(query)
    }

    private fun crearTablaUsuarios(db: SQLiteDatabase) {
        val query = """
            CREATE TABLE ${Tablas.USUARIOS} (
                ${Columnas.ID} TEXT PRIMARY KEY,
                ${Columnas.NOMBRE} TEXT NOT NULL,
                ${Columnas.USUARIO} TEXT UNIQUE NOT NULL,
                ${Columnas.PASSWORD} TEXT NOT NULL,
                ${Columnas.CARGO} INTEGER NOT NULL,
                FOREIGN KEY (${Columnas.CARGO}) 
                REFERENCES ${Tablas.CARGOS}(${Columnas.ID_CARGO})
            );
        """.trimIndent()
        db.execSQL(query)
    }

    private fun crearTablaAlmacenes(db: SQLiteDatabase) {
        val query = """
            CREATE TABLE ${Tablas.ALMACENES} (
                ${Columnas.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${Columnas.NOMBRE} TEXT NOT NULL,
                ${Columnas.UBICACION} TEXT NOT NULL
            );
        """.trimIndent()
        db.execSQL(query)
    }

    private fun crearTablaDetalleInventario(db: SQLiteDatabase) {
        val query = """
            CREATE TABLE ${Tablas.DETALLE_INVENTARIO} (
                ${Columnas.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${Columnas.CODIGO_ALMACEN} INTEGER NOT NULL,
                ${Columnas.PRENDAS} TEXT NOT NULL,
                ${Columnas.CANTIDAD} INTEGER NOT NULL,
                FOREIGN KEY (${Columnas.CODIGO_ALMACEN}) 
                REFERENCES ${Tablas.ALMACENES}(${Columnas.ID}),
                FOREIGN KEY (${Columnas.PRENDAS}) 
                REFERENCES ${Tablas.PRENDAS}(${Columnas.CODIGO_BARRAS})
            );
        """.trimIndent()
        db.execSQL(query)
    }

    private fun crearTablaDetallePacking(db: SQLiteDatabase) {
        val query = """
            CREATE TABLE ${Tablas.DETALLE_PACKING} (
                ${Columnas.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${Columnas.PACKING_LIST_ID} TEXT NOT NULL,
                ${Columnas.PRENDAS} TEXT NOT NULL,
                ${Columnas.CANTIDAD} INTEGER NOT NULL,
                FOREIGN KEY (${Columnas.PACKING_LIST_ID}) 
                REFERENCES ${Tablas.PACKING_LISTS}(${Columnas.ID}),
                FOREIGN KEY (${Columnas.PRENDAS}) 
                REFERENCES ${Tablas.PRENDAS}(${Columnas.CODIGO_BARRAS})
            );
        """.trimIndent()
        db.execSQL(query)
    }

    private fun insertarCargosIniciales(db: SQLiteDatabase) {
        val cargos = listOf(
            ContentValues().apply {
                put(Columnas.NOMBRE_CARGO, "Administrador")
                put(Columnas.NIVEL_CARGO, 1)
            },
            ContentValues().apply {
                put(Columnas.NOMBRE_CARGO, "Empleado")
                put(Columnas.NIVEL_CARGO, 2)
            }
        )
        cargos.forEach { db.insert(Tablas.CARGOS, null, it) }
    }

    private fun insertarUsuarioAdmin(db: SQLiteDatabase) {
        val admin = ContentValues().apply {
            put(Columnas.ID, UUID.randomUUID().toString())
            put(Columnas.NOMBRE, "Admin Principal")
            put(Columnas.USUARIO, "admin")
            put(Columnas.PASSWORD, "admin123")
            put(Columnas.CARGO, 1) // ID de Administrador
        }
        db.insert(Tablas.USUARIOS, null, admin)
    }

    // Métodos para operaciones CRUD de prendas
    fun agregarPrenda(prenda: Prenda): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(Columnas.ID, UUID.randomUUID().toString())
            put(Columnas.CODIGO_BARRAS, prenda.codigoBarras)
            put(Columnas.NOMBRE, prenda.nombre)
            put(Columnas.TALLA, prenda.talla)
            put(Columnas.COLOR, prenda.color)
            put(Columnas.DESCRIPCION, prenda.descripcion)
            put(Columnas.IMAGEN, prenda.imagen)
        }
        return db.insert(Tablas.PRENDAS, null, values)
    }

    fun actualizarPrenda(prenda: Prenda): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(Columnas.CODIGO_BARRAS, prenda.codigoBarras)
            put(Columnas.NOMBRE, prenda.nombre)
            put(Columnas.TALLA, prenda.talla)
            put(Columnas.COLOR, prenda.color)
            put(Columnas.DESCRIPCION, prenda.descripcion)
            put(Columnas.IMAGEN, prenda.imagen)
        }
        return db.update(
            Tablas.PRENDAS,
            values,
            "${Columnas.ID} = ?",
            arrayOf(prenda.id)
        )
    }

    fun obtenerPrendaPorId(id: String): Prenda? {
        val db = readableDatabase
        val cursor = db.query(
            Tablas.PRENDAS,
            null,
            "${Columnas.ID} = ?",
            arrayOf(id),
            null, null, null
        )
        return if (cursor.moveToFirst()) {
            Prenda(
                id = cursor.getString(cursor.getColumnIndexOrThrow(Columnas.ID)),
                codigoBarras = cursor.getString(cursor.getColumnIndexOrThrow(Columnas.CODIGO_BARRAS)),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow(Columnas.NOMBRE)),
                talla = cursor.getString(cursor.getColumnIndexOrThrow(Columnas.TALLA)),
                color = cursor.getString(cursor.getColumnIndexOrThrow(Columnas.COLOR)),
                descripcion = cursor.getString(cursor.getColumnIndexOrThrow(Columnas.DESCRIPCION)),
                imagen = cursor.getString(cursor.getColumnIndexOrThrow(Columnas.IMAGEN))
            )
        } else {
            null
        }.also { cursor.close() }
    }

    fun obtenerTodasLasPrendas(): List<Prenda> {
        val db = readableDatabase
        val prendas = mutableListOf<Prenda>()
        val cursor = db.query(
            Tablas.PRENDAS,
            null, null, null, null, null, null
        )

        cursor.use {
            while (it.moveToNext()) {
                prendas.add(
                    Prenda(
                        id = it.getString(it.getColumnIndexOrThrow(Columnas.ID)),
                        codigoBarras = it.getString(it.getColumnIndexOrThrow(Columnas.CODIGO_BARRAS)),
                        nombre = it.getString(it.getColumnIndexOrThrow(Columnas.NOMBRE)),
                        talla = it.getString(it.getColumnIndexOrThrow(Columnas.TALLA)),
                        color = it.getString(it.getColumnIndexOrThrow(Columnas.COLOR)),
                        descripcion = it.getString(it.getColumnIndexOrThrow(Columnas.DESCRIPCION)),
                        imagen = it.getString(it.getColumnIndexOrThrow(Columnas.IMAGEN))
                    )
                )
            }
        }
        return prendas
    }

    fun eliminarPrenda(id: String): Int {
        val db = writableDatabase
        return db.delete(
            Tablas.PRENDAS,
            "${Columnas.ID} = ?",
            arrayOf(id)
        )
    }

    fun convertImageToBase64(context: Context, drawableId: Int): String {
        val bitmap = BitmapFactory.decodeResource(context.resources, drawableId)
        val scaledBitmap = bitmap.scale(100, 100)

        val byteArrayOutputStream = ByteArrayOutputStream()
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}