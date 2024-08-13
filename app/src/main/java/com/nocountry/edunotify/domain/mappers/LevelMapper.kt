package com.nocountry.edunotify.domain.mappers

import com.nocountry.edunotify.data.api.model.schools.Level
import com.nocountry.edunotify.domain.model.LevelDomain

class LevelMapper(private val yearMapper: YearMapper) {

    fun mapLevelToLevelDomain(level: Level): LevelDomain {
        return LevelDomain(
            schoolLevel = level.schoolLevel,
            years = level.years.map { year ->
                yearMapper.mapYearToYearDomain(year)
            }
        )
    }
}