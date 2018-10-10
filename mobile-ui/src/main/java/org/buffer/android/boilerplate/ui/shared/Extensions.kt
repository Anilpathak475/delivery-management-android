package org.buffer.android.boilerplate.ui.shared

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations

/** Uses `Transformations.map` on a LiveData */
fun <X, Y> LiveData<X>.map(body: (X) -> Y): LiveData<Y> {
    return Transformations.map(this, body)
}