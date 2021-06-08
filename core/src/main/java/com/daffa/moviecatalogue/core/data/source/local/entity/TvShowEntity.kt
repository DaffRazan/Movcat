package com.daffa.moviecatalogue.core.data.source.local.entity

import androidx.annotation.Keep
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_show_entities")
data class TvShowEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "backdrop_path")
    var backdrop_path: String,

    @ColumnInfo(name = "genres")
    var genres: String,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "poster_path")
    var poster_path: String,

    @ColumnInfo(name = "release_date")
    var release_date: String,

    @ColumnInfo(name = "runtime")
    var runtime: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "vote_average")
    var vote_average: Float,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)