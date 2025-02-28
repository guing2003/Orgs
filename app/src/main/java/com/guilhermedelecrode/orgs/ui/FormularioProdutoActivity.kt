package com.guilhermedelecrode.orgs.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.guilhermedelecrode.orgs.DAO.ProdutosDAO
import com.guilhermedelecrode.orgs.databinding.ActivityFormularioProdutoBinding
import com.guilhermedelecrode.orgs.databinding.FormularioImagemBinding
import com.guilhermedelecrode.orgs.model.Produto
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity() {

    private val dao = ProdutosDAO()

    // ViewBinding
    private lateinit var binding: ActivityFormularioProdutoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormularioProdutoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configuraBotaoSalvar()

        binding.activityFormularioprodutoImagem.setOnClickListener{
            val bindingFormularioImagem = FormularioImagemBinding.inflate(layoutInflater)
            bindingFormularioImagem.formularioImagemBotaoCarregar.setOnClickListener {
                val url = bindingFormularioImagem.formularioTextUrl.text.toString()
                bindingFormularioImagem.formularioImagemImageview.load(url)
            }
            AlertDialog.Builder(this)
                .setView(bindingFormularioImagem.root)
                .setPositiveButton("Sim") {_,_ ->
                    val url = bindingFormularioImagem.formularioTextUrl.text.toString()
                    binding.activityFormularioprodutoImagem.load(url)


                }
                .setNegativeButton("Não"){_,_ ->

                }
                .show()
        }
    }

    private fun configuraBotaoSalvar() {
        binding.activityFormularioProdutoBtnSalvar.setOnClickListener {
            val novoProduto = criaProduto()

            Log.i("FormularioProduto", "onCreate: $novoProduto")

            dao.adiciona(novoProduto)
            Log.i("FormularioProduto", "onClick: ${dao.buscaTodos()} ")
            finish()
        }
    }

    private fun criaProduto(): Produto {
        val nome = binding.activityFormularioProdutoNome.text.toString()
        val descricao = binding.activityFormularioProdutoDescricao.text.toString()
        val valorEmTexto = binding.activityFormularioProdutoValor.text.toString()

        val valor = if (valorEmTexto.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valorEmTexto)
        }

        return Produto(
            nome = nome,
            descricao = descricao,
            valor = valor
        )
    }
}
