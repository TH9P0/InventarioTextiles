package com.example.inventariotextiles

import java.text.SimpleDateFormat
import java.util.*

fun main() {
    // Inicializar las listas de ejemplo
    val izquierda = arrayListOf(
        arrayOf("ID1", "Dato izquierda 1", "2014-01-14"),
        arrayOf("ID2", "Dato izquierda 2", "2015-01-14"),
        arrayOf("ID3", "Dato izquierda 3", "2016-01-14")
    )

    val derecha = arrayListOf(
        arrayOf("ID2", "Dato derecha 2", "2017-01-14"),
        arrayOf("ID3", "Dato derecha 3", "2013-01-14"),
        arrayOf("ID4", "Dato derecha 4", "2018-01-14")
    )

    val actualizaciones = arrayListOf<Array<String>>()
    val inserciones = ArrayList(derecha) // Copia inicial de derecha

    for (izq in izquierda) {
        var encontrado = false
        for (der in derecha) {
            if (izq[0] == der[0]) {
                if (aFecha(izq[2])!!.before(aFecha(der[2])!!)) {
                    actualizaciones.add(der)
                } else if (aFecha(izq[2])!!.after(aFecha(der[2])!!)) {
                    actualizaciones.add(izq)
                }
                inserciones.remove(der)
                encontrado = true
                break
            }
        }
        if (!encontrado) {
            inserciones.add(izq)
        }
    }

    println("actualizaciones:")
    for (coincidencia in actualizaciones) {
        println(coincidencia.contentToString())
    }

    println("\ninserciones:")
    for (diferencia in inserciones) {
        println(diferencia.contentToString())
    }
}

fun aFecha(cad: String): Date? {
    val df = SimpleDateFormat("yyyy-MM-dd")
    return try {
        df.parse(cad)
    } catch (e: Exception) {
        null
    }
}
