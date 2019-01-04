package specspulse.app.activities

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.main_ui.*
import kotlinx.android.synthetic.main.nav_header.view.*
import org.jetbrains.anko.defaultSharedPreferences
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import specspulse.app.R
import specspulse.app.SpecsApp
import specspulse.app.adapters.DevicesAdapter
import specspulse.app.data.DevicesViewModel
import specspulse.app.utils.consume
import specspulse.app.utils.getStatusBarHeight
import specspulse.app.utils.showAds

class MainUI : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private var adapter = DevicesAdapter()
    private var isExpanded = false
    private val disposable = CompositeDisposable()
    private var viewType = DevicesAdapter.ViewType.CARD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_ui)

        setSupportActionBar(toolBar)
        setupNavigation()

        recents.isNestedScrollingEnabled = false
        recents.setHasFixedSize(true)
        recents.adapter = adapter

        val isCollapsed = defaultSharedPreferences.getBoolean("collapsed", true)
        recents.collapsed = isCollapsed

        fullList.setOnClickListener { changeList() }

        if (isExpanded) fullList.setText(R.string.collapse_list)
        ViewModelProviders.of(this).get<DevicesViewModel>().data.observe(this, Observer {
            adapter.devices = it
        })

        adView.showAds()
    }

    private fun changeList() {
        fullList.setText(if (isExpanded) R.string.full_list else R.string.collapse_list)
        isExpanded = !isExpanded
//        getData()
    }

    private fun setupNavigation() = navigation.apply {
        getHeaderView(0).header.updatePadding(top = getStatusBarHeight())
        (menu.findItem(R.id.nav_night).actionView as SwitchCompat).apply {
            isChecked = SpecsApp.NIGHT_MODE
            setOnCheckedChangeListener { _, isChecked ->
                defaultSharedPreferences.edit().putBoolean("Night Mode", isChecked).apply()
                application.onCreate()
            }
        }

        setNavigationItemSelectedListener {
            when {
                it.groupId == R.id.main_nav -> consume { toast("Coming Soon!") }
                it.itemId == R.id.nav_night -> consume {
                    (it.actionView as SwitchCompat).apply { isChecked = !isChecked }
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        menu.findItem(R.id.action_view).apply {
            setIcon(if (recents.collapsed) R.drawable.ic_grid else R.drawable.ic_list)
            setTitle(if (recents.collapsed) R.string.action_grid else R.string.action_list)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> consume { drawer.openDrawer(navigation) }
        R.id.action_search -> consume { startActivity<SearchActivity>() }
        R.id.action_view -> consume {
            recents.collapsed = !recents.collapsed
            defaultSharedPreferences.edit()
                .putBoolean("collapsed", recents.collapsed)
                .apply()
            invalidateOptionsMenu()
        }

        else -> super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() = when {
        drawer.isDrawerOpen(navigation) -> drawer.closeDrawer(navigation)
        else -> super.onBackPressed()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("Expanded", isExpanded)
//        outState.putInt("ViewType", viewType.value)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        isExpanded = savedInstanceState.getBoolean("Expanded")
//        viewType = DevicesAdapter.ViewType.fromValue(savedInstanceState.getInt("ViewType"))
    }

    override fun onStart() {
        super.onStart()
        defaultSharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onStop() {
        super.onStop()
        defaultSharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        disposable.clear()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String) {
        if (key == "Night Mode") {
            startActivity(intent)
            finish()
        }
    }
}
