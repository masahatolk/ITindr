package com.hits.itindr.mainflow

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hits.itindr.R

@Composable
fun ProfileScreen(
    onEditClick: () -> Unit,
    onLogoutClick: () -> Unit,
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
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
                icon = R.drawable.edit,
                onClick = onEditClick
            )

            Spacer(modifier = Modifier.width(16.dp))

            CircleIconButton(
                icon = R.drawable.logout,
                onClick = onLogoutClick
            )
        }
    }
}


@Composable
private fun CircleIconButton(
    icon: Int,
    onClick: () -> Unit
) {
    Surface(
        shape = CircleShape,
        color = colorResource(R.color.black_transparent40),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.size(52.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
