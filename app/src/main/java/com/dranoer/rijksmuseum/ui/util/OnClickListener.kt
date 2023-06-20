package com.dranoer.rijksmuseum.ui.util

import com.dranoer.rijksmuseum.ui.ArtItem

class OnClickListener(val clickListener: (item: ArtItem) -> Unit) {
    fun onClick(item: ArtItem) =
        clickListener(item)
}