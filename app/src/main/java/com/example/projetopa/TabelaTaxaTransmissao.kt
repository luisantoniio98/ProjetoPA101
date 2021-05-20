package com.example.projetopa

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaTaxaTransmissao(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,$TAXA_TRANSMISSAO FLOAT NOT NULL, $CAMPO_ID_PAIS INTEGER NOT NULL, FOREIGN KEY($CAMPO_ID_PAIS) REFERENCES ${TabelaPaises.NOME_TABELA})")
    }

    fun insert(values: ContentValues): Long {
        return db.insert(TabelaPaises.NOME_TABELA, null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(TabelaPaises.NOME_TABELA, values, whereClause, whereArgs)
    }

    fun delete(whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(TabelaPaises.NOME_TABELA, whereClause, whereArgs)
    }

    fun query(
        columns: Array<String>,
        selection: String,
        selectionArgs: Array<String>,
        groupBy: String,
        having: String,
        orderBy: String
    ): Cursor? {
        return db.query(TabelaPaises.NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object {
        const val NOME_TABELA = "taxatransmissao"
        const val CAMPO_ID_PAIS = "id_pais"
        const val TAXA_TRANSMISSAO = "taxa_transmissao"
    }
}
