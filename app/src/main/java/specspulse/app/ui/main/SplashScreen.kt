package specspulse.app.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import specspulse.app.ui.list.MainUI
import specspulse.app.utils.startActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity<MainUI>()
        finish()
    }
}