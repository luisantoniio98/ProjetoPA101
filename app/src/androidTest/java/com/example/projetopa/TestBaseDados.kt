package com.example.projetopa

import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TestBaseDados {
        private fun getAppContext() = InstrumentationRegistry.getInstrumentation().targetContext
        private fun getBdOpenHelper() = BdOpenHelper(getAppContext())


    private fun inserePaises(tabela: TabelaPaises, paises: Paises): Long {
        val id = tabela.insert(paises.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun getPaisesBaseDados(tabela: TabelaPaises, id: Long): Paises {
        val cursor = tabela.query(
            TabelaPaises.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Paises.fromCursor(cursor)
    }

    @Before
    fun apagaBaseDados() {
        getAppContext().deleteDatabase(BdOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados() {
        val db = getBdOpenHelper().readableDatabase
        assert(db.isOpen)
        db.close()
    }

    @Test
    fun consegueInserirCategorias() {
        val db = getBdOpenHelper().writableDatabase
        val tabelaPaises = TabelaPaises(db)

        val paises = Paises(nome = "Portugal")
        paises.id = inserePaises(tabelaPaises, paises)

        assertEquals(paises, getPaisesBaseDados(tabelaPaises, paises.id))

        db.close()
    }

    @Test
    fun consegueAlterarCategorias() {
        val db = getBdOpenHelper().writableDatabase
        val tabelaPaises = TabelaPaises(db)

        val paises = Paises(nome = "portugal")
        paises.id = inserePaises(tabelaPaises, paises)

        paises.nome = "Ficção científica"

        val registosAlterados = tabelaPaises.update(
            paises.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(paises.id.toString())
        )

        assertEquals(1, registosAlterados)

        assertEquals(paises, getPaisesBaseDados(tabelaPaises, paises.id))

        db.close()
    }

    @Test
    fun consegueEliminarCategorias() {
        val db = getBdOpenHelper().writableDatabase
        val tabelaPaises = TabelaPaises(db)

        val paises = Paises(nome = "teste")
        paises.id = inserePaises(tabelaPaises, paises)

        val registosEliminados = tabelaPaises.delete(
            "${BaseColumns._ID}=?",
            arrayOf(paises.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerCategorias() {
        val db = getBdOpenHelper().writableDatabase
        val tabelaPaises = TabelaPaises(db)

        val paises = Paises(nome = "Portugal")
        paises.id = inserePaises(tabelaPaises, paises)

        assertEquals(paises, getPaisesBaseDados(tabelaPaises, paises.id))

        db.close()
    }
}
