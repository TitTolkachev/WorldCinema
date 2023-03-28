package com.example.worldcinema.ui.main.compilation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.worldcinema.R
import com.example.worldcinema.databinding.FragmentCompilationBinding
import com.example.worldcinema.ui.main.compilation.model.SwipeRightModel

class CompilationFragment : Fragment() {

    private var _binding: FragmentCompilationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel =
            ViewModelProvider(this)[CompilationViewModel::class.java]

        _binding = FragmentCompilationBinding.inflate(inflater, container, false)


        viewModel
            .modelStream
            .observe(viewLifecycleOwner) {
                bindCard(it)
            }

        binding.motionLayout.setTransitionListener(object : TransitionAdapter() {
            override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
                when (currentId) {
                    R.id.offScreenPass,
                    R.id.offScreenLike -> {
                        motionLayout.progress = 0f
                        motionLayout.setTransition(R.id.rest, R.id.like)
                        viewModel.swipe()
                    }
                }
            }

        })

        binding.likeButton.setOnClickListener {
            binding.motionLayout.transitionToState(R.id.like)
        }

        binding.skipButton.setOnClickListener {
            binding.motionLayout.transitionToState(R.id.pass)
        }

        return binding.root
    }

    private fun bindCard(model: SwipeRightModel) {
        binding.bottomCard.setImageResource(R.drawable.test_image)
        binding.topCard.setImageResource(R.drawable.test_image)
//        binding.bottomCard.setBackgroundColor(model.bottom.backgroundColor)
//        binding.topCard.setBackgroundColor(model.top.backgroundColor)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}