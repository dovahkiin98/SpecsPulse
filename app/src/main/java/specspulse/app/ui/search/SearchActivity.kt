package specspulse.app.ui.search

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import specspulse.app.R
import specspulse.app.data.Repository
import specspulse.app.ui.list.DevicesAdapter

class SearchActivity : AppCompatActivity(R.layout.activity_search) {

    private val adapter = DevicesAdapter()
    private val inputManager by lazy { getSystemService<InputMethodManager>()!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(searchBar)
        setupSearch()

        searchList.adapter = adapter
        searchList.setHasFixedSize(true)

        searchBox.requestFocus()
    }

    private fun setupSearch() {
        searchBox.doAfterTextChanged {
            searchClear.isVisible = it?.isNotEmpty() == true
        }

        searchBox.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                applySearch(searchBox.text.toString().trim())
            }
            true
        }

        searchClear.setOnClickListener { searchBox.text.clear() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        R.id.action_search -> {
            applySearch(searchBox.text.toString().trim())
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun applySearch(term: String) {
        if (term.isEmpty()) return

        inputManager.hideSoftInputFromWindow(coordinator.windowToken, 0)

        lifecycleScope.launch(Dispatchers.Main.immediate) {
            loading.isVisible = true

            adapter.devices = emptyList()

            try {
                val devices = Repository.searchDevices(term)

                adapter.devices = devices
            } catch (e: Exception) {

            }

            loading.isVisible = false
        }
    }
}
