package com.guilhermedelecrode.orgs.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.guilhermedelecrode.orgs.DAO.ProdutosDAO
import com.guilhermedelecrode.orgs.adapter.ListaProdutosAdapter
import com.guilhermedelecrode.orgs.database.AppDatabase
import com.guilhermedelecrode.orgs.databinding.ActivityListaProdutosBinding
import com.guilhermedelecrode.orgs.model.Produto
import java.math.BigDecimal

class ListaProdutosActivity : AppCompatActivity() {
    private val dao = ProdutosDAO()
    private val adapter = ListaProdutosAdapter(
        context = this,
        produtos = dao.buscaTodos()
    )

    // ViewBinding
    private lateinit var binding: ActivityListaProdutosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflando o layout usando ViewBinding
        binding = ActivityListaProdutosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configuraRecyclerView()
        configuraFab()

        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "org,db"
        ).allowMainThreadQueries()
            .build()

        val produtoDao = db.produtoDao()

        produtoDao.salva(
            Produto(
                nome = "Teste Nome 1",
                descricao = "Teste descricao 1",
                valor = BigDecimal("10.0")
            )
        )
        adapter.atualiza(produtoDao.buscaTodos())
    }

    override fun onResume() {
        super.onResume()
        //adapter.atualiza(dao.buscaTodos())
        Log.i("ListaProduto", "onResume: ${dao.buscaTodos()}")
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.activityListaProdutoRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter.quandoClicaNoItemlistener = {
            Log.i("ListaProdutos", "quandoClica: ${it.nome}")
            val intent = Intent(this, DetalhesProdutoActivity::class.java).apply {
                putExtra("produto", it)
            }
            startActivity(intent)
        }
    }



    private fun configuraFab() {
        val fab = binding.activityListaProdutoExtendedFab
        fab.setOnClickListener {
            vaiParaFormularioProduto()
        }
    }

    private fun vaiParaFormularioProduto() {
        val intent = Intent(this, FormularioProdutoActivity::class.java)
        startActivity(intent)
    }
}
