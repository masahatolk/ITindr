package com.hits.itindr

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hits.itindr.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.applyStatusBarPadding()

        _binding = FragmentRegisterBinding.bind(view)

        val navController = findNavController()

        binding.registerButton.setOnClickListener {
            navController.navigate(R.id.action_register_to_info)
        }

        binding.backRegisterButton.setOnClickListener {
            navController.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
