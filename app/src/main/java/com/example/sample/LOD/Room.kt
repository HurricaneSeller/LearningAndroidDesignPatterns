package com.example.sample.LOD

class Room(private val area: Float, private val price: Float) {
    fun getArea(): Float {
        return area
    }

    fun getPrice(): Float {
        return price
    }

    override fun toString(): String {
        return "Room [area=$area, price=$price]"
    }
}
