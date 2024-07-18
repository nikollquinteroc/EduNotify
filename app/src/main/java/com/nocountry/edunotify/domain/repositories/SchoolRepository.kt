package com.nocountry.edunotify.domain.repositories

import com.nocountry.edunotify.domain.model.SchoolDomain
import kotlinx.coroutines.flow.Flow

interface SchoolRepository {

    suspend fun getAllSchools(): Flow<List<SchoolDomain>>
}