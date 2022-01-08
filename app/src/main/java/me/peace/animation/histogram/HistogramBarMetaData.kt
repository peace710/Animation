package me.peace.animation.histogram

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator


data class HistogramBarMetaData(
        val id: Int,
        val height: Float,
        val barColour: Int,
        val barTextColour: Int,
        val name: String = id.toString()) : Parcelable {

    constructor(id: Int, other: HistogramBarMetaData): this(
            id,
            other.height,
            other.barColour,
            other.barTextColour,
            other.name)

    private constructor(source: Parcel): this(
            source.readInt(),
            source.readFloat(),
            source.readInt(),
            source.readInt(),
            source.readString()!!)

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeFloat(height)
        dest.writeInt(barColour)
        dest.writeInt(barTextColour)
        dest.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Creator<HistogramBarMetaData> {
        override fun createFromParcel(parcel: Parcel): HistogramBarMetaData {
            return HistogramBarMetaData(parcel)
        }

        override fun newArray(size: Int): Array<HistogramBarMetaData?> {
            return arrayOfNulls(size)
        }
    }
}
