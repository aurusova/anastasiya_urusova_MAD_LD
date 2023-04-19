package com.example.mad_ld.widgets

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.mad_ld.db.entity.Movie

@Composable
fun MovieCard(
    movie: Movie,
    favorite: Boolean,
    onFavoriteChange: () -> Unit,
    onDelete: () -> Unit,
    onItemClick: (Int) -> Unit = {}
) {
    var expandedState by remember { mutableStateOf(false) }
    val isFavorite = remember { mutableStateOf(favorite) }
    Log.d("isFavorite", "${movie.title} ${isFavorite.value}")

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)
        .clickable { onItemClick(movie.id) },
        shape = RoundedCornerShape(corner = CornerSize(15.dp)),
        elevation = 5.dp
    ) {
        Column {
            Box(modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
            ) {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = rememberAsyncImagePainter(movie.images[0]),
                    contentDescription = "Movie Poster",
                    contentScale = ContentScale.Crop
                )

                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                    contentAlignment = Alignment.TopStart,
                ) {
                    IconButton(
                        onClick = {
                            isFavorite.value = !isFavorite.value
                            onFavoriteChange()
                        }
                    ) {
                        Icon(
                            tint = MaterialTheme.colors.secondary,
                            imageVector =
                            if (movie.isFavorite) {
                                Icons.Default.Favorite
                            } else {
                                Icons.Default.FavoriteBorder
                            },
                            contentDescription = "Add to favorites"
                        )
                    }
                }

                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    IconButton(onClick = onDelete) {
                        Icon(
                            tint = MaterialTheme.colors.secondary,
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete movie"
                        )
                    }
                }

            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    movie.title,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(10.dp),
                )
                IconButton(
                    onClick = {
                        expandedState = !expandedState
                    }
                ) {
                    Icon(
                        imageVector =
                        if (expandedState) {
                            Icons.Default.KeyboardArrowUp
                        } else {
                            Icons.Default.KeyboardArrowDown
                        },
                        contentDescription = "Show details")
                }
            }
            if (expandedState) {
                MovieDetails(movie = movie)
            }
        }
    }
}

@Composable
fun MovieDetails(movie: Movie) {
    Text(
        text =
        "Director: " + movie.director +
                "\nReleased: " + movie.year +
                "\nGenre: " + movie.genre +
                "\nActors: " + movie.actors +
                "\nRating: " + movie.rating,
        modifier = Modifier.padding(10.dp),
        fontSize = MaterialTheme.typography.subtitle1.fontSize,
        fontWeight = FontWeight.Normal,
        overflow = TextOverflow.Ellipsis
    )
    Divider(color = Color.LightGray, thickness = 1.dp)
    Text(
        text = "Plot: " + movie.plot,
        modifier = Modifier.padding(10.dp),
        fontSize = MaterialTheme.typography.subtitle1.fontSize,
        fontWeight = FontWeight.Normal,
        overflow = TextOverflow.Ellipsis)
}