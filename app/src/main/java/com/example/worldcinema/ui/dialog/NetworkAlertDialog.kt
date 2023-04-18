package com.example.worldcinema.ui.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.worldcinema.databinding.DialogFragmentNetworkAlertBinding

class NetworkAlertDialog  : DialogFragment() {

    private lateinit var binding: DialogFragmentNetworkAlertBinding

    interface IExitListener {
        fun exit()
    }
    interface IRetryListener {
        fun retry()
    }

    private var exitListener: IExitListener? = null
    private var retryListener: IRetryListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentNetworkAlertBinding.inflate(inflater)

        // arguments?.getString(getString(R.string.dialog_data_for_profile_avatar))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.alertDialogExitBtn.setOnClickListener {
            // dialog?.dismiss()
            exitListener?.exit()
        }

        binding.alertDialogRetryBtn.setOnClickListener {
            retryListener?.retry()
        }

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            retryListener = parentFragment as IRetryListener?
            exitListener = activity as IExitListener?
        } catch (e: ClassCastException) {
            // TODO(Обработать ошибку)
        }
    }
}