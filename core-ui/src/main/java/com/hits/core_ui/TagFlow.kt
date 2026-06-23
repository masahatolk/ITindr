package com.hits.core_ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp

data class TagItem(val id: String, val text: String)

@Composable
fun TagFlow(
    tags: List<TagItem>,
    selectedIds: Set<String>,
    modifier: Modifier = Modifier,
    multiSelect: Boolean = true,
    maxSelected: Int? = null,
    defaultBgColor: Color = colorResource(R.color.white_transparent20),
    selectedBgColor: Color = Color.White,
    defaultTextColor: Color = Color.White,
    selectedTextColor: Color = Color.Black,
    onSelectionChange: (Set<String>) -> Unit,
    onSelectionLimitReached: (Int) -> Unit = {}
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tags.forEach { tag ->

            val isSelected = tag.id in selectedIds

            Surface(
                modifier = Modifier.clickable {

                    val newSelection = selectedIds.toMutableSet()

                    if (multiSelect) {
                        if (isSelected) {
                            newSelection.remove(tag.id)
                        } else {
                            if (
                                maxSelected != null &&
                                selectedIds.size >= maxSelected
                            ) {
                                onSelectionLimitReached(maxSelected)
                                return@clickable
                            }

                            newSelection.add(tag.id)
                        }
                    } else {
                        newSelection.clear()
                        newSelection.add(tag.id)
                    }

                    onSelectionChange(newSelection)
                },
                color = if (isSelected) {
                    selectedBgColor
                } else {
                    defaultBgColor
                },
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = tag.text,
                    color = if (isSelected) {
                        selectedTextColor
                    } else {
                        defaultTextColor
                    },
                    style = AppTextStyles.SmallText,
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                )
            }
        }
    }
}