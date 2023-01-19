package com.example.intermodular.home.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Home(homeviewmodel : HomeViewModel){
    var home by remember { mutableStateOf(true) }
    var fav by remember { mutableStateOf(false) }
    var mapa by remember { mutableStateOf(false) }

    Scaffold(
        topBar= {
            TopBar()
        },

        bottomBar= {
            BottomBar(showHome= {
                home= true
                fav= false
                mapa= false
            }, showFav= {
                home= false
                fav= true
                mapa= false
            }, showMapa= {
                home= false
                fav= false
                mapa= true
            })
        }
    ){
        if (home){
            ShowHome(homeviewmodel)
        }

        if(fav){
            ShowFav()
        }

        if(mapa){
            ShowMapa()
        }
    }
}

@Composable
fun TopBar(){
    TopAppBar(
        navigationIcon= {
            IconButton(onClick= {/* */}){
                Icon(
                    imageVector= Icons.Filled.Menu,
                    contentDescription= "...",
                    tint= Color.White
                )
            }
        },
        title= {
            Text(
                text= "WIKILOC",
                color= Color.White
            )
        },
        backgroundColor = MaterialTheme.colors.primary,
        actions= {
            IconButton(onClick= {/**/}){
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription= "...",
                    tint= Color.White
                )
            }
        }
    )
}

@Composable
fun BottomBar(
    showHome: () -> Unit,
    showFav: () -> Unit,
    showMapa: () -> Unit
){
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary
    ) {
        IconButton(onClick= { showMapa()}){
            Icon(
                imageVector = Icons.Default.MyLocation,
                contentDescription= "...",
                tint= Color.White,
                modifier= Modifier.size(40.dp)
            )
        }

        IconButton(onClick= { showHome()}){
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription= "...",
                tint= Color.White,
                modifier= Modifier.size(40.dp)
            )
        }

        IconButton(onClick= {showFav()}){
            Icon(
                imageVector= Icons.Default.Favorite,
                contentDescription= "...",
                tint= Color.White,
                modifier= Modifier.size(40.dp)
            )
        }
    }
}

@Composable
fun ShowHome(homeviewmodel: HomeViewModel){

}

@Composable
fun ShowFav(){

}

@Composable
fun ShowMapa(){

}