package com.hits.itindr

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.BounceInterpolator
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hits.itindr.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var isHeart = false
    private val translationY = -40f
    private val duration: Long = 120
    private val durationEnd: Long = 180
    private val scale = 1.1f
    private val half = 2f
    private val maxTranslation = 20f
    private val bgTranslationFactor = 0.3f

    private val gestureDetector by lazy {
        GestureDetector(
            requireContext(),
            object : GestureDetector.SimpleOnGestureListener() {
                override fun onDoubleTap(e: MotionEvent): Boolean {
                    toggleDot()
                    return true
                }
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)

        val navController = findNavController()

        binding.logoContainer.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }

        binding.backgroundImage.scaleX = scale
        binding.backgroundImage.scaleY = scale

        binding.objectsImage.scaleX = scale
        binding.objectsImage.scaleY = scale

        binding.imageContainer.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_MOVE) {
                val centerX = binding.imageContainer.width / half
                val centerY = binding.imageContainer.height / half

                val deltaX = (event.x - centerX) / centerX
                val deltaY = (event.y - centerY) / centerY

                binding.backgroundImage.translationX = deltaX * maxTranslation * bgTranslationFactor
                binding.backgroundImage.translationY = deltaY * maxTranslation * bgTranslationFactor

                binding.objectsImage.translationX = deltaX * maxTranslation
                binding.objectsImage.translationY = deltaY * maxTranslation
            }
            true
        }

        binding.registerHomeButton.setOnClickListener {
            isHeart = false
            navController.navigate(R.id.action_home_to_register)
        }

        binding.loginHomeButton.setOnClickListener {
            isHeart = false
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
            ) = Unit

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) = Unit

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) = Unit
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun toggleDot() {
        val dot = binding.logoDot

        val newDrawable = if (isHeart) {
            R.drawable.logo_dot
        } else {
            R.drawable.logo_heart
        }

        isHeart = !isHeart

        dot.setImageResource(newDrawable)

        dot.animate()
            .translationY(translationY)
            .setDuration(duration)
            .withEndAction {
                dot.animate()
                    .translationY(0f)
                    .setDuration(durationEnd)
                    .setInterpolator(BounceInterpolator())
                    .start()
            }
            .start()
    }
}
