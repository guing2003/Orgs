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
    private var produtoId : Long = 0L
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
        buscaProduto()
    }

    private fun buscaProduto() {
        produto = produtoDao.buscaPorId(produtoId)
        produto?.let {
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
                        putExtra(CHAVE_PRODUTO_ID, produtoId)
                        startActivity(this)
                    }
                }
            }
        return super.onOptionsItemSelected(item)
    }

    private fun tentaCarregarProduto() {
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
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