package com.punnales.moviesapp.data.local.room.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.punnales.moviesapp.core.domain.ResourceRouteSizes

@Entity(tableName = "resource_route")
data class ResourceRouteDTO(

    @PrimaryKey
    @ColumnInfo(name = "code")
    val code: String,

    @ColumnInfo(name = "sizes")
    val resourceRouteSizes: ResourceRouteSizes,
)