package com.example.sample.LSP

import com.example.sample.LSP.SampleView

class SampleWindow {
    fun draw(child: SampleView) {
        child.draw()
    }
}