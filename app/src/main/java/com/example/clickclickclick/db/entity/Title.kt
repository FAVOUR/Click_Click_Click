package com.example.clickclickclick.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Title represents the title fetched from the network
 */
@Entity
class Title constructor(val title:String, @PrimaryKey val id :Int=0) {
}