package com.example.worldcinema.ui.dialog

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.worldcinema.R
import com.example.worldcinema.data.storage.shared_prefs.token.SharedPrefTokenStorage
import com.example.worldcinema.data.storage.shared_prefs.token.TokenStorageRepository
import com.example.worldcinema.databinding.DialogFragmentAlertBinding
import com.example.worldcinema.domain.model.Token
import com.example.worldcinema.domain.usecase.storage.SaveTokenToLocalStorageUseCase

class AlertDialog : DialogFragment() {

    private lateinit var binding: DialogFragmentAlertBinding

    interface IAlertDialogExitListener {
        fun alertDialogExit()
    }

    interface IAlertDialogListener {
        fun alertDialogRetry()
        fun onAlertDialogDismiss()
    }

    private var exitListener: IAlertDialogExitListener? = null
    private var dialogListener: IAlertDialogListener? = null

    private lateinit var alert: AlertType

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentAlertBinding.inflate(inflater)

        val ordinal = arguments?.getInt(getString(R.string.alert_dialog_data_text)) ?: 0

        alert = AlertType.values().first {
            it.ordinal == (ordinal)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textView8.text = getAlertMessage(alert)

        when (getAlertType(alert)) {
            AlertDisplayType.VALIDATION -> {
                binding.imageView4.setImageResource(R.drawable.error_icon_1)
                binding.alertDialogExitBtn.visibility = View.GONE
                binding.alertDialogRetryBtn.text =
                    getString(R.string.alert_button_text_understandable)
                binding.alertDialogRetryBtn.setOnClickListener {
                    dialog?.dismiss()
                }
            }
            AlertDisplayType.INFO_ONE -> {
                binding.imageView4.setImageResource(R.drawable.error_icon_2)
                binding.alertDialogExitBtn.visibility = View.GONE
                binding.alertDialogRetryBtn.text = getString(R.string.alert_button_text_lol)
                binding.alertDialogRetryBtn.setOnClickListener {
                    dialog?.dismiss()
                }
            }
            AlertDisplayType.INFO_TWO -> {
                binding.imageView4.setImageResource(R.drawable.error_icon_3)
                binding.alertDialogExitBtn.visibility = View.GONE
                binding.alertDialogRetryBtn.text = getString(R.string.alert_button_text_lol)
                binding.alertDialogRetryBtn.setOnClickListener {
                    dialog?.dismiss()
                }
            }
            AlertDisplayType.SERVER_ERROR -> {
                binding.imageView4.setImageResource(R.drawable.error_icon_4)

                binding.alertDialogExitBtn.setOnClickListener {
                    SaveTokenToLocalStorageUseCase(
                        TokenStorageRepository(
                            SharedPrefTokenStorage(
                                requireContext()
                            )
                        )
                    ).execute(
                        Token("", "")
                    )
                }

                binding.alertDialogRetryBtn.setOnClickListener {
                    dialog?.dismiss()
                    dialogListener?.alertDialogRetry()
                }
            }
        }

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dialogListener?.onAlertDialogDismiss()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dialogListener = (parentFragment ?: activity) as IAlertDialogListener?
            exitListener = activity as IAlertDialogExitListener?
        } catch (e: ClassCastException) {
            dialog?.dismiss()
        }
    }

    private fun getAlertMessage(alert: AlertType): String {
        return when (alert) {
            AlertType.EMPTY_FIRST_NAME -> getString(R.string.alert_text_empty_first_name)
            AlertType.EMPTY_LAST_NAME -> getString(R.string.alert_text_empty_last_name)
            AlertType.EMPTY_EMAIL -> getString(R.string.alert_text_empty_email)
            AlertType.EMPTY_PASSWORD -> getString(R.string.alert_text_empty_password)
            AlertType.EMPTY_REPEAT_PASSWORD -> getString(R.string.alert_text_empty_repeat_password)

            AlertType.EMPTY_CHAT_MESSAGE -> getString(R.string.alert_text_empty_chat_message)

            AlertType.EMPTY_COLLECTION_NAME -> getString(R.string.alert_text_empty_collection_name)

            AlertType.WRONG_EMAIL -> getString(R.string.alert_text_wrong_email)
            AlertType.WRONG_REPEAT_PASSWORD -> getString(R.string.alert_text_wrong_repeat_password)

            AlertType.AUTH_NOT_SUCCESS -> getString(R.string.alert_text_auth_not_success)
            AlertType.REGISTER_NOT_SUCCESS -> getString(R.string.alert_text_register_not_success)

            AlertType.COLLECTION_WITH_FAVOURITE_NAME -> getString(R.string.alert_text_collection_with_favourite_name)

            AlertType.DEFAULT -> getString(R.string.alert_text_default)

            AlertType.BAD_API -> getString(R.string.alert_text_bad_api)
            AlertType.BAD_FIGMA -> getString(R.string.alert_text_bad_figma)

            AlertType.SERVER_ERROR -> getString(R.string.alert_text_default)

            AlertType.NO_PERMISSIONS -> getString(R.string.alert_text_no_permissions)
        }
    }

    private fun getAlertType(alert: AlertType): AlertDisplayType {
        return when (alert) {
            AlertType.EMPTY_FIRST_NAME -> AlertDisplayType.VALIDATION
            AlertType.EMPTY_LAST_NAME -> AlertDisplayType.VALIDATION
            AlertType.EMPTY_EMAIL -> AlertDisplayType.VALIDATION
            AlertType.EMPTY_PASSWORD -> AlertDisplayType.VALIDATION
            AlertType.EMPTY_REPEAT_PASSWORD -> AlertDisplayType.VALIDATION

            AlertType.EMPTY_CHAT_MESSAGE -> AlertDisplayType.VALIDATION

            AlertType.EMPTY_COLLECTION_NAME -> AlertDisplayType.VALIDATION

            AlertType.WRONG_EMAIL -> AlertDisplayType.VALIDATION
            AlertType.WRONG_REPEAT_PASSWORD -> AlertDisplayType.VALIDATION

            AlertType.AUTH_NOT_SUCCESS -> AlertDisplayType.VALIDATION
            AlertType.REGISTER_NOT_SUCCESS -> AlertDisplayType.VALIDATION

            AlertType.COLLECTION_WITH_FAVOURITE_NAME -> AlertDisplayType.VALIDATION

            AlertType.DEFAULT -> AlertDisplayType.SERVER_ERROR

            AlertType.BAD_API -> AlertDisplayType.INFO_ONE
            AlertType.BAD_FIGMA -> AlertDisplayType.INFO_TWO

            AlertType.SERVER_ERROR -> AlertDisplayType.INFO_ONE

            AlertType.NO_PERMISSIONS -> AlertDisplayType.VALIDATION
        }
    }

    private enum class AlertDisplayType {
        VALIDATION,
        SERVER_ERROR,
        INFO_ONE,
        INFO_TWO
    }
}