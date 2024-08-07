package com.example.petadoptionapp.data.local.cachingAppliedApplications

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Entity::class],
    version = 1)
abstract class Database: RoomDatabase() {

    abstract val appliedApplicationsDao:AppliedApplicationsDao
}