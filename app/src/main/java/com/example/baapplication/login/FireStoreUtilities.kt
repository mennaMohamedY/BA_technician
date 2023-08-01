package com.example.baapplication.login

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User

class FireStoreUtilities {
    fun insertToDB(user:User){
        val db=FirebaseFirestore.getInstance()
        val doc=db.collection("Technicians")
        val docRefM=doc.document("Tech_Mahmoud")
        val docRefR=doc.document("Tech_Reda")
        val docRefH=doc.document("Tech_Hany")
        val docRefB=doc.document("Tech_Bahrawy")

    }
}