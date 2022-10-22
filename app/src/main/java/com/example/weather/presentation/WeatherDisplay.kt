package com.example.weather.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weather.domain.util.weatherInfoExample
import com.example.weather.presentation.ui.theme.WeatherTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun WeatherDisplay(
    modifier: Modifier = Modifier,
    state: WeatherState,
    localityName: String,
    isRefreshing: Boolean,
    onRefresh: () -> Unit
) {

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = onRefresh
    ) {
        Box(modifier = modifier) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                    .verticalScroll(rememberScrollState())
            ) {
                WeatherCard(
                    state = state,
                    localityName = localityName,
                    backgroundColor = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                WeatherForecast(state = state)
            }

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            state.error?.let { error ->
                Text(
                    text = error,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun Default() {
    WeatherTheme {
        WeatherDisplay(
            state = WeatherState(weatherInfoExample, false),
            localityName = "Moscow",
            isRefreshing = false,
            modifier = Modifier.fillMaxSize()
        ) {

        }
    }

//    WeatherCard(
//        state = WeatherState(weatherInfoExample, false),
//        localityName = "Moscow",
//        backgroundColor = MaterialTheme.colors.background
//    )

}