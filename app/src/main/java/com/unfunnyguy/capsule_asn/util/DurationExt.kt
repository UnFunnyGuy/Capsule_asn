package com.unfunnyguy.capsule_asn.util

import kotlin.time.Duration

fun Duration.toFormatString(): String {
    val minutes = this.inWholeMinutes
    val seconds = this.inWholeSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}

