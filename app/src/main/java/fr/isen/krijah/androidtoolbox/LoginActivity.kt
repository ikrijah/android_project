package fr.isen.krijah.androidtoolbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object {
        private const val GOOD_ID = "admin"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginAction.setOnClickListener {
            val identifiant = loginUser.text.toString()
            val mdp = password.text.toString()
            if (identifiant == GOOD_ID && mdp.equals("123")) {
                val message = "tu as bien été identifié $identifiant"
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                startActivity( Intent (this@LoginActivity, HomeActivity::class.java))
            }
        }
    }
}
