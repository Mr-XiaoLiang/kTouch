package com.lollipop.ktouch.faiz

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import androidx.wear.widget.WearableLinearLayoutManager
import com.lollipop.ktouch.R
import com.lollipop.ktouch.base.SubPager
import com.lollipop.ktouch.databinding.FragmentFaizMenuBinding
import com.lollipop.ktouch.databinding.ItemFaizOptionBinding
import com.lollipop.ktouch.databinding.ItemFaizSmartbrainBinding
import com.lollipop.resource.FaizOption

sealed class FaizMenuSubPage : SubPager() {

    private var binding: FragmentFaizMenuBinding? = null

    private val dataList = ArrayList<Any>()
    private val optionAdapter by lazy {
        MenuAdapter(dataList, ::onOptionClick)
    }

    protected abstract val riderIconId: Int
    protected abstract val riderBackgroundId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val newBinding = FragmentFaizMenuBinding.inflate(inflater, container, false)
        binding = newBinding
        return newBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let { b ->
            b.recyclerView.isEdgeItemsCenteringEnabled = true
            initOption()
            b.recyclerView.adapter = optionAdapter
            b.recyclerView.layoutManager = WearableLinearLayoutManager(view.context)
            LinearSnapHelper().attachToRecyclerView(b.recyclerView)
            b.riderIconView.setImageResource(riderIconId)
            b.backgroundView.setBackgroundResource(riderBackgroundId)
        }
    }

    private fun initOption() {
        dataList.add(SmartBrainLogo)
        dataList.addAll(getOptionList())
    }

    protected abstract fun getOptionList(): List<FaizOption>

    private fun onOptionClick(option: FaizOption) {
        // TODO
    }

    class Faiz : FaizMenuSubPage() {

        private val optionDataList by lazy {
            listOf(
                FaizOption.FaizHeisei,
                FaizOption.PhoneBlasterSingleMode,
                FaizOption.PhoneBlasterBurstMode,
                FaizOption.PhoneBlasterCharge,
                FaizOption.JetSligerComeCloser,
                FaizOption.JetSligerGetIntoAction,
                FaizOption.JetSligerTakeOff,
                FaizOption.FaizAxelMode,
                FaizOption.FaizPointer,
                FaizOption.FaizBlasterCrimsonSmash,
                FaizOption.FaizShot,
                FaizOption.FaizBlasterGrandImpact,
                FaizOption.FaizEdge,
                FaizOption.FaizEdgeExceed,
                FaizOption.FaizBlasterTakeOff,
                FaizOption.FaizBlasterDischarge,
                FaizOption.FaizBlasterBladeMode,
                FaizOption.FaizBlasterBlasterMode,
                FaizOption.FaizAutoVajinGetIntoAction,
                FaizOption.FaizAutoVajinComeCloser,
                FaizOption.FaizAutoVajinTakeOff,
                FaizOption.FaizAutoVajinBattleMode,
                FaizOption.FaizAutoVajinVehicleMode
            )
        }

        override val riderIconId: Int = com.lollipop.resource.R.drawable.ic_faiz

        override val riderBackgroundId: Int = R.drawable.bg_faiz_page

        override fun getOptionList(): List<FaizOption> {
            return optionDataList
        }

    }

    class Kaixa : FaizMenuSubPage() {

        override val riderIconId: Int = com.lollipop.resource.R.drawable.ic_kaixa

        override val riderBackgroundId: Int = R.drawable.bg_kaixa_page

        private val optionDataList by lazy {
            listOf(
                FaizOption.KaixaHeisei,
                FaizOption.PhoneBlasterSingleMode,
                FaizOption.PhoneBlasterBurstMode,
                FaizOption.PhoneBlasterCharge,
                FaizOption.JetSligerComeCloser,
                FaizOption.JetSligerGetIntoAction,
                FaizOption.JetSligerTakeOff,
            )
        }

        override fun getOptionList(): List<FaizOption> {
            return optionDataList
        }

    }

    class Delta : FaizMenuSubPage() {

        override val riderIconId: Int = com.lollipop.resource.R.drawable.ic_delta

        override val riderBackgroundId: Int = R.drawable.bg_delta_page

        private val optionDataList by lazy {
            listOf(
                FaizOption.DeltaHeisei,
                FaizOption.PhoneBlasterSingleMode,
                FaizOption.PhoneBlasterBurstMode,
                FaizOption.PhoneBlasterCharge,
                FaizOption.JetSligerComeCloser,
                FaizOption.JetSligerGetIntoAction,
                FaizOption.JetSligerTakeOff,
            )
        }

        override fun getOptionList(): List<FaizOption> {
            return optionDataList
        }

    }

    class Psyga : FaizMenuSubPage() {

        override val riderIconId: Int = com.lollipop.resource.R.drawable.ic_psyga

        override val riderBackgroundId: Int = R.drawable.bg_psyga_page

        private val optionDataList by lazy {
            listOf(
                FaizOption.PsygaHeisei,
                FaizOption.PhoneBlasterSingleMode,
                FaizOption.PhoneBlasterBurstMode,
                FaizOption.PhoneBlasterCharge,
                FaizOption.JetSligerComeCloser,
                FaizOption.JetSligerGetIntoAction,
                FaizOption.JetSligerTakeOff,
            )
        }

        override fun getOptionList(): List<FaizOption> {
            return optionDataList
        }

    }

    class Orga : FaizMenuSubPage() {

        override val riderIconId: Int = com.lollipop.resource.R.drawable.ic_orga

        override val riderBackgroundId: Int = R.drawable.bg_orga_page

        private val optionDataList by lazy {
            listOf(
                FaizOption.OrgaHeisei,
                FaizOption.PhoneBlasterSingleMode,
                FaizOption.PhoneBlasterBurstMode,
                FaizOption.PhoneBlasterCharge,
                FaizOption.JetSligerComeCloser,
                FaizOption.JetSligerGetIntoAction,
                FaizOption.JetSligerTakeOff,
            )
        }

        override fun getOptionList(): List<FaizOption> {
            return optionDataList
        }

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

            @SuppressLint("SetTextI18n")
            fun bind(option: FaizOption) {
                binding.iconView.setImageResource(option.icon)

                if (option.command != 0) {
                    binding.nameIconView.setImageResource(option.command)
                    binding.nameIconView.isVisible = true
                } else {
                    binding.nameIconView.isVisible = false
                }

                if (option.code.isNotEmpty()) {
                    binding.nameView.text = option.code + " "
                    binding.nameView.isVisible = true
                } else {
                    binding.nameView.isVisible = false
                }
            }

        }


    }

}