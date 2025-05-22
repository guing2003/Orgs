package com.guilhermedelecrode.orgs.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.guilhermedelecrode.orgs.R
import com.guilhermedelecrode.orgs.adapter.ListaProdutosAdapter
import com.guilhermedelecrode.orgs.database.AppDatabase
import com.guilhermedelecrode.orgs.databinding.ActivityListaProdutosBinding
import com.guilhermedelecrode.orgs.model.Produto

class ListaProdutosActivity : AppCompatActivity() {
    private val adapter = ListaProdutosAdapter(
        context = this
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

    }

    private fun showMenu(view: View, produto: Produto) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.menu_detalhes_produto, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_detalhes_produto_editar -> {
                    Log.i("PopupMenu", "ListaProduto: editar")
                    true
                }

                R.id.menu_detalhes_produto_deletar -> {
                    Log.i("PopupMenu", "ListaProduto: deletar")
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    override fun onResume() {
        super.onResume()
        val db = AppDatabase.instancia(this)
        val produtoDao = db.produtoDao()
        adapter.atualiza(produtoDao.buscaTodos())
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
        adapter.quandoClicaESeguraNoItemListener = { view, produto ->
            showMenu(view, produto)
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
