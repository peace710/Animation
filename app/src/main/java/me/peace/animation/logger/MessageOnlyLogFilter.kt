package me.peace.animation.logger


class MessageOnlyLogFilter : LogNode {

    var next: LogNode? = null

    constructor(next: LogNode?) {
        this.next = next
    }

    constructor() {}

    override fun println(priority: Int, tag: String?, msg: String?, tr: Throwable?) {
        if (next != null) {
            next!!.println(Log.NONE, null, msg, null)
        }
    }
}