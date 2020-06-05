package com.sonikpalms.testingarage.pojo

data class Contact (var user_id: String = "",
                    var user_image: Int = 0,
                    var user_name: String = "",
                    var last_name: String = "" ,
                    var email: String= ""){

    constructor(user_image:Int , user_name:String , last_name:String , email:String):
        this("", user_image, user_name, last_name, email)

}


