package me.ckn.music.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import me.ckn.music.R
import android.widget.Button
import android.content.Intent

class DhActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // 设置布局为 author-activity.xml
        setContentView(R.layout.author_activity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.A)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        // 找到cancel1按钮
        val cancelBtn = findViewById<Button>(R.id.cancel1)
        cancelBtn.setOnClickListener {
            // 跳转回MainActivity，并关闭当前页面
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }
    }


}

