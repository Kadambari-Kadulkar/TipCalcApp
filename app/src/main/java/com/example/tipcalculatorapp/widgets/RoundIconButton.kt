package com.example.tipcalculatorapp.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tipcalculatorapp.R

val IconbuttonsizeModifier = Modifier.size(40.dp)

@Composable
fun RoundIconButton(
        modifier: Modifier = Modifier,
        imageVector: ImageVector,
        onClick: () -> Unit,
        tint: Color = Color.Black.copy(0.8f),
        cardColors : CardColors = CardColors(colorResource(R.color.white), colorResource(R.color.purple_200), colorResource(R.color.white), Color.LightGray),
        elevation: Dp = 4.dp
    ){

    Card(modifier = Modifier.padding(4.dp)
        .clickable { onClick.invoke() }
        .then(IconbuttonsizeModifier),
        shape = CircleShape,
        colors = cardColors,
        elevation = CardDefaults.cardElevation(elevation,elevation,elevation,elevation,elevation,elevation)

    ) {
        Icon(imageVector,
            "Plus Minus sign", modifier,
            tint)

    }

}