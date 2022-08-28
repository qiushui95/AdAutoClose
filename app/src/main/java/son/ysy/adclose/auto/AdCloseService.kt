package son.ysy.adclose.auto

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

internal class AdCloseService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent) {

        when (event.eventType) {
            AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED -> onViewChanged()
            else -> {
            }
        }
    }

    private fun onViewChanged() {

        val rootNode = rootInActiveWindow ?: return

        val closeButtons = mutableListOf<AccessibilityNodeInfo>()

        closeButtons.addAll(rootNode.findAccessibilityNodeInfosByViewId("com.dragon.read:id/aro"))

        closeButtons.forEach {
            it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
        }
    }

    override fun onInterrupt() {

    }
}