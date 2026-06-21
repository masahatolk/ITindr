package com.hits.itindr.mainflow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hits.core_ui.CircleIconButton
import com.hits.itindr.R
import com.hits.core_ui.R.drawable.edit
import com.hits.core_ui.R.drawable.logout


@Composable
fun ProfileScreen(
    onEditClick: () -> Unit,
    onLogoutClick: () -> Unit,
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .systemBarsPadding(),
    ) {
        Row (
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = stringResource(R.string.title_profile),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            CircleIconButton(
                icon = edit,
                onClick = onEditClick
            )

            Spacer(modifier = Modifier.width(16.dp))

            CircleIconButton(
                icon = logout,
                onClick = onLogoutClick
            )
        }
    }
}

