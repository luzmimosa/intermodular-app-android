package com.example.intermodular.ui.feature.rutanueva.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.intermodular.ui.component.global.WikihonkBaseScreen
import com.example.intermodular.R
import com.example.intermodular.core.camera.CameraManager
import com.example.intermodular.core.route.model.RouteDifficulty
import com.example.intermodular.core.route.model.RouteType
import com.example.intermodular.ui.component.global.ErrorSupporterTextField
import com.example.intermodular.ui.component.global.InputPopup
import com.example.intermodular.ui.component.global.PredefinedSpacer

@Preview
@Composable
fun RutaNueva(
    rutaNuevaViewModel: RutaNuevaViewModel = RutaNuevaViewModel(),
    navigationController: NavHostController = rememberNavController()
){
    val routeName: String by rutaNuevaViewModel.routeName.observeAsState(initial = stringResource(id = R.string.newroute_default_name))
    val routeDescription: String by rutaNuevaViewModel.routeDescription.observeAsState(initial = "")
    val routeDifficulty: RouteDifficulty by rutaNuevaViewModel.routeDifficulty.observeAsState(initial = RouteDifficulty.EASY)
    val routeMainImage: ImageBitmap? by rutaNuevaViewModel.mainImageBitmap.observeAsState(initial = null)
    val routeTypes: List<RouteType> by rutaNuevaViewModel.routeTypes.observeAsState(initial = listOf())

    // Errors
    val routeDescriptionErrorMessage: Int by rutaNuevaViewModel.routeDescriptionErrorMessage.observeAsState(initial = R.string.unknown_error_message)
    val routeDescriptionErrorVisible: Boolean by rutaNuevaViewModel.routeDescriptionErrorVisible.observeAsState(initial = false)

    // Popups
    val routeNamePopupVisible: Boolean by rutaNuevaViewModel.routeNamePopupVisible.observeAsState(initial = false)
    val routeDifficultyPopupVisible: Boolean by rutaNuevaViewModel.routeDifficultyPopupVisible.observeAsState(initial = false)


    WikihonkBaseScreen(navigationController = navigationController) {
        Column(Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f)
            ) {
                Encabezado(
                    routeImage = routeMainImage,
                    routeName = routeName,
                    onImagePressed = {
                        CameraManager.takePicture { imageBitmap ->
                            rutaNuevaViewModel.setMainImage(imageBitmap)
                        }
                    },
                    onTitlePressed = {
                        rutaNuevaViewModel.setRouteNamePopupVisible(true)
                    }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
                    .padding(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Descripcion(
                            value = routeDescription,
                            errorMessage = stringResource(id = routeDescriptionErrorMessage),
                            showError = routeDescriptionErrorVisible,
                            onValueChange = {
                                rutaNuevaViewModel.setDescription(it)
                            },
                        )
                    }

                    Row { PredefinedSpacer() }

                    Row {
                        Dificultad(
                            selected = routeDifficulty,
                            expanded = routeDifficultyPopupVisible,
                            onClick = {
                                rutaNuevaViewModel.setRouteDifficultyPopupVisible(true)
                            },
                            onDismiss = {
                                rutaNuevaViewModel.setRouteDifficultyPopupVisible(false)
                            },
                            onSelect = {
                                rutaNuevaViewModel.setDifficulty(it)
                                rutaNuevaViewModel.setRouteDifficultyPopupVisible(false)
                            }
                        )
                    }

                    Row { PredefinedSpacer() }

                    Row {
                        Tipos(
                            types = routeTypes,
                            onCheck = { type ->
                                rutaNuevaViewModel.addRouteType(type)
                            },
                            onUncheck = { type ->
                                rutaNuevaViewModel.removeRouteType(type)
                            }
                        )
                    }
                }
            }


        }

        // Popups
        if (routeNamePopupVisible) {
            InputPopup(
                message = stringResource(id = R.string.newroute_name_popup_message),
                label = stringResource(id = R.string.newroute_field_name),
                submitButtonLabel = stringResource(id = R.string.global_popup_accept),
                initialText = if (routeName == stringResource(id = R.string.newroute_default_name)) "" else routeName,
                onDismiss = { rutaNuevaViewModel.setRouteNamePopupVisible(false) },
                onSubmit = {
                    rutaNuevaViewModel.setName(it)
                    rutaNuevaViewModel.setRouteNamePopupVisible(false)
                },
                canSubmit = {
                    it.isNotBlank()
                }
            )
        }
    }
}

@Composable
fun Encabezado(
    routeImage: ImageBitmap?,
    routeName: String,

    onImagePressed: () -> Unit = {},
    onTitlePressed: () -> Unit = {},
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .clickable { onImagePressed() }
            ) {
                if (routeImage != null) {
                    Image(
                        bitmap = routeImage,
                        contentDescription = stringResource(id = R.string.route_mainpicture_description)
                            .format(routeName),
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.6f)) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AddAPhoto,
                                    contentDescription = stringResource(id = R.string.newroute_add_image),
                                )
                            }
                        }
                        Row(Modifier.fillMaxSize()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.TopCenter
                            ){
                                Text(
                                    text = stringResource(id = R.string.newroute_add_image),
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp,
                                )
                            }
                        }
                    }
                }
            }

            Row(
                Modifier
                    .fillMaxSize()
                    .clickable { onTitlePressed() }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = routeName,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                    )
                }
            }
        }
    }
}


@Composable
fun Descripcion(
    value: String,
    errorMessage: String,
    showError: Boolean,
    onValueChange: (String) -> Unit,
) {
    ErrorSupporterTextField(
        label = stringResource(id = R.string.newroute_description_label),
        value = value,
        errorVisible = showError,
        errorMessage = errorMessage,
        onValueChange = { onValueChange(it) },
        singleLine = false,
        maxLines = 5,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun Dificultad(
    selected: RouteDifficulty,
    expanded: Boolean,
    onClick: () -> Unit = {},
    onDismiss: () -> Unit = {},
    onSelect: (difficulty: RouteDifficulty) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .border(0.dp, Color.Transparent, RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .height(60.dp)
            .background(MaterialTheme.colors.onSurface.copy(alpha = 0.12f))
            .padding(5.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = stringResource(id = difficultyStringResource(selected)),
            fontSize = 16.sp,
            modifier = Modifier
                .padding(10.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onDismiss() },
            modifier = Modifier.fillMaxWidth(),
        ) {
            RouteDifficulty.values().forEach { difficulty ->
                val difficultyName = stringResource(id = difficultyStringResource(difficulty))

                DropdownMenuItem(onClick = { onSelect(difficulty) }) {
                    Text(text = difficultyName)
                }
            }
        }
    }
}

@Composable
fun Tipos(
    types: List<RouteType>,
    onCheck: (RouteType) -> Unit = {},
    onUncheck: (RouteType) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .border(0.dp, Color.Transparent, RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .background(MaterialTheme.colors.onSurface.copy(alpha = 0.12f))
            .padding(5.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(1 / 3.0f)
            ) {
                Box(modifier = Modifier.fillMaxWidth(0.5f)) {
                    LabeledCheckbox(
                        label = stringResource(id = R.string.route_type_walk),
                        checked = types.contains(RouteType.WALK),
                        onCheckedChange = { if (it) onCheck(RouteType.WALK) else onUncheck(RouteType.WALK) }
                    )
                }
                Box(modifier = Modifier.fillMaxWidth()) {
                    LabeledCheckbox(
                        label = stringResource(id = R.string.route_type_trekking),
                        checked = types.contains(RouteType.TREKKING),
                        onCheckedChange = { if (it) onCheck(RouteType.TREKKING) else onUncheck(RouteType.TREKKING) }
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
            ) {
                Box(modifier = Modifier.fillMaxWidth(0.5f)) {
                    LabeledCheckbox(
                        label = stringResource(id = R.string.route_type_running),
                        checked = types.contains(RouteType.RUNNING),
                        onCheckedChange = { if (it) onCheck(RouteType.RUNNING) else onUncheck(RouteType.RUNNING) }
                    )
                }
                Box(modifier = Modifier.fillMaxWidth()) {
                    LabeledCheckbox(
                        label = stringResource(id = R.string.route_type_bike),
                        checked = types.contains(RouteType.BYCICLE),
                        onCheckedChange = { if (it) onCheck(RouteType.BYCICLE) else onUncheck(RouteType.BYCICLE) }
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Box(modifier = Modifier.fillMaxWidth(0.5f)) {
                    LabeledCheckbox(
                        label = stringResource(id = R.string.route_type_photography),
                        checked = types.contains(RouteType.PHOTOGRAPHY),
                        onCheckedChange = { if (it) onCheck(RouteType.PHOTOGRAPHY) else onUncheck(RouteType.PHOTOGRAPHY) }
                    )
                }
                Box(modifier = Modifier.fillMaxWidth()) {

                }
            }
        }
    }
}

@Composable
fun LabeledCheckbox(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Row {
            Column(Modifier.fillMaxHeight()) {
                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = { onCheckedChange(it) },
                    )
                }
            }
            Column(Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = label
                    )
                }
            }
        }
    }
}

fun difficultyStringResource(difficulty: RouteDifficulty): Int {
    return when (difficulty) {
        RouteDifficulty.TRIVIAL -> R.string.route_difficulty_trivial
        RouteDifficulty.EASY -> R.string.route_difficulty_easy
        RouteDifficulty.MEDIUM -> R.string.route_difficulty_medium
        RouteDifficulty.HARD -> R.string.route_difficulty_hard
        RouteDifficulty.EXPERT -> R.string.route_difficulty_expert
        else -> R.string.route_difficulty_undefined
    }
}