package fr.isen.krijah.androidtoolbox

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*
import java.util.*

class LoginActivity : AppCompatActivity() {

    companion object {
        private const val GOOD_ID = "admin"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val logInput: SharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE)

        if ( logInput.getString("username", null)== GOOD_ID && logInput.getString("password",null).equals("123")){
            Toast.makeText(this,"La sauvegarde a marché", Toast.LENGTH_LONG).show()
            startActivity( Intent (this@LoginActivity, HomeActivity::class.java))
        }


        loginAction.setOnClickListener {
            val identifiant = loginUser.text.toString()
            val mdp = password.text.toString()

            if (identifiant == GOOD_ID && mdp.equals("123")) {
                val message = "tu as bien été identifié $identifiant"

                val newUser = loginUser.text.toString()
                val newPassword = password.text.toString()

                val editor: SharedPreferences.Editor = logInput.edit()

                editor.putString( "username", newUser)
                editor.putString("password", newPassword)
                editor.apply()

                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                startActivity( Intent (this@LoginActivity, HomeActivity::class.java))
            }
        }
    }


}
