package com.example.kinbo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingList() {

    var shoppingItems by remember { mutableStateOf(emptyList<ShoppingItem>()) }
    var newItemName by remember { mutableStateOf("") }
    var newItemQuantity by remember { mutableDoubleStateOf(1.0) }
    var showDialog by remember { mutableStateOf(false) }

    Scaffold (
        topBar = {
            TopAppBar(
                { Text("Shopping List App") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                {showDialog = true}
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Icon"
                )
            }
        }
    ){
        innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            LazyColumn {  }

            if (showDialog){
                AddItemDialog(
                    newItemName,
                    newItemQuantity.toString(),
                    {newItemName = it},
                    {newItemQuantity = (it.toIntOrNull() ?: 1).toDouble() },
                    {showDialog = false},
                    {
                        val quantity = newItemQuantity.toInt()
                        shoppingItems = shoppingItems + ShoppingItem(
                            shoppingItems.size+1,
                            newItemName,
                            quantity
                        )
                    }
                ) { }
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
    onConfirm: () -> Unit,
    function: () -> Unit
) {

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {Text("Add new item")},
        text = {
            Column {
                OutlinedTextField(
                    value = itemName,
                    onValueChange = onItemNameChange,
                    label = {Text("Item Name")},
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()

                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        },
        confirmButton = {
            TextButton(
                {
                    onConfirm()
                    onDismiss()
                }
            ) {
                Text("Add")
            }
        }
    )
}

@Composable
fun ShoppingListItem(
    item : (ShoppingItem)-> Unit,
    onEdit : (ShoppingItem) -> Unit,
    onDelete : (ShoppingItem) -> Unit
) {
    Card(
        modifier = Modifier.padding(8.dp)
    ) {


    }
}