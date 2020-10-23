package specspulse.app.ui.details

import android.app.ActivityOptions
import android.os.Bundle
import android.util.Pair
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import coil.load
import kotlinx.android.synthetic.main.activity_info.*
import kotlinx.coroutines.*
import specspulse.app.R
import specspulse.app.data.Repository
import specspulse.app.utils.consume
import specspulse.app.utils.fromHtml
import specspulse.app.utils.startActivity
import specspulse.app.utils.statusBarHeight

class InfoActivity : AppCompatActivity(R.layout.activity_info) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolBar)

        val name = intent.getStringExtra(DEVICE_NAME)!!
        val link = intent.getStringExtra(DEVICE_LINK)!!

        collapsingToolbar.title = fromHtml(name)

        toolBar.setOnClickListener { infoList.smoothScrollToPosition(0) }
        infoList.setHasFixedSize(true)

        deviceImage.setOnClickListener {
            if (deviceImage.drawable != null) {
                val bundle = ActivityOptions
                    .makeSceneTransitionAnimation(this, Pair.create(it, it.transitionName)).toBundle()
                startActivity<ImageActivity>(DEVICE_NAME to name)
            }
        }

        getData(link)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> consume { finish() }
        else -> super.onOptionsItemSelected(item)
    }

    private fun getData(link: String) {
        lifecycleScope.launch(Dispatchers.Main.immediate) {
            try {
                val details = Repository.getDeviceDetails(link)

                infoList.adapter = InfoListAdapter(details.details)

                deviceImage.load(details.image)
            } catch(e: Exception) {
                println()
            }

            loading.isVisible = false
        }
    }

    companion object {
        const val DEVICE_LINK = "DEVICE_LINK"
        const val DEVICE_NAME = "DEVICE_NAME"
    }
}