package com.guilhermedelecrode.orgs.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.guilhermedelecrode.orgs.R
import com.guilhermedelecrode.orgs.databinding.ActivityDetalhesProdutoBinding
import com.guilhermedelecrode.orgs.model.Produto
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

class DetalhesProdutoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalhesProdutoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesProdutoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recebendo Produto Via Intent
        val produto: Produto? = intent.getParcelableExtra("produto")

        val imagem = findViewById<ImageView>(R.id.activity_detalhes_produto_imageview)
        val nome = findViewById<TextView>(R.id.activity_detalhes_produtos_nome)
        val descricao = findViewById<TextView>(R.id.activity_detalhes_produtos_descricao)
        val valor = findViewById<TextView>(R.id.activity_detalhes_produto_valor)

        // Se produto != nulo, popula a tela com os dados
        produto?.let {
            nome.text = it.nome
            descricao.text = it.descricao
            valor.text = formataParaMoedaBrasileira(it.valor)

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
    }

    private fun formataParaMoedaBrasileira(valor: BigDecimal): String {
        val formatador: NumberFormat = NumberFormat
            .getCurrencyInstance(Locale("pt", "br"))
        return formatador.format(valor)
    }
}