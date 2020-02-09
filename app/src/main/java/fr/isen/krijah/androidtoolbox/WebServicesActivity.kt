package fr.isen.krijah.androidtoolbox

import Json4Kotlin_Base
import Name
import Results
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_web_services.*
import kotlinx.android.synthetic.main.fragment_my.*
import org.json.JSONArray
import org.json.JSONObject


class WebServicesActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_services)


        getUsers()
    }

    // function for network call
    fun getUsers() {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://randomuser.me/api/?format=json"

        // Request a string response from the provided URL.
        val stringReq = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->

                var strResp = response.toString()
                val topic: Json4Kotlin_Base? = Gson().fromJson(response, Json4Kotlin_Base::class.java)
                val name: Name? = Gson().fromJson(response, Name::class.java)
                if (topic != null) {
                    if (name != null) {
                        nameWebService.text= name.first
                    }
                }
                /* val jsonArray: JSONArray = jsonObj.getJSONArray("results")
                var str_user: String = ""
                for (i in 0 until jsonArray.length()) {
                    var jsonInner: JSONObject = jsonArray.getJSONObject(i)
                    str_user = str_user + "\n" + jsonInner.get("name")
                }
                textView!!.text = str_user */
            },
            Response.ErrorListener { textView!!.text = "That didn't work!" })
        queue.add(stringReq)
    }
}
