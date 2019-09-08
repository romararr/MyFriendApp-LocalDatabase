package ezy.id.myfriendapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MyFriend(
    @PrimaryKey(autoGenerate = true)
    val temanId: Int? = null,
    var nama: String,
    var email: String,
    var telp: String,
    var kelamin: String,
    var alamat: String
)