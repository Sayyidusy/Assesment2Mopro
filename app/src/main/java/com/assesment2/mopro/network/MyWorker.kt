package com.assesment2.mopro.network

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class MyWorker(
    context: Context,
               workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    companion object {
        const val WORK_NAME = "updater"
    }
    override suspend fun doWork(): Result {
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_NOTIFICATION_POLICY
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e("Worker", "Tidak diberikan izin notifikasi")
            return Result.failure()
        }
        Log.d("Worker", "Menjalankan proses background..")
        return Result.success()
    }
}
