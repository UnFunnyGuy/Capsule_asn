package com.unfunnyguy.capsule_asn.util

import java.util.Locale

fun String.cap() = this.lowercase()
    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }