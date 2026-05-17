package com.hits.itindr.login.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.hits.itindr.R
import com.hits.itindr.applyStatusBarPadding
import com.hits.itindr.databinding.FragmentLoginBinding
import com.hits.itindr.login.LoginModule
import com.hits.itindr.main_flow.MainActivity

class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: LoginViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.applyStatusBarPadding()

        _binding = FragmentLoginBinding.bind(view)

        val navController = findNavController()
        viewModel = ViewModelProvider(this, LoginModule.provideViewModelFactory())[LoginViewModel::class.java]

        binding.loginButton.setOnClickListener {
            val email = binding.emailInputLayout.editText?.text?.toString().orEmpty()
            val password = binding.passwordInputLayout.editText?.text?.toString().orEmpty()
            viewModel.onLoginClicked(email, password)
        }

        binding.backLoginButton.setOnClickListener {
            navController.popBackStack()
        }

        viewModel.event.observe(viewLifecycleOwner) { event ->
            when (event) {
                LoginUiEvent.OpenMainScreen -> openMain()
                is LoginUiEvent.ShowError -> showError(event.messageResId)
            }
        }
    }

    private fun openMain() {
        startActivity(Intent(requireContext(), MainActivity::class.java))
        requireActivity().finish()
    }

    private fun showError(messageResId: Int) {
        Toast.makeText(requireContext(), getString(messageResId), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}