package domain.event.model.details


data class Localization(val point: GeoPoint)

sealed class Coordinate
data class Longitude(val value: Double) : Coordinate()
data class Latitude(val value: Double) : Coordinate()
data class GeoPoint(val longitude: Longitude, val latitude: Latitude)
