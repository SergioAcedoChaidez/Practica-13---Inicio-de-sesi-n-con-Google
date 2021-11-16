package acedo.sergio.iniciogoogle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class PrincipalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)


        val bundle = intent.extras


        if(bundle!=null){
            val nombre= bundle.getString("name")
            val email= bundle.getString("email")
            val textViewnombre = findViewById<TextView>(R.id.textView4)

            val textViewEmail = findViewById<TextView>(R.id.textView5)

            textViewnombre.setText(nombre)
            textViewEmail.setText(email)
        }
        val btn_cerrar:Button = findViewById(R.id.button)
        btn_cerrar.setOnClickListener { finish() }
    }
}