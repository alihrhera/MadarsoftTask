package com.madarsoft.task.presentation.adduser.compos

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madarsoft.task.R
import com.madarsoft.task.presentation.adduser.AddUserViewModel
import com.madarsoft.task.presentation.ui.theme.Pink40
import androidx.compose.ui.platform.LocalContext

@Composable
fun AddUserScreen(
    viewModel: AddUserViewModel,
    onMoveToUserList: () -> Unit
) {
    val uiState by viewModel.state.collectAsState()
    val toastMessage by viewModel.toastMessage.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    AddUserScreenContent(
        state = uiState,
        onNameChanged = viewModel::onNameChanged,
        onAgeChanged = viewModel::onAgeChanged,
        onJobTitleChanged = viewModel::onJobTitleChanged,
        onGenderChanged = viewModel::onGenderChanged,
        onSubmit = viewModel::submit,
        onMoveToUserList = onMoveToUserList,
    )
}


@Composable
fun AddUserScreenContent(
    state: AddUserUiState,
    onNameChanged: (String) -> Unit,
    onAgeChanged: (String) -> Unit,
    onJobTitleChanged: (String) -> Unit,
    onSubmit: () -> Unit,
    onMoveToUserList: () -> Unit,
    onGenderChanged: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = state.user.name,
            onValueChange = onNameChanged,
            label = { Text(stringResource(R.string.user_name)) },
            isError = state.errors.containsKey("name"),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        )
        if (state.errors["name"] != null) {
            Text(state.errors["name"] ?: "", color = Color.Red)
        }

        OutlinedTextField(
            value = state.user.age.toString().takeIf { it != "0" } ?: "",
            onValueChange = onAgeChanged,
            label = { Text(stringResource(R.string.age)) },
            isError = state.errors.containsKey("age"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        )
        if (state.errors["age"] != null) {
            Text(state.errors["age"] ?: "", color = Color.Red)
        }

        OutlinedTextField(
            value = state.user.jobTitle,
            onValueChange = onJobTitleChanged,
            label = { Text(stringResource(R.string.jobTitle)) },
            isError = state.errors.containsKey("jobTitle"),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        )
        if (state.errors["jobTitle"] != null) {
            Text(state.errors["jobTitle"] ?: "", color = Color.Red)
        }

        Text(
            stringResource(R.string.gender),
            modifier = Modifier
                .padding(vertical = 12.dp)
                .align(Alignment.Start)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            GenderRadioButton("male", "Male", state.user.gender, onGenderChanged)
            GenderRadioButton("female", "Female", state.user.gender, onGenderChanged)
        }
        if (state.errors["gender"] != null) {
            Text(state.errors["gender"] ?: "", color = Color.Red)
        }

        Button(
            onClick = onSubmit,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            enabled = !state.isLoading
        ) {
            if (state.isLoading)
                CircularProgressIndicator()
            else
                Text(stringResource(R.string.add_user))
        }

        Button(
            onClick = onMoveToUserList,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Pink40)
        ) {
            Text(stringResource(R.string.registered_users))
        }
    }
}

@Composable
fun GenderRadioButton(value: String, label: String, selectedGender: String, onGenderChanged: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selectedGender == value,
            onClick = { onGenderChanged(value) }
        )
        Text(text = label)
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun AddUserScreenPreview() {
    AddUserScreenContent(
        state = AddUserUiState(),
        onNameChanged = {},
        onAgeChanged = {},
        onJobTitleChanged = {},
        onGenderChanged = {},
        onSubmit = {},
        onMoveToUserList = {}
    )
}
