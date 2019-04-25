package com.example.sample.FactoryPattern

import java.lang.Exception

class AudiCarFactory :AudiFactory(){
    override fun <T : AudiCar> createAudiCar(clz: Class<T>): T {
        var car: AudiCar ?= null
        try {
            car = Class.forName(clz.name).newInstance() as AudiCar
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return car as T
    }

}