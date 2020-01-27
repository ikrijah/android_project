package fr.isen.krijah.androidtoolbox

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)



        lifeCycleImageView.setOnClickListener {
            startActivity( Intent (this@HomeActivity, LifeCycleActivity::class.java))
        }

        buttonDeconnect.setOnClickListener {
            val message = "tu as bien été déconnecté"

            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            startActivity( Intent (this@HomeActivity, LoginActivity::class.java))

            val logInput: SharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE)

            val editor: SharedPreferences.Editor = logInput.edit()

            editor.clear()

            editor.apply()
        }
        
        saveImageView.setOnClickListener {
            Toast.makeText(this, "vous êtes redirigé vers le formulaire", Toast.LENGTH_LONG).show()
            startActivity( Intent (this@HomeActivity, FormulaireActivity::class.java))
        }


        authorizationImageView.setOnClickListener(){
            Toast.makeText(this, "vous êtes redirigé vers les informations Android", Toast.LENGTH_LONG).show()
            startActivity( Intent ( this@HomeActivity, AccessDataActivity::class.java))
        }
    }

}
