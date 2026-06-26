package com.hits.impl.ui

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
import com.hits.impl.databinding.FragmentRegisterBinding
import com.hits.itindr.R
import com.hits.itindr.applyStatusBarPadding
import com.hits.itindr.databinding.FragmentRegisterBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.applyStatusBarPadding()

        _binding = FragmentRegisterBinding.bind(view)

        val navController = findNavController()

        binding.registerButton.setOnClickListener {
            val email = binding.emailInputLayout.editText?.text?.toString().orEmpty()
            val password = binding.passwordInputLayout.editText?.text?.toString().orEmpty()
            val passwordConfirm = binding.passwordConfirmInputLayout.editText
                ?.text
                ?.toString()
                .orEmpty()
            viewModel.onRegisterClicked(email, password, passwordConfirm)
        }

        binding.backRegisterButton.setOnClickListener {
            navController.popBackStack()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect { event ->
                    when (event) {
                        RegisterUiEvent.OpenInfoScreen -> {
                            val actionId = resources.getIdentifier(
                                "action_register_to_info",
                                "id",
                                requireContext().packageName
                            )
                            navController.navigate(actionId)
                        }
                        is RegisterUiEvent.ShowError -> showError(event.messageResId)
                    }
                }
            }
        }
    }

    private fun showError(messageResId: Int) {
        Snackbar.make(binding.root, getString(messageResId), Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}