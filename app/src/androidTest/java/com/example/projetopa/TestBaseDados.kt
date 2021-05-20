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
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.projetopa", appContext.packageName)
    }

    private fun insereCategoria(tabela: TabelaPaises, paises: Paises): Long {
        val id = tabela.insert(paises.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun getCategoriaBaseDados(tabela: TabelaPaises, id: Long): Paises {
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

        val categoria = Paises(nome = "Portugal")
        categoria.id = insereCategoria(tabelaPaises, categoria)

        assertEquals(categoria, getCategoriaBaseDados(tabelaPaises, categoria.id))

        db.close()
    }

    @Test
    fun consegueAlterarCategorias() {
        val db = getBdOpenHelper().writableDatabase
        val tabelaPaises = TabelaPaises(db)

        val paises = Paises(nome = "portugal")
        paises.id = insereCategoria(tabelaPaises, categoria)

        paises.nome = "Ficção científica"

        val registosAlterados = tabelaPaises.update(
            paises.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(categoria.id.toString())
        )

        assertEquals(1, registosAlterados)

        assertEquals(categoria, getCategoriaBaseDados(tabelaPaises, categoria.id))

        db.close()
    }

    @Test
    fun consegueEliminarCategorias() {
        val db = getBdOpenHelper().writableDatabase
        val tabelaPaises = TabelaPaises(db)

        val paises = Paises(nome = "teste")
        paises.id = insereCategoria(tabelaPaises, paises)

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
        paises.id = insereCategoria(tabelaPaises, paises)

        assertEquals(paises, getCategoriaBaseDados(tabelaPaises, paises.id))

        db.close()
    }
}

}