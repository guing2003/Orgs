package com.guilhermedelecrode.orgs.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.guilhermedelecrode.orgs.R
import com.guilhermedelecrode.orgs.model.Produtos
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_produto)

        val btn_salvar = findViewById<Button>(R.id.btn_salvar)

        btn_salvar.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val campoNome = findViewById<EditText>(R.id.nome)
                val nome = campoNome.text.toString()

                val campoDescricao = findViewById<EditText>(R.id.descricao)
                val descricao = campoDescricao.text.toString()

                val campoValor = findViewById<EditText>(R.id.valor)
                val valorEmTexto = campoValor.text.toString()

                val valor = if(valorEmTexto.isBlank()){
                    BigDecimal.ZERO
                } else{
                    BigDecimal(valorEmTexto)
                }

                val novoProduto = Produtos(
                    nome = nome,
                    descricao = descricao,
                    valor = valor
                )

                Log.i("FormularioProduto", "onCreate: $novoProduto")
            }
        })

    }
}