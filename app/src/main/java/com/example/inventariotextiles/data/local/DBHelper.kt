package com.example.inventariotextiles.data.local


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.UUID

class DBHelper(context: Context) : SQLiteOpenHelper(context, "inventario.db", null, 1) {

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
                ${Columnas.DESCRIPCION} TEXT
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

    // Métodos de ejemplo para operaciones CRUD de prendas
    fun agregarPrenda(codigoBarras: String, nombre: String, talla: String, color: String, descripcion: String?): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(Columnas.ID, UUID.randomUUID().toString())
            put(Columnas.CODIGO_BARRAS, codigoBarras)
            put(Columnas.NOMBRE, nombre)
            put(Columnas.TALLA, talla)
            put(Columnas.COLOR, color)
            put(Columnas.DESCRIPCION, descripcion)
        }
        return db.insert(Tablas.PRENDAS, null, values)
    }

    fun obtenerPrendaPorCodigo(codigo: String): Prenda? {
        val db = readableDatabase
        val cursor = db.query(
            Tablas.PRENDAS,
            null,
            "${Columnas.CODIGO_BARRAS} = ?",
            arrayOf(codigo),
            null, null, null
        )
        return if (cursor.moveToFirst()) {
            Prenda(
                id = cursor.getString(cursor.getColumnIndexOrThrow(Columnas.ID)),
                codigoBarras = cursor.getString(cursor.getColumnIndexOrThrow(Columnas.CODIGO_BARRAS)),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow(Columnas.NOMBRE)),
                talla = cursor.getString(cursor.getColumnIndexOrThrow(Columnas.TALLA)),
                color = cursor.getString(cursor.getColumnIndexOrThrow(Columnas.COLOR)),
                descripcion = cursor.getString(cursor.getColumnIndexOrThrow(Columnas.DESCRIPCION))
            )
        } else {
            null
        }.also { cursor.close() }
    }

    // Clases de modelo
    data class Prenda(
        val id: String,
        val codigoBarras: String,
        val nombre: String,
        val talla: String,
        val color: String,
        val descripcion: String?
    )

    data class Usuario(
        val id: String,
        val nombre: String,
        val usuario: String,
        val password: String,
        val cargo: Int
    )
}