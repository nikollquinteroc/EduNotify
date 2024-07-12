package com.nocountry.edunotify.domain.mappers

import com.nocountry.edunotify.data.api.model.schools.SchoolResponse
import com.nocountry.edunotify.domain.model.SchoolDomain

class SchoolMapper {
    fun mapSchoolResponseToSchoolDomain(schoolResponse: SchoolResponse): SchoolDomain {
        return SchoolDomain(id = schoolResponse.id, name = schoolResponse.name)
    }
}