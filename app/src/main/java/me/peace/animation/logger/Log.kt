package me.peace.animation.logger

import android.util.Log

object Log {
    val NONE = -1
    val VERBOSE = Log.VERBOSE
    val DEBUG = Log.DEBUG
    val INFO = Log.INFO
    val WARN = Log.WARN
    val ERROR = Log.ERROR
    val ASSERT = Log.ASSERT

    private var logNode: LogNode? = null

    fun getLogNode(): LogNode? {
        return logNode
    }

    fun setLogNode(node: LogNode) {
        logNode = node
    }

    fun println(priority: Int, tag: String?, msg: String?, tr: Throwable?) {
        logNode?.println(priority, tag, msg, tr)
    }

    fun println(priority: Int, tag: String?, msg: String?) {
        println(priority, tag, msg, null)
    }

    fun v(tag: String?, msg: String?, tr: Throwable?) {
        println(VERBOSE, tag, msg, tr)
    }

    fun v(tag: String?, msg: String?) {
        v(tag, msg, null)
    }

    fun d(tag: String?, msg: String?, tr: Throwable?) {
        println(DEBUG, tag, msg, tr)
    }

    fun d(tag: String?, msg: String?) {
        d(tag, msg, null)
    }

    fun i(tag: String?, msg: String?, tr: Throwable?) {
        println(INFO, tag, msg, tr)
    }

    fun i(tag: String?, msg: String?) {
        i(tag, msg, null)
    }

    fun w(tag: String?, msg: String?, tr: Throwable?) {
        println(WARN, tag, msg, tr)
    }


    fun w(tag: String?, msg: String?) {
        w(tag, msg, null)
    }

    fun w(tag: String?, tr: Throwable?) {
        w(tag, null, tr)
    }

    fun e(tag: String?, msg: String?, tr: Throwable?) {
        println(ERROR, tag, msg, tr)
    }

    fun e(tag: String?, msg: String?) {
        e(tag, msg, null)
    }

    fun wtf(tag: String?, msg: String?, tr: Throwable?) {
        println(ASSERT, tag, msg, tr)
    }

    fun wtf(tag: String?, msg: String?) {
        wtf(tag, msg, null)
    }

    fun wtf(tag: String?, tr: Throwable?) {
        wtf(tag, null, tr)
    }
}