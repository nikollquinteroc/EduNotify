package com.nocountry.edunotify.data.repository.school

import com.nocountry.edunotify.data.api.RetrofitService
import com.nocountry.edunotify.domain.mappers.SchoolInfoMapper
import com.nocountry.edunotify.domain.mappers.SchoolMapper
import com.nocountry.edunotify.domain.model.SchoolDomain
import com.nocountry.edunotify.domain.model.SchoolInfoDomain
import com.nocountry.edunotify.domain.repositories.SchoolRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SchoolRepositoryImpl(
    private val service: RetrofitService,
    private val schoolMapper: SchoolMapper,
    private val schoolInfoMapper: SchoolInfoMapper
) : SchoolRepository {

    override suspend fun getAllSchools(): Flow<List<SchoolDomain>> {
        val schoolListResponse = service.getAllSchools()
        val schoolListDomain = schoolListResponse.map { schoolResponse ->
            schoolMapper.mapSchoolResponseToSchoolDomain(schoolResponse)
        }
        return flowOf(schoolListDomain)
    }

    override suspend fun getSchoolInfo(schoolId: Int): Flow<SchoolInfoDomain> {
        val schoolInfoResponse = service.getSchoolInfo(schoolId)
        println(schoolInfoResponse)
        val schoolInfoDomain =
            schoolInfoMapper.mapSchoolInfoResponseToSchoolInfoDomain(schoolInfoResponse)
        return flowOf(schoolInfoDomain)
    }
}