package com.shruuspace.shreejimart.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shruuspace.shreejimart.utils.CommonUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    //isLoading state with initial value true
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()


    private val _isCurrentUserExist = MutableStateFlow(false)
    val isCurrentUserExist = _isCurrentUserExist


    init {
        viewModelScope.launch {
            //Delay to simulate some background processing like fetching data
            delay(3000)
            //After task is done set isLoading to false to hide splash screen
            _isLoading.value = false


        }

        CommonUtils.getAuthInstance().currentUser?.let {
            _isCurrentUserExist.value=true
        }

    }
}

