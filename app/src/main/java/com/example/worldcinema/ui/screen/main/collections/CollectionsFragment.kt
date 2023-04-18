package com.example.worldcinema.ui.screen.main.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.worldcinema.R
import com.example.worldcinema.databinding.FragmentCollectionsBinding
import com.example.worldcinema.ui.dialog.AlertDialog
import com.example.worldcinema.ui.dialog.AlertType
import com.example.worldcinema.ui.dialog.showAlertDialog
import com.example.worldcinema.ui.model.UsersCollection
import com.example.worldcinema.ui.screen.main.collections.adapter.CollectionsAdapter
import com.example.worldcinema.ui.screen.main.collections.adapter.ICollectionActionListener

class CollectionsFragment : Fragment(), AlertDialog.IAlertDialogListener {

    private var _binding: FragmentCollectionsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CollectionsViewModel
    private lateinit var navController: NavController

    private lateinit var collectionsAdapter: CollectionsAdapter

    private val collectionsIcons = getIcons()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectionsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            this,
            CollectionsViewModelFactory(requireContext())
        )[CollectionsViewModel::class.java]
        navController = findNavController()

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.collectionsContent.visibility = View.GONE
                binding.progressBarCollections.visibility = View.VISIBLE
            } else {
                binding.collectionsContent.visibility = View.VISIBLE
                binding.progressBarCollections.visibility = View.GONE
                onDataLoaded()
            }
        }

        viewModel.showAlertDialog.observe(viewLifecycleOwner) {
            if(it) {
                showAlertDialog(viewModel.alertType.value ?: AlertType.DEFAULT)
            }
        }

        return binding.root
    }

    private fun onDataLoaded() {

        initCollectionsRecyclerView()

        binding.imageButtonAddCollection.setOnClickListener {
            navController.navigate(R.id.action_navigation_collections_to_createCollectionActivity)
        }

        viewModel.showCollection.observe(viewLifecycleOwner) {
            if (it) {
                val action =
                    CollectionsFragmentDirections.actionNavigationCollectionsToMoviesCollectionActivity(
                        viewModel.selectedCollection.value!!
                    )
                navController.navigate(action)
                viewModel.collectionShowed()
            }
        }
    }

    private fun initCollectionsRecyclerView() {

        binding.CollectionsRecyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        collectionsAdapter = CollectionsAdapter(object : ICollectionActionListener {
            override fun onItemClicked(collection: UsersCollection) {
                viewModel.onItemClicked(collection)
            }
        }, collectionsIcons)
        binding.CollectionsRecyclerView.adapter = collectionsAdapter

        viewModel.collections.observe(viewLifecycleOwner) {
            if (it != null) {
                collectionsAdapter.data = it
            }
        }
    }

    private fun getIcons(): List<Int> {
        return listOf(
            R.drawable.ic_1,
            R.drawable.ic_2,
            R.drawable.ic_3,
            R.drawable.ic_4,
            R.drawable.ic_5,
            R.drawable.ic_6,
            R.drawable.ic_7,
            R.drawable.ic_8,
            R.drawable.ic_9,
            R.drawable.ic_10,
            R.drawable.ic_11,
            R.drawable.ic_12,
            R.drawable.ic_13,
            R.drawable.ic_14,
            R.drawable.ic_15,
            R.drawable.ic_16,
            R.drawable.ic_17,
            R.drawable.ic_18,
            R.drawable.ic_19,
            R.drawable.ic_20,
            R.drawable.ic_21,
            R.drawable.ic_22,
            R.drawable.ic_23,
            R.drawable.ic_24,
            R.drawable.ic_25,
            R.drawable.ic_26,
            R.drawable.ic_27,
            R.drawable.ic_28,
            R.drawable.ic_29,
            R.drawable.ic_30,
            R.drawable.ic_31,
            R.drawable.ic_32,
            R.drawable.ic_33,
            R.drawable.ic_34,
            R.drawable.ic_35,
            R.drawable.ic_36,
        )
    }

    override fun onResume() {
        viewModel.loadData()
        super.onResume()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun alertDialogRetry() {
        viewModel.reload()
    }

    override fun onAlertDialogDismiss() {
        viewModel.alertShowed()
    }
}