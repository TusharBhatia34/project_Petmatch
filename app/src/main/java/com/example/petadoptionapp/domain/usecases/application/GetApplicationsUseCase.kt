package com.example.petadoptionapp.domain.usecases.application

import com.example.petadoptionapp.data.common.Response
import com.example.petadoptionapp.domain.model.Applications
import com.example.petadoptionapp.domain.repo.ApplicationRepo
import javax.inject.Inject

class GetApplicationsUseCase @Inject constructor(
    private val applicationRepo: ApplicationRepo
) {

    suspend  fun invoke(): Pair<List<Applications>, Response<Boolean>> = applicationRepo.getApplications()

}