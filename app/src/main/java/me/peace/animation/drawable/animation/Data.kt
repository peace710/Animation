package me.peace.animation.drawable.animation

import androidx.fragment.app.Fragment


data class Data(
    val title: String,
    val createFragment: () -> Fragment
)