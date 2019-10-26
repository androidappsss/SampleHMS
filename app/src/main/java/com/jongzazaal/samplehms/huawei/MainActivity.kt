package com.jongzazaal.samplehms.huawei

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.huawei.hms.aaid.HmsInstanceId
import com.huawei.hms.push.HmsMessaging

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setHMS()
        getToken()
    }

    private fun setHMS(){
        HmsMessaging.getInstance(this).turnOnPush()


    }

    private fun getToken(){
        Thread{
            kotlin.run {
                val token = HmsInstanceId.getInstance(this).getToken(resources.getString(R.string.huawei_app_id),"HCM")
                Log.d("tag2", "Token: " + token);
            }
        }.start()
    }
}
