package com.example.kinbo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingList() {
    var shoppingItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var currentItemName by remember { mutableStateOf("") }
    var currentItemQuantity by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var editItemId by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shopping List App") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                currentItemName = ""
                currentItemQuantity = ""
                editItemId = null
                showDialog = true
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Item")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn {
                items(shoppingItems) { item ->
                    ShoppingListItem(
                        item = item,
                        onEdit = {
                            currentItemName = it.name
                            currentItemQuantity = it.quantity.toString()
                            editItemId = it.id
                            showDialog = true
                        },
                        onDelete = {
                            shoppingItems = shoppingItems.filter { i -> i.id != it.id }
                        }
                    )
                }
            }

            if (showDialog) {
                AddItemDialog(
                    itemName = currentItemName,
                    itemQuantity = currentItemQuantity,
                    onItemNameChange = { currentItemName = it },
                    onItemQuantityChange = { currentItemQuantity = it },
                    onDismiss = { showDialog = false },
                    onConfirm = {
                        val quantity = currentItemQuantity.toIntOrNull() ?: 1
                        if (editItemId != null) {
                            shoppingItems = shoppingItems.map {
                                if (it.id == editItemId) it.copy(name = currentItemName, quantity = quantity)
                                else it
                            }
                        } else {
                            val newId = (shoppingItems.maxOfOrNull { it.id } ?: 0) + 1
                            shoppingItems = shoppingItems + ShoppingItem(
                                id = newId,
                                name = currentItemName,
                                quantity = quantity
                            )
                        }
                        showDialog = false
                        currentItemName = ""
                        currentItemQuantity = "1"
                        editItemId = null
                    },
                    isEdit = editItemId != null
                )
            }
        }
    }
}
