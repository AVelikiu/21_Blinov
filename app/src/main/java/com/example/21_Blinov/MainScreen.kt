package com.example.`21_Blinov`

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.a21_Blinov.DatabaseHelper

@Composable
fun MainScreen(navController: NavController, databaseHelper: DatabaseHelper) {
    // Переменные для ввода данных
    var name by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    val db: SQLiteDatabase = databaseHelper.writableDatabase
    val context = LocalContext.current // Получение контекста для Toast

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Поле для ввода имени
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Введите имя") },
            modifier = Modifier.fillMaxWidth()
        )

        // Поле для ввода года
        OutlinedTextField(
            value = year,
            onValueChange = { year = it },
            label = { Text("Введите год") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Кнопка для сохранения данных в базу
        Button(
            onClick = {
                val yearInt = year.toIntOrNull()
                if (name.isNotEmpty() && yearInt != null) {
                    val values = ContentValues().apply {
                        put(DatabaseHelper.COLUMN_NAME, name)
                        put(DatabaseHelper.COLUMN_YEAR, yearInt)
                    }
                    db.insert(DatabaseHelper.TABLE_NAME, null, values)
                    name = "" // Очистка полей после сохранения
                    year = ""
                    Toast.makeText(context, "Данные сохранены", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Введите корректные данные", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Сохранить")
        }

        // Кнопка для перехода на экран списка данных
        Button(
            onClick = {
                navController.navigate("userList")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Посмотреть данные")
        }
    }
}
