package com.maksec.test.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maksec.test.R
import com.maksec.test.ui.theme.BadgeRed
import com.maksec.test.ui.theme.DarkBackground
import com.maksec.test.ui.theme.DialNumberColor
import com.maksec.test.ui.theme.PurpleMain




enum class TypeDialScreen(val nameRes: Int) {
    DIAL(R.string.dial),
    HISTORY(R.string.history),
    CONTACTS(R.string.contacts)
}

@Composable
fun DialScreen(
    modifier: Modifier = Modifier
) {
    val enteredNumber = remember { mutableStateOf("") }
    var currentTypeScreen by remember { mutableStateOf(TypeDialScreen.DIAL) }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .background(Color.White)
                    .statusBarsPadding()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = { },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Transparent,
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 8.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.arrow_back),
                            contentDescription = "Back",
                            tint = Color.LightGray
                        )
                    }
                    Text(
                        text = if (enteredNumber.value.isBlank())stringResource(R.string.to_call) else stringResource(R.string.enter_number),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                }
            }
        },
        bottomBar = {
            BottomNavBar()
        },
        containerColor = DarkBackground
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TypeDialScreen.entries.forEach {
                    TabItem(
                        text = stringResource(it.nameRes),
                        isSelected = currentTypeScreen == it,
                        modifier = Modifier.weight(1f),
                        onClick = { currentTypeScreen = it })
                }
            }

            Spacer(modifier = Modifier.weight(0.5f))

            // Entered Number
            Text(
                text = enteredNumber.value,
                fontSize = 54.sp,
                fontWeight = FontWeight.Normal,
                color = DialNumberColor,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.weight(0.5f))

            // Dial Pad
            val keys = listOf(
                listOf("1", "2", "3"),
                listOf("4", "5", "6"),
                listOf("7", "8", "9"),
                listOf("0", "#", "*")
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                keys.forEach { row ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(32.dp)
                    ) {
                        row.forEach { key ->
                            DialKey(key = key, onClick = { enteredNumber.value += key })
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(0.5f))

            // Call Button
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = PurpleMain),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
            ) {
                Text(
                    text = "Вызов",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun TabItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) Color.White else Color(0xFFF2F2F7))
            .then(
                if (isSelected) Modifier.border(2.dp, PurpleMain, RoundedCornerShape(16.dp))
                else Modifier
            )
            .padding(vertical = 10.dp)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) PurpleMain else Color(0xFF8E8E93),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun DialKey(key: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(82.dp)
            .clip(CircleShape)
            .background(PurpleMain)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = key,
            color = Color.White,
            fontSize = 36.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun BottomNavBar() {
    Box(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = Color.White,
            shadowElevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavItem("Дашборд", R.drawable.dash, isSelected = false)
                NavItem("Сервисы", R.drawable.services, isSelected = true)
                NavItem("Позвонить", R.drawable.call, isSelected = false)
                NavItem("Угрозы", R.drawable.danger, isSelected = false, badgeCount = 99)
                NavItem("Профиль", R.drawable.profile, isSelected = false)
            }
        }
    }
}

@Composable
fun NavItem(
    label: String,
    iconRes: Int,
    isSelected: Boolean,
    badgeCount: Int? = null
) {
    BadgedBox(
        badge = {
            if (badgeCount != null) {
                Box(
                    modifier = Modifier
                        .background(
                            color = BadgeRed,
                            shape = CircleShape
                        )
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = badgeCount.toString(),
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .clickable { }
                .padding(horizontal = 4.dp, vertical = 2.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = iconRes),
                contentDescription = label,
                tint = if (isSelected) PurpleMain else Color(0xFF9E9E9E),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                fontSize = 10.sp,
                color = if (isSelected) PurpleMain else Color(0xFF9E9E9E),
                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
            )
        }
    }

}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DialScreenPreview() {
    DialScreen()
}