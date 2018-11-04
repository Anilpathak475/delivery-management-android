package co.parsl.android.boilerplate.ui.onboarding.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import co.parsl.android.ui.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.title = "Parsl"
    }

    override fun onSupportNavigateUp() = Navigation.findNavController(this, R.id.nav_login).navigateUp()
}
