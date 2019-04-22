package com.example.sample.LOD

class Tenant {
    var roomArea = 0.toFloat()
    var roomPrice = 0.toFloat()

    companion object {
        fun getDiffPrice(): Float {
            return 100.0001f
        }

        fun getDiffArea(): Float {
            return 0.00001f
        }
    }

    fun rentRoom(mediator: Mediator) {
        System.out.println("租到房啦！" + mediator.rentOut(roomArea, roomPrice))
    }
}