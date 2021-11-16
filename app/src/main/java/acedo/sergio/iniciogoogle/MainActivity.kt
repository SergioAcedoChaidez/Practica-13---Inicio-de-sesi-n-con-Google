package acedo.sergio.iniciogoogle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton

import android.content.Intent
import android.nfc.Tag
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnCompleteListener





class MainActivity : AppCompatActivity() {
    val RC_SIGN_IN= 123
    val COD_LOGOUT= 323
    lateinit var signInIntent: Intent
    lateinit var mGoogleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
      mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

         signInIntent = mGoogleSignInClient.signInIntent

       signIn()
    }

   private  val googleactivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
       activityResult ->

       val data: Intent? = activityResult.data
       val task = GoogleSignIn.getSignedInAccountFromIntent(data)
       handleSignInResult(task)
       Toast.makeText(this,"Prueba, si llego a abrir la pagina", Toast.LENGTH_SHORT).show()

       if (activityResult.resultCode == RC_SIGN_IN) {
           // The Task returned from this call is always completed, no need to attach
           // a listener.

       }
       if (activityResult.resultCode == COD_LOGOUT){
           signOut()
       }
   }

   private fun signIn() {
       val signinButton: SignInButton =  findViewById(R.id.sign_in_button)
      signinButton.setSize(SignInButton.SIZE_WIDE)
      signinButton.setOnClickListener{
          googleactivity.launch(signInIntent)
          Toast.makeText(this,"Abrio la seleccion de cuentas", Toast.LENGTH_SHORT).show()
          // startActivityForResult(signInIntent, RC_SIGN_IN)
        }
   }

    override fun onStart() {
        super.onStart()

        val account = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)
    }

 //   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       //     super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
     //   if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
       ///     val task = GoogleSignIn.getSignedInAccountFromIntent(data)
          //  handleSignInResult(task)
       // }
        //if (requestCode == COD_LOGOUT){
         //   signOut()
       // }
    //}


    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {

                Toast.makeText(this,"Sesion Terminada", Toast.LENGTH_SHORT).show()
            }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            //Toast.makeText(this,"Cuenta", Toast.LENGTH_SHORT).show()
            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
           // Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            updateUI(null)
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if(account != null){
            val intent = Intent(this,PrincipalActivity::class.java)
            intent.putExtra("name", account.displayName)
            intent.putExtra("email", account.email)

            startActivityForResult(intent,COD_LOGOUT)
        }else{
            Toast.makeText(this,"lA cUENTA ESTA NULA", Toast.LENGTH_SHORT).show()
        }
    }

}