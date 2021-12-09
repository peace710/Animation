package me.peace.animation.logger

import android.util.Log

class LogWrapper : LogNode {
    var next: LogNode? = null

    override fun println(priority: Int, tag: String?, msg: String?, tr: Throwable?) {
        var msg = msg
        var useMsg = msg
        if (useMsg == null) {
            useMsg = ""
        }

        if (tr != null) {
            msg += """
                
                ${Log.getStackTraceString(tr)}
                """.trimIndent()
        }

        Log.println(priority, tag, useMsg)

        if (next != null) {
            next!!.println(priority, tag, msg, tr)
        }
    }
}
