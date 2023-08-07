package com.mark.myapplication.bean

import java.io.File

data class MediaBean (var name : String){

    constructor(name :String , url : String ) : this(name){
    }

    var id = 0
    lateinit var mediaType: MediaType

    lateinit var file : File
    lateinit var url : String
    var totalTime : Long = 0
    var width : Int = 0
    var height : Int = 0

    var iconRes: Int = 0
    lateinit var iconNetRes : String


}