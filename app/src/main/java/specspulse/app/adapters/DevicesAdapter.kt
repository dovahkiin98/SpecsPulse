package specspulse.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.startActivity
import specspulse.app.R
import specspulse.app.activities.InfoActivity
import specspulse.app.databinding.DeviceCardBinding
import specspulse.app.databinding.DeviceGridBinding
import specspulse.app.model.Device

class DevicesAdapter : RecyclerView.Adapter<DevicesAdapter.DeviceViewHolder>() {
    var devices = listOf<Device>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var viewType: ViewType = ViewType.CARD
        set(value) {
            field = value
            notifyDataSetChanged()
            println("B")
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder =
        if (viewType == ViewType.CARD.value)
            CardViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.device_card, parent, false))
        else GridViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.device_grid, parent, false))

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        if (holder is CardViewHolder) holder.binding.device = devices[position]
        else if (holder is GridViewHolder) holder.binding.device = devices[position]
    }

    override fun getItemCount() = this.devices.size

    override fun getItemViewType(position: Int) = viewType.value

    enum class ViewType(val value: Int) {
        CARD(0), TILE(1);

        companion object {
            fun fromValue(value: Int) = if (value == 0) CARD else TILE
        }
    }

    inner class CardViewHolder(val binding: DeviceCardBinding) : DeviceViewHolder(binding)
    inner class GridViewHolder(val binding: DeviceGridBinding) : DeviceViewHolder(binding)

    open inner class DeviceViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                it.context.startActivity<InfoActivity>(InfoActivity.DEVICE_NAME to devices[adapterPosition].name)
            }
        }
    }

    fun addAll(list: List<Device>) {
        this.devices += list
    }
}