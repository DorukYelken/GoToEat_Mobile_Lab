package com.example.gotoeat_mobile_lab

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.gotoeat_mobile_lab.databinding.ActivitySignPageBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SignPage : AppCompatActivity() {
     private lateinit var binding: ActivitySignPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivitySignPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = FirebaseDatabase.getInstance().reference

        binding.register2.setOnClickListener{
            val username = binding.username.text.toString()    //taking username information from user typings.
            val email2 = binding.email2.text.toString()        //taking email information from user typings.
            val password2 = binding.password2.text.toString()  //taking password information from user typings.

            val usernameRef = data.child(username)
            usernameRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {    //ı used onDataChange here because i need informations from database. App will use those informations to compare from user types.
                    if (snapshot.exists()) {     //If this username is in the database app will give warning.
                        binding.alreadyEmail.text = "This Username is already taken."
                    } else {
                        val emailRef = data.orderByChild("emaill").equalTo(email2)
                        emailRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(emailSnapshot: DataSnapshot) {

                                if (emailSnapshot.exists()) { //If this email is in the database app will give warning.

                                    binding.alreadyEmail.text = "This Email is already registered."
                                } else {
                                   //if all those username and email informations are not same with database. We can save and accept those informations.
                                    usernameRef.setValue(TakeInfo(email2, password2))  //Adding informations to database (under the username)

                                    val intent = Intent(this@SignPage, MainActivity::class.java) //After taking informations to the database. App will show MainActivity page to login.
                                    startActivity(intent)
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                            }
                        })
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }


        val buttonClick = findViewById<Button>(R.id.GoToLoginPage)
        buttonClick.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)  // To go to login page
            startActivity(intent)
        }
    }
}