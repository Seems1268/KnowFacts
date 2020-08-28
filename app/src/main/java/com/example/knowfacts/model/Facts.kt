package com.example.knowfacts.model

data class Facts(val title: String, val rows: List<Info>)

data class Info(val title: String, val description: String, val imageHref: String)