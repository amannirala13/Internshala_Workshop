package com.internshala.database.models

import java.util.*

/**
 * Model class for Workshop events
 */
data class Workshop(
    val id: Int? = null,
    val title: String? = null,
    val agenda: String? = null,
    val organizer: String? = null,
    val durationInDays: Int? = null,
    val tags: String? = null
)