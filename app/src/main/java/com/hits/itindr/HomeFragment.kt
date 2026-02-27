package com.hits.itindr

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hits.itindr.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.applyStatusBarPadding()

        _binding = FragmentHomeBinding.bind(view)

        val navController = findNavController()

        binding.registerHomeButton.setOnClickListener {
            navController.navigate(R.id.action_home_to_register)
        }

        binding.loginHomeButton.setOnClickListener {
            navController.navigate(R.id.action_home_to_login)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
