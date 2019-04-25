package com.example.sample.AbstractFactoryPattern.engine

import com.example.sample.AbstractFactoryPattern.engine.IEngine

class NormalEngine : IEngine {
    override fun engine() {
        System.out.println("普通发动机")
    }

}