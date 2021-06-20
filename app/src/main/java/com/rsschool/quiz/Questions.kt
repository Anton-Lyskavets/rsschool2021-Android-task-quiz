package com.rsschool.quiz

import android.os.Parcel
import android.os.Parcelable

data class Questions (
    var question: String?,

    var answer1: String?,
    var answer2: String?,
    var answer3: String?,
    var answer4: String?,
    var answer5: String?,

    var rightAnswer: String?,

    var answerUser: Int = -1

        ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(question)
        parcel.writeString(answer1)
        parcel.writeString(answer2)
        parcel.writeString(answer3)
        parcel.writeString(answer4)
        parcel.writeString(answer5)
        parcel.writeString(rightAnswer)
        parcel.writeInt(answerUser)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Questions> {
        override fun createFromParcel(parcel: Parcel): Questions {
            return Questions(parcel)
        }

        override fun newArray(size: Int): Array<Questions?> {
            return arrayOfNulls(size)
        }
    }
}
