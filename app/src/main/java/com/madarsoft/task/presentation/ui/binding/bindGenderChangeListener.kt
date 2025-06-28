package com.madarsoft.task.presentation.ui.binding

import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.databinding.BindingAdapter

object GenderBindingAdapters {

    @JvmStatic
    @BindingAdapter("onGenderChanged")
    fun bindGenderChangeListener(
        radioGroup: RadioGroup,
        onGenderChanged: OnValueChangedListener?
    ) {
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<RadioButton>(checkedId)
            val selectedGender = radioButton?.tag?.toString() ?: return@setOnCheckedChangeListener
            onGenderChanged?.onValueChanged(selectedGender)
        }
    }
}