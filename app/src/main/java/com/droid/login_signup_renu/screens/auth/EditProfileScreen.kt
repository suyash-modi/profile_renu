package com.droid.login_signup_renu.screens.auth

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.droid.login_signup_renu.R
import com.droid.login_signup_renu.viewmodel.ProfileViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EditProfileScreen(navController: NavController, viewModel: ProfileViewModel = hiltViewModel()) {
    val profileState by viewModel.profileState.collectAsState()

    var name by remember { mutableStateOf(profileState.name) }
    var contact by remember { mutableStateOf(profileState.contact) }
    var email by remember { mutableStateOf(profileState.email) }
    var password by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf(profileState.dob) }
    var age by remember { mutableStateOf(profileState.age) }
    var domain by remember { mutableStateOf(profileState.domain) }
    var internshipDuration by remember { mutableStateOf(profileState.internshipDuration) }
    var profileImageUrl by remember { mutableStateOf(profileState.profileImageUrl) }

    val availableDomains = listOf("UI/UX Design", "Software Testing", "Android Development",  "Web Development", "Content Creation","Blogging","Photograpy","Video Editing","Modeling","Game Development","Marketing","Banking")

    val internshipData = List(6) { "${it + 1} (Months)" }

    // Date Picker Dialog State
    var showDatePicker by remember { mutableStateOf(false) }


    // Image Picker Launcher
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                // Update profile image with selected URI
                profileImageUrl = uri.toString()
            }
        }
    }

    val onImageClick = {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        launcher.launch(intent)
    }

    // Function to validate the age (must be 16 or older)
    fun validateAge(birthDateMillis: Long?): Boolean {
        birthDateMillis?.let {
            val birthDate = Calendar.getInstance().apply {
                timeInMillis = it
            }
            val today = Calendar.getInstance()
            today.add(Calendar.YEAR, -16)
            return birthDate.before(today)
        }
        return false
    }

    // Show the DatePicker when clicking on the DOB field
    val onDateClick = {
        showDatePicker = true
    }

    // Function to handle the date selection
    val onDateSelected = { selectedDateMillis: Long? ->
        if (validateAge(selectedDateMillis)) {
            dob = SimpleDateFormat("dd/MM/yyyy").format(Date(selectedDateMillis ?: 0))
            val birthDate = Calendar.getInstance().apply {
                timeInMillis = selectedDateMillis ?: 0
            }
            age = Calendar.getInstance().get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)
        } else {
            Toast.makeText(context, "You must be at least 16 years old", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = Color.White)
    ) {
        // Header Bar with Save Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF5F93FB))
                .padding(8.dp)
                .height(55.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Edit Profile",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            IconButton(onClick = {
                val updatedProfile = profileState.copy(
                    name = name,
                    contact = contact,
                    email = email,
                    dob = dob,
                    age = age,
                    domain = domain,
                    internshipDuration = internshipDuration,
                    profileImageUrl = profileImageUrl
                )
                viewModel.updateProfile(updatedProfile)
                navController.popBackStack()
            }) {
                Icon(Icons.Filled.Check, contentDescription = "Save", tint = Color.White)
            }
        }



        Spacer(modifier = Modifier.height(16.dp))

        // Profile Photo Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.Center) {
                AsyncImage(
                    model = profileImageUrl,
                    contentDescription = "Profile Photo",
                    error = painterResource(R.drawable.test_profile),
                    placeholder = painterResource(R.drawable.test_profile),
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .clickable { onImageClick() }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Edit Profile Fields in Cards
        EditProfileField(value = name, label = "Name", onValueChange = { name = it })
        EditProfileField(value = contact, label = "Contact", onValueChange = { contact = it })
        EditProfileField(value = email, label = "Email", onValueChange = { email = it })
        EditProfileField(value = password, label = "Password", onValueChange = { password = it })

        // Date of Birth Section
        Column(modifier = Modifier.padding(8.dp)) {
           // Text(text = "Date of Birth", style = MaterialTheme.typography.bodyLarge)
           // Spacer(modifier = Modifier.height(8.dp))
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().clickable { onDateClick() },
                border = BorderStroke(1.dp, Color.Black),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = dob.ifEmpty { "Select DOB" },
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f).padding(16.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Select Date",
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }

        // Domain Dropdown
        DropdownField(value = domain, label = "Domain", options = availableDomains, onValueChange = { domain = it })

        // Internship Duration Dropdown
        DropdownField(value = internshipDuration, label = "Internship Duration", options = internshipData, onValueChange = { internshipDuration = it })
    }

    // DatePicker Modal
    if (showDatePicker) {
        DatePickerModal(
            onDateSelected = { selectedDateMillis ->
                onDateSelected(selectedDateMillis)
            },
            onDismiss = {
                showDatePicker = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}




@Composable
fun DropdownField(value: String, label: String, options: List<String>, onValueChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 10.dp),
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (value.isEmpty()) label else value,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f).padding(16.dp)
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "More options",
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onValueChange(option)
                        expanded = false
                    }
                )
            }
        }
    }
}


@Composable
fun EditProfileField(value: String, label: String, onValueChange: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 10.dp),
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.White),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                disabledIndicatorColor = Color.White,
                focusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                errorContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
            )
        )
    }
}


