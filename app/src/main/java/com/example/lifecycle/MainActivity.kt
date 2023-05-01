package com.example.lifecycle

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RowBlog("Jatin Kumar", "Android Developer")
            MainScreen()
        }
    }
}

@Composable
fun MainScreen(){
    ComposableLifecycle{source, event->
        when(event){
            Lifecycle.Event.ON_CREATE->{ Log.d("TAG", "MainScreen: onCreate")}
            Lifecycle.Event.ON_START->{Log.d("TAG", "MainScreen: ON_START")}
            Lifecycle.Event.ON_RESUME->{Log.d("TAG", "MainScreen: ON_RESUME")}
            Lifecycle.Event.ON_PAUSE->{Log.d("TAG", "MainScreen: ON_PAUSE")}
            Lifecycle.Event.ON_STOP->{Log.d("TAG", "MainScreen: ON_STOP")}
            Lifecycle.Event.ON_DESTROY->{Log.d("TAG", "MainScreen: ON_DESTROY")}
            else -> {Log.d("TAG", "MainScreen: bye bye")}
        }
    }

}

@Composable
fun ComposableLifecycle(
    // The ComposableLifecycle function takes in a lifecycleOwner parameter
    // that defaults to the current LocalLifecycleOwner
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    // It also takes in an onEvent callback that will be called with the
    // LifecycleOwner and Lifecycle.Event when a lifecycle event occurs
    onEvent:(LifecycleOwner,Lifecycle.Event) -> Unit
){
    // The DisposableEffect is a Composable that allows you to perform
    // side effects during composition and dispose of them when they're
    // no longer needed
    DisposableEffect(lifecycleOwner){
        // Here, we create a LifecycleEventObserver that takes in the
        // source (the LifecycleOwner) and the event that occurred
        val observer = LifecycleEventObserver{ source, event ->
            // We call the onEvent callback with the source and event
            onEvent(source, event)
        }
        // We add the observer to the lifecycle of the lifecycleOwner
        lifecycleOwner.lifecycle.addObserver(observer)

        // We remove the observer from the lifecycle when the effect is disposed
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}



@Composable
fun RowBlog(name: String, subtitle : String){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center,

        ) {
        Card(
            modifier = Modifier.padding(8.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            colors = CardDefaults.cardColors(Color.Cyan),
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp)
            ){
                Image(
                    painter = painterResource(id = R.drawable.profile_picture),
                    contentDescription = "",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .rotate(-90f)
                )
                details(
                    name = name,
                    subtitle = subtitle
                )
            }
        }
        Card(
            modifier = Modifier.padding(15.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            colors = CardDefaults.cardColors(Color.Red),
        ) {
            Text(
                text = "For observing the lifecycle state refer to logcat",
                modifier = Modifier.padding(8.dp),
                color = Color.White
            )

        }
    }
}


@Preview(showBackground = true , name = "Lazy Row", showSystemUi = true)
@Composable
fun Preview2(){
    RowBlog("Jatin Kumar", "Android Developer")
}


@Composable
fun details(name: String, subtitle: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = name,
            style = MaterialTheme.typography.headlineMedium,
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium
        )
    }

}
