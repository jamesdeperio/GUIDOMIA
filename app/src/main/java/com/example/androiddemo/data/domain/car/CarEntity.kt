package com.example.androiddemo.data.domain.car

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class CarEntity(
    @Id var id: Long = 0,
    var consList: List<String>? =null,
    var customerPrice: Double? =null,
    var make: String? =null,
    var marketPrice: Double? =null,
    var model: String? =null,
    var prosList: List<String>? =null,
    var rating: Int? =null
)