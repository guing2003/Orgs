package br.com.alura.orgs.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.R
import br.com.alura.orgs.databinding.ActivityPerfilBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class PerfilActivity : UsuarioBaseActivity() {

    private val binding by lazy {
        ActivityPerfilBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        exibeUsuarioLogado()
        configuraBotaoSair()
    }

    private fun exibeUsuarioLogado() {
        lifecycleScope.launch {
            usuario
                .filterNotNull()
                .collect { usuarioLogado ->
                binding.activityPerfilNome.text = usuarioLogado.nome
                binding.activityPerfilUsuario.text = usuarioLogado.id
            }
        }
    }


    private fun configuraBotaoSair() {
        binding.activityPerfilBotaoSair.setOnClickListener {
            lifecycleScope.launch {
                deslogaUsuario()
            }
        }
    }
}