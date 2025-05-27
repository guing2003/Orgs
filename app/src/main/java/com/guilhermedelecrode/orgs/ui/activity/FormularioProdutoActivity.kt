package com.guilhermedelecrode.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.guilhermedelecrode.orgs.database.AppDatabase
import com.guilhermedelecrode.orgs.database.dao.ProdutoDAO
import com.guilhermedelecrode.orgs.databinding.ActivityFormularioProdutoBinding
import com.guilhermedelecrode.orgs.model.Produto
import com.guilhermedelecrode.orgs.ui.dialog.FormularioImagemDialog
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity() {

    // ViewBinding
    private lateinit var binding: ActivityFormularioProdutoBinding
    private var url: String? = null
    private var produtoId = 0L
    private val produtoDao : ProdutoDAO by lazy {
        val db = AppDatabase.instancia(this)
        db.produtoDao()
    }


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
        tentaCarregarProduto()
    }

    override fun onResume() {
        super.onResume()
        tentaBuscarProduto()
    }

    private fun tentaBuscarProduto() {
        produtoDao.buscaPorId(produtoId)?.let {
            title = "Alterar Produto"
            preencheCampos(it)
        }
    }

    private fun tentaCarregarProduto() {
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
    }

    private fun preencheCampos(produto: Produto) {

        url = produto.imagem
        binding.activityFormularioprodutoImagem.load(produto.imagem)
        binding.activityFormularioProdutoNome.setText(produto.nome)
        binding.activityFormularioProdutoDescricao.setText(produto.descricao)
        binding.activityFormularioProdutoValor.setText(produto.valor.toPlainString())
    }

    private fun configuraBotaoSalvar() {
        binding.activityFormularioProdutoBtnSalvar.setOnClickListener {
            val novoProduto = criaProduto()
//            if (produtoId > 0) {
//                produtoDao.atualiza(novoProduto)
//            } else {
//                produtoDao.salva(novoProduto)
//            }
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
            id = produtoId,
            nome = nome,
            descricao = descricao,
            valor = valor,
            imagem = url
        )
    }
}
