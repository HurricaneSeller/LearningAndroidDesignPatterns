package com.example.sample.AbstractFactoryPattern.factory

import com.example.sample.AbstractFactoryPattern.brake.IBrake
import com.example.sample.AbstractFactoryPattern.brake.NormalBrake
import com.example.sample.AbstractFactoryPattern.engine.IEngine
import com.example.sample.AbstractFactoryPattern.engine.NormalEngine
import com.example.sample.AbstractFactoryPattern.tire.ITire
import com.example.sample.AbstractFactoryPattern.tire.NormalTire

class Q3Factory : CarFactory() {
    override fun createTire(): ITire {
        return NormalTire()
    }

    override fun createEngine(): IEngine {
        return NormalEngine()
    }

    override fun createBrake(): IBrake {
        return NormalBrake()
    }
}