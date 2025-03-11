package com.guilhermedelecrode.orgs.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.guilhermedelecrode.orgs.R
import com.guilhermedelecrode.orgs.databinding.ProdutoItemBinding
import com.guilhermedelecrode.orgs.model.Produto
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

class ListaProdutosAdapter(
    produtos: List<Produto>,
    private val context: Context,
    var quandoClicaNoItemlistener: (produto: Produto) -> Unit = {}

) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

    private val produtos = produtos.toMutableList()


    inner class ViewHolder(private val binding: ProdutoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            private lateinit var produto: Produto

        init {
            itemView.setOnClickListener {
                Log.i("ListaProduto", "Clicando no Item")
                if(::produto.isInitialized){
                    quandoClicaNoItemlistener(produto)
                }
            }
        }

        fun vincula(produto: Produto) {
            this.produto = produto
            val nome = binding.produtoItemNome
            nome.text = produto.nome
            val descricao = binding.produtoItemDescricao
            descricao.text = produto.descricao
            val valor = binding.produtoItemValor

            val valorEmMoeda: String = formataParaMoedaBrasileira(produto.valor)
            valor.text = valorEmMoeda

            val visibilade = if (produto.imagem != null) {
                View.VISIBLE
            } else {
                View.GONE
            }

            binding.imageView.visibility = visibilade

            binding.imageView.load(produto.imagem) {
                fallback(R.drawable.erro)
                error(R.drawable.erro)
            }
        }

        private fun formataParaMoedaBrasileira(valor: BigDecimal): String {
            val formatador: NumberFormat = NumberFormat
                .getCurrencyInstance(Locale("pt", "br"))
            return formatador.format(valor)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ProdutoItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produto = produtos[position]
        holder.vincula(produto)
    }

    override fun getItemCount(): Int = produtos.size

    fun atualiza(produtos: List<Produto>) {
        this.produtos.clear()
        this.produtos.addAll(produtos)
        notifyDataSetChanged()
    }
}
