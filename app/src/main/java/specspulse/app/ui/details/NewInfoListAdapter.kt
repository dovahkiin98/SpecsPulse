package specspulse.app.ui.details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import specspulse.app.R
import specspulse.app.databinding.InfoChildBinding
import specspulse.app.databinding.ViewInfoBinding
import specspulse.app.model.Info
import specspulse.app.model.Specification

class NewInfoListAdapter(private val flatList: List<Info>) : RecyclerView.Adapter<NewInfoListAdapter.InfoHolder>() {

    private lateinit var viewPool: RecyclerView.RecycledViewPool

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        InfoHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_info, parent, false))

    override fun onBindViewHolder(holder: InfoHolder, position: Int) =
        holder.setInfo(flatList[position])

    override fun getItemCount() = flatList.size

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        viewPool = recyclerView.recycledViewPool
    }

    inner class InfoHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun setInfo(info: Info) {
            val binding = DataBindingUtil.bind<ViewInfoBinding>(itemView)!!
            binding.info = info
            binding.list.apply {
                setHasFixedSize(true)
                setRecycledViewPool(viewPool)
                adapter = InfoCardAdapter(info.Data)
            }
//            binding.infoIcon.setImageResource(getIcon(itemView.context, info))
        }

        private fun getIcon(context: Context, specification: Specification) = when (val icon = specification.Icon) {
            "NULL" -> -1
            "" -> 0
            else -> context.resources.getIdentifier(icon, "drawable", context.packageName)
        }
    }

    inner class InfoCardAdapter(private val flatList: List<Specification>) : RecyclerView.Adapter<InfoCardAdapter.InfoCardHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            InfoCardHolder(LayoutInflater.from(parent.context).inflate(R.layout.info_child, parent, false))

        override fun onBindViewHolder(holder: InfoCardHolder, position: Int) =
            holder.setInfo(flatList[position])

        override fun getItemCount() = flatList.size

        inner class InfoCardHolder(view: View) : RecyclerView.ViewHolder(view) {
            fun setInfo(info: Specification) {
                val binding = DataBindingUtil.bind<InfoChildBinding>(itemView)!!
                binding.spec = info
                binding.infoIcon.setImageResource(getIcon(itemView.context, info))
            }

            private fun getIcon(context: Context, specification: Specification) = when (val icon = specification.Icon) {
                "NULL" -> -1
                "" -> 0
                else -> context.resources.getIdentifier(icon, "drawable", context.packageName)
            }
        }
    }
}