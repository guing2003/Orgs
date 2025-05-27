package com.guilhermedelecrode.orgs.ui.activity

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

    private val produtoDao by lazy {
        AppDatabase.instancia(this).produtoDao()
    }


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
                    val intent = Intent(this, FormularioProdutoActivity::class.java)
                    intent.putExtra(CHAVE_PRODUTO_ID, produto.id)
                    startActivity(intent)
                    Log.i("PopupMenu", "ListaProduto: editar")
                    true
                }

                R.id.menu_detalhes_produto_deletar -> {
                    produto?.let{produtoDao.deleta(it)}
                    adapter.atualiza(produtoDao.buscaTodos())
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
        adapter.atualiza(produtoDao.buscaTodos())
    }


    private fun configuraRecyclerView() {
        val recyclerView = binding.activityListaProdutoRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter.quandoClicaNoItemlistener = {
            val intent = Intent(this, DetalhesProdutoActivity::class.java).apply {
                putExtra(CHAVE_PRODUTO_ID, it.id)
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
