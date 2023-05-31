package com.example.gotoeat_mobile_lab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.gotoeat_mobile_lab.databinding.ActivityMainBinding
import com.google.android.play.integrity.internal.x


import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
         lateinit var binding: ActivityMainBinding
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = FirebaseDatabase.getInstance().reference   // database name is data.

        val buttonClick = findViewById<Button>(R.id.register)  // when user click the register button, app will show Sign Page.
        buttonClick.setOnClickListener {
            val intent = Intent(this, SignPage::class.java)
            startActivity(intent)
        }

        val buttonClick2 = findViewById<Button>(R.id.login)   // when user click the login button, user will login the app. (if informations are correct)
        buttonClick2.setOnClickListener {


            val getdata = object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {     //app is using onDataChange snapshot to get data from data base.
                    for(i in snapshot.children){        //App use this for loop to traverse each data.

                        var email = i.child("emaill").getValue()  // Traverse every email informations form database. emaill caming from TakeInfo.
                        var password = i.child("passwordd").getValue()  // Traverse every password informations form database. passwordd caming from TakeInfo

                        val EmailTextField=binding.email.text.toString() //Taking email information that typed by user.
                        val PasswordTextField=binding.password.text.toString()  //Taking password information that typed by user.
                        if(email == EmailTextField ){
                            if(password == PasswordTextField){   //if email and password matching with database information app will show page3 (it means app will approve your login informations.).
                                    val intent2 = Intent(this@MainActivity, page3::class.java)
                                    startActivity(intent2)

                            }
                        }

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            }
            data.addValueEventListener(getdata)
            data.addListenerForSingleValueEvent(getdata)
        }









    }
}