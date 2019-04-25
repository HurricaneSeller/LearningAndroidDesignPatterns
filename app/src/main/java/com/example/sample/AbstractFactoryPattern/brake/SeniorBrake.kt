package com.example.sample.AbstractFactoryPattern.brake

class SeniorBrake : IBrake {
    override fun brake() {
        System.out.println("高级制动")
    }

}