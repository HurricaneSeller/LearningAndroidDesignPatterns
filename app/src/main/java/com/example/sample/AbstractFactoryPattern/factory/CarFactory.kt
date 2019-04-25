package com.example.sample.AbstractFactoryPattern.factory

import com.example.sample.AbstractFactoryPattern.brake.IBrake
import com.example.sample.AbstractFactoryPattern.engine.IEngine
import com.example.sample.AbstractFactoryPattern.tire.ITire

abstract class CarFactory {
    abstract fun createTire(): ITire
    abstract fun createEngine(): IEngine
    abstract fun createBrake(): IBrake
}