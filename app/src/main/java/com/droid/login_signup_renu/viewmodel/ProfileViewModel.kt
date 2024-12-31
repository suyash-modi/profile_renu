package com.droid.login_signup_renu.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droid.login_signup_renu.repository.ProfileRepository
import com.droid.login_signup_renu.screens.profile.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState: StateFlow<ProfileState> = _profileState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchProfile()
    }

    private fun fetchProfile() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val profile = profileRepository.getProfile()
                _profileState.value = profile
            } catch (e: Exception) {
                _error.value = "Failed to load profile"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Toggle edit states for About, Skills, and Education
    fun toggleAboutEdit() {
        _profileState.value = _profileState.value.copy(isAboutEditable = !_profileState.value.isAboutEditable)
    }

    fun toggleSkillsEdit() {
        _profileState.value = _profileState.value.copy(isSkillsEditable = !_profileState.value.isSkillsEditable)
    }

    fun toggleEducationEdit() {
        _profileState.value = _profileState.value.copy(isEducationEditable = !_profileState.value.isEducationEditable)
    }

    // Update profile fields and save
    fun updateAbout(updatedAbout: String) {
        _profileState.value = _profileState.value.copy(about = updatedAbout)
        saveProfile()
    }

    fun updateSkills(updatedSkills: String) {
        _profileState.value = _profileState.value.copy(skills = updatedSkills.split(",").map { it.trim() })
        saveProfile()
    }

    fun updateEducation(updatedEducation: String) {
        _profileState.value = _profileState.value.copy(education = updatedEducation)
        saveProfile()
    }

    fun updateProfile(updatedProfile: ProfileState) {
        _profileState.value = updatedProfile
        saveProfile()
    }

    // Save the profile to repository or database
    private fun saveProfile() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                profileRepository.updateProfile(_profileState.value)
            } catch (e: Exception) {
                _error.value = "Failed to save profile"
            } finally {
                _isLoading.value = false
            }
        }
    }
}

