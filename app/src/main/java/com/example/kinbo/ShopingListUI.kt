package com.example.kinbo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingList() {
    var shoppingItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var currentItemName by remember { mutableStateOf("") }
    var currentItemQuantity by remember { mutableStateOf("1") }
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
                currentItemQuantity = "1"
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
                            // Edit mode
                            shoppingItems = shoppingItems.map {
                                if (it.id == editItemId) {
                                    it.name = currentItemName
                                    it.quantity = quantity
                                    it.isEditing = false
                                }
                                it
                            }
                        } else {
                            // Add new item
                            val newId = (shoppingItems.maxOfOrNull { it.id } ?: 0) + 1
                            shoppingItems = shoppingItems + ShoppingItem(
                                id = newId,
                                name = currentItemName,
                                quantity = quantity
                            )
                        }
                        showDialog = false
                    }
                )
            }
        }
    }
}

@Composable
fun AddItemDialog(
    itemName: String,
    itemQuantity: String,
    onItemNameChange: (String) -> Unit,
    onItemQuantityChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add / Edit Item") },
        text = {
            Column {
                OutlinedTextField(
                    value = itemName,
                    onValueChange = onItemNameChange,
                    label = { Text("Item Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = itemQuantity,
                    onValueChange = onItemQuantityChange,
                    label = { Text("Quantity") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun ShoppingListItem(
    item: ShoppingItem,
    onEdit: (ShoppingItem) -> Unit,
    onDelete: (ShoppingItem) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = item.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "Quantity: ${item.quantity}", style = MaterialTheme.typography.bodyMedium)
            }

            Row {
                IconButton(onClick = { onEdit(item) }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = { onDelete(item) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}
