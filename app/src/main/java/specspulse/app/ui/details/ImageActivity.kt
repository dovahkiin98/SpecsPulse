package specspulse.app.ui.details

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import specspulse.app.R
import specspulse.app.utils.fromHtml

class ImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        val name = intent.getStringExtra(DEVICE_NAME) ?: ""
        val imageLink = intent.getStringExtra(DEVICE_IMAGE) ?: ""

//        toolbar.setNavigationOnClickListener { supportFinishAfterTransition() }

        title = fromHtml(name)

//        deviceImage.load(imageLink)

        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    companion object {
        const val DEVICE_NAME = "DEVICE_NAME"
        const val DEVICE_IMAGE = "DEVICE_IMAGE"
    }
}