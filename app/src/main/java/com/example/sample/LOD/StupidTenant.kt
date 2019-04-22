package com.example.sample.LOD

class StupidTenant {
    private val roomArea: Float = 0.toFloat()
    private val roomPrice: Float = 0.toFloat()
    fun rentRoom(mediator: StupidMediator) {
        val rooms = mediator.getStupidRooms()
        for (stupidRoom in rooms) {
            if (isSuitable(stupidRoom)) {
                println("find what u what!")
                break
            }
        }
    }

    private fun isSuitable(stupidRoom: Room): Boolean {
        return Math.abs(stupidRoom.getArea() - roomArea) < diffArea && Math.abs(stupidRoom.getPrice() - roomPrice) < diffPrice
    }

    companion object {
        private val diffPrice = 100.0001f
        private val diffArea = 0.00001f
    }
}
