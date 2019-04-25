package com.example.sample.AbstractFactoryPattern.tire

class NormalTire : ITire {
    override fun tire() {
        System.out.println("普通轮胎")
    }

}