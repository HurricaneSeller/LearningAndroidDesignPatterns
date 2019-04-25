package com.example.sample.FactoryPattern

class AudiQ3: AudiCar() {
    override fun drive() {
        System.out.println("Q3 is running now!")
    }

    override fun selfNavigation() {
        System.out.println("Q3 starts automatically driving!")
    }

}
