package com.example.core3

import java.time.LocalDateTime

data class Club(
    val id : Int,
    val clubName : String,
    val location : String,
    val type : String,
    val dateTime : LocalDateTime
)
