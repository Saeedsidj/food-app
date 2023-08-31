package mobin.shabanifar.foodpart.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import mobin.shabanifar.foodpart.NavigationBottom

@Composable
fun Profile() {
    Text(text = NavigationBottom.profile.name.toString())
}