package com.example.petadoptionapp.domain.usecases.application

import com.example.petadoptionapp.data.local.cachingAppliedApplications.AppliedApplicationsDao
import com.example.petadoptionapp.data.local.cachingAppliedApplications.Entity
import javax.inject.Inject

class AddAppliedApplicationLocallyUseCase @Inject constructor(
    private val dao: AppliedApplicationsDao
) {
     fun invoke(entity: Entity) = dao.insertAppliedApplication(entity)
}