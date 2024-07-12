package com.nocountry.edunotify.domain.mappers

import com.nocountry.edunotify.data.api.model.schools.SchoolInfoResponse
import com.nocountry.edunotify.domain.model.SchoolInfoDomain

class SchoolInfoMapper(private val levelMapper: LevelMapper) {
    fun mapSchoolInfoResponseToSchoolInfoDomain(schoolInfo: SchoolInfoResponse): SchoolInfoDomain {
        return SchoolInfoDomain(levels = schoolInfo.levels.map { level ->
            levelMapper.mapLevelToLevelDomain(level)
        }, name = schoolInfo.name)
    }
}