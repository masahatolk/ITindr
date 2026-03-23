package com.hits.itindr

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.hits.itindr.databinding.FragmentInfoBinding
import com.hits.itindr.main_flow.MainActivity

class InfoFragment : Fragment(R.layout.fragment_info) {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.applyStatusBarPadding()

        _binding = FragmentInfoBinding.bind(view)

        setupTags()

        binding.saveButton.setOnClickListener {
            openMain()
        }
    }

    private fun setupTags() {
        val tags = listOf(
            TagItem(1, "Python"),
            TagItem(2, "Django"),
            TagItem(3, "REST"),
            TagItem(4, "Swift"),
            TagItem(5, "Obj-C"),
            TagItem(6, "React JS"),
            TagItem(7, "Kotlin"),
            TagItem(8, "Git"),
            TagItem(9, "Unity"),
            TagItem(10, ".NET"),
            TagItem(11, "SQL"),
            TagItem(12, "Clean Architecture"),
            TagItem(13, "UML")
        )

        binding.tagView.apply {
            this.tags = tags

            multiSelect = true

            // maxSelected = 5

            onSelectionChange = { selectedIds ->
                println("Selected: $selectedIds")
            }

            onTagClick = { id, isSelected ->
                println("Tag $id clicked, selected = $isSelected")
            }

            onSelectionLimitReached = { limit ->
                println("Limit reached: $limit")
            }
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
