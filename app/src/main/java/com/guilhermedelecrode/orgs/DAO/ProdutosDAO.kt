package com.guilhermedelecrode.orgs.DAO

import com.guilhermedelecrode.orgs.model.Produto
import java.math.BigDecimal

class ProdutosDAO {

    fun adiciona(produto: Produto) {
        produtos.add(produto)
    }

    fun buscaTodos(): List<Produto> {
        return produtos.toList()

    }

    companion object {
        private val produtos = mutableListOf<Produto>(
            Produto(
                nome = "Salada de Frutas",
                descricao = "Laranja, Manga, Uva",
                valor = BigDecimal("19.83")
            )
        )
    }
}