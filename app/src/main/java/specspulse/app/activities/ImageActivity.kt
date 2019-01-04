package specspulse.app.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_image.*
import specspulse.app.R
import specspulse.app.data.SpecsUtils

class ImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        val name = intent.getStringExtra(InfoActivity.DEVICE_NAME)

        toolbar.setNavigationOnClickListener { supportFinishAfterTransition() }
        titleText.text = name

        SpecsUtils.loadImage(SpecsUtils.imageUri(name), deviceImage)

        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }
}