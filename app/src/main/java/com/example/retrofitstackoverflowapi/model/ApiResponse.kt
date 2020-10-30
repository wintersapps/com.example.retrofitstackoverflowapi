package com.example.retrofitstackoverflowapi.model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.text.Html
import android.text.format.DateFormat
import androidx.databinding.BindingAdapter
import com.google.android.material.textview.MaterialTextView
import com.google.gson.annotations.SerializedName
import java.util.*

data class ResponseWrapper<T>(
        val items: List<T>
)

data class Question(
        @SerializedName("question_id")
        val questionId: Int?,

        val title: String?,
        val score: String?,

        @SerializedName("creation_date")
        val date: Long?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(questionId)
        parcel.writeString(title)
        parcel.writeString(score)
        parcel.writeValue(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }
}

fun MaterialTextView.convertHtml(title: String?){
    if (Build.VERSION.SDK_INT >= 24)
        this.text = Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY).toString()
    else
        this.text = Html.fromHtml(title).toString()
}

fun MaterialTextView.convertDate(timestamp: Long?){
    var time = ""
    timestamp?.let {
        val cal = Calendar.getInstance()
        cal.timeInMillis = it * 1000
        time = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString()
    }
    this.text = time
}

@BindingAdapter("android:convertHtmlToText")
fun convertTitle(view: MaterialTextView, title: String?) {
    view.convertHtml(title)
}

@BindingAdapter("android:convertDateToString")
fun convertDateToString(view: MaterialTextView, timestamp: Long?){
    view.convertDate(timestamp)
}