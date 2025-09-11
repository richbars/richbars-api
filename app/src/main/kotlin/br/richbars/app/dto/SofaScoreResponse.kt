package br.richbars.app.dto


import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class SofaScoreResponse(
    val totalGames: Int,
    val events: List<EventOriginal>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class EventOriginal(
    val id: Long,
    val tournament: Tournament,
    val homeTeam: Team,
    val awayTeam: Team
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Tournament(
    val name: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Team(
    val id: Long,
    val name: String,
    val flag: String? = null
)
