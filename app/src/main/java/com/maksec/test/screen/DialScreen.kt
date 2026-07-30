package com.maksec.test.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maksec.test.R

// Цвета из макета
val PurpleMain = Color(0xFF6A669D)
val DarkBackground = Color(0xFFF5F5F5)
val TabBackground = Color(0xFFF2F2F7)
val TabTextSelected = Color(0xFF6A669D)
val TabTextUnselected = Color(0xFF8E8E93)
val DialNumberColor = Color(0xFF3A3A3C)
val BadgeRed = Color(0xFFFF3B30)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                DialScreen()
            }
        }
    }
}

@Composable
fun DialScreen() {
    val enteredNumber = remember { mutableStateOf("89227246484") }

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
                        text = "Набор номера",
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
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            // Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TabItem("Набор", isSelected = true, modifier = Modifier.weight(1f))
                TabItem("История", isSelected = false, modifier = Modifier.weight(1f))
                TabItem("Контакты", isSelected = false, modifier = Modifier.weight(1f))
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
fun TabItem(text: String, isSelected: Boolean, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) Color.White else Color(0xFFF2F2F7))
            .then(
                if (isSelected) Modifier.border(2.dp, PurpleMain, RoundedCornerShape(16.dp))
                else Modifier
            )
            .clickable { },
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
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 24.dp)
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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { }
            .padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
        Box {
            Icon(
                imageVector = ImageVector.vectorResource(id = iconRes),
                contentDescription = label,
                tint = if (isSelected) PurpleMain else Color(0xFF9E9E9E),
                modifier = Modifier.size(24.dp)
            )
            if (badgeCount != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 12.dp, y = (-2).dp)
                        .background(BadgeRed, CircleShape)
                        .padding(horizontal = 4.dp, vertical = 1.dp)
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
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            color = if (isSelected) PurpleMain else Color(0xFF9E9E9E),
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
        )
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