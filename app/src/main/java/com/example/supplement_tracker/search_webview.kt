package com.example.supplement_tracker

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.supplement_tracker.databinding.ActivitySearchWebviewBinding
import com.googlecode.tesseract.android.TessBaseAPI
import kotlinx.coroutines.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class search_webview : AppCompatActivity() {

    val binding by lazy { ActivitySearchWebviewBinding.inflate(layoutInflater) }
    var realURI: Uri? = null

    lateinit var tess: TessBaseAPI
    var dataPath: String = ""
    var ocrResult: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var searchString = ""
        GlobalScope.launch(Dispatchers.Main) {

            launch {
                binding.progressBar.visibility = View.VISIBLE
            }

            searchString = async {
                var bitmap: Bitmap? = null
                realURI = intent.getParcelableExtra("photoURI")
                realURI?.let { bitmap = loadBitmap(realURI!!, this@search_webview) }
                /* tessData 사용 */
                dataPath = filesDir.toString() + "/tesseract/"
                checkFile(File(dataPath + "tessdata"), "kor")
                checkFile(File(dataPath + "tessdata"), "eng")
                var lang = "kor+eng"
                tess = TessBaseAPI()
                var dataPath: String = filesDir.toString() + "/tesseract/"
                tess.init(dataPath, lang)
                processImage(bitmap)
            }.await()

            binding.progressBar.visibility = View.GONE
            contentResolver.delete(realURI!!, null, null)
            binding.searchWebView.apply {
                settings.javaScriptEnabled = true
                loadUrl("https://www.google.com/search?q=$searchString")
            }
        }
    }

    /* 언어파일 경로에 복사 */
    fun copyFile(lang: String) {
        try {
            var filePath: String = dataPath + "/tessdata/" + lang + ".traineddata"
            var assetManager = assets
            var inputStream = assetManager.open("tessdata/" + lang + ".traineddata")
            var outStream = FileOutputStream(filePath)
            var buffer = ByteArray(1024)

            var read = 0
            read = inputStream.read(buffer)
            while (read != -1) {
                outStream.write(buffer, 0, read)
                read = inputStream.read(buffer)
            }
            outStream.flush()
            outStream.close()
            inputStream.close()

        } catch (e: FileNotFoundException) {
            Log.v("오류발생", e.toString())
        } catch (e: IOException) {
            Log.v("오류발생", e.toString())
        }
    }

    fun checkFile(dir: File, lang: String) {
        //파일의 존재여부 확인 후 내부로 복사
        if (!dir.exists() && dir.mkdirs()) {
            copyFile(lang)
        }

        if (dir.exists()) {
            var datafilePath: String = dataPath + "/tessdata/" + lang + ".traineddata"
            var dataFile: File = File(datafilePath)
            if (!dataFile.exists()) {
                copyFile(lang)
            }
        }
    }

    fun processImage(bitmap: Bitmap?): String {
        if (bitmap != null) {
            tess.setImage(bitmap)
            ocrResult = tess.utF8Text
        } else {
            Toast.makeText(this, "Error...Please Retry", Toast.LENGTH_SHORT).show()
        }
        binding.progressBar.visibility = View.GONE
        return ocrResult
    }
}