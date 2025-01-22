package com.guilhermedelecrode.orgs.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.guilhermedelecrode.orgs.DAO.ProdutosDAO
import com.guilhermedelecrode.orgs.R
import com.guilhermedelecrode.orgs.model.Produto
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity() {

    val dao = ProdutosDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_produto)

        configuraBotaoSalvar()

    }

    private fun configuraBotaoSalvar() {
        val btn_salvar = findViewById<Button>(R.id.activity_formulario_produto_btn_salvar)

        btn_salvar.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val novoProduto = criaProduto()

                Log.i("FormularioProduto", "onCreate: $novoProduto")


                dao.adiciona(novoProduto)
                Log.i("FormularioProduto", "onClick: ${dao.buscaTodos()} ")
                finish()
            }

            private fun criaProduto(): Produto {
                val campoNome = findViewById<EditText>(R.id.activity_formulario_produto_nome)
                val nome = campoNome.text.toString()

                val campoDescricao = findViewById<EditText>(R.id.activity_formulario_produto_descricao)
                val descricao = campoDescricao.text.toString()

                val campoValor = findViewById<EditText>(R.id.activity_formulario_produto_item_valor)
                val valorEmTexto = campoValor.text.toString()

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
        })
    }
}