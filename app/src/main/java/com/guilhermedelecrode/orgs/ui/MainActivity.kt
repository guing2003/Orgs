package com.guilhermedelecrode.orgs.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.guilhermedelecrode.orgs.R
import com.guilhermedelecrode.orgs.adapter.ListaProdutosAdapter
import com.guilhermedelecrode.orgs.model.Produtos
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = ListaProdutosAdapter(
            context = this, produtos = listOf(
                Produtos(
                    nome = "Teste",
                    descricao = "Teste Desc",
                    valor = BigDecimal("19.99")
                ), Produtos(
                    nome = "Teste 1",
                    descricao = "Teste Desc 1",
                    valor = BigDecimal("29.99")
                ), Produtos(
                    nome = "Teste 2",
                    descricao = "Teste Desc 2",
                    valor = BigDecimal("39.99")
                )
            )
        )
        recyclerView.layoutManager = LinearLayoutManager(this)

        val fab = findViewById<FloatingActionButton>(R.id.fab_main)

        fab.setOnClickListener{
            val intent = Intent(this, FormularioProdutoActivity::class.java)
            startActivity(intent)
        }
    }
}