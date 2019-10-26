package com.jongzazaal.samplehms.huawei.pushkit

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.os.SystemClock
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.gson.Gson
import com.huawei.hms.push.HmsMessageService
import com.huawei.hms.push.RemoteMessage
import com.jongzazaal.samplehms.huawei.R
import kotlin.random.Random


class MyHmsMessageService: HmsMessageService(){
    private var mNotificationManager: NotificationManager? = null

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        Log.d("tag2", "onNewToken: " + token);

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
//        super.onMessageReceived(remoteMessage)
        Log.d("tag2", "Message data payload: " + remoteMessage?.getData())
        if (!remoteMessage?.data.isNullOrEmpty()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createChatChannel()
            }
            var data = Gson().fromJson(remoteMessage?.data!!, NotificationData::class.java)

//            val data = NotificationData()
            sendNotification(data)

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChatChannel() {
        val followersChannel = NotificationChannel(getString(R.string.default_notification_channel_notification),
            getString(R.string.default_notification_channel_notification),
            NotificationManager.IMPORTANCE_HIGH)

        // Configure the channel's initial settings
        followersChannel.lightColor = Color.RED
        followersChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 500, 200, 500)
        followersChannel.setShowBadge(true)
        followersChannel.canShowBadge()

        // Submit the notification channel object to the notification manager
        getNotificationManager().createNotificationChannel(followersChannel)
    }
    private fun getNotificationManager(): NotificationManager {
        if (mNotificationManager == null) {
            mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        return mNotificationManager as NotificationManager
    }

    private fun sendNotification(messageBody: NotificationData) {

//        val backIntent = Intent(this, SCGMainActivity::class.java)
//        backIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        when (messageBody.action?:"") {
//            "appointment_responsed" -> { openWebView(messageBody.notification_id?:"8459") }
//            "appointment_reminder_1" -> { openWebView(messageBody.notification_id?:"8459") }
//            "appointment_reminder_2" -> { openWebView(messageBody.notification_id?:"8459") }
//            "appointment_modified" -> { openWebView(messageBody.notification_id?:"8459") }
//
//            "boq_responsed" -> { openBoqDetail(messageBody.notification_id?:"8459", messageBody.target_id_1!!) }
//
//            "point_received_1" -> { openPoint(messageBody.notification_id?:"8459") }
//            "point_received_2" -> { openPoint(messageBody.notification_id?:"8459") }
//            "point_received_3" -> { openPoint(messageBody.notification_id?:"8459") }
//            "point_received_4" -> { openPoint(messageBody.notification_id?:"8459") }
//            "point_reminder_1" -> { openPoint(messageBody.notification_id?:"8459") }
//            "point_reminder_2" -> { openPoint(messageBody.notification_id?:"8459") }
//
//            "voucher_reminder_1" -> { openPoint(messageBody.notification_id?:"8459") }
//            "voucher_reminder_2" -> { openPoint(messageBody.notification_id?:"8459") }
//
//            "promotion_received_1" -> { openPromotionDetail(messageBody.notification_id?:"8459", messageBody.target_id_1!!) }
//            "promotion_received_2" -> { openPromotionList(messageBody.notification_id?:"8459") }
//            "promotion_cms" -> { openPromotionDetail(messageBody.notification_id?:"8459", messageBody.target_id_1!!) }
//
//            "material_responsed" -> {openWebView(messageBody.notification_id?:"8459")}
//            "material_cms" -> { openMaterialDetail(messageBody.notification_id?:"8459", messageBody.target_id_1!!)}
//            "activity_cms_1" -> { openHighlightActivityDetail(messageBody.notification_id?:"8459", messageBody.target_id_1!!) }
//            "activity_cms_2" -> {openWebView(messageBody.notification_id?:"8459")}
//
//            else -> {
//                openWebView(messageBody.notification_id?:"8459")
//            }
//        }
        val randomValues = Random.nextInt(0, 9999)

//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        if(SCGApplicationCheck.isActivityRuningInBackground()){
//            pendingIntent = PendingIntent.getActivities(this, randomValues, arrayOf(intent), PendingIntent.FLAG_ONE_SHOT)
//        }
//        else{
//            pendingIntent = PendingIntent.getActivities(this, randomValues, arrayOf(backIntent, intent), PendingIntent.FLAG_ONE_SHOT)
//        }

        val channelId = getString(R.string.default_notification_channel_notification)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(getSmallIcon())
            .setContentTitle(messageBody.title?:"sample title")
            .setContentText(messageBody.body?:"sample des")
            .setSound(defaultSoundUri)
            .setColor(Color.RED)
//            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setNumber(UserManager.getNotificationCount())

        val notificationManager = getNotificationManager()
        val notificationBuild = notificationBuilder.build()
//        ShortcutBadger.applyNotification(getApplicationContext(), 0)
//        ShortcutBadger.applyNotification(getApplicationContext(), notificationBuild, UserManager.getNotificationCount())

//        notificationManager.notify(0, notificationBuilder.build())
        val oneTimeID = SystemClock.uptimeMillis().toInt()
        notificationManager.notify(oneTimeID, notificationBuild)
    }

    private fun getSmallIcon(): Int {
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) R.drawable.ic_noti else R.drawable.ic_noti
    }
}