package com.lollipop.ktouch.faiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import androidx.wear.widget.WearableLinearLayoutManager
import com.lollipop.ktouch.databinding.ActivityFaizMenuBinding
import com.lollipop.ktouch.databinding.ItemFaizOptionBinding
import com.lollipop.ktouch.databinding.ItemFaizSmartbrainBinding

class FaizMenuActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFaizMenuBinding.inflate(layoutInflater)
    }

    private val dataList = ArrayList<Any>()
    private val optionAdapter by lazy {
        MenuAdapter(dataList, ::onOptionClick)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.recyclerView.isEdgeItemsCenteringEnabled = true
        initOption()
        binding.recyclerView.adapter = optionAdapter
        binding.recyclerView.layoutManager = WearableLinearLayoutManager(this)
        LinearSnapHelper().attachToRecyclerView(binding.recyclerView)
    }

    private fun initOption() {
        dataList.add(SmartBrainLogo)
        dataList.addAll(FaizOption.entries)
    }

    private fun onOptionClick(option: FaizOption) {
        // TODO
    }

    private data object SmartBrainLogo

    private class MenuAdapter(
        val data: List<Any>,
        val onOptionClick: (FaizOption) -> Unit
    ) : RecyclerView.Adapter<MenuHolder>() {

        companion object {
            private const val TYPE_LOGO = 0
            private const val TYPE_OPTION = 1
        }

        private var layoutInflaterReference: LayoutInflater? = null

        private fun layoutInflater(parent: ViewGroup): LayoutInflater {
            return layoutInflaterReference ?: LayoutInflater.from(parent.context).also {
                layoutInflaterReference = it
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
            when (viewType) {
                TYPE_OPTION -> {
                    return MenuHolder.Option(
                        ItemFaizOptionBinding.inflate(
                            layoutInflater(parent),
                            parent,
                            false
                        )
                    ).also {
                        it.onItemClickCallback = ::onItemClick
                    }
                }

                else -> {
                    return MenuHolder.Logo(
                        ItemFaizSmartbrainBinding.inflate(
                            layoutInflater(parent),
                            parent,
                            false
                        )
                    )
                }
            }
        }

        private fun onItemClick(position: Int) {
            if (position < 0 || position >= data.size) {
                return
            }
            val item = data[position]
            if (item is FaizOption) {
                onOptionClick(item)
            }
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(holder: MenuHolder, position: Int) {
            when (holder) {
                is MenuHolder.Logo -> {}
                is MenuHolder.Option -> {
                    val item = data[position]
                    if (item is FaizOption) {
                        holder.bind(item)
                    }
                }
            }
        }

        override fun getItemViewType(position: Int): Int {
            val item = data[position]
            if (item is FaizOption) {
                return TYPE_OPTION
            }
            return TYPE_LOGO
        }

    }

    private sealed class MenuHolder(
        binding: ViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        var onItemClickCallback: ((Int) -> Unit)? = null

        init {
            itemView.setOnClickListener {
                onItemClick()
            }
        }

        private fun onItemClick() {
            onItemClickCallback?.invoke(adapterPosition)
        }

        class Logo(binding: ItemFaizSmartbrainBinding) : MenuHolder(binding)

        class Option(val binding: ItemFaizOptionBinding) : MenuHolder(binding) {

            fun bind(option: FaizOption) {
                binding.iconView.setImageResource(option.icon)

                if (option.command != 0) {
                    binding.nameIconView.setImageResource(option.command)
                    binding.nameIconView.isVisible = true
                } else {
                    binding.nameIconView.isVisible = false
                }

                if (option.code.isNotEmpty()) {
                    binding.nameView.text = option.code
                    binding.nameView.isVisible = true
                } else {
                    binding.nameView.isVisible = false
                }
            }

        }


    }

}