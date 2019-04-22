package com.example.sample.LOD

class Mediator {
    private val rooms = ArrayList<Room>()

    init {
        for (i in 0..4) {
            rooms.add(Room((14 + i).toFloat(), ((14 + i) * 500).toFloat()))
        }
    }

    fun rentOut(area: Float, price: Float): Room? {
        for (room in rooms) {
            if (isSuitable(area, price, room)) {
                return room
            }
        }
        return null
    }
    private fun isSuitable(area: Float, price: Float, room: Room):Boolean {
        return Math.abs(room.getArea() - area) < Tenant.getDiffArea() && Math.abs(room.getPrice() - price) < Tenant.getDiffPrice()
    }
}