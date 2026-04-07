package com.hits.itindr.main_flow

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.ui.platform.testTag

const val PEOPLE_SCREEN_TITLE_TAG = "people_screen_title"

@Composable
fun PeopleScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(
            modifier = Modifier.testTag(PEOPLE_SCREEN_TITLE_TAG),
            text = "Люди",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp
        )
    }
}