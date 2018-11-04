package co.parsl.android.boilerplate.ui.home

interface NfcTagListener {
    fun onNfcTagRead(message: String)
}