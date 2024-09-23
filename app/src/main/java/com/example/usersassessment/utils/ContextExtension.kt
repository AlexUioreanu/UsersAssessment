package com.example.usersassessment.utils

import android.content.Context
import android.net.ConnectivityManager

fun Context.connectivityManager(): ConnectivityManager =
    (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)