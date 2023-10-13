package bme.hit.greenhouse.navigation

sealed class Screen(val route: String) {
    object Sectors: Screen("sectors")
    object CreateSector: Screen("create")
    object CheckSector: Screen("check/{id}") {
        fun passId(id: Int) = "check/$id"
    }
}