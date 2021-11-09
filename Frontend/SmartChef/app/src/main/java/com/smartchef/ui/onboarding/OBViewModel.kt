package com.smartchef.ui.onboarding

import androidx.lifecycle.ViewModel
import com.smartchef.repository.OnboardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OBViewModel @Inject constructor(obRepo : OnboardingRepository): ViewModel() {



}