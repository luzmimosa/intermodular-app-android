package com.example.intermodular.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.intermodular.core.route.model.GpsMeasure
import com.example.intermodular.core.route.model.Route
import com.example.intermodular.core.route.model.RouteDifficulty
import com.example.intermodular.core.route.model.RouteType
import java.time.LocalDateTime

class RoutePreviewProvider : PreviewParameterProvider<Route> {
    override val values: Sequence<Route>
        get() = sequenceOf(
            Route(
                uid = "nbjhfdbghjbjghbshjngh",
                name = "Ruta de la seda",
                description = "Una ruta muy bonita por la costa de Galicia, con vistas al mar y a la montaña. Es una ruta muy bonita y muy recomendable para hacer en familia. Se puede hacer andando o corriendo. Además, se puede hacer en bicicleta, si se es atrevido. Naranja de Valencia.",
                imageID = "uhubnjfbjhbjbdfnjgb43",
                lengthInKm = 42.75623,
                locations = arrayOf(
                    GpsMeasure(42.75623, -8.20654),
                    GpsMeasure(42.75823, -8.76654),
                    GpsMeasure(42.76323, -8.91654),
                    GpsMeasure(42.77023, -8.85654),
                ),
                types = arrayOf(RouteType.RUNNING, RouteType.WALK, RouteType.BYCICLE, RouteType.PHOTOGRAPHY, RouteType.TREKKING),
                difficulty = RouteDifficulty.MEDIUM,
                creator = "alpacaBella19",
                creationDatetime = LocalDateTime.now().minusDays(5)
            )
        )
}