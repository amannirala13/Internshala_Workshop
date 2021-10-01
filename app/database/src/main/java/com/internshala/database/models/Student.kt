package com.internshala.database.models

/**
 * Model class for student
 *
 * @param id [Integer?]
 * @param name [String?]
 * @param phone [String?]
 * @param email [String?]
 * @param password [String?]
 */
data class Student(
    val id: Int? = null,
    val name: String? = null,
    val phone: String? = null,
    val email: String? = null,
    val password: String? = null
)