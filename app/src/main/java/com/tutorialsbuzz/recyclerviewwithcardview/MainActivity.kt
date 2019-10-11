package com.tutorialsbuzz.recyclerviewwithcardview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tutorialsbuzz.recyclerview.CustomAdapter
import com.tutorialsbuzz.recyclerview.Model
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val modelList = readFromAsset()

        val adapter = CustomAdapter(modelList, this)

        rcv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false) as RecyclerView.LayoutManager?
        rcv.adapter = adapter;

        adapter.setOnItemClickListener(object : CustomAdapter.ClickListener {
            override fun onClick(pos: Int, aView: View) {
                Toast.makeText(this@MainActivity, modelList.get(pos).name, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun readFromAsset(): List<Model> {

        val modeList = mutableListOf<Model>()
        val bufferReader = application.assets.open("android_version.json").bufferedReader()
        val json_string = bufferReader.use {
            it.readText()
        }

        val jsonArray = JSONArray(json_string);
        for (i in 0..jsonArray.length() - 1) {
            val jsonObject: JSONObject = jsonArray.getJSONObject(i)
            val model = Model(jsonObject.getString("name"), jsonObject.getString("version"))
            modeList.add(model)
        }
        return modeList
    }

}
