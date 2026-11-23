package com.example.workoutreminder.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.workoutreminder.data.WorkoutDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar

class ReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val db = WorkoutDatabase.getInstance(applicationContext)
            val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
            val ourDay = if (today == Calendar.SUNDAY) 7 else today - 1
            val entry = db.workoutDao().getByDay(ourDay)

            val message = entry?.instruction?.takeIf { it.isNotBlank() }
                ?: "Не забудьте записать инструкцию к тренировке на сегодня!"

            sendNotification(applicationContext, "Тренировка на сегодня", message)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private fun sendNotification(context: Context, title: String, content: String) {
        val channelId = "workout_reminder_channel"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Напоминания о тренировках",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }
}
