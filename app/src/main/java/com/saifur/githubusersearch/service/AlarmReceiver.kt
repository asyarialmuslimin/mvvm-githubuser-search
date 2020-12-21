package com.saifur.githubusersearch.service

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.saifur.githubusersearch.MainActivity
import com.saifur.githubusersearch.R
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Alarm Show", Toast.LENGTH_SHORT).show()
        showAlarmNotification(context, ID_ALARM)
    }

    private fun showAlarmNotification(context: Context, notifId: Int) {

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, ID_INTENT, intent, 0)

        val CHANNEL_ID = "Channel_1"
        val CHANNEL_NAME = "AlarmManager channel"
        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(context.getString(R.string.reminder))
            .setContentText(context.getString(R.string.reminder_subtitle))
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(CHANNEL_ID)
            notificationManagerCompat.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManagerCompat.notify(notifId, notification)
    }

    companion object{
        const val ID_ALARM = 101
        const val ID_INTENT = 102

        fun setAlarm(context: Context){
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, ID_ALARM, intent, 0)

            val alarmTime = Calendar.getInstance()
            alarmTime.set(Calendar.HOUR_OF_DAY, 9)
            alarmTime.set(Calendar.MINUTE, 0)
            alarmTime.set(Calendar.SECOND, 0)

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, alarmTime.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
            Toast.makeText(context, "Alarm Set", Toast.LENGTH_SHORT).show()
        }

        fun cancelAlarm(context: Context){
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, ID_ALARM, intent, 0)
            pendingIntent.cancel()
            alarmManager.cancel(pendingIntent)
            Toast.makeText(context, "Alarm Cancel", Toast.LENGTH_SHORT).show()
        }
    }
}