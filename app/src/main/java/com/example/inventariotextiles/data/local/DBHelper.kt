package com.example.inventariotextiles.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.inventariotextiles.data.local.tables.ProductoTable

class DBHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME = "inventario.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(ProductoTable.CREATE_TABLE)
        db.execSQL("""
                INSERT INTO ${ProductoTable.TABLE_NAME} (name, price, description, date_modified, eliminated) VALUES
                ('Zapatillas Running Elite', 89.99, 'Zapatillas deportivas para running de alta gama', strftime('%s', 'now'), 0),
        ('Camiseta Algodón Orgánico', 24.95, 'Camiseta 100% algodón orgánico, talla unisex', strftime('%s', 'now'), 0),
        ('Portátil UltraSlim Pro', 1299.00, 'Portátil de 15.6 pulgadas, 16GB RAM, 512GB SSD', strftime('%s', 'now'), 0),
        ('Smartphone X12', 799.00, 'Teléfono inteligente con cámara de 108MP', strftime('%s', 'now'), 0),
        ('Auriculares Bluetooth Noise Cancelling', 199.99, 'Auriculares inalámbricos con cancelación de ruido', strftime('%s', 'now'), 0),
        ('Botella Térmica 750ml', 29.95, 'Botella de acero inoxidable, mantiene temperatura 24h', strftime('%s', 'now'), 0),
        ('Mochila Impermeable', 59.50, 'Mochila resistente al agua con USB charging port', strftime('%s', 'now'), 0),
        ('Reloj Inteligente Fitness', 159.00, 'Monitor de actividad y notificaciones', strftime('%s', 'now'), 0),
        ('Cargador Inalámbrico 15W', 35.00, 'Cargador rápido Qi compatible', strftime('%s', 'now'), 0),
        ('Teclado Mecánico Gaming', 89.95, 'Teclado RGB con switches azules', strftime('%s', 'now'), 0),
        ('Ratón Ergonómico Inalámbrico', 45.50, 'Diseño ergonómico para largas horas de trabajo', strftime('%s', 'now'), 0),
        ('Monitor 27" 4K', 349.99, 'Pantalla IPS con HDR10', strftime('%s', 'now'), 0),
        ('Altavoz Bluetooth Portátil', 129.00, 'Sonido estéreo con 20h de autonomía', strftime('%s', 'now'), 0),
        ('Tablet Android Pro', 459.00, 'Tablet de 10.5 pulgadas con lápiz óptico', strftime('%s', 'now'), 0),
        ('Cámara Instantánea', 119.95, 'Cámara fotográfica con impresión inmediata', strftime('%s', 'now'), 0),
        ('Batería Externa 20000mAh', 49.99, 'Batería portátil con carga rápida', strftime('%s', 'now'), 0),
        ('Gafas de Sol Polarizadas', 89.00, 'Protección UV400, diseño unisex', strftime('%s', 'now'), 0),
        ('Set de Maletas Viaje 3 Piezas', 279.00, 'Set de maletas rígidas, 20/24/28 pulgadas', strftime('%s', 'now'), 0),
        ('Silla de Oficina Ergonómica', 229.95, 'Silla ajustable con soporte lumbar', strftime('%s', 'now'), 0),
        ('Mesa de Estudio Plegable', 89.50, 'Mesa compacta con superficie antideslizante', strftime('%s', 'now'), 0);""")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${ProductoTable.TABLE_NAME}")
        onCreate(db)
    }
}