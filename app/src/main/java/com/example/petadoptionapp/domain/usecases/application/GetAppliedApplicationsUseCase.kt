package com.example.petadoptionapp.domain.usecases.application

import com.example.petadoptionapp.data.local.cachingAppliedApplications.AppliedApplicationsDao
import com.example.petadoptionapp.data.local.cachingAppliedApplications.Entity
import com.example.petadoptionapp.domain.repo.ApplicationRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAppliedApplicationsUseCase @Inject constructor(
    private val applicationsRepo: ApplicationRepo,
    private val dao: AppliedApplicationsDao
) {
    suspend  fun invokeToGetAppliedApplicationsRemotely(applicantId:String) = applicationsRepo.getAppliedApplications(applicantId)

     fun invokeToGetAppliedApplicationsLocally(): Flow<List<Entity>> = dao.getAllAppliedApplications()


}