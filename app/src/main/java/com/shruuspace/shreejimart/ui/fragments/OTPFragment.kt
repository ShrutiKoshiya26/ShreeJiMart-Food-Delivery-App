package com.shruuspace.shreejimart.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController

import com.shruuspace.shreejimart.R
import com.shruuspace.shreejimart.data.models.UserModel
import com.shruuspace.shreejimart.databinding.FragmentOTPBinding
import com.shruuspace.shreejimart.ui.activity.HomeActivity
import com.shruuspace.shreejimart.utils.CommonUtils
import com.shruuspace.shreejimart.utils.Constants
import com.shruuspace.shreejimart.viewmodels.AuthViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class OTPFragment : Fragment() {

    private lateinit var binding: FragmentOTPBinding

    private val viewModel: AuthViewModel by viewModels()

    var mobileNumber: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentOTPBinding.inflate(layoutInflater, container, false)

        onBackButtonClick()
        getUserNumber()
        customizingEnteringOTP()
        sendOTP()
        nextButtonClick()

        return binding.root
    }

    private fun nextButtonClick() {

        binding.btnNext.setOnClickListener {

            CommonUtils.showDialog(requireContext(), "Signing User...")

            val editTexts = arrayOf(
                binding.et1, binding.et2, binding.et3, binding.et4, binding.et5, binding.et6
            )
            val otp = editTexts.joinToString("") { it.text.toString() }
            if (otp.length < editTexts.size) {
                CommonUtils.showToast(requireContext(), "Please enter right otp")
            } else {
                editTexts.forEach {
                    it.text?.clear()
                    it.clearFocus()
                    verifyOtp(otp)
                }
            }
        }


    }

    private fun verifyOtp(otp: String) {

        viewModel.signInWithPhoneAuthCredential(otp, CommonUtils.getAuthInstance())

        lifecycleScope.launch {

            viewModel.isOTPVerified.collect {

                if (it) {

                    CommonUtils.hideDialog()
                    CommonUtils.showToast(requireContext(), "Logging in...")

                    startActivity(Intent(requireActivity(), HomeActivity::class.java))
                    requireActivity().finish()
                }
            }
        }

    }

    private fun sendOTP() {

        CommonUtils.showDialog(requireContext(), "Sending OTP...")

        viewModel.apply {

            mobileNumber?.let {
                sendOtp(it, requireActivity())
            }
            lifecycleScope.launch {
                otpSent.collect {
                    if (it) {
                        CommonUtils.hideDialog()
                        CommonUtils.showToast(requireContext(), "OTP Sent Successfully...")
                    }
                }
            }


        }


    }

    private fun onBackButtonClick() {

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_OTPFragment_to_signInFragment)
        }

    }

    private fun getUserNumber() {

        val bundle = arguments
        val userNumber = bundle?.getString(Constants.BUNDLE_KEY_SENDMOBILENUMBER).toString()

        binding.slidedescription.text = HtmlCompat.fromHtml(
            ContextCompat.getString(requireContext(), R.string.auth_notificationcodesent)
                .plus(" <b>$userNumber</b>"), HtmlCompat.FROM_HTML_MODE_COMPACT
        )

        mobileNumber = userNumber
    }

    private fun customizingEnteringOTP() {
        val editTexts = arrayOf(
            binding.et1, binding.et2, binding.et3, binding.et4, binding.et5, binding.et6
        )
        for (i in editTexts.indices) {
            editTexts[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1) {
                        if (i < editTexts.size - 1) {
                            editTexts[i + 1].requestFocus()
                        }
                    } else if (s?.length == 0) {
                        if (i > 0) {
                            editTexts[i - 1].requestFocus()
                        }
                    }
                }
            })
        }
    }


}