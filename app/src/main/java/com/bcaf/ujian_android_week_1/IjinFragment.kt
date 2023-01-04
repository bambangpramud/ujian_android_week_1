package com.bcaf.ujian_android_week_1

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_ijin.*
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [IjinFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IjinFragment : Fragment() {
    var isAddIjin1 = false;
    var isAddIjin2 = false;
    var isAddIjin3 = false;

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment IjinFragment.
         */
        private val REQUEST_CODE_PERMISSION = 999;
        private val CAMERA_REQUEST_CAPTURE_1 = 666;
        private val CAMERA_REQUEST_CAPTURE_2 = 777;
        private val CAMERA_REQUEST_CAPTURE_3 = 888;

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            IjinFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ijin, container, false)
    }

    fun pickDate(code:Number){
        val c = Calendar.getInstance()
        val dateSetListener = object : DatePickerDialog.OnDateSetListener{
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int
            ){
                c.set(Calendar.YEAR,year)
                c.set(Calendar.MONTH,monthOfYear)
                c.set(Calendar.DAY_OF_MONTH,dayOfMonth)

                val myFormat = "dd/MM/yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
                if (code.equals(1)){
                    txtDateAwal.setText(sdf.format(c.getTime()));
                }else{
                    txtDateAkhir.setText(sdf.format(c.getTime()));
                }
            }
        }

        DatePickerDialog((activity as MainActivity),dateSetListener,c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(
            Calendar.DAY_OF_MONTH)).show() //parameter c.get adalah inisialisasi awal kalender yaitu hari ini
    }

    fun captureCamera(code: Int) {
        val takeCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takeCamera, code);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IjinFragment.CAMERA_REQUEST_CAPTURE_1 && resultCode == AppCompatActivity.RESULT_OK) {
            val bitmapImage = data?.extras?.get("data") as Bitmap;
            imgIjin1.setImageBitmap(bitmapImage);
            isAddIjin1 = true;

        } else if (requestCode == IjinFragment.CAMERA_REQUEST_CAPTURE_2 && resultCode == AppCompatActivity.RESULT_OK) {
            val bitmapImage = data?.extras?.get("data") as Bitmap;
            imgIjin2.setImageBitmap(bitmapImage);
            isAddIjin2 = true;

        } else if (requestCode == IjinFragment.CAMERA_REQUEST_CAPTURE_3 && resultCode == AppCompatActivity.RESULT_OK) {
            val bitmapImage = data?.extras?.get("data") as Bitmap;
            imgIjin3.setImageBitmap(bitmapImage);
            isAddIjin3 = true;

        } else if (resultCode == AppCompatActivity.RESULT_CANCELED) {
            Toast.makeText(activity, "Foto Dibatalkan", Toast.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            IjinFragment.REQUEST_CODE_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED ){

                }else{
                    Toast.makeText(activity,"Maaf Permission Denied", Toast.LENGTH_LONG).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        imgDateAwal.setOnClickListener( {
            pickDate(1)
        })
        imgDateAkhir.setOnClickListener( {
            pickDate(2)
        })
        imgIjin1.setOnClickListener( {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (activity?.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                ) {
                    val permissions = arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    requestPermissions(permissions, IjinFragment.REQUEST_CODE_PERMISSION)
                } else {
                    captureCamera(CAMERA_REQUEST_CAPTURE_1)
                }
            }
        })
        imgIjin2.setOnClickListener( {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (activity?.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                ) {
                    val permissions = arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    requestPermissions(permissions, IjinFragment.REQUEST_CODE_PERMISSION)
                } else {
                    captureCamera(CAMERA_REQUEST_CAPTURE_2)
                }
            }
        })
        imgIjin3.setOnClickListener( {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (activity?.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                ) {
                    val permissions = arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    requestPermissions(permissions, IjinFragment.REQUEST_CODE_PERMISSION)
                } else {
                    captureCamera(CAMERA_REQUEST_CAPTURE_3)
                }
            }
        })


    }

}