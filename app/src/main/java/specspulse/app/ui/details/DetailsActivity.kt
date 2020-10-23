package specspulse.app.ui.details

import android.app.ActivityOptions
import android.os.Bundle
import android.util.Pair
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil.load
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.coroutines.*
import specspulse.app.R
import specspulse.app.data.Repository
import specspulse.app.utils.consume
import specspulse.app.utils.fromHtml
import specspulse.app.utils.startActivity

class DetailsActivity : AppCompatActivity(R.layout.activity_details) {

    private val viewModel by viewModels<DetailsViewModel>()

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
                startActivity<ImageActivity>(
                    ImageActivity.DEVICE_NAME to name,
                    ImageActivity.DEVICE_IMAGE to link,
                )
            }
        }

        if(savedInstanceState == null) viewModel.getData(link)

        viewModel.device.observe(this) {
            infoList.adapter = DetailsAdapter(it.details)

            deviceImage.load(it.image)

            loading.isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> consume { finish() }
        else -> super.onOptionsItemSelected(item)
    }

    companion object {
        const val DEVICE_LINK = "DEVICE_LINK"
        const val DEVICE_NAME = "DEVICE_NAME"
    }
}