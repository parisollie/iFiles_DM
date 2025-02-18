package com.pjff.ifilesdm

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

/*Paso 8,Ponemos nuestras utilerias y creamos nuestras funciones de extencion
Activity.toast ,esta es mi funcion por extension ,y ponemos sus parametros.
length: Int = Toast.LENGTH_SHORT es dw tipo int y con el default value de Toast.LENGTH_SHORT*/
fun Activity.toast(text: String, length: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, text, length).show()
}

//Paso 9
fun Activity.sbMessage(view: View, text: String, textColor: String? = "#FFFFFF", bgColor: String? = "#9E1734", length: Int = Snackbar.LENGTH_SHORT){
    Snackbar.make(view, text, length)
        .setTextColor(Color.parseColor(textColor))
        .setBackgroundTint(Color.parseColor(bgColor))
        .show()
}
//Paso 10,Para que me oculte el teclado
fun View.hideKeyboard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)
}