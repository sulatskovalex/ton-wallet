package me.sulatskovalex.twallet.domain

fun Long.format(decimals: Int = 9, count: Int = 3): String {
    if (this == 0L) {
        return "0"
    }
    val thisStr = this.toString(10)
    val amountStr = buildString {
        append(thisStr.dropLast(decimals).ifEmpty { "0" })
        append(".")
        val afterDot = thisStr.takeLast(decimals).padStart(decimals, '0')
        append(afterDot.substring(0, minOf(count, afterDot.length)))
    }
    return amountStr.remove0Suffix()
}

fun String.remove0Suffix(): String {
    var tmp = this@remove0Suffix
    if (tmp.contains(",") || tmp.contains(".")) {
        while (tmp.endsWith("0")) {
            tmp = tmp.removeSuffix("0").removeSuffix(".").removeSuffix(",")
        }
    }
    return tmp
}