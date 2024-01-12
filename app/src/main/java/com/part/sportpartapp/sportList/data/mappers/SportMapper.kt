package com.part.sportpartapp.sportList.data.mappers

import com.part.sportpartapp.sportList.data.local.sport.SportEntity
import com.part.sportpartapp.sportList.data.remote.respond.SportListDto
import com.part.sportpartapp.sportList.domain.model.Sport

fun SportListDto.toSportEntity(): SportEntity {
    return SportEntity(
        id = id,
        image = image,
        is_free = is_free,
        description = description,
        order = order,
        recommended = recommended,
        title = title,
        type = type,
        bookmark = bookmark

    )
}

fun SportEntity.toSport(): Sport {
    return Sport(
        id = id,
        image = image,
        is_free = is_free,
        description = description,
        order = order,
        recommended = recommended,
        title = title,
        type = type,
        bookmark = bookmark
    )
}