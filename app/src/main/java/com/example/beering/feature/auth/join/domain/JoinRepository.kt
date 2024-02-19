package com.example.beering.feature.auth.join.domain

interface JoinRepository {
    suspend fun checkId(id : String) : Boolean
}