package kr.ac.kumoh.ce.s20200710.s26midexamhackernews

import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import kr.ac.kumoh.ce.s20200710.s26midexamhackernews.repository.HackerNewsRepository
import kr.ac.kumoh.ce.s20200710.s26midexamhackernews.ui.screen.NewsListScreen
import kr.ac.kumoh.ce.s20200710.s26midexamhackernews.ui.screen.CommentListScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import kr.ac.kumoh.ce.s20200710.s26midexamhackernews.navigation.NewsRoute
import kr.ac.kumoh.ce.s20200710.s26midexamhackernews.ui.theme.S26MidExamHackerNewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            S26MidExamHackerNewsTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val repository = HackerNewsRepository()
    val backStack = rememberNavBackStack(NewsRoute.TopNews)

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavDisplay(
            modifier = Modifier.padding(innerPadding),
            backStack = backStack,

            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider= entryProvider{
                entry<NewsRoute.TopNews> {
                    NewsListScreen(
                        repository = repository,
                        onNavigateToComment = { title, kids ->
                            backStack.add(
                                NewsRoute.NewsComment(title, kids)
                            )
                        }
                    )
                }
                entry<NewsRoute.NewsComment> { route ->
                    CommentListScreen(
                        text = route.text,
                        kids = route.kids,
                        repository = repository,
                        onNavigateToComment = { text, kids ->
                            backStack.add(
                                NewsRoute.NewsComment(text, kids)
                            )
                        }
                    )
                }
            }
        )
    }
}

