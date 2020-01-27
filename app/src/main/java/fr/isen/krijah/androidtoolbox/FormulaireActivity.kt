package fr.isen.krijah.androidtoolbox

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import androidx.core.view.isInvisible
import kotlinx.android.synthetic.main.activity_formulaire.*
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class FormulaireActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulaire)

        birthDateBtn.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                // Display Selected date in textbox
                birthdayText.setText("" + dayOfMonth + "/" + (monthOfYear+1) + "/" + year)
            }, year, month, day)

            dpd.show()

        }
    }


}
