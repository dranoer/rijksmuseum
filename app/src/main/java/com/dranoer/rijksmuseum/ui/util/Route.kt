package com.dranoer.rijksmuseum.ui.util

import com.dranoer.rijksmuseum.ui.util.Const.DETAIL_SCREEN
import com.dranoer.rijksmuseum.ui.util.Const.OVERVIEW_SCREEN

sealed class Route(val route: String) {
    object Overview : Route(OVERVIEW_SCREEN)
    object Detail : Route(DETAIL_SCREEN)
}