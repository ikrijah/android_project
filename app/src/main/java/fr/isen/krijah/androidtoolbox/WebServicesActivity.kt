package fr.isen.krijah.androidtoolbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class WebServicesActivity : AppCompatActivity() {
    val url = "https://randomuser.me/api/?format=json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_services)



    }
}
