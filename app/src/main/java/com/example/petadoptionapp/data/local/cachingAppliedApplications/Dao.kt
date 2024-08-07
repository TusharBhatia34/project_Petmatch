package com.example.petadoptionapp.data.local.cachingAppliedApplications

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AppliedApplicationsDao {

    @Insert
    fun insertAppliedApplication(entity: Entity)

    @Query("SELECT * FROM AppliedApplications")
    fun getAllAppliedApplications(): Flow<List<Entity>>

    @Query("SELECT (SELECT COUNT(*) FROM AppliedApplications) == 0")
    fun isEmpty(): Boolean



}