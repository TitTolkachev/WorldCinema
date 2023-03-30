package com.example.worldcinema.ui.main.compilation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.worldcinema.databinding.FragmentCompilationBinding
import com.example.worldcinema.ui.main.compilation.adapter.CardAdapter
import com.yuyakaido.android.cardstackview.*

class CompilationFragment : Fragment() {

    private var _binding: FragmentCompilationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: CompilationViewModel

    private lateinit var cardAdapter: CardAdapter
    private lateinit var cardStackView: CardStackView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[CompilationViewModel::class.java]

        _binding = FragmentCompilationBinding.inflate(inflater, container, false)

        initCardStackView()

        binding.likeButton.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Slow.duration)
                .build()
            (cardStackView.layoutManager as CardStackLayoutManager).setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }

        binding.skipButton.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Slow.duration)
                .build()
            (cardStackView.layoutManager as CardStackLayoutManager).setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }

        return binding.root
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
                        binding.textView2.text = "Left"
                    }
                    if (direction == Direction.Right) {
                        binding.textView2.text = "Right"
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}