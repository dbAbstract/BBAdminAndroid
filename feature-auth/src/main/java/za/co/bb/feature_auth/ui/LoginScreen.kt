package za.co.bb.feature_auth.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import za.co.bb.core.ui.components.AppButton
import za.co.bb.core.ui.theme.AppColors
import za.co.bb.feature_auth.presentation.LoginEventHandler
import za.co.bb.feature_auth.presentation.LoginScreenState

@Composable
internal fun LoginScreen(
    uiState: LoginScreenState,
    loginEventHandler: LoginEventHandler
) {
    var showPassword by remember {
        mutableStateOf(false)    
    }
    
    Scaffold { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .imePadding()
                    .alpha(if (uiState.isLoading) 0.3f else 1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    value = uiState.email,
                    onValueChange = loginEventHandler::onEmailChanged,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = AppColors.current.primary,
                        focusedLabelColor = AppColors.current.primary
                    ),
                    label = {
                        Text(text = "Email")
                    },
                    singleLine = true
                )

                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = loginEventHandler::onPasswordChanged,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = AppColors.current.primary,
                        focusedLabelColor = AppColors.current.primary
                    ),
                    label = {
                        Text(text = "Password")
                    },
                    visualTransformation = if (showPassword)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(
                            onClick = { showPassword = !showPassword }
                        ) {
                            Icon(
                                imageVector = if (showPassword)
                                    Icons.Filled.Visibility
                                else
                                    Icons.Filled.VisibilityOff,
                                contentDescription = null
                            )
                        }
                    },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(60.dp))

                AppButton(
                    modifier = Modifier
                        .height(60.dp)
                        .width(240.dp),
                    onClick = loginEventHandler::onLogin,
                    shape = CircleShape,
                    text = "Login"
                )
            }

            if (uiState.isLoading) CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = AppColors.current.primary
            )
        }
    }
}