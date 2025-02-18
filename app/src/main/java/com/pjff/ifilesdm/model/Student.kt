package com.pjff.ifilesdm.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root


//Paso 36, le ponemos el root, para el xml, y le ponemos el nombre del elemento
@Root(name = "student")
//Paso 28, creamos la dataclass
data class Student(
    //Paso 37,Para el xml debemos poner las notaciones
    @field:Element(name = "id")
    //Paso 29,Pasa el tiempo en milisegundos del sistema y lo pasa a entero
    var id: Int = System.currentTimeMillis().toInt(), //Para generar un id único cada vez


    @field:Element(name = "name")
    //Paso 30,Ponemos el nombre del elemento
    var name: String? = null
)
