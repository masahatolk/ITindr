package com.hits.itindr

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hits.itindr.databinding.FragmentLoginBinding
import com.hits.itindr.main_flow.MainActivity

class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.applyStatusBarPadding()

        _binding = FragmentLoginBinding.bind(view)

        val navController = findNavController()

        binding.loginButton.setOnClickListener {
            openMain()
        }

        binding.backLoginButton.setOnClickListener {
            navController.popBackStack()
        }
    }

    private fun openMain() {
        startActivity(Intent(requireContext(), MainActivity::class.java))
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
