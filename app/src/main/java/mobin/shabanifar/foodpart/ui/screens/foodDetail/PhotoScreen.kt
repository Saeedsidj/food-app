package mobin.shabanifar.foodpart.ui.screens.foodDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import mobin.shabanifar.foodpart.R

@Composable
fun ShowPhoto(
    imageUrl: String,
    navigateUp: () -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.onBackground,
            elevation = 0.dp
        ) {
            IconButton(onClick = {
                navigateUp()
            }) {
                Icon(
                    painterResource(R.drawable.ic_back),
                    contentDescription = "",
                    tint = MaterialTheme.colors.onBackground
                )
            }
            Text(
                text = stringResource(R.string.photo),
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
            }) {
                Icon(
                    painterResource(R.drawable.save),
                    contentDescription = "",
                    tint = MaterialTheme.colors.onBackground
                )
            }
        }
    }) {
        Column(
            verticalArrangement = Arrangement.Center, modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}