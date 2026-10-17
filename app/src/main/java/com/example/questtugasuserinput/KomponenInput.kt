package com.example.questtugasuserinput

package com.example.questtugasuserinput

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.layout.fillMaxWidth

@Composable
fun FieldBiodata(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false,
    pesanError: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = { Text(text = hint, fontSize = spResource(R.dimen.ukuran_isian)) },
        textStyle = TextStyle(fontSize = spResource(R.dimen.ukuran_isian)),
        trailingIcon = trailingIcon,
        singleLine = true,
        readOnly = readOnly,
        isError = isError,
        supportingText = if (pesanError != null) {
            { Text(text = pesanError) }
        } else null,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = RoundedCornerShape(dimensionResource(R.dimen.radius_isian)),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(R.color.ungu_utama),
            unfocusedBorderColor = colorResource(R.color.garis_isian),
            cursorColor = colorResource(R.color.ungu_utama),
            focusedTextColor = colorResource(R.color.teks_gelap),
            unfocusedTextColor = colorResource(R.color.teks_gelap),
            focusedPlaceholderColor = colorResource(R.color.teks_abu),
            unfocusedPlaceholderColor = colorResource(R.color.teks_abu)
        )
    )
}

@Composable
fun FieldDropdown(
    nilai: String,
    hint: String,
    pilihan: List<String>,
    onPilih: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var terbuka by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        FieldBiodata(
            value = nilai,
            onValueChange = {},
            hint = hint,
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = { Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null) }
        )
        Box(modifier = Modifier
            .matchParentSize()
            .clickable { terbuka = true })
        DropdownMenu(expanded = terbuka, onDismissRequest = { terbuka = false }) {
            pilihan.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        onPilih(item)
                        terbuka = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogTanggal(onPilih: (Long) -> Unit, onTutup: () -> Unit) {
    val state = rememberDatePickerState()
    DatePickerDialog(
        onDismissRequest = onTutup,
        confirmButton = {
            TextButton(
                enabled = state.selectedDateMillis != null,
                onClick = {
                    state.selectedDateMillis?.let { onPilih(it) }
                    onTutup()
                }
            ) { Text(text = stringResource(R.string.dialog_ok)) }
        },
        dismissButton = {
            TextButton(onClick = onTutup) { Text(text = stringResource(R.string.dialog_batal)) }
        }
    ) {
        DatePicker(state = state)
    }
}

