package com.droid.login_signup_renu.repository

import com.droid.login_signup_renu.screens.profile.ProfileState

interface ProfileRepository {
    suspend fun getProfile(): ProfileState
    suspend fun updateProfile(profile: ProfileState)
}
