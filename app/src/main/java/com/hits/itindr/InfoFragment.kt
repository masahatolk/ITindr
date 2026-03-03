package com.hits.itindr

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.hits.itindr.databinding.FragmentInfoBinding

class InfoFragment : Fragment(R.layout.fragment_info) {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.applyStatusBarPadding()

        _binding = FragmentInfoBinding.bind(view)

        binding.saveButton.setOnClickListener {
            openMain()
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
