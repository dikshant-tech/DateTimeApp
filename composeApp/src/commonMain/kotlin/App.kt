import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import datetime.composeapp.generated.resources.Res
import datetime.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

data class City(
    val name: String,
    var timeZone: TimeZone
)


@Composable
@Preview
fun App() {
    MaterialTheme {
       var cities = remember {
           listOf(
               City("London", TimeZone.of("Europe/London")),
               City("Madrid", TimeZone.of("Europe/Madrid")),
               City("Paris", TimeZone.of("Europe/Paris")),
               City("Tokyo", TimeZone.of("Asia/Tokyo"))

           )
       }
        var cityTimes = remember {
            mutableStateOf(
                listOf<Pair<City, LocalDateTime>>()
            )
        }
        LaunchedEffect(true){
            while(true){
                cityTimes.value = cities.map {it
                    var nowTime = Clock.System.now()
                    it to nowTime.toLocalDateTime(it.timeZone)
                }
                delay(1000L)
            }
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
          items(cityTimes.value){(city,dateTime)->
              Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                 Text(text = city.name,
                     fontSize = 30.sp,
                     fontWeight = FontWeight.Bold

                     )

                  Column(
                      horizontalAlignment = Alignment.End
                  ){
                      Text(
                          text = dateTime.format(
                              LocalDateTime.Format {
                                  hour()
                                  char(':')
                                  minute()
                                  char(':')
                                  second()
                              }
                          ),
                          fontSize = 30.sp,
                          fontWeight = FontWeight.Light
                      )

                      Text(
                          text = dateTime.format(
                              LocalDateTime.Format {
                                  dayOfMonth()
                                  char('/')
                                  monthNumber()
                                  char('/')
                                  year()
                              }
                          ),
                          fontSize = 30.sp,
                          fontWeight = FontWeight.Light
                      )
                  }
              }

          }

        }
    }
}