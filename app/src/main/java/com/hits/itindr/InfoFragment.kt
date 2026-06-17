package com.hits.itindr

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.hits.itindr.databinding.FragmentInfoBinding
import com.hits.itindr.mainflow.MainActivity

class InfoFragment : Fragment(R.layout.fragment_info) {
    private lateinit var viewModel: InfoViewModel
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!
    private var selectedTopicIds = emptyList<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.applyStatusBarPadding()

        _binding = FragmentInfoBinding.bind(view)

        setupTags()

        binding.saveButton.setOnClickListener {

            val name =
                binding.nameInputLayout
                    .editText
                    ?.text
                    ?.toString()
                    .orEmpty()

            val about =
                binding.additionalInfoInputLayout
                    .editText
                    ?.text
                    ?.toString()

            viewModel.saveProfile(
                name = name,
                aboutMyself = about,
                topics = selectedTopicIds,
                onSuccess = {
                    openMain()
                },
                onError = {
                    // показать Snackbar
                }
            )
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

            onSelectionChange = { ids ->

                selectedTopicIds =
                    ids.map { it.toString() }
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
