package com.guilhermedelecrode.orgs.ui.dialog

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import coil.load
import com.guilhermedelecrode.orgs.databinding.FormularioImagemBinding

class FormularioImagemDialog(private val context: Context) {

    fun mostra(quandoImagemCarregada:(imagem: String) -> Unit)  {
        val binding = FormularioImagemBinding.inflate(LayoutInflater.from(context))

        binding.formularioImagemBotaoCarregar.setOnClickListener {
            val url = binding.formularioTextUrl.text.toString()
            binding.formularioImagemImageview.load(url)
        }
        AlertDialog.Builder(context)
            .setView(binding.root)
            .setPositiveButton("Sim") { _, _ ->
                val url = binding.formularioTextUrl.text.toString()
                Log.i("FormularioImagemDialog", "$url")
                quandoImagemCarregada(url)

            }
            .setNegativeButton("Não") { _, _ ->

            }
            .show()
    }

}

