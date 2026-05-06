package com.barron

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_PERMISSIONS = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissions()

        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {

            val userId = user.uid
            val database = FirebaseDatabase.getInstance().reference

            database.child("usuarios").child(userId)
                .addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {

                        if (snapshot.exists()) {
                            startActivity(Intent(this@MainActivity, MenuActivity::class.java))
                        } else {
                            startActivity(Intent(this@MainActivity, UserDataActivity::class.java))
                        }

                        finish()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        finish()
                    }
                })

        } else {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    private fun requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.POST_NOTIFICATIONS
        )

        val permissionsToRequest = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    println("${permissions[i]} concedido")
                } else {
                    println("${permissions[i]} denegado")
                }
            }
        }
    }
}