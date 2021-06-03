package com

import com.epam.drill.kni.Kni

@Kni
actual object WebContainerSource {
    fun fillWebAppSource(warPath: String?, warResource: String?) {
        println("fillWebAppSource")
        if (warPath == null || warResource == null) {
            return
        }
        webAppStarted()
    }

    actual external fun webAppStarted()
    actual external fun printSmth(index: Int)
}
