package com.example.flowconcept.di

import com.example.flowconcept.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NetworkModule::class])

interface NetworkComponent {
    fun inject(mainActivity: MainActivity)
}