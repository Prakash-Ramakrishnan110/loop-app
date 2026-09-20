package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.*
import com.example.ui.components.*
import com.example.ui.navigation.LoopNavGraph
import com.example.ui.navigation.LoopRoute
import com.example.ui.screens.*
import com.example.ui.theme.LoopTheme

sealed class Screen {
    object Onboarding : Screen()
    object Login : Screen()
    object CustomerMain : Screen()
    data class CategoryListing(val category: ServiceCategory) : Screen()
    data class ProfessionalProfile(val provider: ServiceProvider) : Screen()
    data class BookService(val provider: ServiceProvider) : Screen()
    object EventStaffBooking : Screen()
    data class BookingDetail(val bookingId: String) : Screen()
    data class Chat(val bookingId: String) : Screen()
    object Search : Screen()
    object Notifications : Screen()
    object Support : Screen()
    object ProviderRegistration : Screen()
    object ProviderEarnings : Screen()
    object ProviderManageServices : Screen()
}

enum class CustomerTab(val labelEn: String, val labelHi: String, val icon: ImageVector) {
    HOME("Explore", "सेवाएं", Icons.Default.Explore),
    BOOKINGS("Bookings", "बुकिंग", Icons.Default.ReceiptLong),
    NOTIFICATIONS("Alerts", "सूचनाएं", Icons.Default.Notifications),
    PROFILE("Profile", "प्रोफ़ाइल", Icons.Default.Person)
}

enum class ProviderTab(val label: String, val icon: ImageVector) {
    DASHBOARD("Dashboard", Icons.Default.Dashboard),
    BOOKINGS("Jobs", Icons.Default.Assignment),
    SERVICES("Rates", Icons.Default.Tune),
    WALLET("Earnings", Icons.Default.AccountBalanceWallet)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDark by LoopRepository.isDarkMode.collectAsState()
            val currentRole by LoopRepository.currentRole.collectAsState()
            val lang by LoopRepository.language.collectAsState()
            val notifs by LoopRepository.notifications.collectAsState()
            val isAuthenticated by LoopRepository.isAuthenticated.collectAsState()
            var screenStack by remember { mutableStateOf(listOf<Screen>(Screen.CustomerMain)) }
            var currentCustomerTab by remember { mutableStateOf(CustomerTab.HOME) }
            var currentProviderTab by remember { mutableStateOf(ProviderTab.DASHBOARD) }

            val currentScreen = screenStack.lastOrNull() ?: Screen.CustomerMain

            fun navigateTo(screen: Screen) {
                screenStack = screenStack + screen
            }

            fun navigateBack() {
                if (screenStack.size > 1) {
                    screenStack = screenStack.dropLast(1)
                }
            }

            BackHandler(enabled = screenStack.size > 1) {
                navigateBack()
            }

            LoopTheme(darkTheme = isDark) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoopNavGraph(
                        startDestination = if (isAuthenticated) LoopRoute.MainApp.route else LoopRoute.Splash.route,
                        onLoginSuccess = {
                            screenStack = listOf(Screen.CustomerMain)
                        }
                    ) {
                        // Authenticated App Flow without top mode switcher bar
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .statusBarsPadding()
                        ) {
                            when (currentRole) {
                                UserRole.CUSTOMER -> {
                                    CustomerFlowContainer(
                                        currentScreen = currentScreen,
                                        currentTab = currentCustomerTab,
                                        onSelectTab = { currentCustomerTab = it },
                                        onNavigate = { navigateTo(it) },
                                        onBack = { navigateBack() },
                                        unreadNotifs = notifs.size,
                                        lang = lang
                                    )
                                }
                                UserRole.PROVIDER -> {
                                    ProviderFlowContainer(
                                        currentScreen = currentScreen,
                                        currentTab = currentProviderTab,
                                        onSelectTab = { currentProviderTab = it },
                                        onNavigate = { navigateTo(it) },
                                        onBack = { navigateBack() }
                                    )
                                }
                                UserRole.ADMIN -> {
                                    AdminDashboardScreen()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomerFlowContainer(
    currentScreen: Screen,
    currentTab: CustomerTab,
    onSelectTab: (CustomerTab) -> Unit,
    onNavigate: (Screen) -> Unit,
    onBack: () -> Unit,
    unreadNotifs: Int,
    lang: AppLanguage
) {
    Scaffold(
        bottomBar = {
            // Show bottom bar only on main screens
            if (currentScreen is Screen.CustomerMain) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 6.dp
                ) {
                    CustomerTab.values().forEach { tab ->
                        val selected = currentTab == tab
                        NavigationBarItem(
                            selected = selected,
                            onClick = { onSelectTab(tab) },
                            icon = {
                                if (tab == CustomerTab.NOTIFICATIONS && unreadNotifs > 0) {
                                    BadgedBox(badge = { Badge { Text("$unreadNotifs") } }) {
                                        Icon(tab.icon, contentDescription = tab.labelEn)
                                    }
                                } else {
                                    Icon(tab.icon, contentDescription = tab.labelEn)
                                }
                            },
                            label = {
                                Text(
                                    text = if (lang == AppLanguage.HINDI) tab.labelHi else tab.labelEn,
                                    fontSize = 11.sp
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        )
                    }
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (currentScreen) {
                is Screen.Onboarding -> {
                    AppOnboardingScreen(onFinish = { role -> onNavigate(Screen.Login) })
                }
                is Screen.Login -> {
                    AuthScreen(
                        initialRole = UserRole.CUSTOMER,
                        onOpenOnboarding = { onNavigate(Screen.Onboarding) },
                        onLoginSuccess = { onBack() }
                    )
                }
                is Screen.CustomerMain -> {
                    when (currentTab) {
                        CustomerTab.HOME -> {
                            Column {
                                LoopTopBar(
                                    title = "Loop Services",
                                    subtitle = "Hyper-Local Experts Near You",
                                    unreadNotifs = unreadNotifs,
                                    onNotificationClick = { onSelectTab(CustomerTab.NOTIFICATIONS) },
                                    currentLang = lang,
                                    onToggleLang = {
                                        LoopRepository.setLanguage(if (lang == AppLanguage.ENGLISH) AppLanguage.HINDI else AppLanguage.ENGLISH)
                                    }
                                )
                                CustomerHomeScreen(
                                    onSelectCategory = { onNavigate(Screen.CategoryListing(it)) },
                                    onBookEventStaff = { onNavigate(Screen.EventStaffBooking) },
                                    onSelectProvider = { onNavigate(Screen.ProfessionalProfile(it)) },
                                    onOpenSearch = { onNavigate(Screen.Search) },
                                    onOpenBookings = { onSelectTab(CustomerTab.BOOKINGS) }
                                )
                            }
                        }
                        CustomerTab.BOOKINGS -> {
                            Column {
                                LoopTopBar(
                                    title = if (lang == AppLanguage.HINDI) "मेरी बुकिंग्स" else "My Bookings",
                                    subtitle = "Track real-time progress",
                                    currentLang = lang,
                                    onToggleLang = {
                                        LoopRepository.setLanguage(if (lang == AppLanguage.ENGLISH) AppLanguage.HINDI else AppLanguage.ENGLISH)
                                    }
                                )
                                CustomerBookingsScreen(
                                    onSelectBooking = { onNavigate(Screen.BookingDetail(it.id)) }
                                )
                            }
                        }
                        CustomerTab.NOTIFICATIONS -> {
                            NotificationsScreen(onBack = { onSelectTab(CustomerTab.HOME) })
                        }
                        CustomerTab.PROFILE -> {
                            Column {
                                LoopTopBar(
                                    title = if (lang == AppLanguage.HINDI) "मेरी प्रोफ़ाइल" else "Customer Profile",
                                    currentLang = lang,
                                    onToggleLang = {
                                        LoopRepository.setLanguage(if (lang == AppLanguage.ENGLISH) AppLanguage.HINDI else AppLanguage.ENGLISH)
                                    }
                                )
                                CustomerProfileScreen(
                                    onBecomeProvider = { onNavigate(Screen.ProviderRegistration) },
                                    onOpenBookings = { onSelectTab(CustomerTab.BOOKINGS) },
                                    onOpenNotifications = { onSelectTab(CustomerTab.NOTIFICATIONS) },
                                    onOpenSupport = { onNavigate(Screen.Support) }
                                )
                            }
                        }
                    }
                }
                is Screen.CategoryListing -> {
                    CategoryListingScreen(
                        category = currentScreen.category,
                        onBack = onBack,
                        onSelectProvider = { onNavigate(Screen.ProfessionalProfile(it)) }
                    )
                }
                is Screen.ProfessionalProfile -> {
                    ProfessionalProfileScreen(
                        provider = currentScreen.provider,
                        onBack = onBack,
                        onBookNow = { onNavigate(Screen.BookService(currentScreen.provider)) }
                    )
                }
                is Screen.BookService -> {
                    BookServiceScreen(
                        provider = currentScreen.provider,
                        onBack = onBack,
                        onBookingSuccess = { booking ->
                            onNavigate(Screen.BookingDetail(booking.id))
                        }
                    )
                }
                is Screen.EventStaffBooking -> {
                    EventStaffBookingScreen(
                        onBack = onBack,
                        onBookingSuccess = { booking ->
                            onNavigate(Screen.BookingDetail(booking.id))
                        }
                    )
                }
                is Screen.BookingDetail -> {
                    BookingDetailScreen(
                        bookingId = currentScreen.bookingId,
                        onBack = onBack,
                        onOpenChat = { onNavigate(Screen.Chat(it.id)) },
                        onRateJob = { booking ->
                            LoopRepository.rateBooking(booking.id, 5.0f, "Excellent service! On time and very polite.")
                        }
                    )
                }
                is Screen.Chat -> {
                    ChatScreen(
                        bookingId = currentScreen.bookingId,
                        onBack = onBack
                    )
                }
                is Screen.Search -> {
                    SearchResultsScreen(
                        onBack = onBack,
                        onSelectProvider = { onNavigate(Screen.ProfessionalProfile(it)) },
                        onSelectCategory = { onNavigate(Screen.CategoryListing(it)) }
                    )
                }
                is Screen.Notifications -> {
                    NotificationsScreen(onBack = onBack)
                }
                is Screen.Support -> {
                    SupportScreen(onBack = onBack)
                }
                is Screen.ProviderRegistration -> {
                    ProviderRegistrationScreen(
                        onBack = onBack,
                        onRegistrationSubmitted = {
                            LoopRepository.switchRole(UserRole.PROVIDER)
                            onBack()
                        }
                    )
                }
                else -> {
                    // Fallback
                    Box(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun ProviderFlowContainer(
    currentScreen: Screen,
    currentTab: ProviderTab,
    onSelectTab: (ProviderTab) -> Unit,
    onNavigate: (Screen) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            LoopTopBar(
                title = "Loop Partner Pro",
                subtitle = "Service Provider Terminal",
                showBack = currentScreen !is Screen.CustomerMain,
                onBackClick = onBack,
                actions = {
                    IconButton(onClick = { LoopRepository.logout() }) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Sign Out & Switch Account",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
        bottomBar = {
            if (currentScreen is Screen.CustomerMain) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 6.dp
                ) {
                    ProviderTab.values().forEach { tab ->
                        val selected = currentTab == tab
                        NavigationBarItem(
                            selected = selected,
                            onClick = { onSelectTab(tab) },
                            icon = { Icon(tab.icon, contentDescription = tab.label) },
                            label = { Text(tab.label, fontSize = 11.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        )
                    }
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (currentScreen) {
                is Screen.CustomerMain -> {
                    when (currentTab) {
                        ProviderTab.DASHBOARD -> {
                            ProviderDashboardScreen(
                                onOpenBookings = { onSelectTab(ProviderTab.BOOKINGS) },
                                onOpenEarnings = { onSelectTab(ProviderTab.WALLET) },
                                onOpenServices = { onSelectTab(ProviderTab.SERVICES) }
                            )
                        }
                        ProviderTab.BOOKINGS -> {
                            CustomerBookingsScreen(
                                onSelectBooking = { onNavigate(Screen.BookingDetail(it.id)) }
                            )
                        }
                        ProviderTab.SERVICES -> {
                            ProviderManageServicesScreen(onBack = { onSelectTab(ProviderTab.DASHBOARD) })
                        }
                        ProviderTab.WALLET -> {
                            ProviderEarningsScreen(onBack = { onSelectTab(ProviderTab.DASHBOARD) })
                        }
                    }
                }
                is Screen.BookingDetail -> {
                    BookingDetailScreen(
                        bookingId = currentScreen.bookingId,
                        onBack = onBack,
                        onOpenChat = { onNavigate(Screen.Chat(it.id)) },
                        onRateJob = {}
                    )
                }
                is Screen.Chat -> {
                    ChatScreen(bookingId = currentScreen.bookingId, onBack = onBack)
                }
                is Screen.ProviderRegistration -> {
                    ProviderRegistrationScreen(
                        onBack = onBack,
                        onRegistrationSubmitted = { onBack() }
                    )
                }
                else -> {
                    Box(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}
