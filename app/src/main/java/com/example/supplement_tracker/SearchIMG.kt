package com.example.supplement_tracker

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.supplement_tracker.databinding.ActivitySearchimgBinding
import java.io.IOException
import java.text.SimpleDateFormat

class SearchIMG : AppCompatActivity(), View.OnClickListener {

    val binding by lazy { ActivitySearchimgBinding.inflate(layoutInflater) }
    lateinit var resultListener: ActivityResultLauncher<Intent>
    var realURI: Uri? = null
//    var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        resultListener = openCamera()

        with(binding) {
            CallCameraBtn.setOnClickListener(this@SearchIMG)
            SearchImgBtn.setOnClickListener(this@SearchIMG)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.CallCameraBtn.id -> {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                createIMGUri(newFileName(), "image/jpg")?.let { uri ->
                    realURI = uri
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, realURI)
                    resultListener.launch(intent)
                }
            }
            binding.SearchImgBtn.id -> {
                val intent = Intent(this,search_webview::class.java)
                intent.putExtra("photoURI",realURI)
                realURI = null
                startActivity(intent)
            }
        }
    }

    /* 카메라 호출 연결 함수 */
    fun openCamera(): ActivityResultLauncher<Intent> {
        resultListener = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                realURI?.let { uri ->
                    var bitmap = loadBitmap(uri)
                    binding.imagePreview.setImageBitmap(bitmap)
                    binding.SearchImgBtn.visibility = View.VISIBLE
//                    realURI = null
//                    contentResolver.delete(uri!!, null, null)
                }
            }
        }
        return resultListener
    }

    /* 촬영 이미지 미디어스토어 저장 함수 */
    fun createIMGUri(filename: String, mimeType: String): Uri? {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    }

    /* 사진저장 파일명 생성 함수 */
    fun newFileName(): String {
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmSS")
        val fileName = sdf.format(System.currentTimeMillis())
        return "$fileName.jpg"
    }

    /* URI 미디어스토어 저장된 이미지 읽어오는 함수 */
    fun loadBitmap(photoURI: Uri): Bitmap? {
        var image: Bitmap? = null
        try {
            image = if (Build.VERSION.SDK_INT > 27) {
                val source: ImageDecoder.Source = ImageDecoder.createSource(this.contentResolver, photoURI)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(this.contentResolver, photoURI)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return image
    }
}
