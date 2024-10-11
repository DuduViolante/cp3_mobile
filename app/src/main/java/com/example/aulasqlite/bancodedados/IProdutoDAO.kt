package com.example.aulasqlite.bancodedados

import com.example.aulasqlite.model.Produto

interface IProdutoDAO {
    fun salvar(produto:Produto):Boolean
    fun atualizar(produto:Produto):Boolean
    fun remover(idProduto:Int):Boolean
    fun listar():List<Produto>
}