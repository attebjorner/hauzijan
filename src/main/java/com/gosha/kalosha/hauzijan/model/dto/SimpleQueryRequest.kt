package com.gosha.kalosha.hauzijan.model.dto

import java.time.LocalDate
import java.util.*

data class SimpleQueryRequest(
    val query: String,
    val page: Int,
    val maxResults: Int
)
