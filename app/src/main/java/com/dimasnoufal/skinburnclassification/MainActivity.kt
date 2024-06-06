package com.dimasnoufal.skinburnclassification

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dimasnoufal.skinburnclassification.databinding.ActivityMainBinding
import com.dimasnoufal.skinburnclassification.ml.MobilenetSkinBurnClassification
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.lang.Integer.min
import java.nio.ByteBuffer
import java.nio.ByteOrder


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    var imageSize: Int = 224

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCamera.isEnabled = true

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                101
            )
        } else {
            binding.btnCamera.isEnabled = true
        }

        binding.btnCamera.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, 1)
        }

        if (Build.VERSION.SDK_INT > 32) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_IMAGES
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                    102
                )
            } else {
                binding.btnGallery.setOnClickListener {
                    val intent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(intent, 2)
                }
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    103
                )
            } else {
                binding.btnGallery.setOnClickListener {
                    var intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(
                        Intent.createChooser(intent, "Pilih Gambar"),
                        2
                    )
                }
            }
        }
    }

    fun classifyImage(image: Bitmap) {
        try {
            val model: MobilenetSkinBurnClassification =
                MobilenetSkinBurnClassification.newInstance(applicationContext)
            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
            byteBuffer.order(ByteOrder.nativeOrder())
            val intValues = IntArray(imageSize * imageSize)
            image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
            var pixel = 0
            for (i in 0 until imageSize) {
                for (j in 0 until imageSize) {
                    val `val` = intValues[pixel++]
                    byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 1))
                    byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 1))
                    byteBuffer.putFloat((`val` and 0xFF) * (1f / 1))
                }
            }
            inputFeature0.loadBuffer(byteBuffer)
            val outputs: MobilenetSkinBurnClassification.Outputs = model.process(inputFeature0)
            val outputFeature0: TensorBuffer = outputs.outputFeature0AsTensorBuffer
            val confidences = outputFeature0.floatArray
            var maxPos = 0
            var maxConfidence = 0f
            for (i in confidences.indices) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i]
                    maxPos = i
                }
            }
            val classes = arrayOf(
                "First Degree Burn Superficial Thickness",
                "Second Degree Burn Partial of Intermediate Thickness",
                "Third Degree Burn Full hickness",
                "No Sunburn"
            )
            val resultText = """
            Class: ${classes[maxPos]}
            
            """.trimIndent()
            binding.tvClass.text = resultText
            model.close()

            // Display the result as a toast message with a delay of 3 seconds
            val handler = Handler()
            handler.postDelayed(Runnable {
                Toast.makeText(
                    this@MainActivity,
                    resultText,
                    Toast.LENGTH_SHORT
                ).show()
            }, 1000)
        } catch (e: IOException) {
            // Exception handling
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            101 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    binding.btnCamera.isEnabled = true

                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
                return
            }

            102 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                    binding.btnGallery.setOnClickListener {
//                        val intent =
//                            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//                        startActivityForResult(intent, 2)
//                    }

                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
                return
            }

            103 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
////                    binding.btnGallery.setOnClickListener {
////                        var intent = Intent(Intent.ACTION_PICK)
////                        intent.type = "image/*"
////                        startActivityForResult(
////                            Intent.createChooser(intent, "Pilih Gambar"),
////                            2
////                        )
//                    }
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            var pic: Bitmap? = data?.getParcelableExtra<Bitmap>("data")
            binding.ivPreview.setImageBitmap(pic)

//            val image = data.extras?.get("data") as Bitmap
//            val dimension = min(image.width, image.height)
//            val thumbnail = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
//            binding.ivPreview.setImageBitmap(image)

            val scaledImage =
                Bitmap.createScaledBitmap(pic!!, imageSize, imageSize, false)
            classifyImage(scaledImage)
        }

        if (resultCode == RESULT_OK) {
            val imageUri = data?.data
            Log.d("IMAGE-FOTO", "onActivityResult: $imageUri")
            //  Proses load image ke ImageView
            if (imageUri != null) {
                when (requestCode) {
                    2 -> {
                        var image: Bitmap? = null
                        try {
                            image =
                                MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        binding.ivPreview.setImageBitmap(image)

                        image = Bitmap.createScaledBitmap(image!!, imageSize, imageSize, false)
                        classifyImage(image)
                    }

                    3 -> {
                        var image: Bitmap? = null
                        try {
                            image =
                                MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        binding.ivPreview.setImageBitmap(image)

                        image = Bitmap.createScaledBitmap(image!!, imageSize, imageSize, false)
                        classifyImage(image)
                    }
                }
            }
        }
    }
}

