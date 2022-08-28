package son.ysy.adclose.auto

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.tvStatus).setOnClickListener {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }


    private fun isServiceOn(): Boolean {

        val service = "$packageName/${AdCloseService::class.java.canonicalName}"

        val accessibilityEnabled = try {
            Settings.Secure.getInt(
                applicationContext.contentResolver,
                Settings.Secure.ACCESSIBILITY_ENABLED
            )
        } catch (e: Exception) {
            return false
        }

        if (accessibilityEnabled != 1) {
            return false
        }

        val settingValue = Settings.Secure.getString(
            applicationContext.contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        ) ?: return false

        val stringSplitter = TextUtils.SimpleStringSplitter(':')
        stringSplitter.setString(settingValue)

        while (stringSplitter.hasNext()) {
            val accessibilityService = stringSplitter.next()

            if (accessibilityService.equals(service, true)) {
                return true
            }
        }

        return false
    }

    override fun onResume() {
        super.onResume()

        if (isServiceOn()) {
            R.string.accessibility_service_on
        } else {
            R.string.accessibility_service_off
        }.apply(findViewById<TextView>(R.id.tvStatus)::setText)
    }
}