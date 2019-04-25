package com.example.sample.FactoryPattern

class AudiQ5 :AudiCar() {
    override fun drive() {
        System.out.println("Q5 is running now!")
    }

    override fun selfNavigation() {
        System.out.println("Q5 starts automatically driving!")
    }
}