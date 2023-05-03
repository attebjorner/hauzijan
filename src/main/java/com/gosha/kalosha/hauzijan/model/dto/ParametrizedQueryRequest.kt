package com.gosha.kalosha.hauzijan.model.dto

data class ParametrizedQueryRequest(
    val queries: List<ComplexQueryRequest>,
    val page: Int,
    val maxResults: Int
)