package com.nocountry.edunotify.domain.repositories

import com.nocountry.edunotify.data.api.model.schools.SchoolResponse
import com.nocountry.edunotify.domain.model.SchoolDomain
import com.nocountry.edunotify.domain.model.SchoolInfoDomain
import kotlinx.coroutines.flow.Flow

interface SchoolRepository {

    suspend fun getAllSchools(): Flow<List<SchoolDomain>>

    suspend fun getSchoolInfo(schoolId: Int): Flow<SchoolInfoDomain>

}