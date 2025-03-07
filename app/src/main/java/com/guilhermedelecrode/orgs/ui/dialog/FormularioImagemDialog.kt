package com.guilhermedelecrode.orgs.ui.dialog

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import coil.load
import com.guilhermedelecrode.orgs.databinding.FormularioImagemBinding

class FormularioImagemDialog(private val context: Context) {

    fun mostra(urlPadrao: String? = null, quandoImagemCarregada: (imagem: String) -> Unit) {
        FormularioImagemBinding.inflate(LayoutInflater.from(context)).apply {
            urlPadrao?.let {
                formularioImagemImageview.load(it)
                formularioTextUrl.setText(it)
            }

            formularioImagemBotaoCarregar.setOnClickListener {
                val url = formularioTextUrl.text.toString()
                formularioImagemImageview.load(url)
            }
            AlertDialog.Builder(context)
                .setView(root)
                .setPositiveButton("Confirmar") { _, _ ->
                    val url = formularioTextUrl.text.toString()
                    quandoImagemCarregada(url)

                }
                .setNegativeButton("Cancelar") { _, _ ->

                }
                .show()
        }
    }
}

