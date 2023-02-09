package com.ericggdev.beerfinderapp.helpers.extensions

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.ColorInt
import androidx.databinding.DataBindingUtil
import com.ericggdev.beerfinderapp.R
import com.ericggdev.beerfinderapp.databinding.DialogCustomLayoutBinding

fun Context.showErrorDialog(
    textToShow: String,
    onPositive: () -> Unit,
    onNegative: () -> Unit,

):Dialog {
    val dialogBinding: DialogCustomLayoutBinding =
        DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_custom_layout,
            null,
            false
        )
    dialogBinding.dialogText.text = textToShow

    val dialog = Dialog(this)
    dialog.setCancelable(false)

    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(dialogBinding.root)
    dialog.window?.setGravity(Gravity.CENTER)
    dialog.window?.setLayout(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )

    dialogBinding.dialogButtonAccept.setOnClickListener {
        onPositive()
        dialog.dismiss()
    }

    dialogBinding.dialogButtonCancel.setOnClickListener {
        onNegative()
        dialog.dismiss()
    }

    dialog.show()
    return dialog
}