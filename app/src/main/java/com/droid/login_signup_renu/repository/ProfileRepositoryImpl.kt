package com.droid.login_signup_renu.repository

import com.droid.login_signup_renu.screens.profile.ProfileState

class ProfileRepositoryImpl : ProfileRepository {

    override suspend fun getProfile(): ProfileState {
        // Simulate fetching profile data
        return ProfileState(
            name = "John Doe",
            title = "UI/UX Designer",
            location = "Gurgaon, Haryana",
            about = "Passionate about creating user-friendly designs.",
            skills = listOf("UI/UX Design", "Prototyping", "Wireframing"),
            education = "XYZ University\nComputer Science\n2021 - 2025",
            profileImageUrl = "",
            contact = "7493654893",
            email = "example@email.com",
            dob = "23/11/2003",
            domain = "Android Development Intern",
            internshipDuration = "1 (Months)",
        )
    }
    //https://via.placeholder.com/150

    override suspend fun updateProfile(profile: ProfileState) {
        // Simulate updating profile data (e.g., save to a database or network)
        // Here you would typically interact with an API or a local database to persist changes
    }
}
