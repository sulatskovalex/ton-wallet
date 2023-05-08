package me.sulatskovalex.twallet

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import me.sulatskovalex.twallet.di.TWalletDI
import org.koin.dsl.module

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TWalletDI.startDI(
            module {
                single { applicationContext }
            },
        )
        setContent {
            MainView(this)
        }
    }

    override fun onDestroy() {
        TWalletDI.stopDI()
        super.onDestroy()
    }
}