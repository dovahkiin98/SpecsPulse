package specspulse.app.ui.details.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import coil.compose.AsyncImage
import specspulse.app.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagesUI(
    startIndex: Int,
    images: List<String>,
    onDismissRequest: () -> Unit,
) {
    val pagerState = rememberPagerState(
        initialPage = startIndex,
        pageCount = {
            images.size
        },
    )

    Box(
        modifier = Modifier
            .background(Color.Black)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            var offset by remember { mutableStateOf(Offset.Zero) }
            var zoom by remember { mutableFloatStateOf(1f) }

            val animatedZoom by animateFloatAsState(
                targetValue = zoom,
                label = "zoom",
            )
            val animatedOffsetX by animateFloatAsState(
                targetValue = offset.x,
                label = "Offset X",
            )
            val animatedOffsetY by animateFloatAsState(
                targetValue = offset.y,
                label = "Offset Y",
            )

            BoxWithConstraints {
                val contentScale =
                    if (maxWidth < maxHeight) ContentScale.FillWidth else ContentScale.FillHeight

                AsyncImage(
                    model = images[it],
                    contentDescription = null,
                    contentScale = contentScale,
                    modifier = Modifier
                        .fillMaxSize()
                        .clipToBounds()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onDoubleTap = { tapOffset ->
                                    zoom = if (zoom > 1f) 1f else 2f
                                    offset = calculateDoubleTapOffset(zoom, size, tapOffset)
                                }
                            )
                        }
//                        .pointerInput(Unit) {
//                            detectTransformGestures(
//                                onGesture = { centroid, pan, gestureZoom, _ ->
//                                    offset = offset.calculateNewOffset(
//                                        centroid, pan, zoom, gestureZoom, size
//                                    )
//                                    zoom = maxOf(1f, zoom * gestureZoom)
//                                }
//                            )
//                        }
                        .graphicsLayer {
                            translationX = -animatedOffsetX * animatedZoom
                            translationY = -animatedOffsetY * animatedZoom
                            scaleX = animatedZoom
                            scaleY = animatedZoom
                            transformOrigin = TransformOrigin(0f, 0f)
                        }
                )
            }
        }

        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black.copy(alpha = 0.5f),
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White,
                actionIconContentColor = Color.White,
            ),
            title = {
                Text("Images")
            },
            navigationIcon = {
                IconButton(onClick = onDismissRequest) {
                    Icon(
                        Icons.AutoMirrored.Default.ArrowBack,
                        null,
                    )
                }
            },
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun ImagesFullScreenDialog_Preview() {
    AppTheme {
        ImagesUI(
            startIndex = 0,
            images = listOf(),
            onDismissRequest = {},
        )
    }
}


fun Offset.calculateNewOffset(
    centroid: Offset,
    pan: Offset,
    zoom: Float,
    gestureZoom: Float,
    size: IntSize
): Offset {
    val newScale = maxOf(1f, zoom * gestureZoom)
    val newOffset = (this + centroid / zoom) -
            (centroid / newScale + pan / zoom)
    return Offset(
        newOffset.x.coerceIn(0f, (size.width / zoom) * (zoom - 1f)),
        newOffset.y.coerceIn(0f, (size.height / zoom) * (zoom - 1f))
    )
}

fun calculateDoubleTapOffset(
    zoom: Float,
    size: IntSize,
    tapOffset: Offset
): Offset {
    val newOffset = Offset(tapOffset.x, tapOffset.y)
    return Offset(
        newOffset.x.coerceIn(0f, (size.width / zoom) * (zoom - 1f)),
        newOffset.y.coerceIn(0f, (size.height / zoom) * (zoom - 1f))
    )
}

