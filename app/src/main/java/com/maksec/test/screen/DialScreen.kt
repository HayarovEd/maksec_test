package com.maksec.test.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maksec.test.R

// Цвета из макета
val PurplePrimary = Color(0xFF6A669D)
val PurpleSecondary = Color(0xFF8581B4)
val LightGray = Color(0xFFF0F0F0)
val TextGray = Color(0xFF888888)
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
fun DialScreen(
    modifier: Modifier = Modifier,
) {
    val enteredNumber = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Row(
                modifier = modifier
                    .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                    .fillMaxWidth()
                    .background(Color.White)
                    .statusBarsPadding()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { /* Действие при нажатии на кнопку "Назад" */ },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Transparent,
                    ),
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.arrow_back),
                        contentDescription = "Back",
                        tint = TextGray,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Позвонить",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(24.dp)) // Баланс для центровки
            }
        },
        bottomBar = {
            BottomNavBar()
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
            ) {
                TabItem(text = "Набор", isSelected = true, modifier = Modifier.weight(1f))
                TabItem(text = "История", isSelected = false, modifier = Modifier.weight(1f))
                TabItem(text = "Контакты", isSelected = false, modifier = Modifier.weight(1f))
            }

            // 3. Цифровая клавиатура (Grid)
            val keys = listOf(
                "1", "2", "3",
                "4", "5", "6",
                "7", "8", "9",
                "0", "#", "*"
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                keys.chunked(3).forEach { rowKeys ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        rowKeys.forEach { key ->
                            DialKey(key = key, onClick = { enteredNumber.value += key })
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // 4. Кнопка вызова
            Button(
                onClick = { /* Действие вызова */ },
                colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(56.dp)
            ) {
                Text(
                    text = "Вызов",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // 5. Нижняя навигация

        }
    }
}

@Composable
fun TabItem(text: String, isSelected: Boolean, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(44.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) Color.White else Color.Transparent)
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) PurplePrimary else TextGray,
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
            fontSize = 15.sp
        )
    }
}

@Composable
fun DialKey(key: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(70.dp)
            .clip(CircleShape)
            .background(PurpleSecondary)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = key,
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Light
        )
    }
}

@Composable
fun BottomNavBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFFFAFAFA))
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        NavIconItem("Дашборд", ImageVector.vectorResource(R.drawable.dash)) // Заглушка иконки
        NavIconItem("Сервисы", ImageVector.vectorResource(R.drawable.services), isSelected = true)
        NavIconItem("Позвонить", ImageVector.vectorResource(R.drawable.call))
        NavIconItem("Угрозы", ImageVector.vectorResource(R.drawable.danger), badgeCount = 99)
        NavIconItem("Профиль", ImageVector.vectorResource(R.drawable.profile))
    }
}

@Composable
fun NavIconItem(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean = false,
    badgeCount: Int? = null
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isSelected) PurplePrimary else TextGray,
                modifier = Modifier.size(26.dp)
            )
            if (badgeCount != null) {
                Badge(
                    containerColor = BadgeRed,
                    contentColor = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 8.dp, y = (-8).dp)
                ) {
                    Text(
                        text = if (badgeCount > 99) "99+" else badgeCount.toString(),
                        fontSize = 10.sp
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            color = if (isSelected) PurplePrimary else TextGray,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
    )
@Composable
fun DialScreenPreview() {
    MaterialTheme {
        DialScreen()
    }
}