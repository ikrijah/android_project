package fr.isen.krijah.androidtoolbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginAction.setOnClickListener {
            val identifiant = loginUser.text.toString()
            val mdp = password.text.toString()
            if (identifiant.equals("admin") and mdp.equals("123")) {
                val message = "tu as bien été identifié $identifiant"
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
