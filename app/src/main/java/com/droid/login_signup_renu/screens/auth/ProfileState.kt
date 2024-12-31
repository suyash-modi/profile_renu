package com.droid.login_signup_renu.screens.profile

data class ProfileState(
    val name: String = "",
    val title: String = "",
    val location: String = "",
    val about: String = "",
    val skills: List<String> = emptyList(),
    val education: String = "",
    val profileImageUrl: String = "",
    val contact: String = "",
    val email: String = "",
    val dob: String = "",
    val age: Int = 0,
    val domain: String = "",
    val internshipDuration: String = "",
    val isAboutEditable: Boolean = false,
    val isSkillsEditable: Boolean = false,
    val isEducationEditable: Boolean = false
)
