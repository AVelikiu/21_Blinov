package com.example.a21_Blinov

import android.os.Bundle
import android.database.sqlite.SQLiteDatabase
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.`21_Blinov`.MainScreen
import com.example.`21_Blinov`.UserListScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация базы данных
        val databaseHelper = DatabaseHelper(this)

        // Сброс базы данных в фоновом потоке
        GlobalScope.launch(Dispatchers.IO) {
            val db: SQLiteDatabase = databaseHelper.writableDatabase
            db.beginTransaction()
            try {
                databaseHelper.onUpgrade(db, 1, 2)
                db.setTransactionSuccessful()
            } finally {
                db.endTransaction()
            }
        }

        // Установка интерфейса с навигацией
        setContent {
            val navController = rememberNavController()
            MaterialTheme {
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") { MainScreen(navController, databaseHelper) }
                    composable("userList") { UserListScreen(databaseHelper) }
                }
            }
        }
    }
}
