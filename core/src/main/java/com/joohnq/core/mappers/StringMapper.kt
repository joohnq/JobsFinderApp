package com.joohnq.core.mappers

import java.io.UnsupportedEncodingException

object StringMapper {
				fun getFirstWord(s: String): String = s.split(" ")[0]
				fun fixEncoding(text: String): String {
								try {
												val bytes = text.toByteArray(charset("ISO-8859-1"))
												return String(bytes, charset("UTF-8"))
								} catch (e: UnsupportedEncodingException) {
												return text
								}
				}

				fun convertHttpInHttps(url: String): String =
								if (!url.startsWith("http://") && !url.startsWith("https://"))
												"https://$url"
								else
												throw Exception("Erro")
}
