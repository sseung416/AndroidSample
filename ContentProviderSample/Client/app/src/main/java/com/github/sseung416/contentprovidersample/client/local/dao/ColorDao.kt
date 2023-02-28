package com.github.sseung416.contentprovidersample.client.local.dao

import androidx.room.Query
import com.github.sseung416.contentprovidersample.client.local.dto.Color
import kotlinx.coroutines.flow.Flow

interface ColorDao : BaseDao<Color> {

    @Query("SELECT * FROM color")
    fun getColors(): Flow<Color>
}