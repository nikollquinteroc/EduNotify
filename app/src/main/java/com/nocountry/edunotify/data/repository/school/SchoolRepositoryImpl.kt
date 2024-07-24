package com.nocountry.edunotify.data.repository.school

import com.nocountry.edunotify.data.api.RetrofitService
import com.nocountry.edunotify.domain.mappers.SchoolMapper
import com.nocountry.edunotify.domain.model.SchoolDomain
import com.nocountry.edunotify.domain.repositories.SchoolRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SchoolRepositoryImpl(
    private val service: RetrofitService,
    private val schoolMapper: SchoolMapper
) : SchoolRepository {

    private val fakeSchoolListResponse = listOf(
        SchoolDomain(id = 1, name = "Colegio Bilingüe Los Olivos"),
        SchoolDomain(id = 2, name = "Colegio Pucará"),
        SchoolDomain(id = 3, name = "Colegio Bilingüe Diana Oeste"),
        SchoolDomain(id = 4, name = "Colegio Nuevo Cambridge"),
    )

    override suspend fun getAllSchools(): Flow<List<SchoolDomain>> {
        val schoolListResponse = service.getAllSchools()
        val schoolListDomain = schoolListResponse.map { schoolResponse ->
            schoolMapper.mapSchoolResponseToSchoolDomain(schoolResponse)
        }
        return flowOf(schoolListDomain)

        //return flowOf(fakeSchoolListResponse)
    }
}