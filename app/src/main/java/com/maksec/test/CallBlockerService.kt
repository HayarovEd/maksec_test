package com.maksec.test

import android.content.Intent
import android.os.Build
import android.telecom.Call
import android.telecom.CallScreeningService
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.Q)
class CallBlockerService : CallScreeningService() {


    override fun onScreenCall(callDetails: Call.Details) {
        
        val isIncoming = callDetails.callDirection == Call.Details.DIRECTION_INCOMING

        if (isIncoming) {
            val handle = callDetails.handle?.schemeSpecificPart ?: getString(R.string.unknown_number)
            
            // Start Overlay Service
            val intent = Intent(this, OverlayService::class.java).apply {
                putExtra(OverlayService.EXTRA_PHONE_NUMBER, handle)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            try {
                startService(intent)
            } catch (e: Exception) {
                 e.printStackTrace()
            }

            val response = CallResponse.Builder()
                .setDisallowCall(true)
                .setRejectCall(true)
                .setSkipCallLog(false)
                .setSkipNotification(false)
                .build()
            
            respondToCall(callDetails, response)
        } else {
            respondToCall(callDetails, CallResponse.Builder().build())
        }
    }
}
