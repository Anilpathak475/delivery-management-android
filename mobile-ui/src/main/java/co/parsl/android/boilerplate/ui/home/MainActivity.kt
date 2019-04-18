package co.parsl.android.boilerplate.ui.home

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.tech.MifareUltralight
import android.nfc.tech.NfcA
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.Navigation
import co.parsl.android.boilerplate.ui.onboarding.LaunchActivity
import co.parsl.android.boilerplate.ui.onboarding.login.LoginActivity
import co.parsl.android.ui.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.scope.ext.android.bindScope
import org.koin.android.scope.ext.android.getCurrentScope
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    private var adapter: NfcAdapter? = null

    private lateinit var pendingIntent: PendingIntent

    private lateinit var intentFiltersArray: Array<IntentFilter>

    private lateinit var techListsArray: Array<Array<String>>

    private var nfcTagListener: NfcTagListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.AppTheme_TransparentTheme)

        super.onCreate(savedInstanceState)

        checkForLogin()

        //Check - Permissions
        checkForPermissions()

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Parsl"

        bindScope(getCurrentScope())

        adapter = NfcAdapter.getDefaultAdapter(this)

        if (isNFCEnabled()) {
            configureNFC()
        }else{
            Toast.makeText(this, "NFC not found or has not been enabled", Toast.LENGTH_LONG).show()
        }
    }

    private fun isNFCEnabled() = adapter != null && adapter?.isEnabled == true

    private fun checkForLogin() {
        val isUserLoggedIn = readSharedSetting(applicationContext, MainActivity.PREF_USER_LOGGED_IN, "false")!!.toBoolean()

        if (isUserLoggedIn) {
            // All good
        } else {
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun configureNFC() {


        val intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val ndef = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED).apply {
            try {
                addDataType("*/*")    /* Handles all MIME based dispatches.
                                     You should specify only the ones that you need. */
            } catch (e: IntentFilter.MalformedMimeTypeException) {
                throw RuntimeException("fail", e)
            }
        }
        intentFiltersArray = arrayOf(ndef)
        techListsArray = arrayOf(arrayOf(NfcA::class.java.name, MifareUltralight::class.java.name))
        Log.d("NFC", "<!--- NFC Configured --->")
    }

    override fun onSupportNavigateUp() = Navigation.findNavController(this, R.id.nav_host_main_fragment).navigateUp()

    public override fun onPause() {
        super.onPause()
        if (isNFCEnabled())
            adapter?.disableForegroundDispatch(this)
    }

    public override fun onResume() {
        super.onResume()
        if (isNFCEnabled())
            adapter?.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray)
    }

    public override fun onNewIntent(intent: Intent) {
        Log.d("NFC", "<!--- NFC Detected with Message")
        Log.d("NFC", "<!--- NFC Detected with Message = ${intent.action}" )
        val message = retrieveNFCMessage(intent)
        Log.d("NFC", "<!--- NFC Detected with Message = $message --->")
        Log.d("NFC", "<!--- NFC Listener  = $nfcTagListener --->")

       if (message == "") {
           nfcTagListener?.onNfcTagRead("Blank Tag")
        } else {
           nfcTagListener?.onNfcTagRead(message)
        }
     }

    private fun retrieveNFCMessage(intent: Intent?): String {
        intent?.let {
            if (NfcAdapter.ACTION_TECH_DISCOVERED == intent.action || NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
                val nDefMessages = getNDefMessages(intent)
                nDefMessages[0].records?.let {
                    it.forEach {
                        it?.payload.let {
                            it?.let {
                                return String(it)
                            }
                        }
                    }
                }

            } else {
                return "Touch NFC tag to read data"
            }
        }
        return "Touch NFC tag to read data"
    }

    private fun getNDefMessages(intent: Intent): Array<NdefMessage> {

        val rawMessage = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
        rawMessage?.let {
            return rawMessage.map {
                it as NdefMessage
            }.toTypedArray()
        }
        // Unknown tag type
        val empty = byteArrayOf()
        val record = NdefRecord(NdefRecord.TNF_UNKNOWN, empty, empty, empty)
        val msg = NdefMessage(arrayOf(record))
        return arrayOf(msg)
    }

    companion object {

        val PREFERENCES_FILE = "parsl.co"
        val PREF_USER_FIRST_TIME = "user_first_time"
        val PREF_USER_LOGGED_IN = "user_logged_in"
        val PREF_USER_ID_TOKEN = "user_id_token"

        fun readSharedSetting(ctx: Context, settingName: String, defaultValue: String): String? {
            val sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE)
            return sharedPref.getString(settingName, defaultValue)
        }

        fun saveSharedSetting(ctx: Context, settingName: String, settingValue: String) {
            val sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString(settingName, settingValue)
            editor.apply()
        }
    }

    private fun checkForPermissions() {

        Log.d("flow", "Checking for permissions")

        val nfcPermission = ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.NFC)
        val cameraPermission = ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
        val locationPermission = ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION)

        val permissions = arrayListOf<String>()

        if (nfcPermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.NFC)
        }

        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CAMERA)
        }

        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        handlePermissions(permissions)
    }

    private fun handlePermissions(permissions: ArrayList<String>) {
        if (permissions.isEmpty()) {

        } else {
            startActivity(Intent(this, LaunchActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_settings -> {
                true
            }
            R.id.action_logout -> {
                this.finish()
                MainActivity.saveSharedSetting(applicationContext, MainActivity.PREF_USER_LOGGED_IN, "false")
                startActivity(Intent(this,MainActivity::class.java ).apply {
                    addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                })
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun setNfcTagListener(listener: NfcTagListener?) {
        nfcTagListener = listener
    }
}
