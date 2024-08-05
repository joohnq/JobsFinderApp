package com.joohnq.core.helper

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import java.io.ByteArrayOutputStream

object BitmapHelper {
				@SuppressLint("UseCompatLoadingForDrawables")
				fun drawableToBitmap(context: Context, drawableId: Int): Bitmap {
								val drawable = context.getDrawable(drawableId) ?: return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
								val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
								val canvas = android.graphics.Canvas(bitmap)
								drawable.setBounds(0, 0, canvas.width, canvas.height)
								drawable.draw(canvas)
								return bitmap
				}

				fun bitmapToUriConverter(context: Context, bitmap: Bitmap): Uri {
//								val bytes = ByteArrayOutputStream()
//								bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
//								val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
								return Uri.parse("path")
				}
}