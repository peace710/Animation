package me.peace.animation.logger


interface LogNode {
    fun println(priority: Int, tag: String?, msg: String?, tr: Throwable?)
}