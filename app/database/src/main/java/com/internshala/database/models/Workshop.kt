package com.internshala.database.models

import java.util.*

/**
 * This is the model class for Workshops
 *
 * @param id [Int?]
 * @param title [String?]
 * @param agenda [String?]
 * @param organizer [String?]
 * @param durationInDays [Int?]
 * @param tags [String?]
 * @constructor
 */
data class Workshop(
    val id: Int? = null,
    val title: String? = null,
    val agenda: String? = null,
    val organizer: String? = null,
    val durationInDays: Int? = null,
    val tags: String? = null
)