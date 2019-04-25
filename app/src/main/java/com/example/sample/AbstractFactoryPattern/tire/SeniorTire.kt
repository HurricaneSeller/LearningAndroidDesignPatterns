package com.example.sample.AbstractFactoryPattern.tire

class SeniorTire : ITire{
    override fun tire() {
        System.out.println("高级轮胎")
    }
}