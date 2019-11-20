package com.example.mytask.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.example.mytask.R
import com.squareup.picasso.Picasso
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View


class FullScreenActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen)

        toolbar=findViewById(R.id.toolBar)
        setSupportActionBar(toolbar);
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()?.setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val i = Intent(this@FullScreenActivity, MainActivity::class.java)
                startActivity(i)
                finish()
            }
        })

        val url=intent.getStringExtra("IMAGE_URL")

        imageView=findViewById(R.id.imageView)

        Picasso.get().load(url).into(imageView)

    }
}
