package com.example.design.itemodel.card

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.design.R
import com.example.design.helper.BaseEpoxyHolder
import com.example.design.itemodel.TextViewItemModel
import com.example.design.itemodel.textViewItem
import com.example.design.listener.setOnThrottleClickListener
import com.google.android.material.bottomsheet.BottomSheetDialog

@EpoxyModelClass
abstract class CarFiltersModel : EpoxyModelWithHolder<CarFiltersModel.Holder>() {

    @EpoxyAttribute
    var filterItems:List<Pair<String,String>>? = null


    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var listener: CarFiltersModel.Listener? = null

    private var maker:String? = null
    private var model:String? = null
    private var dialog: BottomSheetDialog? = null
    interface Listener {
        fun onItemChanged(maker: String?, model: String?)

    }

    override fun getDefaultLayout(): Int = R.layout.view_filters

    @SuppressLint("SetTextI18n")
    override fun bind(holder: Holder) {
        with(holder) {
            btnMaker.setOnThrottleClickListener {
                context?.let {
                    showBottomSheet(it,  filterItems
                        ?.map { it.first }
                        ?.distinct()) { selectedItem ->
                        btnMaker.text = if (selectedItem.equals("ALL",true))  "Any make" else selectedItem
                        maker = if (selectedItem.equals("ALL",true)) null else selectedItem
                        model = null
                        btnModel.text = "Any model"
                        dialog?.dismiss()
                        listener?.onItemChanged(maker,model)
                    }
                }

            }

            btnModel.setOnThrottleClickListener {

                context?.let {
                    if (maker.isNullOrEmpty()){
                        Toast.makeText(it,"Please select maker first!",Toast.LENGTH_SHORT).show()
                    }
                    else {
                        showBottomSheet(it, filterItems
                            ?.filter { it.first == maker}
                            ?.map { it.second }) { selectedItem ->
                            btnModel.text = if (selectedItem.equals("ALL",true)) "Any model" else selectedItem
                            model = if (selectedItem.equals("ALL",true)) null else selectedItem
                            dialog?.dismiss()
                            listener?.onItemChanged(maker,model)
                        }
                    }
                }

            }
        }

    }

    @SuppressLint("InflateParams")
    private fun showBottomSheet(context: Context, items: List<String>?, selectedCallback: (String) -> Unit) {
        val bottomSheetView =
            LayoutInflater.from(context).inflate(R.layout.bottom_sheet_layout, null)
        dialog = BottomSheetDialog(context)
        dialog?.setContentView(bottomSheetView)
        val recyclerView = bottomSheetView.findViewById<EpoxyRecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.withModels {
            textViewItem {
                id("ALL")
                item("All")
                listener(object : TextViewItemModel.Listener {
                    override fun onItemClicked(item: String) {
                        selectedCallback(item)
                    }

                })
            }
            items?.forEach {
              textViewItem {
                  id(it)
                  item(it)
                  listener(object : TextViewItemModel.Listener {
                      override fun onItemClicked(item: String) {
                          selectedCallback(item)
                      }

                  })
              }
            }
        }
        dialog?.show()
    }
    class Holder : BaseEpoxyHolder() {
        val btnMaker by bind<TextView>(R.id.btn_maker)
        val btnModel by bind<TextView>(R.id.btn_model)
    }

}
