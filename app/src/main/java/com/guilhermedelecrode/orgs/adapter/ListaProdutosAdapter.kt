package com.guilhermedelecrode.orgs.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.guilhermedelecrode.orgs.R
import com.guilhermedelecrode.orgs.model.Produtos
import org.w3c.dom.Text

class ListaProdutosAdapter(
    private val produtos: List<Produtos>,
    private val context : Context
) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun vincula(produtos: Produtos) {
            val nome = itemView.findViewById<TextView>(R.id.nome)
            nome.text = produtos.nome

            val descricao = itemView.findViewById<TextView>(R.id.descricao)
            descricao.text = produtos.descricao

            val valor = itemView.findViewById<TextView>(R.id.valor)
            valor.text = produtos.valor.toPlainString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.produto_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produtos = produtos[position]
        holder.vincula(produtos)
    }

    override fun getItemCount(): Int = produtos.size


}