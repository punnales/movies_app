package com.punnales.moviesapp.data.local.room.mappers

import androidx.room.TypeConverter
import com.punnales.moviesapp.core.domain.Media
import com.punnales.moviesapp.core.domain.ResourceRouteSizes
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class RoomTypeConverter {

    private val mediaType = Types.newParameterizedType(List::class.java, Media::class.java)
    private val mediaAdapter = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build().adapter<List<Media>>(mediaType)

    private val resourceRouteAdapter = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build().adapter(ResourceRouteSizes::class.java)

    @TypeConverter
    fun mediaListToJson(mediaList: List<Media>) =
        mediaAdapter.toJson(mediaList)

    @TypeConverter
    fun mediaListFromJson(mediaList: String) =
        mediaAdapter.fromJson(mediaList)

    @TypeConverter
    fun resourceRouteSizesToJson(resourceRouteSizes: ResourceRouteSizes) =
        resourceRouteAdapter.toJson(resourceRouteSizes)

    @TypeConverter
    fun resourceRouteSizesFromJson(resourceRouteSizes: String) =
        resourceRouteAdapter.fromJson(resourceRouteSizes)


}