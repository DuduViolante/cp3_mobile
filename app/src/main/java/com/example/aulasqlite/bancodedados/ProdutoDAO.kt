package com.example.aulasqlite.bancodedados

import android.content.Context
import android.util.Log
import com.example.aulasqlite.model.Produto
import java.lang.Exception

class ProdutoDAO(context:Context):IProdutoDAO {
    val escrita = DatabaseHelper(context).writableDatabase
    val leitura = DatabaseHelper(context).readableDatabase

    override fun salvar(produto: Produto): Boolean {
        val nomeProduto = produto.titulo
        val descricao = produto.descricao

        try {
            val sql = "INSERT INTO ${DatabaseHelper.TABELA_PRODUTOS} VALUES(null,'$nomeProduto','$descricao')"
            escrita.execSQL(sql)
            Log.i("db_info", "Registro salvo")
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

        return true
    }

    override fun atualizar(produto: Produto): Boolean {
        val nomeProduto = produto.titulo

        try {
            val sql = "UPDATE ${DatabaseHelper.TABELA_PRODUTOS} SET ${DatabaseHelper.TITULO} = '$nomeProduto' WHERE ${DatabaseHelper.ID_PRODUTO}=2"
            escrita.execSQL(sql)
            Log.i("db_info", "Registro atualizado com sucesso")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("db_info", "Registro não atualizado")
            return false
        }

        return true
    }

    override fun remover(idProduto: Int): Boolean {
        val sql = "DELETE FROM ${DatabaseHelper.TABELA_PRODUTOS} WHERE ${DatabaseHelper.ID_PRODUTO}= $idProduto"

        try {
            escrita.execSQL(sql)
            Log.i("db_info","Sucesso ao remover")
        }catch (e:Exception){
            e.printStackTrace()
            Log.i("db_info","Error ao remover")
            return false
        }

        return true
    }

    override fun listar(): List<Produto> {
        val listaProduto = mutableListOf<Produto>()

        val sql = "SELECT * FROM ${DatabaseHelper.TABELA_PRODUTOS}"
        val cursor = leitura.rawQuery(sql,null)

        val indiceProduto = cursor.getColumnIndex("${DatabaseHelper.ID_PRODUTO}")
        val indiceTitulo = cursor.getColumnIndex("${DatabaseHelper.TITULO}")
        val indiceDescricao = cursor.getColumnIndex("${DatabaseHelper.DESCRICAO}")

        while(cursor.moveToNext()){
            val idProduto = cursor.getInt(indiceProduto)
            val titulo = cursor.getString(indiceTitulo)
            val descricao = cursor.getString(indiceDescricao)

            val produto = Produto(idProduto,titulo,descricao)
            listaProduto.add(produto)

            //Log.i("db_info","Produto: $idProduto - $titulo - $descricao")
        }

        return listaProduto
    }
}