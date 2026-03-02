package com.hits.itindr

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hits.itindr.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)

        val navController = findNavController()

        binding.registerHomeButton.setOnClickListener {
            navController.navigate(R.id.action_home_to_register)
        }

        binding.loginHomeButton.setOnClickListener {
            navController.navigate(R.id.action_home_to_login)
        }

        binding.registerHomeButton.isEnabled = false
        binding.loginHomeButton.isEnabled = false

        binding.motionHomeLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                binding.registerHomeButton.isEnabled = true
                binding.loginHomeButton.isEnabled = true

                binding.root.performHapticFeedback(android.view.HapticFeedbackConstants.VIRTUAL_KEY)
            }

            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
