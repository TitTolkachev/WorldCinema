package com.example.worldcinema.ui.screen.main.compilation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.worldcinema.databinding.FragmentCompilationBinding
import com.example.worldcinema.ui.screen.main.compilation.adapter.CardAdapter
import com.yuyakaido.android.cardstackview.*

class CompilationFragment : Fragment() {

    private var _binding: FragmentCompilationBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CompilationViewModel
    private lateinit var navController: NavController

    private lateinit var cardAdapter: CardAdapter
    private lateinit var cardStackView: CardStackView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompilationBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            this,
            CompilationViewModelFactory(requireContext())
        )[CompilationViewModel::class.java]
        navController = findNavController()

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBarCompilation.visibility = View.VISIBLE
                binding.compilationContent.visibility = View.GONE
            } else {
                binding.progressBarCompilation.visibility = View.GONE
                binding.compilationContent.visibility = View.VISIBLE
                onDataLoaded()
            }
        }

        return binding.root
    }

    private fun onDataLoaded() {

        initCardStackView()

        binding.likeButton.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Slow.duration)
                .build()
            (cardStackView.layoutManager as CardStackLayoutManager).setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }

        binding.playButton.setOnClickListener {
            showMovie()
        }

        binding.skipButton.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Slow.duration)
                .build()
            (cardStackView.layoutManager as CardStackLayoutManager).setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }

        viewModel.isCardStackEmpty.observe(viewLifecycleOwner) {
            if (it) {
                binding.textView2.visibility = View.GONE
                binding.flow.visibility = View.GONE
                binding.cardStackView.visibility = View.GONE
                binding.flowEmptyStack.visibility = View.VISIBLE
            } else {
                binding.textView2.visibility = View.VISIBLE
                binding.flow.visibility = View.VISIBLE
                binding.cardStackView.visibility = View.VISIBLE
                binding.flowEmptyStack.visibility = View.GONE
            }
        }

        viewModel.displayedTitle.observe(viewLifecycleOwner) {
            binding.textView2.text = it ?: ""
        }
    }

    private fun initCardStackView() {
        cardAdapter = CardAdapter()
        cardStackView = binding.cardStackView
        cardStackView.adapter = cardAdapter
        cardStackView.layoutManager =
            CardStackLayoutManager(binding.root.context, object : CardStackListener {
                override fun onCardDragging(direction: Direction?, ratio: Float) {}

                override fun onCardSwiped(direction: Direction?) {
                    if (direction == Direction.Left) {
                        viewModel.skip()
                    }
                    if (direction == Direction.Right) {
                        viewModel.like()
                    }
                }

                override fun onCardRewound() {}

                override fun onCardCanceled() {}

                override fun onCardAppeared(view: View?, position: Int) {}

                override fun onCardDisappeared(view: View?, position: Int) {}

            })
        viewModel.cards.observe(viewLifecycleOwner) {
            cardAdapter.data = it
        }

        val setting = SwipeAnimationSetting.Builder()
            .setDuration(Duration.Normal.duration)
            .setInterpolator(AccelerateInterpolator())
            .build()
        val manager = cardStackView.layoutManager as CardStackLayoutManager
        manager.setSwipeAnimationSetting(setting)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(false)
        manager.setMaxDegree(-35.0f)
        manager.setSwipeThreshold(0.35f)
    }

    private fun showMovie() {
        val currentMovie = viewModel.getCurrentMovie()
        if (currentMovie != null) {
            val action =
                CompilationFragmentDirections.actionNavigationCompilationToMovieActivity(
                    currentMovie
                )
            navController.navigate(action)
        }
    }

    override fun onResume() {
        viewModel.onViewResume()
        super.onResume()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}