package com.example.examen1pkotlin

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePicketFragment (val listener: (
    day:Int,month:Int,year:Int) -> Unit)
    : DialogFragment(),
    DatePickerDialog.OnDateSetListener {



        override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
            listener(dayOfMonth,month,year)
        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            // super se usa para usar la función default
            //return super.onCreateDialog(savedInstanceState)
            val vCalendario: Calendar = Calendar.getInstance()
            val vDay: Int = vCalendario.get(Calendar.DAY_OF_MONTH)
            val vMonth: Int = vCalendario.get(Calendar.MONTH)
            val vYear: Int = vCalendario.get(Calendar.YEAR)

            val vPicker = DatePickerDialog(activity as Context,this,vYear,vMonth,vDay)
            vPicker.datePicker.maxDate = vCalendario.timeInMillis;
            return vPicker
        }


    }