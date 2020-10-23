package specspulse.app.ui.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.main_ui.*
import specspulse.app.R
import specspulse.app.ui.search.SearchActivity
import specspulse.app.utils.consume
import specspulse.app.utils.startActivity

class MainUI : AppCompatActivity(R.layout.main_ui) {

    private var adapter = DevicesAdapter()
    private val viewModel by viewModels<DevicesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolBar)

        mostPopular.setHasFixedSize(true)
        mostPopular.adapter = adapter

//        fullList.setOnClickListener { changeList() }

//        if (isExpanded) fullList.setText(R.string.collapse_list)
        viewModel.devices.observe(this) {
            adapter.devices = it
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_search -> consume { startActivity<SearchActivity>() }

        else -> super.onOptionsItemSelected(item)
    }
}
