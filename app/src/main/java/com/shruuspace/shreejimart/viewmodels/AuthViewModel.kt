package com.shruuspace.shreejimart.viewmodels

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.shruuspace.shreejimart.data.models.UserModel
import com.shruuspace.shreejimart.utils.CommonUtils
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.concurrent.TimeUnit

class AuthViewModel : ViewModel() {

    private val _verificationId = MutableStateFlow<String?>(null)
    private val _isOTPSent = MutableStateFlow(false)
    val otpSent = _isOTPSent

    private val _isOTPVerified = MutableStateFlow(false)
    val isOTPVerified = _isOTPVerified


    fun sendOtp(mobileNumber: String, activity: Activity) {

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(e: FirebaseException) {
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken,
            ) {
                _verificationId.value = verificationId
                _isOTPSent.value = true
            }
        }


        val options = PhoneAuthOptions.newBuilder(CommonUtils.getAuthInstance())
            .setPhoneNumber("+91$mobileNumber") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)


    }


    fun signInWithPhoneAuthCredential(
        otp: String,
        auth: FirebaseAuth

    ) {

        val verificationId = _verificationId.value

            val credential = PhoneAuthProvider.getCredential(verificationId!!, otp)

            auth.signInWithCredential(credential).addOnCompleteListener { task ->
                // Check if task is not null
                // Log the task if it's not null

                if (task.isSuccessful) {

                      val user = task.result?.user
                      val usermodel = UserModel(user?.uid, user?.phoneNumber, null)

                      FirebaseDatabase.getInstance().getReference("AllUsers").child("Users")
                          .child(user!!.uid).setValue(usermodel)
                      _isOTPVerified.value = true

                } else {
                    // Sign in failed, display a message
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                }
            }


}


/*
    fun signInWithPhoneAuthCredential(otp: String) {

        val verificationId = _verificationId.value

        if (!verificationId.isNullOrEmpty()) {
            val credential = PhoneAuthProvider.getCredential(verificationId, otp)

            CommonUtils.getAuthInstance().signInWithCredential(credential)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        val user = task.result?.user
                        val usermodel=UserModel(user?.uid,user?.phoneNumber,null)

                        Log.e("TEST", "signInWithPhoneAuthCredential: "+user?.uid+"--temp-")
                        FirebaseDatabase.getInstance().getReference("AllUsers").child("Users")
                            .child(user!!.uid).setValue(usermodel)
                        _isOTPVerified.value = true

                    } else {
                        // Handle the case when verificationId is null or empty
                        Log.e("TEST", "signInWithPhoneAuthCredential: "+"Failed")
                    }
                }
        }
    }

*/

}