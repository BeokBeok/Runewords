package com.beok.runewords.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

object ActivityHomeView {

    @Composable
    fun Layout() {
        MaterialTheme {
            LazyVerticalGrid(cells = GridCells.Adaptive(minSize = 128.dp)) {
                items(Rune.all()) { item ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = item.iconResourceID),
                            contentDescription = null,
                            modifier = Modifier.width(50.dp).height(50.dp),
                            contentScale = ContentScale.Crop
                        )
                        Text(text = stringResource(id = item.nameResourceID))
                    }
                }
            }
        }
    }
}
