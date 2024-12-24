package com.joohnq.domain.mappers

import java.io.UnsupportedEncodingException

object StringMapper {
				fun getFirstWord(s: String): String = s.split(" ").first()
				fun fixEncoding(text: String): String =
								try {
												String(text.toByteArray(charset("ISO-8859-1")), charset("UTF-8"))
								} catch (e: UnsupportedEncodingException) {
												text
								}

				fun convertHttpInHttps(url: String): String =
								if (!url.startsWith("http://") && !url.startsWith("https://"))
												"https://$url"
								else
												throw Exception("Invalid url")
}
