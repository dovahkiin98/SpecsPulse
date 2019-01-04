package specspulse.app.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import specspulse.app.data.SpecsUtils

@BindingAdapter("android:src")
fun bindImageFromIcon(imageView: ImageView, @DrawableRes iconRes: Int) {
    imageView.setImageResource(iconRes)
}

@BindingAdapter("app:imageUrl")
fun bindImageFromUrl(imageView: ImageView, imageUrl: String) {
    SpecsUtils.loadImage(imageUrl, imageView)
}

//@BindingAdapter("app:specsIcon")
//fun bindImageFromUrl(imageView: ImageView, imageUrl: String) {
//    SpecsUtils.loadImage(imageUrl, imageView)
//}