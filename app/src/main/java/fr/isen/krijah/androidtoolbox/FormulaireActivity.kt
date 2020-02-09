package fr.isen.krijah.androidtoolbox

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_formulaire.*
import org.json.JSONObject
import java.io.File
import java.util.*

class FormulaireActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulaire)

        birthDateBtn.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, yearToday, monthOfYear, dayOfMonth ->

                // Display Selected date in textbox
                birthdayText.setText("" + dayOfMonth + "/" + (monthOfYear+1) + "/" + yearToday)
            }, year, month, day)

            dpd.show()

            save.setOnClickListener() {
                saveDataToFile (lastName.text.toString(), firstName.text.toString(), birthdayText.text.toString())
                startActivity( Intent (this@FormulaireActivity, HomeActivity::class.java))
            }
            show.setOnClickListener() {
                showDataFromFile()
            }
        }
    }

    private fun saveDataToFile (lastName:String, firstName:String, date:String){
        if (firstName.isNotEmpty() && lastName.isNotEmpty() && date != getString(R.string.storage_data_value)){
            val data = "{'lastName' : '$lastName', 'firstName' : '$firstName', 'date' : '$date'}"
            // val dataJson : JSONObject = JSONObject().put(name:"lastname", lastName)
            File (cacheDir.absolutePath + JSON_FILE).writeText(data)
            Toast.makeText(this@FormulaireActivity, "Sauvegarde des info de l'utilisateur", Toast.LENGTH_LONG).show()

        }
    }

    private fun showDataFromFile (){
        saveDataToFile (lastName.text.toString(), firstName.text.toString(), birthdayText.text.toString())
        val dataJson : String = File (cacheDir.absolutePath + JSON_FILE ).readText()
        if (dataJson.isNotEmpty()){
            val jsonObject = JSONObject(dataJson)

            val strDate : String = jsonObject.optString("date")
            val strLastName: String = jsonObject.optString("lastName")
            val strFirstName: String = jsonObject.optString("firstName")

            AlertDialog.Builder(this@FormulaireActivity)
                .setTitle("Lecture du fichier")
                .setMessage("Nom: $strLastName \n Prenom: $strFirstName \n Date: $strDate \n Age : 0")
                .create()
                .show()

        }
        else{
            Toast.makeText(this@FormulaireActivity, "Aucune information fournie", Toast.LENGTH_LONG).show()
        }
    }

    companion object{
        const val JSON_FILE = "var_data.json"
    }

}
