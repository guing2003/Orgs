package com.guilhermedelecrode.orgs.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import coil.load
import com.guilhermedelecrode.orgs.DAO.ProdutosDAO
import com.guilhermedelecrode.orgs.database.AppDatabase
import com.guilhermedelecrode.orgs.databinding.ActivityFormularioProdutoBinding
import com.guilhermedelecrode.orgs.databinding.FormularioImagemBinding
import com.guilhermedelecrode.orgs.model.Produto
import com.guilhermedelecrode.orgs.ui.dialog.FormularioImagemDialog
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity() {



    // ViewBinding
    private lateinit var binding: ActivityFormularioProdutoBinding
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormularioProdutoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Cadastrar Novo Produto"
        configuraBotaoSalvar()

        binding.activityFormularioprodutoImagem.setOnClickListener {
            FormularioImagemDialog(this).mostra(url) { imagem ->
                url = imagem
                binding.activityFormularioprodutoImagem.load(url)
            }
        }

    }

    private fun configuraBotaoSalvar() {
        val db = AppDatabase.instancia(this)
        val produtoDao = db.produtoDao()

        binding.activityFormularioProdutoBtnSalvar.setOnClickListener {
            val novoProduto = criaProduto()
            produtoDao.salva(novoProduto)
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
            valor = valor,
            imagem = url
        )
    }
}
