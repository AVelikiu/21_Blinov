package com.example.`21_Blinov`

import android.database.Cursor
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import com.example.a21_Blinov.DatabaseHelper

@Composable
fun UserListScreen(databaseHelper: DatabaseHelper) {
    val db = databaseHelper.readableDatabase // Получаем доступ к базе данных
    var userList by remember { mutableStateOf(listOf<Pair<String, Int>>()) }

    // LaunchedEffect выполняет код 1 раз
    // Загрузка данных из базы при первом рендере экрана
    LaunchedEffect(Unit) {
        // переменная возвращающая все записи из таблицыы
        val cursor: Cursor = db.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_NAME}", null)
        val users = mutableListOf<Pair<String, Int>>()
        if (cursor.moveToFirst()) {
            do {
                //извлечение данных из строки
                val name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME))
                val year = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_YEAR))
                users.add(name to year)
            } while (cursor.moveToNext())
        }
        cursor.close() // Закрываем курсор после чтения
        userList = users
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Вывод списка данных внизу экрана
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            items(userList) { user ->
                // Карточка для каждого пользователя
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Имя: ${user.first}")
                        Text(text = "Год: ${user.second}")
                    }
                }
            }
        }
    }
}
