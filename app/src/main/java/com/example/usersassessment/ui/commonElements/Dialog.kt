package com.example.usersassessment.ui.commonElements

import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.usersassessment.R
import com.example.usersassessment.ui.theme.LocalDimensions

data class DialogData(var isShowing: Boolean, var title: String)

@Composable
fun Dialog(title: String, icon: Int, onDismiss: () -> Unit) {
    val dimensions = LocalDimensions.current

    AlertDialog(
        icon = {
            Icon(
                modifier = Modifier.size(dimensions.space4Xl),
                painter = painterResource(icon),
                contentDescription = null,
                tint = Color.Red
            )
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(stringResource(R.string.ok))
            }
        },
        title = { Text(text = title, textAlign = TextAlign.Center) }
    )
}
