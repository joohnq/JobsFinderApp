package com.joohnq.jobsfinderapp.util

object Functions {
    fun getTwoWords(str: String): String? {
        val palavras = str.split(" ")
        return if (palavras.size >= 2) {
            "${palavras[0]} ${palavras[1]}"
        }else{
            palavras[0]
        }
    }
}