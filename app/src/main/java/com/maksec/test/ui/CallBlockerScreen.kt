package com.maksec.test.ui

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maksec.test.R
import com.maksec.test.checkRoleHeld
import com.maksec.test.requestRole
import androidx.core.net.toUri

@Composable
fun CallBlockerScreen(modifier: Modifier = Modifier) {
    val context = androidx.compose.ui.platform.LocalContext.current
    var isRoleHeld by remember {
        mutableStateOf(checkRoleHeld(context))
    }
    var canDrawOverlays by remember {
        mutableStateOf(Settings.canDrawOverlays(context))
    }

    val roleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { _ ->
        isRoleHeld = checkRoleHeld(context)
    }

    val overlayLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { _ ->
        canDrawOverlays = Settings.canDrawOverlays(context)
    }

    LaunchedEffect(Unit) {
        canDrawOverlays = Settings.canDrawOverlays(context)
    }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.call_blocker_title),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Role Status
        val isSupported = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
        StatusItem(
            label = stringResource(R.string.label_call_screening),
            isEnabled = isRoleHeld,
            isSupported = isSupported
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Overlay Status
        StatusItem(
            label = stringResource(R.string.label_overlay_permission),
            isEnabled = canDrawOverlays,
            isSupported = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (isSupported && !isRoleHeld) {
            Button(onClick = {
                requestRole(context) { intent ->
                    roleLauncher.launch(intent)
                }
            }) {
                Text(stringResource(R.string.btn_request_role))
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (!canDrawOverlays) {
            Button(onClick = {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    "package:${context.packageName}".toUri()
                )
                overlayLauncher.launch(intent)
            }) {
                Text(stringResource(R.string.btn_request_overlay))
            }
        }

        if (isRoleHeld && canDrawOverlays) {
            Text(
                text = stringResource(R.string.status_setup_complete),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun StatusItem(label: String, isEnabled: Boolean, isSupported: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, style = MaterialTheme.typography.labelLarge)
        Text(
            text = if (!isSupported) {
                stringResource(R.string.status_not_supported)
            } else if (isEnabled) {
                stringResource(R.string.status_enabled)
            } else {
                stringResource(R.string.status_disabled)
            },
            color = if (isSupported && isEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
