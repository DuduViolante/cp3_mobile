package com.example.aulasqlite

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aulasqlite.bancodedados.DatabaseHelper
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private val bancoDados by lazy {
        DatabaseHelper(this)
    }

    private lateinit var btnSalvar: Button
    private lateinit var btnListar:Button
    private lateinit var btnAtualizar:Button
    private lateinit var btnDeletar:Button
    private lateinit var editNomeProduto: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnSalvar = findViewById(R.id.btnSalvar)
        btnListar = findViewById(R.id.btnListar)
        btnAtualizar = findViewById(R.id.btnAtualizar)
        btnDeletar = findViewById(R.id.btnDeletar)
        editNomeProduto = findViewById(R.id.editNomeProduto)

        btnSalvar.setOnClickListener {
            salvar()
        }

        btnListar.setOnClickListener{
            listar()
        }

        btnAtualizar.setOnClickListener{
            atualizar()
        }

        btnDeletar.setOnClickListener{
            deletar()
        }

    }

    private fun salvar() {
        val nomeProduto = editNomeProduto.text.toString()

        try {
            val sql = "INSERT INTO produtos VALUES(null,'$nomeProduto','512gb')"
            bancoDados.writableDatabase.execSQL(sql)
            Log.i("db_info", "Registro salvo")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun listar(){
        val sql = "SELECT * FROM ${DatabaseHelper.TABELA_PRODUTOS}"
        val cursor = bancoDados.readableDatabase.rawQuery(sql,null)

        val indiceProduto = cursor.getColumnIndex("${DatabaseHelper.ID_PRODUTO}")
        val indiceTitulo = cursor.getColumnIndex("${DatabaseHelper.TITULO}")
        val indiceDescricao = cursor.getColumnIndex("${DatabaseHelper.DESCRICAO}")

        while(cursor.moveToNext()){
            val idProduto = cursor.getInt(indiceProduto)
            val titulo = cursor.getString(indiceTitulo)
            val descricao = cursor.getString(indiceDescricao)

            Log.i("db_info","Produto: $idProduto - $titulo - $descricao")
        }
    }

    private fun atualizar(){
        val nomeProduto = editNomeProduto.text.toString()

        try {
            val sql = "UPDATE produtos SET titulo= '$nomeProduto' WHERE id_produto=2"
            bancoDados.writableDatabase.execSQL(sql)
            Log.i("db_info", "Registro atualizado com sucesso")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("db_info", "Registro não atualizado")
        }
    }

    private fun deletar(){
        val sql = "DELETE FROM produtos WHERE id_produto=2"

        try {
            bancoDados.writableDatabase.execSQL(sql)
            Log.i("db_info","Sucesso ao remover")
        }catch (e:Exception){
            e.printStackTrace()
            Log.i("db_info","Error ao remover")
        }
    }
}