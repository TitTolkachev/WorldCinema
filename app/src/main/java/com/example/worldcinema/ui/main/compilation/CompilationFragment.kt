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
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            (cardStackView.layoutManager as CardStackLayoutManager).setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }

        binding.skipButton.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
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
        cardStackView.layoutManager = CardStackLayoutManager(binding.root.context)
        viewModel.cards.observe(viewLifecycleOwner) {
            cardAdapter.data = it
        }

        //TODO(Повозиться еще с настройками)

        val setting = SwipeAnimationSetting.Builder()
            .setDuration(Duration.Normal.duration)
            .setInterpolator(AccelerateInterpolator())
            .build()
        (cardStackView.layoutManager as CardStackLayoutManager).setSwipeAnimationSetting(setting)
        (cardStackView.layoutManager as CardStackLayoutManager).setCanScrollHorizontal(true)
        (cardStackView.layoutManager as CardStackLayoutManager).setCanScrollVertical(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}