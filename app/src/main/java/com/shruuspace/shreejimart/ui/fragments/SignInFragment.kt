package com.shruuspace.shreejimart.ui.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.installations.Utils

import com.shruuspace.shreejimart.R
import com.shruuspace.shreejimart.databinding.FragmentSignInBinding
import com.shruuspace.shreejimart.utils.CommonUtils
import com.shruuspace.shreejimart.utils.Constants

/**
 * A simple [Fragment] subclass.
 * Use the [SignInFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSignInBinding.inflate(layoutInflater, container, false)



        setPhoneNumberValidation()
        sendOTPButtonClick()
        return binding.root
    }

    private fun sendOTPButtonClick() {

        binding.btnSendOtp.setOnClickListener {

            val number = binding.enterPhoneText.text.toString()

            if (number.isEmpty() || number.length != 10) {
                CommonUtils.showToast(requireContext(), "Please Enter a Valid Number")
            } else {
                val bundle = Bundle()
                bundle.putString(Constants.BUNDLE_KEY_SENDMOBILENUMBER, number)
                findNavController().navigate(R.id.action_signInFragment_to_OTPFragment, bundle)

            }
        }
    }

    private fun setPhoneNumberValidation() {


        binding.enterPhoneText.addTextChangedListener {
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(
                    number: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    val len = number?.length
                    if (len == 10) {
                        val newColor = ContextCompat.getColor(requireContext(), R.color.orange)
                        binding.btnSendOtp.backgroundTintList = ColorStateList.valueOf(newColor)

                    } else {
                        val newColor = ContextCompat.getColor(requireContext(), R.color.orange50)
                        binding.btnSendOtp.backgroundTintList = ColorStateList.valueOf(newColor)

                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }

            }

        }

    }

}
