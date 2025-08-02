package com.example.kinbo

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddItemDialog(
    itemName: String,
    itemQuantity: String,
    onItemNameChange: (String) -> Unit,
    onItemQuantityChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    isEdit : Boolean
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (isEdit) "Update Item" else "Add Item") },
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
