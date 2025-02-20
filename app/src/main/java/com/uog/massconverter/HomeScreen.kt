package com.uog.massconverter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.uog.massconverter.R

/**
 * HomeScreen serves as the landing page of the application.
 * Displays a welcome message, an image, and a button to navigate to the MainScreen.
 *
 * @param navController The navigation controller to handle screen transitions.
 */

@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF008080)) // Teal Background
            .padding(16.dp)
    ) {
        // Main content inside a Column
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center, // Center vertically
            horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
        ) {
            // Image from the drawable folder
            Image(
                painter = painterResource(id = R.drawable.baseline_loop_24), // Use the image from drawable
                contentDescription = "Loop Icon",
                modifier = Modifier
                    .size(300.dp) // Adjust image size
                    .padding(bottom = 16.dp) // Space between the image and the title
            )

            // Title
            Text(
                text = "Welcome to Imperial Converter...",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = FontFamily.Serif, // Serif Font
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp // Larger Font Size
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Subtitle
            Text(
                text = "Easily convert between imperial units.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White,
                    fontSize = 13.sp
                ),
                lineHeight = 20.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Button and Agreement Text at the Bottom
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        ) {
            Button(
                onClick = { navController.navigate("main") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White, // White Button
                    contentColor = Color(0xFF008080) // Teal Text
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Text(text = "Continue", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            Text(
                text = "By continuing, you agree to our Terms and Conditions.",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.White,
                    fontSize = 12.sp
                ),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
