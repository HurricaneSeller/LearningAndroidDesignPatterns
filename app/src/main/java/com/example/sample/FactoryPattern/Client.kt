package com.example.sample.FactoryPattern

class Client {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val factory: AudiFactory = AudiCarFactory()

            val audiQ3: AudiCar = AudiQ3()
            audiQ3.drive()
            audiQ3.selfNavigation()

            val audiQ5: AudiCar = AudiQ5()
            audiQ5.drive()
            audiQ5.selfNavigation()
        }
    }
}