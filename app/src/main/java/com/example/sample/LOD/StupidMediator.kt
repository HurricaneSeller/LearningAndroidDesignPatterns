package com.example.sample.LOD

import java.util.ArrayList

class StupidMediator {
    private val stupidRooms = ArrayList<Room>()

    init {
        for (i in 0..4) {
            stupidRooms.add(Room((14 + i).toFloat(), ((14 + i) * 150).toFloat()))
        }
    }

    fun getStupidRooms(): List<Room> {
        return stupidRooms
    }
}
