package com.example.mytask.activity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.mytask.R
import com.example.mytask.adapter.ImageAdapter
import com.example.mytask.model.Images
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var toolbar: Toolbar
    private lateinit var recyclerViewImages:RecyclerView
    private val mJSONURLString="http://jsonplaceholder.typicode.com/photos"
    private lateinit var imagesList: ArrayList<Images>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // initializing views
        initViews()

        imagesList=gettingStoredData()

        if (imagesList.size>0)
        {
            setAdapter()
        }
        else if (isNetworkConnection())
        {
            fetchImages()
        }
        else{
            Toast.makeText(baseContext,"Please check internet connection...!!!",Toast.LENGTH_LONG).show()
        }

    }

    private fun setAdapter(){
        recyclerViewImages.adapter=ImageAdapter(this,imagesList)
    }

    private fun gettingStoredData():ArrayList<Images> {

        try {

            imagesList= ArrayList()
            val prefs = getSharedPreferences("ImageList", MODE_PRIVATE)
            val gson = Gson()
            val json = prefs.getString("MyObject", "")
            val type = object : TypeToken<List<Images>>() {

            }.type
            imagesList = gson.fromJson<ArrayList<Images>>(json, type)
        }catch (e:Exception){

        }




        return imagesList
    }

    private fun fetchImages() {

        progressBar.visibility=View.VISIBLE
        imagesList= ArrayList<Images>()
        val requestQueue = Volley.newRequestQueue(this)

        // Initialize a new JsonArrayRequest instance
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            mJSONURLString,
            null,
            Response.Listener { response ->

                try {
                    progressBar.visibility=View.GONE

                    // Loop through the array elements
                    for (i in 0 until response.length()) {
                        // Get current json object
                        val image = response.getJSONObject(i)

                        val imageModel= Images(image.getString("title"),image.getString("url"))

                        imagesList.add(imageModel)

                    }

                    //setting adapter

                    setAdapter()

                    //storing data in a list
                    storeData()

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                progressBar.visibility=View.GONE
            }
        )

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest)
    }

    private fun storeData(){

        val editor = getSharedPreferences("ImageList", MODE_PRIVATE).edit()
        val gson = Gson()
        val json = gson.toJson(imagesList)
        editor.putString("MyObject", json)
        editor.apply()
    }

    private fun initViews() {

        progressBar=findViewById(R.id.progress_circular)

        toolbar=findViewById(R.id.toolBar)
        setSupportActionBar(toolbar)

        recyclerViewImages=findViewById(R.id.recyclerViewImages)

        // Creates a vertical Layout Manager
        recyclerViewImages.layoutManager = LinearLayoutManager(this)

        // You can use GridLayoutManager if you want multiple columns. Enter the number of columns as a parameter.
        recyclerViewImages.layoutManager = GridLayoutManager(this, 2)

        // Access the RecyclerView Adapter and load the data into it
//        recyclerViewImages.adapter = AnimalAdapter(animals, this)
    }

    private fun isNetworkConnection(): Boolean {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo

        return networkInfo != null && networkInfo.isConnected
    }
}
