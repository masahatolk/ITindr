package com.hits.itindr.main_flow

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var selectedIndex by mutableIntStateOf(0)
}