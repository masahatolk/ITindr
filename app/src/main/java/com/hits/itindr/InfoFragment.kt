package com.hits.itindr

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.hits.itindr.databinding.FragmentInfoBinding
import com.hits.itindr.mainflow.MainActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class InfoFragment : Fragment(R.layout.fragment_info) {
    private val viewModel: InfoViewModel by viewModel()
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    private val _selectedTopicIds = MutableStateFlow<Set<String>>(emptySet())
    private val selectedTopicIds = _selectedTopicIds.asStateFlow()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.applyStatusBarPadding()

        _binding = FragmentInfoBinding.bind(view)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.topics.collect { tags ->
                binding.tagView.tags = tags
            }
        }

        viewModel.loadTopics()

        binding.tagView.onSelectionChange = { selectedIds ->
            _selectedTopicIds.value = selectedIds.toSet()
        }

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
                topics = selectedTopicIds.value.toList(),
                onSuccess = {
                    openMain()
                },
                onError = {
                    Snackbar.make(
                        binding.root,
                        "Не удалось сохранить профиль",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            )
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
