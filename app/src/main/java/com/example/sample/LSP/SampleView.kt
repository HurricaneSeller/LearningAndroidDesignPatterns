package com.example.sample.LSP

abstract class SampleView{
    abstract fun draw()
    fun measure(width: Int, height: Int) {
        //测量的代码放在这里
    }
}