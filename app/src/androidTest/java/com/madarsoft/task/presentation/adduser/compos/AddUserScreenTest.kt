package com.madarsoft.task.presentation.adduser.compos

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.madarsoft.task.data.local.users.UserEntity
import com.madarsoft.task.presentation.adduser.AddUserViewModel
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddUserScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    val mockViewModel = mockk<AddUserViewModel>(relaxed = true)
    val mockState = AddUserUiState(
        user = UserEntity.emptyUser(),
        errors = mapOf("name" to "Name is required"),
        isLoading = false
    )

    @Before
    fun setup() {
        every { mockViewModel.state } returns MutableStateFlow(mockState)
        every { mockViewModel.toastMessage } returns MutableStateFlow("")
        composeTestRule.setContent {
            AddUserScreen(
                viewModel = mockViewModel,
                onMoveToUserList = {}
            )
        }
    }

    @Test
    fun testNameErrorMessageIsDisplayed() {
        composeTestRule.onNodeWithText("Name is required")
            .assertExists()
    }


    @Test
    fun testNameInputUpdatesViewModel() {
        every { mockViewModel.onNameChanged(any()) } just Runs

        composeTestRule.onNodeWithTag("name_input_field")
            .performTextInput("John Doe")
        verify(exactly = 1) {
            mockViewModel.onNameChanged("John Doe")
        }
    }


    @Test
      fun testSubmitButtonCallsSubmit() {
          composeTestRule.onNodeWithText("Add User")
              .performClick()
        verify { mockViewModel.submit() }
      }


}
