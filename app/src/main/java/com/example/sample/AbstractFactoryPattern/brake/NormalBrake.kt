package com.example.sample.AbstractFactoryPattern.brake

import com.example.sample.AbstractFactoryPattern.brake.IBrake

class NormalBrake : IBrake {
    override fun brake() {
        System.out.println("普通发动机")
    }

}