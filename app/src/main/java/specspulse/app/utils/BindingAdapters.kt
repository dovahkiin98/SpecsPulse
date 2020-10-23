package specspulse.app.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import coil.load
import specspulse.app.data.Repository

@BindingAdapter("android:src")
fun bindImageFromIcon(imageView: ImageView, @DrawableRes iconRes: Int) {
    imageView.setImageResource(iconRes)
}

@BindingAdapter("app:imageUrl")
fun bindImageFromUrl(imageView: ImageView, imageUrl: String) {
    imageView.load(imageUrl)
}

//@BindingAdapter("app:specsIcon")
//fun bindImageFromUrl(imageView: ImageView, imageUrl: String) {
//    SpecsUtils.loadImage(imageUrl, imageView)
//}