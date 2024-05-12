package com.example.androiddemo.ui.main

import android.graphics.Bitmap
import androidx.activity.viewModels
import com.example.androiddemo.databinding.ActivityMainBinding
import com.example.common.ext.loadFromAssetsToBitmap
import com.example.common.ext.viewBinding
import com.example.common.helper.UiState
import com.example.design.base.BaseActivity
import com.example.design.itemodel.SeparatorModel
import com.example.design.itemodel.card.BannerItemModel
import com.example.design.itemodel.card.CarFiltersModel
import com.example.design.itemodel.card.CarItemModel
import com.example.design.itemodel.card.CarLoadingItemModel
import com.example.design.itemodel.card.bannerItem
import com.example.design.itemodel.card.carFilters
import com.example.design.itemodel.card.carItem
import com.example.design.itemodel.card.carLoadingItem
import com.example.design.itemodel.separator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val binding by viewBinding<ActivityMainBinding>()
    private val viewModel by viewModels<MainViewModel>()

    private var selectedCarItem: String? = null
    private val bitmapMap:HashMap<String, Bitmap> = HashMap<String, Bitmap>()
    override fun onViewDidLoad() {
        viewModel.loadCars()
    }

    override fun onViewConfiguration() {
        setContentView(binding.root)
        this@MainActivity.loadFromAssetsToBitmap("Tacoma.jpg")?.let {
            bitmapMap["Tacoma.jpg"] = it
        }
        binding.recyclerView.withModels {

            bannerItem {
                id(BannerItemModel::class.simpleName)
                title("Tacoma 2011")
                description("Get your\'s now")
                bitmap(bitmapMap["Tacoma.jpg"])
            }


            carFilters {
                id(CarFiltersModel::class.simpleName)
                val items = ArrayList<Pair<String,String>>()
                viewModel.cars
                    .filter { !it.make.isNullOrEmpty() }
                    .filter { !it.model.isNullOrEmpty() }
                    .forEach {
                        items.add(Pair(it.make!!,it.model!!))
                }
                filterItems(items)
                listener(object : CarFiltersModel.Listener {
                    override fun onItemChanged(maker: String?, model: String?) {
                        viewModel.filterCars(maker,model)
                    }

                })
            }


            when (viewModel.carData.value) {
                is UiState.Loading -> {
                    carLoadingItem {
                        id(CarLoadingItemModel::class.simpleName)
                    }
                }
                is UiState.Success -> {
                    viewModel.filteredCards.forEachIndexed { index, item ->
                        carItem {
                            val filePath = """${item.make} ${item.model}.jpg""".replace(" ", "_")

                            if (bitmapMap[filePath] == null) {
                                this@MainActivity.loadFromAssetsToBitmap(filePath)?.let {
                                    bitmapMap[filePath] = it
                                }
                            }

                            id(CarItemModel::class.simpleName + "$index")
                            carItem(
                                CarItemModel.CarItem(
                                    bitmapMap[filePath],
                                    """${item.make} ${item.model}""",
                                    "Price: ${(item.marketPrice?.div(1000.0))?.toInt()}K",
                                    item.rating ?: 0,
                                    item.prosList,
                                    item.consList
                                )
                            )
                            expanded((index == 0 && selectedCarItem == null) || selectedCarItem == """${item.make} ${item.model}""")
                            listener(object : CarItemModel.Listener {
                                override fun onItemClicked(carItem: CarItemModel.CarItem) {
                                    if (selectedCarItem == carItem.maker) {
                                        selectedCarItem = ""
                                    } else {
                                        selectedCarItem = carItem.maker
                                    }
                                    binding.recyclerView.requestModelBuild()
                                }
                            })
                        }

                        if (index != viewModel.filteredCards.size.minus(1)) {
                            separator {
                                id(SeparatorModel::class.simpleName + "$index")
                            }
                        }
                    }
                }
                else -> {
                    //do nothing
                }


            }


        }

    }

    override fun onInitializeIntentData() {
        //do nothing
    }

    override fun onObserveState() {
        viewModel.carData.observe(this) {
            binding.recyclerView.requestModelBuild()
        }
    }


}