package com.maksec.test

import android.app.role.RoleManager
import android.content.Context
import android.content.Intent
import android.os.Build

fun checkRoleHeld(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val roleManager = context.getSystemService(RoleManager::class.java)
        roleManager?.isRoleHeld(RoleManager.ROLE_CALL_SCREENING) == true
    } else {
        false
    }
}

fun requestRole(context: Context, onLaunch: (Intent) -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val roleManager = context.getSystemService(RoleManager::class.java)
        val intent = roleManager?.createRequestRoleIntent(RoleManager.ROLE_CALL_SCREENING)
        if (intent != null) {
            onLaunch(intent)
        }
    }
}
