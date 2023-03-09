package com.github.sseung416.contentprovidersample.client.local.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Query
import com.github.sseung416.contentprovidersample.client.local.dto.Color

@Dao
interface ColorDao : BaseDao<Color> {

    @Query("SELECT * FROM color")
    fun getColors(): Cursor
}