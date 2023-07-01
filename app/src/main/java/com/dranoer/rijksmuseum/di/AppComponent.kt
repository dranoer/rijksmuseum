package com.dranoer.rijksmuseum.di

import com.dranoer.rijksmuseum.MainActivity
import com.dranoer.rijksmuseum.networking.di.NetworkingComponent
import dagger.Component

@Component(dependencies = [NetworkingComponent::class], modules = [AppModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun getNetworkingComponent(): NetworkingComponent
}