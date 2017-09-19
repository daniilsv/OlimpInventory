package tech.babashnik.olimp.inventory.data

import android.app.Application

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.babashnik.olimp.inventory.data.components.olimp.OlimpApi

class App : Application() {
    private var retrofit: Retrofit? = null

    override fun onCreate() {
        super.onCreate()

        retrofit = Retrofit.Builder()
                .baseUrl("https://babashnik.tech/api/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build()
        olimp = retrofit!!.create(OlimpApi::class.java) //Создаем объект, при помощи которого будем выполнять запросы
    }

    companion object {

        var olimp: OlimpApi? = null
            private set
    }
}