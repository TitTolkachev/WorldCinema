package com.example.worldcinema.ui.main.compilation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.worldcinema.databinding.FragmentCompilationBinding

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
        val compilationViewModel =
            ViewModelProvider(this)[CompilationViewModel::class.java]

        _binding = FragmentCompilationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textCompilation
        compilationViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}