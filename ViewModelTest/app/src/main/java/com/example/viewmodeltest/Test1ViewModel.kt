package com.example.viewmodeltest

import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class Test1ViewModel : ViewModel() {
    val text = ObservableField<String>("")

    fun showText(view: View) {
        Toast.makeText(view.context, "${text.get()}", Toast.LENGTH_SHORT).show()
    }
}