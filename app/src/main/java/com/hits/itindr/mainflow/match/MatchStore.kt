package com.hits.itindr.mainflow.match

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MatchStore {

    private val _matchData =
        MutableStateFlow<MatchData?>(null)

    val matchData: StateFlow<MatchData?> =
        _matchData.asStateFlow()

    fun showMatch(
        matchData: MatchData
    ) {
        _matchData.value = matchData
    }

    fun dismissMatch() {
        _matchData.value = null
    }
}