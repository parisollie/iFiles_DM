package com.pjff.ifilesdm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.pjff.ifilesdm.databinding.ActivityMainBinding
import com.pjff.ifilesdm.model.Student
import org.simpleframework.xml.core.Persister
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.StringWriter

class MainActivity : AppCompatActivity() {
    //-----------------FUNCIONES DE EXTENSION --------------------------

    //Paso 5,Agregamos  nuestro binding
    private lateinit var binding: ActivityMainBinding

    //Paso 34,Para el xml
    private lateinit var serializer: Persister

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Paso 6, Ponemos el binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Paso 35, Instanciamos para el xml
        serializer = Persister()


        //Esto es un toast de manera larga
        //Toast.makeText(this, "Hola", Toast.LENGTH_SHORT).show()

        /*Paso 8,Le mandamos el toast de nuestras funciones de extencion del archivo utils.kt*/
        //toast("Hola", Toast.LENGTH_LONG)

        //Paso 11, Le mandamos a llamar nuestra función de extension de Snackbar
        sbMessage(binding.clRoot, "Hola")

        //Paso 12, Para poder guardar la información del botón
        binding.btnSave.setOnClickListener {
            //llamamos a la funcion de extensión para que me oculte el teclado
            it.hideKeyboard()

            /*Paso 13, Hacemos una validación,(tietText) es donde el usuario escribe ,agarro lo que
            escribio el usuario,y sino esta vacio*/
            if(!binding.tietText.text.toString().isEmpty()){
                //El usuario escribió algo
                //Paso 15
                //val bytesToSave = binding.tietText.text.toString().encodeToByteArray()

                //Paso 31, importamos la clase student, toma el id por defecto con la clase system.
                val student = Student(name = binding.tietText.text.toString())

                //Paso 32, para guardarmos lo pasamos al Json.
                //val bytesToSave = Gson().toJson(student).encodeToByteArray()


                /*--------------------------------XML-------------------------------------*/

                //Paso 38,Creamos un escritor para el XML y lo instanciamos.
                val writer = StringWriter()

                //Paso 39,Serializamos el objeto en XML
                serializer.write(student, writer)

                //Paso 40,Obtenemos el XML como cadena
                val xmlString = writer.toString()

                //Paso 41
                val bytesToSave = xmlString.encodeToByteArray()

                /*-----------------------------------------------------------------------*/

                //Paso 16, ponemos el try
                try{
                    //Paso 17, le ponemos la carpeta donde se va a guardar
                    val file = File(filesDir, "app_data.txt")

                    //Si el archivo txt no existe , creame uno nuevo
                    if(!file.exists()) file.createNewFile()

                    //Paso 18, Como se hacía en Java
                    /*val fos = FileOutputStream(file)
                    fos.write(bytesToSave)
                    fos.close()*/

                    //------Es la nueva forma----

                    /*Paso 19,tenemos un metodo llamado writeBytes que nos permite escribir en el archivo
                    Escribimos en el archivo,sobreescribiendo el archivo*/

                    file.writeBytes(bytesToSave)
                    /*Para aregar datos al archivo,en vez de estar sobreescribiendo va estar
                     agregando contenido al archivo mientras lo modificamos
                    file.appendBytes(bytesToSave)*/

                    //Paso 20,Resetamos la cajita de texto.
                    binding.tietText.setText("")
                    //Paso 21 Establecemos la caja de texto en vacía(textView)
                    binding.tvContent.text = ""

                    //sbMessage(binding.root,"información ejecuta correctamente")
                    //Paso 22, Le ponemos el color verde cuando todo fue ok
                    sbMessage(binding.root, "Información almacenada exitosamente", bgColor = "#50C228")


                }catch(e: IOException){
                    //Manejo de la excepción
                    e.printStackTrace()
                }
            //Si el usuario no escribio algo  y usamos nuestra función de extensión
            }else{
                //Paso 14, esto esta harcodeado
                sbMessage(binding.root, "Ingrese la información a almacenar")

                binding.tietText.error = "No puede estar vacío"
                binding.tietText.requestFocus()
            }
        }

    }

    //Paso 23, para leer lo que escribio el usuario.
    fun clickRead(view: View) {
        try{
            //Paso 25,Instanciamos la carpeta donde mandamos ese archivo.
            val file = File(filesDir,"app_data.txt")

            //Si el archivo existe.
            if(file.exists()){
                /*------------------------------------JSON-----------------------------------*/
                //Paso 33, para leer on Json
                /*val jsonString = file.readBytes().decodeToString()
                //Le parseamos el Json y al modelo de datos que lo va a mappear a Java
                val student = Gson().fromJson(jsonString,Student::class.java)
                //Le mostramos al usuario lo que hay en el archivo
                binding.tvContent.text = "ID: ${student.id},Nombre:${student.name}"*/
                /*--------------------------------------------------------------------------*/

                /*------------------------------------XML-----------------------------------*/
                //Paso 42, Para leerlo con formato XML
                val xmlString = file.readBytes().decodeToString()

                //Paso 43, Para recuperar mi objeto student
                val student = serializer.read(Student::class.java, xmlString)
                binding.tvContent.text = "ID: ${student.id}, Nombre: ${student.name}"

                /*------------------------------------------------------------------------*/


                //val xmlString = file.readBytes().decodeToString()
                //val student = serializer.read(Student::class.java, xmlString)
                //Le mostramos al usuario lo que hay en el archivo
                // binding.tvContent.text = "ID: ${student.id}, Nombre: ${student.name}"

                /*Paso 27,Para poder leer en Java
                val fis = FileInputStream(file)
                val content = fis.readBytes()
                binding.tvContent.text = content.decodeToString()
                fis.close()*/

                //Simplificado el paso 27.
                //binding.tvContent.text = file.readBytes().decodeToString()


            }else{
                //Paso 26,Si el archivo no existe.
                sbMessage(binding.clRoot, "No existe información o archivo almacenado")
            }

        }catch(e: Exception){
            //Paso 24,manejo de excepciones.
            e.printStackTrace()
            Log.d("LOGS", "Error: ${e.message.toString()}")
        }
    }
}

