package com.tawajood.the_community_user.app.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.CardGiftcard
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.tawajood.the_community_user.app.language.LocalStrings
import com.tawajood.the_community_user.app.language.Strings
import com.tawajood.the_community_user.app.ui.shared.UiText
import com.tawajood.the_community_user.app.ui.shared.noRippleClickable

sealed class BottomBarScreen(
    val route: Any,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val label: (Strings) -> String
) {
    data object Home : BottomBarScreen(
        route = Splash,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        label = { it.home }
    )

    data object TopRate : BottomBarScreen(
        route = Splash,
        selectedIcon = Icons.Filled.CardGiftcard,
        unselectedIcon = Icons.Outlined.CardGiftcard,
        label = { it.topRate }
    )

    data object Championships : BottomBarScreen(
        route = Splash,
        selectedIcon = Icons.Filled.EmojiEvents,
        unselectedIcon = Icons.Outlined.EmojiEvents,
        label = { it.championshipsTitle }
    )

    data object Matches : BottomBarScreen(
        route = Splash,
        selectedIcon = Icons.Filled.LocalFireDepartment,
        unselectedIcon = Icons.Filled.LocalFireDepartment,
        label = { it.matches }
    )

    data object Record : BottomBarScreen(
        route = Splash,
        selectedIcon = Icons.Filled.AccessTimeFilled,
        unselectedIcon = Icons.Outlined.AccessTime,
        label = { it.record }
    )

    data object Profile : BottomBarScreen(
        route = Splash,
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.PersonOutline,
        label = { it.profile }
    )
}

val Any.navRoute: String?
    get() = this::class.qualifiedName


@Composable
fun BottomNavigation(
    navController: NavHostController,
    content: @Composable () -> Unit
) {
    val items = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Championships,
        BottomBarScreen.Matches,
        BottomBarScreen.Record,
        BottomBarScreen.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    var bottomBarState by rememberSaveable { (mutableStateOf(true)) }

    bottomBarState = currentDestination?.route in listOf(
        BottomBarScreen.Home.route.navRoute,
        BottomBarScreen.Championships.route.navRoute,
        BottomBarScreen.TopRate.route.navRoute,
        BottomBarScreen.Matches.route.navRoute,
        BottomBarScreen.Record.route.navRoute,
        BottomBarScreen.Profile.route.navRoute,
    )

    Box(modifier = Modifier.fillMaxSize()) {
        content()

        AnimatedVisibility(
            visible = bottomBarState,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> fullHeight }
            ) + fadeIn(
                animationSpec = tween(
                    durationMillis = 250,
                    easing = FastOutSlowInEasing
                )
            ),
            exit = slideOutVertically(
                targetOffsetY = { fullHeight -> fullHeight }
            ) + fadeOut(
                animationSpec = tween(
                    durationMillis = 200,
                    easing = FastOutSlowInEasing
                )
            )
        ) {
            BottomNavigation(
                items = items,
                onItemSelected = { screen ->
                    navController.navigate(screen.route) {
                        popUpTo(BottomBarScreen.Home.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                currentDestination = currentDestination
            )
        }
    }
}

@Composable
fun BottomNavigation(
    items: List<BottomBarScreen>,
    onItemSelected: (BottomBarScreen) -> Unit,
    modifier: Modifier = Modifier,
    purple: Color = Color.Black,
    currentDestination: NavDestination?
) {
    val strings = LocalStrings.current

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    clip = false
                ),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items.forEach { item ->
                        val labelText = item.label(strings)
                        val isSelected = currentDestination?.hierarchy?.any {
                            it.route == item.route.navRoute
                        } == true
                        val textColor = if (isSelected) purple else Color(0xFFB0B0B0)

                        if (item != BottomBarScreen.Matches) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .navigationBarsPadding()
                                    .noRippleClickable { onItemSelected(item) },
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = labelText,
                                    tint = if (isSelected) Color.Black else Color(0xFFB0B0B0),
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(Modifier.height(4.dp))
                                UiText(
                                    text = labelText,
                                    color = textColor,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center,
                                    maxLines = 1
                                )
                            }
                        } else {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .navigationBarsPadding(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Spacer(Modifier.size(22.dp))
                                Spacer(Modifier.height(4.dp))
                                UiText(
                                    text = labelText,
                                    color = textColor,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }
        }
        val matchItem = BottomBarScreen.Matches
        val labelText = matchItem.label(strings)

        val infiniteTransition = rememberInfiniteTransition(label = "float")
        val floatOffset by infiniteTransition.animateFloat(
            initialValue = -4f,
            targetValue = 4f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1400, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "floatOffset"
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .graphicsLayer {
                    translationY = floatOffset
                }
        ) {
            Box(
                modifier = Modifier
                    .offset(y = (-14).dp)
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(purple)
                    .noRippleClickable { onItemSelected(matchItem) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = matchItem.selectedIcon,
                    contentDescription = labelText,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}
