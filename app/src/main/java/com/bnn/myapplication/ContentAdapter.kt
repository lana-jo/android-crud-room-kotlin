package com.bnn.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bnn.myapplication.data.Developer
import com.bnn.myapplication.databinding.LayoutRecyclerviewItemBinding

class ContentAdapter /*internal constructor(
	context: Context
) */: RecyclerView.Adapter<ContentAdapter.ViewHolder>() {

	/*private val inflater: LayoutInflater = LayoutInflater.from(context)*/
	private var developers = emptyList<Developer>() // Cached copy

	inner class ViewHolder(itemView: LayoutRecyclerviewItemBinding) : RecyclerView.ViewHolder(itemView.root) {
		val binding = itemView
		fun setBinding(data: Developer){
			binding.textView.text = data.developer
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		/*val itemView = inflater.inflate(R.layout.layout_recyclerview_item, parent, false)
		return ViewHolder(itemView)*/
		return ViewHolder(LayoutRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		/*val current = developers[position]
		holder.developerItemView.text = current.developer*/
		holder.setBinding(developers[position])
	}

	@SuppressLint("NotifyDataSetChanged")
	internal fun setItems(developers: List<Developer>) {
		this.developers = developers
		notifyDataSetChanged()
	}

	override fun getItemCount() = developers.size
}