package com.example.core3

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var adapter : ClubAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var toolBar : androidx.appcompat.widget.Toolbar
    private var data: List<Club> = listOf()
    private var showingSorted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolBar = findViewById(R.id.navBar)
        setSupportActionBar(toolBar)

        val listView = findViewById<RecyclerView>(R.id.clubList)

        linearLayoutManager = LinearLayoutManager(this)
        listView.layoutManager = linearLayoutManager

        adapter = ClubAdapter()
        listView.adapter = adapter

        data = handleCSV()
        adapter.submitList(data)
    }

    private fun handleCSV() : List<Club> {
        val list = mutableListOf<Club>()

        resources.openRawResource(R.raw.groups).bufferedReader().forEachLine {
            val temp = it.split(",").map { it.trim() }
            if (temp.size >= 5) {
                try {
                    val parsedDate = parseDateTime(temp[4])
                    list.add(Club(temp[0].toInt(), temp[1], temp[2], temp[3], parsedDate))
                } catch (e: Exception) {
                    // Catch any potential issues during parsing (e.g., non-integer IDs)0
                    e.printStackTrace()
                }
            }
        }
        return list.sortedBy { it.dateTime }
    }

    private fun parseDateTime(dateTimeStr: String): LocalDateTime {
        val pattern = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm")
        return LocalDateTime.parse(dateTimeStr, pattern)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sort -> {
                if (showingSorted) {
                    adapter.submitList(data)
                } else {
                    val sortedList = sortedTech(data)
                    adapter.submitList(sortedList)
                }
                showingSorted = !showingSorted
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun sortedTech(clubs : List<Club>) : List<Club>{
        return clubs.filter { it.type == "Tech" }.sortedBy { it.id }
    }
}
