package com.example.sample.FactoryPattern

abstract class AudiFactory {
      abstract fun <T : AudiCar> createAudiCar(clz: Class<T>): T
}
