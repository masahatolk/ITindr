package com.hits.itindr

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hits.core_auth.TokenStore
import com.hits.itindr.databinding.FragmentSplashScreenBinding
import com.hits.itindr.mainflow.MainActivity
import org.koin.android.ext.android.inject

class SplashScreenFragment : Fragment(R.layout.fragment_splash_screen) {
    private var _binding: FragmentSplashScreenBinding? = null
    private val tokenStore: TokenStore by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSplashScreenBinding.bind(view)

        val navController = findNavController()

        if (tokenStore.getToken().isNullOrBlank()) {
            navController.navigate(R.id.action_splash_to_home)
        } else {
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
