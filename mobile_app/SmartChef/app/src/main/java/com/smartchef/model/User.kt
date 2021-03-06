package com.smartchef.model

import androidx.room.*


@Entity(indices = [Index(value = ["email_id"], unique = true)])
data class User(
    @PrimaryKey val id: String,

    @ColumnInfo(name = "email_id")
    val email: String,

    @ColumnInfo(name = "first_name")
    val firstName: String?,

    @ColumnInfo(name = "last_name")
    val lastName: String?,

)

