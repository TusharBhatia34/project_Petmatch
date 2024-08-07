package com.example.petadoptionapp.domain.usecases.application

import com.example.petadoptionapp.data.local.cachingAppliedApplications.AppliedApplicationsDao
import javax.inject.Inject

class DatabaseIsEmptyUseCase @Inject constructor(
    private val appliedApplicationsDao: AppliedApplicationsDao
) {

    fun invoke():Boolean = appliedApplicationsDao.isEmpty()
}