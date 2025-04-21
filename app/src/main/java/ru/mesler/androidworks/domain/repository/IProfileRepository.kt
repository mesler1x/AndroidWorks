package ru.mesler.androidworks.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.mesler.androidworks.domain.model.Profile

interface IProfileRepository {
    suspend fun getProfile(): Profile?
    suspend fun setProfile(profile: Profile): Profile
    suspend fun observeProfile(): Flow<Profile>
}