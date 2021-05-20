package com.example.projetopa

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Paises(var id: Long = -1, var nome: String) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues()
        valores.put(TabelaPaises.CAMPO_NOME, nome)
        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Paises {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNome = cursor.getColumnIndex(TabelaPaises.CAMPO_NOME)

            val id = cursor.getLong(colId)
            val nome = cursor.getString(colNome)

            return Paises(id, nome)
        }
    }
}