package com.madarsoft.task.presentation.ui.binding

import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter

@BindingAdapter("onTextValueChanged")
fun EditText.setOnTextValueChangedListener(onValueChangedListener:OnValueChangedListener?) {
    this.doAfterTextChanged {
        onValueChangedListener?.onValueChanged(it?.toString().orEmpty())
    }

}