package com.example.trial4

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trial4.models.historyPostItem
import com.example.trial4.models.lineItemResult
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.Calendar
import java.util.Date

private const val TAG = "SuscheckActivity"
private const val API_KEY ="**"

class SuscheckActivity : AppCompatActivity() {

    private lateinit var lineItemJson : ArrayList<String>
    private lateinit var s3Url : String
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suscheck)

        val imageUriString = intent.getStringExtra("imageUri")
        val selectedImageUri = Uri.parse(imageUriString)
        Log.i(TAG,"URI of image selected is $selectedImageUri")
        // Call the uploadImage function
        uploadImage(selectedImageUri)


    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun uploadImage(selectedImageUri: Uri) {

        selectedImageUri.let{

            //Gemini Initialization
            val generativeModel = GenerativeModel(
                // For text-only input, use the gemini-pro model
                modelName = "gemini-pro",
                // Access your API key as a Build Configuration variable (see "Set up your API key" above)
                apiKey = API_KEY
            )

            //Base 64 encoding
            val imageBytes = Files.readAllBytes(Paths.get(getRealPathFromURI(selectedImageUri,applicationContext)))
            val sImage = Base64.encodeToString(imageBytes, Base64.DEFAULT).replace("\n", "")
            Log.i(TAG,"encoded string $sImage")

            //Retrofit
            val retrofit = Retrofit.Builder()
                .baseUrl("aws-url")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(ApiService::class.java)
            apiService.uploadImage(sImage).enqueue(object : Callback<lineItemResult>{
                override fun onResponse(
                    call: retrofit2.Call<lineItemResult>,
                    response: retrofit2.Response<lineItemResult>
                ) {
                    if(response.isSuccessful) {


                        lineItemJson = response.body()?.lineItems as ArrayList<String>
                        s3Url = response.body()?.s3Url as String

                        Log.i(TAG, "line items response is $lineItemJson")
                        Log.i(TAG,"S3URL received is $s3Url")


                        CoroutineScope(Dispatchers.Main).launch {
                            val responseMap = mutableMapOf<String, String>()
                            var finalResult = ""
                            var countSustainable = 0
                            var countNonSustainable = 0
                            for (item in lineItemJson) {

                                val response = withContext(Dispatchers.IO) {
                                    generativeModel.generateContent("Is $item sustainable or not. Please strictly give me one word answer only. Your answer should be either 'Sustainable' or 'Non-Sustainable'. If you don't know then default is 'Non-Sustainable'")
                                }
                                Log.i(TAG, "$item is ${response.text}")
                                responseMap[item] = response.text.toString()
                                if (response.text.toString()=="Sustainable"){
                                    countSustainable += 1
                                }
                                else{
                                    countNonSustainable += 1
                                }
                            }

                            Log.i(TAG,"response Map is $responseMap")

                            /*
                            val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
                            recyclerView.layoutManager = LinearLayoutManager(this@SuscheckActivity)

                            // Assuming responseMap is your map of key-value pairs
                            val adapter = KeyValueAdapter(responseMap)
                            recyclerView.adapter = adapter
                            */

                            val tableLayout: TableLayout = findViewById(R.id.table_layout)

                            // Assuming responseMap is your map of key-value pairs
                            for ((key, value) in responseMap) {
                                val tableRow = TableRow(this@SuscheckActivity)

                                val keyTextView = TextView(this@SuscheckActivity)

                                val valueTextView = TextView(this@SuscheckActivity)

                                val iconView = TextView(this@SuscheckActivity)


                                keyTextView.text = key
                                valueTextView.text = value

                                if(value == "Sustainable"){
                                    iconView.text = "✅"
                                }
                                else{
                                    iconView.text = "❌"
                                }

                                // Add margin between key and value
                                /*
                                val layoutParams = TableRow.LayoutParams(
                                    TableRow.LayoutParams.WRAP_CONTENT,
                                    TableRow.LayoutParams.WRAP_CONTENT
                                )
                                layoutParams.setMargins(0, 0, 20, 0)

                                keyTextView.layoutParams = layoutParams
                                   */

                                keyTextView.layoutParams = TableRow.LayoutParams(0, 75, 1f)
                                valueTextView.layoutParams = TableRow.LayoutParams(0, 75, 1f)
                                iconView.layoutParams = TableRow.LayoutParams(0, 75, 1f)

                                keyTextView.gravity = Gravity.CENTER
                                valueTextView.gravity = Gravity.CENTER
                                iconView.gravity = Gravity.CENTER



                                tableRow.addView(keyTextView)
                                tableRow.addView(valueTextView)
                                tableRow.addView(iconView)


                                tableLayout.addView(tableRow)
                            }

                            val tvRemark : TextView = findViewById(R.id.tvRemark)
                            tvRemark.setTextSize(24F)
                            tvRemark.gravity = Gravity.CENTER_HORIZONTAL

                            if (countSustainable>countNonSustainable){
                                finalResult = "Sustainable"
                                tvRemark.text = "Great Work !! Keep up the good habit \uD83D\uDE0A"
                                tvRemark.setTextColor(Color.GREEN)


                            }
                            else{
                                finalResult = "Non-Sustainable"
                                tvRemark.text = "Consider choosing eco-friendly alternatives for a greener impact on your next purchase \uD83C\uDF0E"
                                tvRemark.setTextColor(Color.RED)
                            }

                            val year = Calendar.getInstance().get(Calendar.YEAR)
                            val month = Calendar.getInstance().get(Calendar.MONTH)
                            val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                            val timeStamp : String = "$year-0$month-$day"

                            uploadHistory(s3Url,finalResult,timeStamp)



                        }

                    }
                    else{
                        Log.i(TAG,"No successful response")
                    }
                }

                override fun onFailure(call: retrofit2.Call<lineItemResult>, t: Throwable) {
                    Log.i(TAG,"$t failed")
                }

            })

        }

    }

    private fun uploadHistory(s3Url: String,result:String, timeStamp: String) {
        val historyPostItem = historyPostItem(s3Url, result, timeStamp)
        val historyretrofit = Retrofit.Builder()
                        .baseUrl("aws-url")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

        val historyApiService = historyretrofit.create(HistoryPostService::class.java)
        historyApiService.uploadResult(historyPostItem).enqueue(object : Callback<Any>{
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                Log.i(TAG,"dynamodB history upload response is $response")
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.i(TAG,"Item upload failed")
            }

        })
    }


    private fun getRealPathFromURI(uri: Uri, context: Context): String? {
        val returnCursor = context.contentResolver.query(uri, null, null, null, null)
        val nameIndex =  returnCursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        val sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE)
        returnCursor.moveToFirst()
        val name = returnCursor.getString(nameIndex)
        val size = returnCursor.getLong(sizeIndex).toString()
        val file = File(context.filesDir, name)
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(file)
            var read = 0
            val maxBufferSize = 1 * 1024 * 1024
            val bytesAvailable: Int = inputStream?.available() ?: 0
            //int bufferSize = 1024;
            val bufferSize = Math.min(bytesAvailable, maxBufferSize)
            val buffers = ByteArray(bufferSize)
            while (inputStream?.read(buffers).also {
                    if (it != null) {
                        read = it
                    }
                } != -1) {
                outputStream.write(buffers, 0, read)
            }
            Log.e("File Size", "Size " + file.length())
            inputStream?.close()
            outputStream.close()
            Log.e("File Path", "Path " + file.path)

        } catch (e: java.lang.Exception) {
            Log.e("Exception", e.message!!)
        }
        Log.i(TAG,"The file path is $file.path")
        return file.path
    }
}