package com.example.knowfacts.model

/**
 * Created by Seema Savadi on 28/08/20.
 */

/**
 * Data classes to parse the API responses into.
 */
data class Facts(val title: String?, var rows: List<Info>?)

data class Info(val title: String?, val description: String?, val imageHref: String?)