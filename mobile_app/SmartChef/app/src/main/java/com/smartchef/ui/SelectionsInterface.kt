package com.smartchef.ui

interface SelectionsInterface{
    fun getSelections(): HashMap<String, Boolean>
    fun getExclusions(): HashMap<String, Boolean>
}