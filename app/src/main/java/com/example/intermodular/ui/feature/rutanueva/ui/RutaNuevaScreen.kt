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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.intermodular.R
import com.example.intermodular.core.camera.CameraManager
import com.example.intermodular.core.route.model.RouteDifficulty
import com.example.intermodular.core.route.model.RouteType
import com.example.intermodular.ui.component.global.*

@Preview
@Composable
fun RutaNueva(
    rutaNuevaViewModel: RutaNuevaViewModel = RutaNuevaViewModel(navigationController = rememberNavController()),
    navigationController: NavHostController = rememberNavController()
){
    val routeName: String                       by rutaNuevaViewModel.routeName.observeAsState(initial = stringResource(id = R.string.newroute_default_name))
    val routeDescription: String                by rutaNuevaViewModel.routeDescription.observeAsState(initial = "")
    val routeDifficulty: RouteDifficulty?        by rutaNuevaViewModel.routeDifficulty.observeAsState(initial = null)
    val routeMainImage: ImageBitmap?            by rutaNuevaViewModel.mainImageBitmap.observeAsState(initial = null)
    val routeTypes: List<RouteType>             by rutaNuevaViewModel.routeTypes.observeAsState(initial = listOf())

    // Errors
    val routeDescriptionErrorVisible: Boolean   by rutaNuevaViewModel.routeDescriptionErrorVisible.observeAsState(initial = false)
    val routeTypesErrorVisible: Boolean         by rutaNuevaViewModel.routeTypesErrorVisible.observeAsState(initial = false)
    val routeDifficultyErrorVisible: Boolean    by rutaNuevaViewModel.routeDifficultyErrorVisible.observeAsState(initial = false)
    val routeImageErrorPopupVisible: Boolean    by rutaNuevaViewModel.routeImageErrorVisible.observeAsState(initial = false)

    // Popups
    val routeNamePopupVisible: Boolean          by rutaNuevaViewModel.routeNamePopupVisible.observeAsState(initial = false)
    val routeDifficultyPopupVisible: Boolean    by rutaNuevaViewModel.routeDifficultyPopupVisible.observeAsState(initial = false)
    val waypointCreatedPopupVisible: Boolean    by rutaNuevaViewModel.waypointCreatedPopupVisible.observeAsState(initial = false)

    // Waypoint builder
    val waypointBuilderVisible: Boolean         by rutaNuevaViewModel.waypointPopupVisible.observeAsState(initial = false)
    val waypointName: String                    by rutaNuevaViewModel.waypointName.observeAsState(initial = "")
    val waypointDescription: String             by rutaNuevaViewModel.waypointDescription.observeAsState(initial = "")
    val waypointImage: ImageBitmap?             by rutaNuevaViewModel.waypointImage.observeAsState(initial = null)

    val recordingState: RecordingState          by rutaNuevaViewModel.recordingState.observeAsState(initial = RecordingState.READY)


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
                            errorMessage = stringResource(id = R.string.newroute_description_error_message),
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

                    if (routeDifficultyErrorVisible) {
                        Row {
                            ErrorText(text = stringResource(id = R.string.newroute_difficulty_error))
                        }
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
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    Row {
                        Column {
                            if (routeTypesErrorVisible) {
                                Row {
                                    ErrorText(text = stringResource(id = R.string.newroute_types_unselected_error))
                                }
                            }
                            Row {
                                PredefinedSpacer()
                            }
                        }
                    }
                    Row {
                        when (recordingState) {
                            RecordingState.READY -> StartRecordingButton {
                                rutaNuevaViewModel.startRecording()
                            }
                            RecordingState.RECORDING -> RecordingButton(
                                onClick = { rutaNuevaViewModel.stopRecording() },
                                onWaypointAddRequest = { rutaNuevaViewModel.addWaypointRequest() }
                            )
                            RecordingState.RECORDED -> UploadButton {
                                rutaNuevaViewModel.requestUpload()
                            }
                        }
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
        
        if (routeImageErrorPopupVisible) {
            ErrorPopup(
                message = stringResource(id = R.string.newroute_image_error),
                buttonLabel = stringResource(id = R.string.global_popup_accept)
            ) {
                rutaNuevaViewModel.setRouteImageErrorVisible(false)
            }
        }

        if (waypointBuilderVisible && rutaNuevaViewModel.canAddWaypoint()) {
            WaypointPopup(
                name = waypointName,
                description = waypointDescription,
                image = waypointImage,
                onDismiss = { rutaNuevaViewModel.closeWaypointPopup() },
                onChange = { name, description, image ->
                       rutaNuevaViewModel.setWaypointData(name, description, image)
                },
                canSubmit = {name, description, image ->
                    name.isNotBlank() && description.isNotBlank() && image != null
                }
            ) {
                rutaNuevaViewModel.saveCurrentWaypoint()
            }
        } else if (waypointBuilderVisible) {
            ErrorPopup(
                message = stringResource(id = R.string.newroute_cannot_add_waypoint),
                buttonLabel = stringResource(id = R.string.global_popup_accept)
            ) {
                rutaNuevaViewModel.closeWaypointPopup()
            }
        }

        if (waypointCreatedPopupVisible) {
            ErrorPopup(
                message = stringResource(id = R.string.newroute_waypoint_added),
                buttonLabel = stringResource(id = R.string.global_popup_accept)
            ) {
                rutaNuevaViewModel.setWaypointCreatedPopupVisible(false)
            }
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
    selected: RouteDifficulty?,
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

fun difficultyStringResource(difficulty: RouteDifficulty?): Int {
    return when (difficulty) {
        RouteDifficulty.TRIVIAL -> R.string.route_difficulty_trivial
        RouteDifficulty.EASY -> R.string.route_difficulty_easy
        RouteDifficulty.MEDIUM -> R.string.route_difficulty_medium
        RouteDifficulty.HARD -> R.string.route_difficulty_hard
        RouteDifficulty.EXPERT -> R.string.route_difficulty_expert
        else -> R.string.route_difficulty_undefined
    }
}

@Composable
fun StartRecordingButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.White,
                RoundedCornerShape(10.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row {
            Column(Modifier.fillMaxHeight()) {
                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.newroute_button_start_recording),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Composable
fun RecordingButton(
    onClick: () -> Unit,
    onWaypointAddRequest: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.7f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color.Red,
                        RoundedCornerShape(10.dp)
                    )
                    .clickable { onClick() },
                contentAlignment = Alignment.Center
            ) {
                Row {
                    Column(Modifier.fillMaxHeight()) {
                        Box(
                            modifier = Modifier.fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.padding(10.dp),
                                color = Color.White
                            )
                        }
                    }
                    Column(Modifier.fillMaxHeight()) {
                        Box(
                            modifier = Modifier.fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.newroute_button_recording),
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color.Yellow,
                        RoundedCornerShape(10.dp)
                    )
                    .clickable { onWaypointAddRequest() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.newroute_add_waypoint_button),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
            }
        }
    }
}

@Composable
fun UploadButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.Yellow,
                RoundedCornerShape(10.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row {
            Column(Modifier.fillMaxHeight()) {
                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.newroute_button_upload),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Composable
fun WaypointPopup(
    name: String,
    description: String,
    image: ImageBitmap?,

    onDismiss: () -> Unit,
    onChange: (name: String, description: String, image: ImageBitmap?) -> Unit,
    canSubmit: (name: String, description: String, image: ImageBitmap?) -> Boolean,
    onSuccess: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .background(MaterialTheme.colors.surface, shape = RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clickable { CameraManager.takePicture { onChange(name, description, it) } }
                ) {
                    if (image != null) {
                        Image(
                            bitmap = image,
                            contentDescription = stringResource(id = R.string.route_mainpicture_description)
                                .format(image),
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
                    modifier = Modifier
                        .padding(10.dp)
                ) {
                    TextField(
                        value = name,
                        onValueChange = { onChange(it, description, image) },
                        label = { Text(text = stringResource(id = R.string.newroute_waypoint_name)) }
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                ) {
                    TextField(
                        value = description,
                        onValueChange = { onChange(name, it, image) },
                        label = { Text(text = stringResource(id = R.string.newroute_waypoint_description)) }
                    )
                }
                Row {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = { if (canSubmit(name, description, image)) onSuccess() },
                            enabled = canSubmit(name, description, image)
                        ) {
                            Text(text = stringResource(id = R.string.newroute_waypoint_submit_button))
                        }
                    }
                }
            }
        }
    }
}