package com.tawajood.the_community_user.domain.base

enum class NetworkError {
    REQUEST_TIMEOUT,
    UNAUTHORIZED,
    CONFLICT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    PAYLOAD_TOO_LARGE,
    SERVER_ERROR,
    SERIALIZATION,
    RESPONSE_NULL,
    UNKNOWN,
    API_ERROR
}