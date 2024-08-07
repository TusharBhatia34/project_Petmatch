package com.example.petadoptionapp.data.local.cachingAppliedApplications

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AppliedApplications")
data class Entity(

    val uniqueIdentifier:String, //it consists of authorId_applicantId_timestamp of post

    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
)
