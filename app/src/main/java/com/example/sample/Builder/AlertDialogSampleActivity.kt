package com.example.sample.Builder

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.example.sample.R

class AlertDialogSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert_dialog_sample)
    }
    fun showDialog(context: Context) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setIcon(R.drawable.ic_launcher_foreground)
        builder.setMessage(MESSAGE)
        builder.setPositiveButton("Button1") {dialog, which -> title = "onClickButton1" }
        builder.create().show()
        Dialog
    }
    companion object{
        val MESSAGE = "message_sample"
    }
}
