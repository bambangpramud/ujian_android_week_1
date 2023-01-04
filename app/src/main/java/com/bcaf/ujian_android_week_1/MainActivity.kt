package com.bcaf.ujian_android_week_1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_check_in.*
import kotlinx.android.synthetic.main.fragment_ijin.*
import kotlinx.android.synthetic.main.fragment_login.*

class MainActivity : AppCompatActivity() {
    companion object{
        private val REQUEST_CODE_PERMISSION = 999
        private val REQUEST_CAMERA_PERMISSION = 100
    }


    var orientation:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        orientation=resources.configuration.orientation
        if (orientation== Configuration.ORIENTATION_PORTRAIT){
            loadFragmentFirst(LoginFragment.newInstance("",""))
        }
    }

    fun loadFragmentFirst(fragment : Fragment){
        val fragManager = supportFragmentManager.beginTransaction()
        fragManager.replace(R.id.vFragment,fragment)

        fragManager.commit()
    }
    fun loadFragment(fragment : Fragment){
        val fragManager = supportFragmentManager.beginTransaction()
        fragManager.replace(R.id.vFragment,fragment)
        fragManager.addToBackStack("")
        fragManager.commit()
    }
    fun checkIn(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permissions,REQUEST_CODE_PERMISSION)
            } else{
                captureCamera()
            }
        }
    }
    fun captureCamera(){
        val takeCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takeCamera, REQUEST_CAMERA_PERMISSION)

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    captureCamera();
                }else{
                    Toast.makeText(this,"Maaf Permission Denied", Toast.LENGTH_LONG).show();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== REQUEST_CAMERA_PERMISSION && resultCode== RESULT_OK){
            val bitmapImage = data?.extras?.get("data") as Bitmap
            imgCheckin.setImageBitmap(bitmapImage)
            Toast.makeText(applicationContext,"Check In Berhasil",Toast.LENGTH_LONG).show()
            imgCheckin.setImageBitmap(bitmapImage)
//            imgIjin1.setImageBitmap(bitmapImage)
//            saveImage(bitmapImage)

        }else{
            Toast.makeText(applicationContext,"Check In Gagal",Toast.LENGTH_LONG).show()
        }


    }



}