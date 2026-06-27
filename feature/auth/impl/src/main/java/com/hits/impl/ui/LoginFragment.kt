package com.hits.impl.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hits.core_ui.applyStatusBarPadding
import com.hits.impl.R
import com.hits.impl.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.applyStatusBarPadding()

        _binding = FragmentLoginBinding.bind(view)

        val navController = findNavController()

        binding.loginButton.setOnClickListener {
            val email = binding.emailInputLayout.editText?.text?.toString().orEmpty()
            val password = binding.passwordInputLayout.editText?.text?.toString().orEmpty()
            viewModel.onLoginClicked(email, password)
        }

        binding.backLoginButton.setOnClickListener {
            navController.popBackStack()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect { event ->
                    when (event) {
                        LoginUiEvent.OpenMainScreen -> openMain()
                        is LoginUiEvent.ShowError -> showError(event.messageResId)
                    }
                }
            }
        }
    }

    private fun openMain() {
        startActivity(Intent(requireContext(), Class.forName("com.hits.itindr.mainflow.MainActivity")))
        requireActivity().finish()
    }

    private fun showError(messageResId: Int) {
        Snackbar.make(binding.root, getString(messageResId), Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}