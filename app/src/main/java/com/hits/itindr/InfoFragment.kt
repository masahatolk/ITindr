package com.hits.itindr

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hits.itindr.databinding.FragmentInfoBinding

class InfoFragment : Fragment(R.layout.fragment_info) {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.applyStatusBarPadding()

        _binding = FragmentInfoBinding.bind(view)

        val navController = findNavController()

        binding.saveButton.setOnClickListener {
            navController.navigate(R.id.action_info_to_main)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
