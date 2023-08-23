package com.sabiantools.controls.recyclerview

import androidx.recyclerview.widget.DiffUtil

class AnyDiffUtil : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return "$oldItem".contentEquals("$newItem")
    }

    companion object {
        @JvmStatic
        val instance = AnyDiffUtil()
    }
}