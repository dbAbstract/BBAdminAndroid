package za.co.bb.core.di

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

object CoreDependencyContainer {
    val firebaseFirestore by lazy {
        Firebase.firestore
    }
}