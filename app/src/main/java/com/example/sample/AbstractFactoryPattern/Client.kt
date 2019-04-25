package com.example.sample.AbstractFactoryPattern

import com.example.sample.AbstractFactoryPattern.factory.Q3Factory

class Client {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val q3Factory = Q3Factory()
            q3Factory.createBrake()
            q3Factory.createEngine()
            q3Factory.createTire()
        }
    }
}