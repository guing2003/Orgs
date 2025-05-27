package com.guilhermedelecrode.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.guilhermedelecrode.orgs.R
import com.guilhermedelecrode.orgs.database.AppDatabase
import com.guilhermedelecrode.orgs.databinding.ActivityDetalhesProdutoBinding
import com.guilhermedelecrode.orgs.model.Produto
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

class DetalhesProdutoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalhesProdutoBinding
    private var produtoId : Long? =null
    private var produto : Produto? = null

    val produtoDao by lazy {
        AppDatabase.instancia(this).produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesProdutoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Recebendo Produto Via Intent
        tentaCarregarProduto()
    }

    override fun onResume() {
        super.onResume()
        produtoId?.let { id ->
            produto = produtoDao.buscaPorId(id)
        }
        produto?.let{
            preencheCampos(it)
        } ?: finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.menu_detalhes_produto_deletar -> {
                    produto?.let{produtoDao.deleta(it)}
                    finish()
                }

                R.id.menu_detalhes_produto_editar -> {
                    Intent(this, FormularioProdutoActivity::class.java)?.apply {
                        putExtra(CHAVE_PRODUTO, produto)
                        startActivity(this)
                    }
                }
            }
        return super.onOptionsItemSelected(item)
    }

    private fun tentaCarregarProduto() {
        intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)?.let { produtoCarregado ->
            produtoId = produtoCarregado.id
        }?: finish()
    }

    private fun preencheCampos(produtoCarregado : Produto){
        with(binding){
            verificaImagem(produtoCarregado, binding.activityDetalhesProdutoImageview)
            activityDetalhesProdutosNome.text = produtoCarregado.nome
            activityDetalhesProdutosDescricao.text = produtoCarregado.descricao
            activityDetalhesProdutoValor.text = produtoCarregado.valor.formataParaMoedaBrasileira()
        }
    }

    private fun verificaImagem(
        it: Produto,
        imagem: ImageView
    ) {
        Log.i("DetalheProdutoActivity", "onCreate: ${it.imagem}")

        if (!it.imagem.isNullOrEmpty()) {
            val visibilade = if (it.imagem != null) {
                View.VISIBLE
            } else {
                View.GONE
            }
            binding.activityDetalhesProdutoImageview.visibility = visibilade
            binding.activityDetalhesProdutoImageview.load(it.imagem) {
                fallback(R.drawable.erro)
                error(R.drawable.erro)
            }
            imagem.load(it.imagem) {
                error(R.drawable.erro)
            }
        }
    }


    private fun BigDecimal.formataParaMoedaBrasileira(): String {
        val formatador: NumberFormat = NumberFormat
            .getCurrencyInstance(Locale("pt", "BR"))
        return formatador.format(this)
    }

}