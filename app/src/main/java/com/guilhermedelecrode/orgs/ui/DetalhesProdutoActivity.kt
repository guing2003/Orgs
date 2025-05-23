package com.guilhermedelecrode.orgs.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
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
    private lateinit var produto: Produto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesProdutoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recebendo Produto Via Intent
        intent.getParcelableExtra<Produto>("produto")?.let { produtoCarregado ->
            produto = produtoCarregado
        }

        val imagem = findViewById<ImageView>(R.id.activity_detalhes_produto_imageview)
        val nome = findViewById<TextView>(R.id.activity_detalhes_produtos_nome)
        val descricao = findViewById<TextView>(R.id.activity_detalhes_produtos_descricao)
        val valor = findViewById<TextView>(R.id.activity_detalhes_produto_valor)

        // Se produto != nulo, popula a tela com os dados
        buscandoProduto(produto, nome, descricao, valor, imagem)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (::produto.isInitialized) {
            val db = AppDatabase.instancia(this)
            val produtoDao = db.produtoDao()
            when (item.itemId) {
                R.id.menu_detalhes_produto_deletar -> {
                    produtoDao.deletar(produto)
                    finish()
                    Log.i("Menu", "DetalhesProduto: Deletar")
                }

                R.id.menu_detalhes_produto_editar -> {
                    Intent(this, FormularioProdutoActivity::class.java)?.apply {
                        putExtra("produto", produto)
                        startActivity(this)
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun buscandoProduto(
        produto: Produto?,
        nome: TextView,
        descricao: TextView,
        valor: TextView,
        imagem: ImageView
    ) {
        produto?.let {
            nome.text = it.nome
            descricao.text = it.descricao
            valor.text = formataParaMoedaBrasileira(it.valor)

            VerificaImagem(it, imagem)
        }
    }

    private fun VerificaImagem(
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


    private fun formataParaMoedaBrasileira(valor: BigDecimal): String {
        val formatador: NumberFormat = NumberFormat
            .getCurrencyInstance(Locale("pt", "br"))
        return formatador.format(valor)
    }
}