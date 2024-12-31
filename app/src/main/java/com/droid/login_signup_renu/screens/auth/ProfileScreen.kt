package com.droid.login_signup_renu.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.droid.login_signup_renu.R
import com.droid.login_signup_renu.screens.profile.ProfileState
import com.droid.login_signup_renu.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(navController: NavController) {
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val profileState by profileViewModel.profileState.collectAsState()

    if (profileState.name.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading profile...", style = MaterialTheme.typography.bodyLarge)
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
           // .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            // Header with background color and back button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .background(Color(0xFF5F93FB)) // Set the background color
                    .padding(vertical = 8.dp) // Padding for the header
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp), // Padding for the content inside the header
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    // Back Button
                    IconButton(
                        onClick = { navController.popBackStack() }, // Navigate back
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White // White color for the back icon
                        )
                    }

                    // Profile Title Text
                    Text(
                        text = "My Profile",
                       // fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        style = MaterialTheme.typography.titleLarge.copy(color = Color.White),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1f) // Make sure the text takes available space
                            .padding(start = 16.dp)
                    )
                }
            }}

        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                // Background Image
                Image(
                    painter = painterResource(id = R.drawable.bg1),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                        //.clip(RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)),
                    contentScale = ContentScale.Crop
                )

                // Profile Image
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 115.dp) // Adjust for top half inside the background
                ) {
                    AsyncImage(
                        model = profileState.profileImageUrl,
                        contentDescription = "Profile Image",
                        error = painterResource(R.drawable.test_profile),
                        placeholder = painterResource(R.drawable.test_profile),
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .border(6.dp, Color.White, CircleShape), // Optional border for separation
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(70.dp))

            // User Details
            Text(
                text = profileState.name,
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = profileState.title,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(13.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "Location Icon",
                    tint = Color.Black,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = profileState.location,
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start
                )
            }

            Spacer(modifier = Modifier.height(13.dp))

            // Edit Profile Button
            Button(
                onClick = { navController.navigate("editProfile") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F93FB)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.padding(vertical = 16.dp),
            ) {
                Text("Edit Profile")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // About Section
        item {
            ProfileSection(
                title = "About",
                content = profileState.about,
                isEditable = profileState.isAboutEditable,
                onContentChange = { profileViewModel.updateAbout(it) },
                onEditClick = { profileViewModel.toggleAboutEdit() }
            )
           // Spacer(modifier = Modifier.height(16.dp))
        }

        // Skills Section
        item {
            ProfileSection(
                title = "Top Skills",
                content = profileState.skills.joinToString(", "),
                isEditable = profileState.isSkillsEditable,
                onContentChange = { profileViewModel.updateSkills(it) },
                onEditClick = { profileViewModel.toggleSkillsEdit() }
            )
           // Spacer(modifier = Modifier.height(16.dp))
        }

        // Education Section
        item {
            ProfileSection(
                title = "Education",
                content = profileState.education,
                isEditable = profileState.isEducationEditable,
                onContentChange = { profileViewModel.updateEducation(it) },
                onEditClick = { profileViewModel.toggleEducationEdit() }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Composable
fun ProfileSection(
    title: String,
    content: String,
    isEditable: Boolean,
    onContentChange: (String) -> Unit,
    onEditClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F1F1)),
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                // Edit Icon
                IconButton(onClick = onEditClick) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (isEditable) {
                TextField(
                    value = content,
                    onValueChange = onContentChange,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = false,
                    maxLines = 5,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF1F1F1),  // Setting the text field background when it is unfocused or in initial state
                        unfocusedContainerColor = Color(0xFFF1F1F1), // Setting the text field background when it is disabled
                        disabledContainerColor = Color(0xFFF1F1F1), // Grey when disabled
                        focusedIndicatorColor =  Color(0xFFF1F1F1),
                        unfocusedIndicatorColor =  Color(0xFFF1F1F1),
                        disabledIndicatorColor =  Color(0xFFF1F1F1),
                    )
                )
            } else {
                Text(text = content)
            }
        }
    }
}



@Preview(showBackground = true, device = "spec:width=411dp,height=891dp", name = "Full Screen Preview")
@Composable
fun ProfileScreenPreview() {
    // Mock state for preview
    val profileState = ProfileState(
        name = "John Doe",
        title = "Software Developer",
        location = "San Francisco, CA",
        profileImageUrl = "",
        about = "Passionate developer with 5 years of experience.",
        skills = listOf("Kotlin", "Compose", "Android"),
        education = "Bachelor's in Computer Science",
        isAboutEditable = false,
        isSkillsEditable = false,
        isEducationEditable = false
    )
    ProfileScreenPreviewContent(profileState)
}

@Composable
fun ProfileScreenPreviewContent(profileState: ProfileState) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Add mocked content similar to ProfileScreen
    }
}
