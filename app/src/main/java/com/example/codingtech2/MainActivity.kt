package com.example.codingtech2

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var etUserId: EditText
    private lateinit var etEditUser: EditText
    private lateinit var btnRemove: Button
    private lateinit var btnEdit: Button
    private lateinit var userAdapter: UserAdapter
    private val userList = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Fake data
        userList.addAll(
            listOf(
                User(1, "Test User 1", 1697389200),
                User(2, "Test User 2", 1697475600),
                User(3, "Test User 3", 1697562000)
            )
        )

        recyclerView = findViewById(R.id.recyclerView)
        etUserId = findViewById(R.id.etUserId)
        etEditUser=findViewById(R.id.etEditUser)
        btnRemove = findViewById(R.id.btnRemove)
        btnEdit = findViewById(R.id.btnEdit)

        userAdapter = UserAdapter(userList) { user ->
            showUserDetails(user)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = userAdapter

        btnRemove.setOnClickListener {
            val idText = etUserId.text.toString()
            if (idText.isNotEmpty()) {
                val id = idText.toInt()
                userAdapter.removeUserById(id)
                etUserId.text.clear()
            }
        }

        btnEdit.setOnClickListener {
            val idText = etEditUser.text.toString()
            Log.e("TAG", "btnEdit clicked: $idText")
            if (idText.isNotEmpty()) {
                val id = idText.toInt()
                val user = userList.find { it.id == id }
                if (user != null) {
                    showEditDialog(user)
                } else {
                    Toast.makeText(this, "User with ID $id not found", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter a valid ID", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun showUserDetails(user: User) {
        AlertDialog.Builder(this)
            .setTitle("User Details")
            .setMessage("Name: ${user.name}\nDate: ${android.icu.text.SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(user.time * 1000))}")
            .setPositiveButton("OK", null)
            .show()
    }

    private fun showEditDialog(user: User) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_user, null)
        val etEditName: EditText = dialogView.findViewById(R.id.etEditName)

        etEditName.setText(user.name)

        AlertDialog.Builder(this)
            .setTitle("Edit User")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val newName = etEditName.text.toString()
                if (newName.isNotEmpty()) {
                    userAdapter.updateUserById(user.id, newName)
                    Toast.makeText(this, "User updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
