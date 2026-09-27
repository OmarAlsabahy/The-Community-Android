package com.tawajood.the_community_user.app.ui.shared

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tawajood.the_community_user.app.language.LocalStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForceUpdateBottomSheet(
    visible: Boolean,
    onUpdateNow: () -> Unit,
) {
    if (!visible) return

    val strings = LocalStrings.current

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { newValue ->
            newValue != SheetValue.Hidden
        }
    )

    BackHandler(enabled = true) {}

    ModalBottomSheet(
        onDismissRequest = {},
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        dragHandle = null,
        properties = ModalBottomSheetProperties(
            shouldDismissOnBackPress = false
        ),
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(White)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            UiText(
                text = strings.newVersionAvailable,
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            UiText(
                text = strings.newUpdate,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
                color = Gray,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            AppButton(
                title = strings.updateNow,
                backgroundColor = Color.Black,
                textColor = White,
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = onUpdateNow
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
