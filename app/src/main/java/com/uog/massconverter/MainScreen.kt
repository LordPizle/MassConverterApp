package com.uog.massconverter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.text.NumberFormat

/**
 * MainScreen provides the user interface for the unit conversion process.
 *
 * @param navController The navigation controller to manage navigation between screens.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    var valueEntered by remember { mutableStateOf("") }
    var inputUnit by remember { mutableStateOf("inch") }
    var outputUnit by remember { mutableStateOf("inch") }
    var convertedValue by remember { mutableStateOf("") }
    var showMenu by remember { mutableStateOf(false) }
    var showGuide by remember { mutableStateOf(false) }
    var isDarkMode by remember { mutableStateOf(false) }
    val numberFormat = NumberFormat.getInstance().apply {
        maximumFractionDigits = 2
        minimumFractionDigits = 2
    }

    val tealColor = Color(0xFF008080)
    val tealTextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = tealColor,
        cursorColor = tealColor,
        focusedLabelColor = tealColor
    )
    val colorScheme = if (isDarkMode) darkColorScheme() else lightColorScheme()

    MaterialTheme(colorScheme = colorScheme) {
        Scaffold(
            topBar = {
                AppTopBar(
                    navController = navController,
                    onOptionsClicked = { showMenu = true },
                    isDarkMode = isDarkMode,
                    onThemeToggle = { isDarkMode = !isDarkMode },
                    onShowGuide = { showGuide = true },
                    showMenu = showMenu,
                    onDismissMenu = { showMenu = false }
                )
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    ConversionForm(
                        valueEntered = valueEntered,
                        onValueChange = { valueEntered = it },
                        inputUnit = inputUnit,
                        onInputUnitChange = { inputUnit = it },
                        outputUnit = outputUnit,
                        onOutputUnitChange = { outputUnit = it },
                        textFieldColors = tealTextFieldColors
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    ConvertButton(
                        onClick = {
                            val value = valueEntered.toDoubleOrNull()
                            if (value != null && value > 0) {
                                convertedValue = convertUnits(value, inputUnit, outputUnit, numberFormat)
                            }
                        },
                        buttonColor = tealColor
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    ConvertedValueDisplay(value =
                    convertedValue, textColor = tealColor)
                }

                if (showGuide) {
                    ConversionGuideDialog(onDismiss = { showGuide = false })
                }
            }
        )
    }
}

/**
 * Displays the top app bar with navigation and menu options.
 *
 * @param navController Handles navigation between screens.
 * @param onOptionsClicked Opens the options menu.
 * @param isDarkMode Current theme mode.
 * @param onThemeToggle Toggles between dark and light mode.
 * @param onShowGuide Displays the conversion guide dialog.
 * @param showMenu State of the options menu visibility.
 * @param onDismissMenu Hides the options menu.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    navController: NavController,
    onOptionsClicked: () -> Unit,
    isDarkMode: Boolean,
    onThemeToggle: () -> Unit,
    onShowGuide: () -> Unit,
    showMenu: Boolean,
    onDismissMenu: () -> Unit
) {
    TopAppBar(
        title = { Text("Imperial Unit Converter", color = Color.White) },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
        },
        actions = {
            Box {
                IconButton(onClick = onOptionsClicked) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "Options", tint = Color.White)
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = onDismissMenu
                ) {
                    DropdownMenuItem(text = { Text("Conversion Guide") }, onClick = onShowGuide)
                    DropdownMenuItem(
                        text = { Text(if (isDarkMode) "Light Mode" else "Dark Mode") },
                        onClick = {
                            onThemeToggle()
                            onDismissMenu()
                        }
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF008080))
    )
}

/**
 * Displays the form for entering values and selecting units.
 */
@Composable
fun ConversionForm(
    valueEntered: String,
    onValueChange: (String) -> Unit,
    inputUnit: String,
    onInputUnitChange: (String) -> Unit,
    outputUnit: String,
    onOutputUnitChange: (String) -> Unit,
    textFieldColors: TextFieldColors
) {
    OutlinedTextField(
        value = valueEntered,
        onValueChange = onValueChange,
        label = { Text("Enter Value") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth(),
        colors = textFieldColors
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text("From Unit:")
    DropdownMenuWrapper(
        selectedOption = inputUnit,
        options = getUnitList(),
        onOptionSelected = onInputUnitChange,
        textFieldColors = textFieldColors
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text("To Unit:")
    DropdownMenuWrapper(
        selectedOption = outputUnit,
        options = getUnitList(),
        onOptionSelected = onOutputUnitChange,
        textFieldColors = textFieldColors
    )
}

/**
 * Displays the convert button.
 */
@Composable
fun ConvertButton(onClick: () -> Unit, buttonColor: Color) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
    ) {
        Text("Convert")
    }
}

/**
 * Displays the converted value.
 */
@Composable
fun ConvertedValueDisplay(value: String, textColor: Color) {
    Text(
        text = "Converted Value: $value",
        style = MaterialTheme.typography.bodyLarge,
        color = textColor
    )
}


@Composable
fun DropdownMenuWrapper(
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    textFieldColors: TextFieldColors
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        // Outlined text field acting as a dropdown trigger
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {}, // No input handling needed as the field is read-only
            readOnly = true,
            label = { Text("Select Unit") },
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Dropdown"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors
        )

        // Dropdown menu for unit selection
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option) // Callback for selected unit
                        expanded = false
                    }
                )
            }
        }
    }
}


@Composable
fun ConversionGuideDialog(onDismiss: () -> Unit) {
    // Mapping of units to their meter equivalents
    val conversionMap = mapOf(
        "twip" to "0.000017639 meters",
        "point" to "0.000352778 meters",
        "line" to "0.00211667 meters",
        "pica" to "0.00423333 meters",
        "barleycorn" to "0.00847 meters",
        "inch" to "0.0254 meters",
        "nail" to "0.05715 meters",
        "finger" to "0.1143 meters",
        "palm" to "0.2286 meters",
        "shaftment" to "0.1524 meters",
        "hand" to "0.1016 meters",
        "span" to "0.2286 meters",
        "foot" to "0.3048 meters",
        "yard" to "0.9144 meters",
        "fathom" to "1.8288 meters",
        "rod" to "5.0292 meters",
        "chain" to "20.1168 meters",
        "furlong" to "201.168 meters",
        "mile" to "1609.344 meters",
        "nautical mile" to "1852.0 meters",
        "league" to "4828.032 meters"
    )

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Conversion Guide") },
        text = {
            Column {
                conversionMap.forEach { (unit, value) ->
                    Text(
                        text = "$unit = $value",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Close")
            }
        }
    )
}


fun getUnitList(): List<String> = listOf(
    "twip", "point", "line", "pica", "barleycorn", "inch", "nail", "finger", "palm",
    "shaftment", "hand", "span", "foot", "yard", "fathom", "rod", "chain", "furlong",
    "mile", "nautical mile", "league"
)

fun convertUnits(
    value: Double,
    inputUnit: String,
    outputUnit: String,
    numberFormat: NumberFormat
): String {
    // Conversion factors to meters
    val conversions = mapOf(
        "twip" to 0.000017639,
        "point" to 0.000352778,
        "line" to 0.00211667,
        "pica" to 0.00423333,
        "barleycorn" to 0.00847,
        "inch" to 0.0254,
        "nail" to 0.05715,
        "finger" to 0.1143,
        "palm" to 0.2286,
        "shaftment" to 0.1524,
        "hand" to 0.1016,
        "span" to 0.2286,
        "foot" to 0.3048,
        "yard" to 0.9144,
        "fathom" to 1.8288,
        "rod" to 5.0292,
        "chain" to 20.1168,
        "furlong" to 201.168,
        "mile" to 1609.344,
        "nautical mile" to 1852.0,
        "league" to 4828.032
    )


// Convert input value to meters
    val valueInMeters = value * (conversions[inputUnit] ?: 1.0)
// Convert meters to output unit
    val convertedValue = valueInMeters / (conversions[outputUnit] ?: 1.0)

// Format and return the result
    return "${numberFormat.format(convertedValue)} $outputUnit"
}
