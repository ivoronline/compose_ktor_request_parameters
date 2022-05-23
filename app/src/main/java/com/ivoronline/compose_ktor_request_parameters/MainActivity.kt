package com.ivoronline.compose_ktor_request_parameters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.launch

//==================================================================
// MAIN ACTIVITY
//==================================================================
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {

      var responseBody by remember { mutableStateOf("") }
      val coroutineScope = rememberCoroutineScope()

      Button(onClick = { coroutineScope.launch { responseBody = callURL() } }) {
        Text("RESPONSE: $responseBody")
      }

    }
  }
}

//==================================================================
// CALL URL
//==================================================================
suspend fun callURL() : String {

  //CONFIGURE CLIENT
  val httpClient = HttpClient(CIO)

  //CAL URL
  val httpResponse: HttpResponse = httpClient.get("http://192.168.0.102:8080/SendRequestParameters") {
    parameter("name", "John")
    parameter("age" , 20    )
  }

  //CLOSE CLIENT
  httpClient.close()

  //RETURN BODY
  return httpResponse.body();    //Joh is 20 years old
}
