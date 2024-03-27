package com.shruuspace.shreejimart.utils

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.shruuspace.shreejimart.databinding.ProgressDialogBinding

object CommonUtils {


    private var dialog: AlertDialog? = null


    fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }

    fun showDialog(context: Context, message: String) {
        val progress = ProgressDialogBinding.inflate(LayoutInflater.from(context))
        progress.tvloading.text = message
        // Set background color of the root layout to transparent
        progress.root.setBackgroundColor(Color.TRANSPARENT)
        dialog = AlertDialog.Builder(context).setView(progress.root).setCancelable(false).create()
        dialog!!.show()
    }

    fun hideDialog() {
        dialog?.dismiss()
    }


    private var firebaseAuthInstance: FirebaseAuth? = null


    fun getAuthInstance(): FirebaseAuth {
        if (firebaseAuthInstance == null) {
            firebaseAuthInstance = FirebaseAuth.getInstance()
        }
        return firebaseAuthInstance!!
    }

}