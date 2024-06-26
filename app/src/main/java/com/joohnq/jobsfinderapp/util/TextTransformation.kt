package com.joohnq.jobsfinderapp.util

fun String.getFirstWord(): String {
    val s = this.split(" ")
    return s[0]
}
