package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.*
import com.example.ui.components.*
import com.example.ui.theme.*

// Helper for category icons
fun getCategoryIcon(iconName: String): ImageVector {
    return when (iconName) {
        "plumbing" -> Icons.Default.Plumbing
        "flash_on" -> Icons.Default.ElectricBolt
        "cleaning_services" -> Icons.Default.CleaningServices
        "construction" -> Icons.Default.Handyman
        "format_paint" -> Icons.Default.FormatPaint
        "ac_unit" -> Icons.Default.AcUnit
        "groups" -> Icons.Default.Groups
        else -> Icons.Default.Build
    }
}

// -------------------------------------------------------------
// 1. ONBOARDING & AUTH SCREENS
// -------------------------------------------------------------
@Composable
fun OnboardingScreen(
    onFinish: () -> Unit
) {
    var currentPage by remember { mutableIntStateOf(0) }
    val pages = listOf(
        Triple(
            "Hyper-Local Home Services",
            "Book trusted plumbers, electricians, cleaners, and carpenters in your neighborhood within minutes.",
            Icons.Default.HomeRepairService
        ),
        Triple(
            "Event & Marriage Staff",
            "Hire verified banquet waiters, catering helpers, and cleanup staff for your grand wedding and private functions.",
            Icons.Default.Celebration
        ),
        Triple(
            "Real-Time Tracking & Secure Pay",
            "Track your service provider live and pay securely via Razorpay UPI, Cards, and Net Banking.",
            Icons.Default.VerifiedUser
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onFinish) {
                Text("Skip", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            }
        }

        val (title, desc, icon) = pages[currentPage]

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(130.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(64.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = desc,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                repeat(pages.size) { idx ->
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .height(8.dp)
                            .width(if (currentPage == idx) 24.dp else 8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                if (currentPage == idx) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                            )
                    )
                }
            }

            Button(
                onClick = {
                    if (currentPage < pages.size - 1) currentPage++ else onFinish()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = if (currentPage == pages.size - 1) "Get Started" else "Continue",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }
    }
}

@Composable
fun PhoneLoginScreen(
    onLoginSuccess: () -> Unit
) {
    var phone by remember { mutableStateOf("9876543210") }
    var showOtp by remember { mutableStateOf(false) }
    var otpCode by remember { mutableStateOf("4819") }
    var name by remember { mutableStateOf("Rahul Sharma") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
            .statusBarsPadding(),
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(56.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(imageVector = Icons.Default.AllInclusive, contentDescription = null, tint = Color.White, modifier = Modifier.size(32.dp))
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = if (!showOtp) "Welcome to Loop" else "Verify Mobile Number",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = if (!showOtp) "Enter your mobile number to sign in or sign up" else "Enter the 4-digit verification code sent to +91 $phone",
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )

        Spacer(modifier = Modifier.height(28.dp))

        if (!showOtp) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Your Full Name") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(14.dp))
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone Number") },
                prefix = { Text("+91 ") },
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    if (phone.isNotBlank()) showOtp = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Get OTP", fontWeight = FontWeight.Bold)
            }
        } else {
            OutlinedTextField(
                value = otpCode,
                onValueChange = { if (it.length <= 4) otpCode = it },
                label = { Text("4-Digit OTP Code") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Demo Auto-fill OTP: 4819",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    LoopRepository.login(phone = "+91 $phone", name = name)
                    onLoginSuccess()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Verify & Continue", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(12.dp))
            TextButton(
                onClick = { showOtp = false },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Change Mobile Number")
            }
        }
    }
}

// -------------------------------------------------------------
// 2. CUSTOMER HOME SCREEN
// -------------------------------------------------------------
@Composable
fun CustomerHomeScreen(
    onSelectCategory: (ServiceCategory) -> Unit,
    onBookEventStaff: () -> Unit,
    onSelectProvider: (ServiceProvider) -> Unit,
    onOpenSearch: () -> Unit,
    onOpenBookings: () -> Unit
) {
    val categories by LoopRepository.categories.collectAsState()
    val providers by LoopRepository.providers.collectAsState()
    val bookings by LoopRepository.bookings.collectAsState()
    val lang by LoopRepository.language.collectAsState()
    val isHindi = lang == AppLanguage.HINDI

    var selectedFilterCategory by remember { mutableStateOf<String?>("ALL") }

    // Find latest active booking for sticky live tracker
    val activeBooking = bookings.firstOrNull { 
        it.status == BookingStatus.CONFIRMED || it.status == BookingStatus.ON_THE_WAY || it.status == BookingStatus.IN_PROGRESS 
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Sticky / Top Active Booking Live Status Pill (If any job in progress)
        if (activeBooking != null) {
            item {
                ActiveBookingStatusPill(
                    booking = activeBooking,
                    onTrack = { onOpenBookings() },
                    onCall = { /* In a real device initiates tel intent */ },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }

        // Location & Search Bar Card
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                                MaterialTheme.colorScheme.background
                            )
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Indiranagar, Bengaluru",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                            Text(
                                text = "Within 5 km radius",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Search Bar Trigger
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .clickable { onOpenSearch() },
                    color = MaterialTheme.colorScheme.surface,
                    shadowElevation = 2.dp,
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = if (isHindi) "प्लंबर, इलेक्ट्रीशियन, वेटर खोजें..." else "Search 'Plumber', 'Electrician', 'Waiters'...",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        // EVENT & MARRIAGE STAFF HERO BANNER
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable { onBookEventStaff() },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1E293B)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xFF1F4E79), Color(0xFF2E75B6))
                            )
                        )
                        .padding(18.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Surface(
                                color = LoopAccent,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "SPECIAL FEATURE",
                                    color = Color.Black,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Icon(Icons.Default.Celebration, contentDescription = null, tint = LoopAccent, modifier = Modifier.size(24.dp))
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (isHindi) "विवाह एवं समारोह स्टाफ बुक करें" else "Event & Marriage Staff Booking",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (isHindi) "वर्दीधारी वेटर, खानपान सहायक एवं सफाई दल उपलब्ध।" else "Uniformed banquet waiters, kitchen helpers, runner boys & cleaning crew on demand.",
                            color = Color(0xFFE2E8F0),
                            fontSize = 12.sp,
                            maxLines = 2
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (isHindi) "अभी बुक करें" else "Book Staff Now",
                                color = LoopAccent,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = LoopAccent,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }

        // Categories Grid Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isHindi) "सेवा श्रेणियां" else "Service Categories",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }

        // Categories 2-Column / Grid
        item {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val chunked = categories.chunked(2)
                chunked.forEach { rowCategories ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        rowCategories.forEach { cat ->
                            Card(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        if (cat.isEventStaff) onBookEventStaff()
                                        else onSelectCategory(cat)
                                    },
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = if (cat.isEventStaff) LoopAccent.copy(alpha = 0.2f) else MaterialTheme.colorScheme.primaryContainer,
                                        modifier = Modifier.size(42.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = getCategoryIcon(cat.icon),
                                                contentDescription = cat.nameEn,
                                                tint = if (cat.isEventStaff) LoopAccent else MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(22.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = if (isHindi) cat.nameHi else cat.nameEn,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = "From ₹${cat.startingPrice}",
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }
                        }
                        if (rowCategories.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }

        // Popular Verified Professionals Header + Sticky Filter Pills
        item {
            Column(modifier = Modifier.padding(top = 18.dp, bottom = 4.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isHindi) "सत्यापित प्रोफेशनल्स" else "Verified Professionals",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.FlashOn, contentDescription = null, tint = Color(0xFFD97706), modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Hyper-Local",
                            color = Color(0xFFD97706),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Sticky Category Filter Pills
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        FilterChip(
                            selected = selectedFilterCategory == "ALL",
                            onClick = { selectedFilterCategory = "ALL" },
                            label = { Text("All Pros", fontSize = 12.sp) }
                        )
                    }
                    items(categories.filter { !it.isEventStaff }) { cat ->
                        FilterChip(
                            selected = selectedFilterCategory == cat.id,
                            onClick = { selectedFilterCategory = cat.id },
                            label = { Text(if (isHindi) cat.nameHi else cat.nameEn, fontSize = 12.sp) }
                        )
                    }
                }
            }
        }

        val filteredPros = providers.filter { p ->
            p.isApproved && (selectedFilterCategory == "ALL" || p.categoryId == selectedFilterCategory)
        }

        items(filteredPros) { provider ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clickable { onSelectProvider(provider) },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(provider.avatarColorHex),
                            modifier = Modifier.size(50.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = provider.name.take(1),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = provider.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = "Verified",
                                    tint = LoopSuccess,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Text(
                                text = "${provider.categoryName} • ${provider.experienceYears} yrs exp",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, contentDescription = null, tint = LoopAccent, modifier = Modifier.size(14.dp))
                                Text(
                                    text = " ${provider.rating} (${provider.reviewsCount})",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "₹${provider.hourlyRate}/hr",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            FilledTonalButton(
                                onClick = { onSelectProvider(provider) },
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Book", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))

                    // Micro-Proximity & Trust badges row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        MicroProximityVisualizer(distanceKm = provider.distanceKm)

                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            VerificationTrustBadge(text = "Aadhaar", icon = Icons.Default.Shield)
                            VerificationTrustBadge(text = "Police Checked", icon = Icons.Default.GppGood)
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 3. CATEGORY LISTING SCREEN
// -------------------------------------------------------------
@Composable
fun CategoryListingScreen(
    category: ServiceCategory,
    onBack: () -> Unit,
    onSelectProvider: (ServiceProvider) -> Unit
) {
    val allProviders by LoopRepository.providers.collectAsState()
    val providers = allProviders.filter { it.categoryId == category.id && it.isApproved }
    var filterRating by remember { mutableStateOf(false) }

    val displayedProviders = if (filterRating) providers.filter { it.rating >= 4.8f } else providers

    Scaffold(
        topBar = {
            LoopTopBar(
                title = category.nameEn,
                subtitle = "${providers.size} professionals available near you",
                showBack = true,
                onBackClick = onBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Filter chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = !filterRating,
                    onClick = { filterRating = false },
                    label = { Text("All Pros") }
                )
                FilterChip(
                    selected = filterRating,
                    onClick = { filterRating = true },
                    label = { Text("Top Rated (4.8+)") }
                )
            }

            if (displayedProviders.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No professionals found for this filter.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(displayedProviders) { pro ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelectProvider(pro) },
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        shape = CircleShape,
                                        color = Color(pro.avatarColorHex),
                                        modifier = Modifier.size(46.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(pro.name.take(1), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(pro.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                        Text("${pro.experienceYears} Years Experience • ${pro.distanceKm} km away", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            StarRatingBar(rating = pro.rating)
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text("${pro.rating} (${pro.reviewsCount})", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text("₹${pro.hourlyRate}/hr", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                                        Text("Starting", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                Text(pro.bio, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 2)

                                Spacer(modifier = Modifier.height(12.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Button(
                                        onClick = { onSelectProvider(pro) },
                                        shape = RoundedCornerShape(10.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                    ) {
                                        Text("Book Now", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 4. PROFESSIONAL PROFILE SCREEN
// -------------------------------------------------------------
@Composable
fun ProfessionalProfileScreen(
    provider: ServiceProvider,
    onBack: () -> Unit,
    onBookNow: () -> Unit
) {
    val allServices by LoopRepository.services.collectAsState()
    val proServices = allServices.filter { it.categoryId == provider.categoryId }

    Scaffold(
        topBar = {
            LoopTopBar(title = provider.name, showBack = true, onBackClick = onBack)
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                shadowElevation = 8.dp,
                modifier = Modifier.navigationBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Base Rate", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("₹${provider.hourlyRate} / hour", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                    }
                    Button(
                        onClick = onBookNow,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.height(48.dp)
                    ) {
                        Text("Book Service", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(provider.avatarColorHex),
                            modifier = Modifier.size(64.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(provider.name.take(1), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(provider.name, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                            if (provider.aadharVerified) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(Icons.Default.Verified, contentDescription = "ID Verified", tint = LoopSuccess, modifier = Modifier.size(18.dp))
                            }
                        }
                        Text(provider.categoryName, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            StarRatingBar(rating = provider.rating)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("${provider.rating} (${provider.reviewsCount} reviews)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        // Micro Proximity visualizer
                        MicroProximityVisualizer(distanceKm = provider.distanceKm)

                        Spacer(modifier = Modifier.height(12.dp))
                        // Trust & Safety Badges Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            VerificationTrustBadge(text = "Aadhaar Verified", icon = Icons.Default.Shield)
                            Spacer(modifier = Modifier.width(6.dp))
                            VerificationTrustBadge(text = "Police Checked", icon = Icons.Default.GppGood)
                            Spacer(modifier = Modifier.width(6.dp))
                            VerificationTrustBadge(text = "Hygiene Certified", icon = Icons.Default.CleanHands, tint = MaterialTheme.colorScheme.primary)
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("${provider.experienceYears}+ Years", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text("Experience", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("${provider.completedJobs}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text("Jobs Done", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("${provider.distanceKm} km", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text("Distance", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = provider.bio,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // BEFORE & AFTER RECENT WORK SHOWCASE GALLERY
            item {
                Column(modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)) {
                    Text(
                        text = "Recent Work: Before & After Proof",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                    Text(
                        text = "Verified job completion photos from previous local assignments",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                    )

                    val showcaseCards = listOf(
                        Triple("Initial Inspection & Issue", "Clean Finished Installation", "Bathroom Drainage Line Replacement"),
                        Triple("Old Faulty Wiring Setup", "Clean Modular Fuse Box & Earthing", "Main Switchboard Upgrade"),
                        Triple("Unorganized Function Hall", "Banquet Ready Setup (6 Waiters)", "Grand Reception Buffet Layout")
                    )

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(showcaseCards) { (beforeLabel, afterLabel, jobTitle) ->
                            Card(
                                modifier = Modifier.width(280.dp),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(jobTitle, fontWeight = FontWeight.Bold, fontSize = 13.sp, maxLines = 1)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        // Before Side
                                        Surface(
                                            modifier = Modifier
                                                .weight(1f)
                                                .height(95.dp),
                                            shape = RoundedCornerShape(8.dp),
                                            color = Color(0xFFF1F5F9),
                                            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                                        ) {
                                            Column(
                                                modifier = Modifier.padding(8.dp),
                                                verticalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Surface(
                                                    color = Color(0xFFEF4444).copy(alpha = 0.15f),
                                                    shape = RoundedCornerShape(4.dp)
                                                ) {
                                                    Text("BEFORE", fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color(0xFFDC2626), modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                                                }
                                                Text(beforeLabel, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 2)
                                                Icon(Icons.Default.Build, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                                            }
                                        }

                                        // After Side
                                        Surface(
                                            modifier = Modifier
                                                .weight(1f)
                                                .height(95.dp),
                                            shape = RoundedCornerShape(8.dp),
                                            color = Color(0xFFF0FDF4),
                                            border = BorderStroke(1.dp, Color(0xFFBBF7D0))
                                        ) {
                                            Column(
                                                modifier = Modifier.padding(8.dp),
                                                verticalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Surface(
                                                    color = LoopSuccess.copy(alpha = 0.15f),
                                                    shape = RoundedCornerShape(4.dp)
                                                ) {
                                                    Text("AFTER", fontSize = 9.sp, fontWeight = FontWeight.Black, color = LoopSuccess, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                                                }
                                                Text(afterLabel, fontSize = 10.sp, color = Color(0xFF166534), maxLines = 2)
                                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = LoopSuccess, modifier = Modifier.size(16.dp))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Offered Services & Rate Card",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            items(proServices) { srv ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(srv.nameEn, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text(srv.description, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("Est. ${srv.durationMin} mins", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                        }
                        Text("₹${srv.price}", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Customer Reviews",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Ananya Deshmukh", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            StarRatingBar(rating = 5f)
                        }
                        Text("2 days ago • Verified Booking", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("Arrived exactly on time with all proper tools. Work was finished cleanly and thoroughly.", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 5. BOOK SERVICE SCREEN (With Razorpay checkout)
// -------------------------------------------------------------
@Composable
fun BookServiceScreen(
    provider: ServiceProvider,
    onBack: () -> Unit,
    onBookingSuccess: (Booking) -> Unit
) {
    val allServices by LoopRepository.services.collectAsState()
    val availableServices = allServices.filter { it.categoryId == provider.categoryId }
    val coupons by LoopRepository.coupons.collectAsState()

    var selectedService by remember { mutableStateOf(availableServices.firstOrNull()) }
    var selectedDate by remember { mutableStateOf("Today, 04:00 PM") }
    var address by remember { mutableStateOf("Flat 402, Green Glen Heights, Indiranagar, Bengaluru") }
    var workDesc by remember { mutableStateOf("Need inspection and immediate repair.") }
    var couponCodeInput by remember { mutableStateOf("LOOP50") }
    var appliedCoupon by remember { mutableStateOf<Coupon?>(coupons.firstOrNull()) }
    var showRazorpaySheet by remember { mutableStateOf(false) }
    var celebrationBooking by remember { mutableStateOf<Booking?>(null) }
    var showPriceBreakdownDialog by remember { mutableStateOf(false) }

    val baseRate = (selectedService?.price ?: provider.hourlyRate).toDouble()
    val discount = if (appliedCoupon != null && baseRate >= appliedCoupon!!.minOrder) {
        (baseRate * appliedCoupon!!.discountPercent / 100.0).coerceAtMost(appliedCoupon!!.maxDiscount)
    } else 0.0
    val tax = (baseRate - discount) * 0.18
    val totalAmount = baseRate - discount + tax

    Scaffold(
        topBar = {
            LoopTopBar(title = "Book Service", subtitle = provider.name, showBack = true, onBackClick = onBack)
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                shadowElevation = 8.dp,
                modifier = Modifier.navigationBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Total to Pay", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("₹${"%.2f".format(totalAmount)}", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                    }
                    Button(
                        onClick = { showRazorpaySheet = true },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.height(48.dp)
                    ) {
                        Text("Pay with Razorpay", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Text("Select Service Item", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    availableServices.forEach { srv ->
                        val isSelected = selectedService?.id == srv.id
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedService = srv },
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f) else MaterialTheme.colorScheme.surface
                            ),
                            border = BorderStroke(1.dp, if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(selected = isSelected, onClick = { selectedService = srv })
                                Spacer(modifier = Modifier.width(8.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(srv.nameEn, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                                    Text(srv.description, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                                Text("₹${srv.price}", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
            }

            item {
                Text("Date & Time Slot", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                val slots = listOf("Today, 04:00 PM", "Today, 06:00 PM", "Tomorrow, 10:00 AM", "Tomorrow, 02:00 PM")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    slots.forEach { slot ->
                        FilterChip(
                            selected = selectedDate == slot,
                            onClick = { selectedDate = slot },
                            label = { Text(slot, fontSize = 12.sp) }
                        )
                    }
                }
            }

            item {
                Text("Service Address", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) }
                )
            }

            item {
                Text("Work Description / Notes", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                OutlinedTextField(
                    value = workDesc,
                    onValueChange = { workDesc = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = { Text("Briefly describe what needs fixing...") },
                    minLines = 2
                )
            }

            // Coupon Code
            item {
                Text("Apply Coupon", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = couponCodeInput,
                        onValueChange = { couponCodeInput = it.uppercase() },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        leadingIcon = { Icon(Icons.Default.LocalOffer, contentDescription = null) },
                        placeholder = { Text("Enter coupon (e.g. LOOP50)") }
                    )
                    Button(
                        onClick = {
                            val found = coupons.find { it.code.equals(couponCodeInput.trim(), ignoreCase = true) }
                            appliedCoupon = found
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Apply")
                    }
                }
                if (appliedCoupon != null) {
                    Text(
                        text = "✓ Coupon ${appliedCoupon!!.code} applied (${appliedCoupon!!.discountPercent}% OFF)",
                        color = LoopSuccess,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Bill Breakdown
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Price Breakdown", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            IconButton(
                                onClick = { showPriceBreakdownDialog = true },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    Icons.Default.Info,
                                    contentDescription = "Fee Breakdown Details",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Service Base Amount", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("₹${"%.2f".format(baseRate)}", fontSize = 12.sp)
                        }
                        if (discount > 0) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Coupon Discount (${appliedCoupon?.code})", fontSize = 12.sp, color = LoopSuccess)
                                Text("- ₹${"%.2f".format(discount)}", fontSize = 12.sp, color = LoopSuccess, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("GST & Platform Fee (18%)", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("₹${"%.2f".format(tax)}", fontSize = 12.sp)
                        }
                        HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total Payable", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("₹${"%.2f".format(totalAmount)}", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    }

    if (showPriceBreakdownDialog) {
        PriceBreakdownInfoDialog(
            baseAmount = baseRate,
            discountAmount = discount,
            taxAmount = tax,
            totalAmount = totalAmount,
            onDismiss = { showPriceBreakdownDialog = false }
        )
    }

    if (celebrationBooking != null) {
        BookingCelebrationDialog(
            bookingId = celebrationBooking!!.id,
            serviceName = celebrationBooking!!.serviceName,
            providerName = celebrationBooking!!.providerName,
            onViewBooking = {
                val b = celebrationBooking!!
                celebrationBooking = null
                onBookingSuccess(b)
            }
        )
    }

    if (showRazorpaySheet) {
        RazorpayPaymentBottomSheet(
            totalAmount = totalAmount,
            serviceTitle = selectedService?.nameEn ?: provider.categoryName,
            onPaymentSuccess = {
                showRazorpaySheet = false
                val createdBooking = LoopRepository.createBooking(
                    provider = provider,
                    serviceItem = selectedService,
                    customServiceName = null,
                    date = selectedDate.split(",")[0],
                    timeSlot = selectedDate.split(",").getOrElse(1) { "04:00 PM" }.trim(),
                    address = address,
                    workDesc = workDesc,
                    isEventStaff = false,
                    staffCount = 1,
                    durationHours = 1,
                    appliedCoupon = appliedCoupon
                )
                celebrationBooking = createdBooking
            },
            onDismiss = { showRazorpaySheet = false }
        )
    }
}

// -------------------------------------------------------------
// 6. EVENT STAFF BOOKING SCREEN (Special Feature)
// -------------------------------------------------------------
@Composable
fun EventStaffBookingScreen(
    onBack: () -> Unit,
    onBookingSuccess: (Booking) -> Unit
) {
    val providers by LoopRepository.providers.collectAsState()
    val eventProvider = providers.find { it.categoryId == "cat_event_staff" } ?: providers.first()
    val coupons by LoopRepository.coupons.collectAsState()

    var staffType by remember { mutableStateOf("Function Waiters (Uniformed)") }
    var staffCount by remember { mutableIntStateOf(6) }
    var durationHours by remember { mutableIntStateOf(5) }
    var eventDate by remember { mutableStateOf("Tomorrow, 06:00 PM") }
    var venueAddress by remember { mutableStateOf("Grand Sapphire Banquet Hall, Outer Ring Road, Bengaluru") }
    var notes by remember { mutableStateOf("Buffet dinner service for 200 guests. Need uniformed boys with trays.") }
    var dressCode by remember { mutableStateOf("Formal White Shirt + Black Waistcoat") }
    var selectedPreset by remember { mutableStateOf<String?>("Haldi / Sangeet Catering") }
    var couponInput by remember { mutableStateOf("EVENT15") }
    var appliedCoupon by remember { mutableStateOf<Coupon?>(coupons.find { it.code == "EVENT15" }) }
    var showRazorpaySheet by remember { mutableStateOf(false) }
    var celebrationBooking by remember { mutableStateOf<Booking?>(null) }
    var showPriceBreakdownDialog by remember { mutableStateOf(false) }

    val ratePerHourPerStaff = when (staffType) {
        "Function Waiters (Uniformed)" -> 450.0
        "Kitchen & Catering Helpers" -> 400.0
        "Supplier Boys (Runner)" -> 420.0
        "Post-Event Cleaning Crew" -> 500.0
        else -> 450.0
    }

    val subtotal = ratePerHourPerStaff * staffCount * durationHours
    val discount = if (appliedCoupon != null && subtotal >= appliedCoupon!!.minOrder) {
        (subtotal * appliedCoupon!!.discountPercent / 100.0).coerceAtMost(appliedCoupon!!.maxDiscount)
    } else 0.0
    val tax = (subtotal - discount) * 0.18
    val totalAmount = subtotal - discount + tax

    Scaffold(
        topBar = {
            LoopTopBar(
                title = "Book Event & Marriage Staff",
                subtitle = "Waiters, helpers & function crew",
                showBack = true,
                onBackClick = onBack
            )
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                shadowElevation = 8.dp,
                modifier = Modifier.navigationBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("$staffCount Staff • $durationHours Hours", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("₹${"%.2f".format(totalAmount)}", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                    }
                    Button(
                        onClick = { showRazorpaySheet = true },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.height(48.dp)
                    ) {
                        Text("Pay & Confirm Crew", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Preset Occasion Bundles (One-Tap Fast Booking)
            item {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Popular Occasion Bundles", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("1-Tap Select", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                    Text("Pre-configured crew sizes recommended by top banquet managers", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(10.dp))

                    val bundles = listOf(
                        Triple("Small Birthday / Kitty Party", "2 Waiters + 1 Cleaner", Pair(3, 4)),
                        Triple("Haldi / Sangeet Catering", "4 Banquet Waiters + 2 Kitchen Helpers", Pair(6, 5)),
                        Triple("Grand Reception Crew", "8 Banquet Staff + 1 Lead Captain", Pair(9, 6))
                    )

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(bundles) { (title, subtitle, config) ->
                            val isSel = selectedPreset == title
                            Card(
                                modifier = Modifier
                                    .width(230.dp)
                                    .clickable {
                                        selectedPreset = title
                                        staffCount = config.first
                                        durationHours = config.second
                                        notes = "$title: $subtitle"
                                    },
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSel) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surface
                                ),
                                border = BorderStroke(1.5.dp, if (isSel) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = if (isSel) Icons.Default.CheckCircle else Icons.Default.Celebration,
                                            contentDescription = null,
                                            tint = if (isSel) MaterialTheme.colorScheme.primary else LoopAccent,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(title, fontWeight = FontWeight.Bold, fontSize = 12.sp, maxLines = 1)
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(subtitle, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text("${config.first} Staff • ${config.second} Hours", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                }
                            }
                        }
                    }
                }
            }

            item {
                Text("Select Crew Role", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                val roles = listOf(
                    "Function Waiters (Uniformed)" to "₹450/person/hr",
                    "Kitchen & Catering Helpers" to "₹400/person/hr",
                    "Supplier Boys (Runner)" to "₹420/person/hr",
                    "Post-Event Cleaning Crew" to "₹500/person/hr"
                )
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    roles.forEach { (role, rate) ->
                        val isSel = staffType == role
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { staffType = role },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSel) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f) else MaterialTheme.colorScheme.surface
                            ),
                            border = BorderStroke(1.dp, if (isSel) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(selected = isSel, onClick = { staffType = role })
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(role, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, modifier = Modifier.weight(1f))
                                Text(rate, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
            }

            // Staff Count & Duration Steppers
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Number of People", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(
                                    onClick = { if (staffCount > 1) staffCount-- },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(Icons.Default.RemoveCircleOutline, contentDescription = "Decrease")
                                }
                                Text("$staffCount", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, modifier = Modifier.padding(horizontal = 12.dp))
                                IconButton(
                                    onClick = { staffCount++ },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(Icons.Default.AddCircleOutline, contentDescription = "Increase", tint = MaterialTheme.colorScheme.primary)
                                }
                            }
                        }
                    }

                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Duration (Hours)", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(
                                    onClick = { if (durationHours > 2) durationHours-- },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(Icons.Default.RemoveCircleOutline, contentDescription = "Decrease")
                                }
                                Text("$durationHours", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, modifier = Modifier.padding(horizontal = 12.dp))
                                IconButton(
                                    onClick = { durationHours++ },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(Icons.Default.AddCircleOutline, contentDescription = "Increase", tint = MaterialTheme.colorScheme.primary)
                                }
                            }
                        }
                    }
                }
            }

            item {
                Text("Event Venue / Banquet Address", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                OutlinedTextField(
                    value = venueAddress,
                    onValueChange = { venueAddress = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = { Icon(Icons.Default.Place, contentDescription = null) }
                )
            }

            // Uniform & Dress Code Selector
            item {
                Column {
                    Text("Crew Dress Code & Attire", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    val dressOptions = listOf(
                        "Formal Black Waistcoat + White Shirt",
                        "Traditional Kurta Pajama (Indian Wedding)",
                        "All-Black Casual Catering Apron",
                        "Standard White Shirt & Bow Tie"
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(dressOptions) { opt ->
                            FilterChip(
                                selected = dressCode == opt,
                                onClick = { dressCode = opt },
                                label = { Text(opt, fontSize = 11.sp) }
                            )
                        }
                    }
                }
            }

            item {
                Text("Special Instructions & Event Notes", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    minLines = 2
                )
            }

            // Coupon
            item {
                Text("Event Coupon", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = couponInput,
                        onValueChange = { couponInput = it.uppercase() },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        leadingIcon = { Icon(Icons.Default.LocalOffer, contentDescription = null) }
                    )
                    Button(
                        onClick = {
                            val found = coupons.find { it.code.equals(couponInput.trim(), ignoreCase = true) }
                            appliedCoupon = found
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Apply")
                    }
                }
                if (appliedCoupon != null) {
                    Text(
                        text = "✓ Coupon ${appliedCoupon!!.code} applied (Save ₹${"%.0f".format(discount)})",
                        color = LoopSuccess,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Event Bill Calculation
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Calculation Breakdown", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            IconButton(
                                onClick = { showPriceBreakdownDialog = true },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(Icons.Default.Info, contentDescription = "Breakdown Details", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Base ($staffCount staff × $durationHours hrs × ₹${ratePerHourPerStaff.toInt()})", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("₹${"%.2f".format(subtotal)}", fontSize = 12.sp)
                        }
                        if (discount > 0) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Event Coupon Discount", fontSize = 12.sp, color = LoopSuccess)
                                Text("- ₹${"%.2f".format(discount)}", fontSize = 12.sp, color = LoopSuccess, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("GST & Crew Insurance (18%)", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("₹${"%.2f".format(tax)}", fontSize = 12.sp)
                        }
                        HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total Amount", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("₹${"%.2f".format(totalAmount)}", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    }

    if (showPriceBreakdownDialog) {
        PriceBreakdownInfoDialog(
            baseAmount = subtotal,
            discountAmount = discount,
            taxAmount = tax,
            totalAmount = totalAmount,
            onDismiss = { showPriceBreakdownDialog = false }
        )
    }

    if (celebrationBooking != null) {
        BookingCelebrationDialog(
            bookingId = celebrationBooking!!.id,
            serviceName = celebrationBooking!!.serviceName,
            providerName = celebrationBooking!!.providerName,
            onViewBooking = {
                val b = celebrationBooking!!
                celebrationBooking = null
                onBookingSuccess(b)
            }
        )
    }

    if (showRazorpaySheet) {
        RazorpayPaymentBottomSheet(
            totalAmount = totalAmount,
            serviceTitle = "$staffType ($staffCount People)",
            onPaymentSuccess = {
                showRazorpaySheet = false
                val createdBooking = LoopRepository.createBooking(
                    provider = eventProvider,
                    serviceItem = null,
                    customServiceName = "$staffType ($staffCount Staff - $dressCode)",
                    date = eventDate.split(",")[0],
                    timeSlot = eventDate.split(",").getOrElse(1) { "06:00 PM" }.trim(),
                    address = venueAddress,
                    workDesc = "Attire: $dressCode | Notes: $notes",
                    isEventStaff = true,
                    staffCount = staffCount,
                    durationHours = durationHours,
                    appliedCoupon = appliedCoupon
                )
                celebrationBooking = createdBooking
            },
            onDismiss = { showRazorpaySheet = false }
        )
    }
}

// -------------------------------------------------------------
// 7. MY BOOKINGS SCREEN
// -------------------------------------------------------------
@Composable
fun CustomerBookingsScreen(
    onSelectBooking: (Booking) -> Unit
) {
    val bookings by LoopRepository.bookings.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Upcoming", "Ongoing", "Completed", "Cancelled")

    val filteredBookings = when (selectedTab) {
        0 -> bookings.filter { it.status == BookingStatus.REQUESTED || it.status == BookingStatus.CONFIRMED }
        1 -> bookings.filter { it.status == BookingStatus.ON_THE_WAY || it.status == BookingStatus.IN_PROGRESS }
        2 -> bookings.filter { it.status == BookingStatus.COMPLETED }
        3 -> bookings.filter { it.status == BookingStatus.CANCELLED }
        else -> bookings
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title, fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal, fontSize = 12.sp) }
                )
            }
        }

        if (filteredBookings.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Outlined.ReceiptLong, contentDescription = null, modifier = Modifier.size(54.dp), tint = MaterialTheme.colorScheme.outline)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("No bookings in this tab.", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredBookings) { booking ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelectBooking(booking) },
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("#${booking.id}", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                                StatusBadge(status = booking.status)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(booking.serviceName, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Text("Professional: ${booking.providerName}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.CalendarToday, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("${booking.date} • ${booking.timeSlot}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Paid ₹${"%.2f".format(booking.totalAmount)}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("View Details", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.primary)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 8. BOOKING DETAIL SCREEN (Status Tracker + Chat + Rating)
// -------------------------------------------------------------
@Composable
fun BookingDetailScreen(
    bookingId: String,
    onBack: () -> Unit,
    onOpenChat: (Booking) -> Unit,
    onRateJob: (Booking) -> Unit
) {
    val bookings by LoopRepository.bookings.collectAsState()
    val booking = bookings.find { it.id == bookingId }

    if (booking == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Booking not found")
        }
        return
    }

    Scaffold(
        topBar = {
            LoopTopBar(title = "Booking #${booking.id}", subtitle = booking.status.labelEn, showBack = true, onBackClick = onBack)
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Live Status Tracker Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Tracking Status", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            StatusBadge(status = booking.status)
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        // Status Steps Timeline
                        val steps = listOf(
                            BookingStatus.REQUESTED,
                            BookingStatus.CONFIRMED,
                            BookingStatus.ON_THE_WAY,
                            BookingStatus.IN_PROGRESS,
                            BookingStatus.COMPLETED
                        )

                        val currentIdx = steps.indexOf(booking.status)

                        steps.forEachIndexed { index, step ->
                            val isDone = currentIdx >= index && booking.status != BookingStatus.CANCELLED
                            val isCurrent = booking.status == step
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = CircleShape,
                                    color = if (isDone) LoopSuccess else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        if (isDone) {
                                            Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = step.labelEn,
                                    fontSize = 13.sp,
                                    fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isDone) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            if (index < steps.size - 1) {
                                Box(
                                    modifier = Modifier
                                        .padding(start = 11.dp)
                                        .height(16.dp)
                                        .width(2.dp)
                                        .background(if (currentIdx > index) LoopSuccess else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                                )
                            }
                        }
                    }
                }
            }

            // Professional Contact Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("Assigned Professional", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(booking.providerName, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Text(booking.categoryName, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(
                                    onClick = { onOpenChat(booking) },
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Chat", fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }

            // Booking Details & Address
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("Job Particulars", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Service: ${booking.serviceName}", fontSize = 13.sp)
                        Text("Scheduled: ${booking.date} at ${booking.timeSlot}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Address:", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                        Text(booking.address, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Description:", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                        Text(booking.workDescription, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            // Payment Receipt
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("Payment Details", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Base Amount", fontSize = 12.sp)
                            Text("₹${"%.2f".format(booking.baseAmount)}", fontSize = 12.sp)
                        }
                        if (booking.discountAmount > 0) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Discount", fontSize = 12.sp, color = LoopSuccess)
                                Text("- ₹${"%.2f".format(booking.discountAmount)}", fontSize = 12.sp, color = LoopSuccess)
                            }
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("GST (18%)", fontSize = 12.sp)
                            Text("₹${"%.2f".format(booking.taxAmount)}", fontSize = 12.sp)
                        }
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total Paid", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text("₹${"%.2f".format(booking.totalAmount)}", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp, color = MaterialTheme.colorScheme.primary)
                        }
                        Text("Paid via Razorpay UPI", fontSize = 11.sp, color = LoopSuccess, fontWeight = FontWeight.Medium, modifier = Modifier.padding(top = 4.dp))
                    }
                }
            }

            // Actions
            item {
                if (booking.status == BookingStatus.COMPLETED) {
                    if (booking.rating != null) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text("Your Rating & Feedback", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                StarRatingBar(rating = booking.rating!!)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(booking.review ?: "Great service!", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    } else {
                        Button(
                            onClick = { onRateJob(booking) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.Star, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Rate & Review Professional")
                        }
                    }
                } else if (booking.status != BookingStatus.CANCELLED) {
                    OutlinedButton(
                        onClick = { LoopRepository.cancelBooking(booking.id) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Cancel Booking & Issue Refund")
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 9. CHAT SCREEN
// -------------------------------------------------------------
@Composable
fun ChatScreen(
    bookingId: String,
    onBack: () -> Unit
) {
    val messages by LoopRepository.chatMessages.collectAsState()
    val bookingMessages = messages.filter { it.bookingId == bookingId }
    var inputText by remember { mutableStateOf("") }
    val bookings by LoopRepository.bookings.collectAsState()
    val booking = bookings.find { it.id == bookingId }

    Scaffold(
        topBar = {
            LoopTopBar(
                title = booking?.providerName ?: "Chat with Professional",
                subtitle = "Booking #${booking?.id ?: bookingId}",
                showBack = true,
                onBackClick = onBack
            )
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp,
                modifier = Modifier.navigationBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        placeholder = { Text("Type a message...", fontSize = 13.sp) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(20.dp),
                        maxLines = 3
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            if (inputText.isNotBlank()) {
                                LoopRepository.sendMessage(
                                    bookingId = bookingId,
                                    senderName = "Rahul Sharma",
                                    text = inputText,
                                    isCustomer = true
                                )
                                inputText = ""
                            }
                        },
                        colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = Color.White)
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(bookingMessages) { msg ->
                val isMe = msg.isFromCustomer
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = if (isMe) Alignment.End else Alignment.Start
                ) {
                    Surface(
                        shape = RoundedCornerShape(
                            topStart = 14.dp,
                            topEnd = 14.dp,
                            bottomStart = if (isMe) 14.dp else 2.dp,
                            bottomEnd = if (isMe) 2.dp else 14.dp
                        ),
                        color = if (isMe) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.widthIn(max = 280.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = msg.text,
                                color = if (isMe) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = msg.timestamp,
                                color = if (isMe) Color.White.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                fontSize = 10.sp,
                                modifier = Modifier.align(Alignment.End)
                            )
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 10. CUSTOMER PROFILE SCREEN
// -------------------------------------------------------------
@Composable
fun CustomerProfileScreen(
    onBecomeProvider: () -> Unit,
    onOpenBookings: () -> Unit,
    onOpenNotifications: () -> Unit,
    onOpenSupport: () -> Unit
) {
    val user by LoopRepository.userProfile.collectAsState()
    val isDark by LoopRepository.isDarkMode.collectAsState()
    val lang by LoopRepository.language.collectAsState()

    var showEditProfileDialog by remember { mutableStateOf(false) }
    var editName by remember { mutableStateOf(user.name) }
    var editEmail by remember { mutableStateOf(user.email) }
    var editLocation by remember { mutableStateOf(user.location) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // User Info Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(54.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(user.name.take(1), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                        }
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(user.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(user.phone, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(user.location, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    IconButton(onClick = { showEditProfileDialog = true }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Profile", tint = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }

        // Loop Wallet Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Loop Cash Wallet", fontSize = 12.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        Text("₹${"%.2f".format(user.walletBalance)}", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                    Icon(Icons.Default.AccountBalanceWallet, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.size(32.dp))
                }
            }
        }

        // BECOME A SERVICE PROVIDER CTA
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onBecomeProvider() },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = LoopAccent,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Handyman, contentDescription = null, tint = Color.Black, modifier = Modifier.size(20.dp))
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Become a Service Provider", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
                        Text("Earn money doing home repairs or event staffing", color = Color(0xFF94A3B8), fontSize = 11.sp)
                    }
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = LoopAccent)
                }
            }
        }

        // Navigation Items
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column {
                    ProfileMenuRow(icon = Icons.Default.ReceiptLong, title = "My Bookings", onClick = onOpenBookings)
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    ProfileMenuRow(icon = Icons.Default.Notifications, title = "Notifications", onClick = onOpenNotifications)
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    ProfileMenuRow(icon = Icons.Default.Headphones, title = "Help & Support Tickets", onClick = onOpenSupport)
                }
            }
        }

        // App Settings Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("App Preferences", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Translate, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Language (भाषा)", fontSize = 13.sp)
                        }
                        Switch(
                            checked = lang == AppLanguage.HINDI,
                            onCheckedChange = { LoopRepository.setLanguage(if (it) AppLanguage.HINDI else AppLanguage.ENGLISH) }
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.DarkMode, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Dark Theme", fontSize = 13.sp)
                        }
                        Switch(
                            checked = isDark,
                            onCheckedChange = { LoopRepository.toggleDarkMode() }
                        )
                    }
                }
            }
        }

        // Account & Roles Switching Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            ) {
                Column {
                    ProfileMenuRow(
                        icon = Icons.Default.SwitchAccount,
                        title = "Switch Profile (Demo Accounts)",
                        onClick = { LoopRepository.logout() }
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    ProfileMenuRow(
                        icon = Icons.Default.Logout,
                        title = "Sign Out",
                        onClick = { LoopRepository.logout() }
                    )
                }
            }
        }
    }

    if (showEditProfileDialog) {
        AlertDialog(
            onDismissRequest = { showEditProfileDialog = false },
            title = { Text("Edit Profile") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = editName, onValueChange = { editName = it }, label = { Text("Full Name") })
                    OutlinedTextField(value = editEmail, onValueChange = { editEmail = it }, label = { Text("Email") })
                    OutlinedTextField(value = editLocation, onValueChange = { editLocation = it }, label = { Text("Address / City") })
                }
            },
            confirmButton = {
                Button(onClick = {
                    LoopRepository.updateProfile(editName, editEmail, editLocation)
                    showEditProfileDialog = false
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditProfileDialog = false }) { Text("Cancel") }
            }
        )
    }
}

@Composable
fun ProfileMenuRow(icon: ImageVector, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(14.dp))
        Text(title, fontSize = 13.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
        Icon(Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = null, tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(14.dp))
    }
}

// -------------------------------------------------------------
// 11. NOTIFICATIONS SCREEN
// -------------------------------------------------------------
@Composable
fun NotificationsScreen(onBack: () -> Unit) {
    val notifs by LoopRepository.notifications.collectAsState()
    val lang by LoopRepository.language.collectAsState()
    val isHi = lang == AppLanguage.HINDI

    Scaffold(
        topBar = {
            LoopTopBar(title = if (isHi) "सूचनाएं" else "Notifications", showBack = true, onBackClick = onBack)
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(notifs) { n ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.NotificationsActive, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(if (isHi) n.titleHi else n.titleEn, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(if (isHi) n.messageHi else n.messageEn, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(n.time, fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 12. SUPPORT & TICKETS SCREEN
// -------------------------------------------------------------
@Composable
fun SupportScreen(onBack: () -> Unit) {
    val tickets by LoopRepository.supportTickets.collectAsState()
    var showNewTicketDialog by remember { mutableStateOf(false) }
    var subject by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            LoopTopBar(title = "Help & Support", subtitle = "24x7 Customer Assistance", showBack = true, onBackClick = onBack)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showNewTicketDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("New Ticket")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("Frequently Asked Questions", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Q: How does the payment protection work?", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                        Text("A: All payments are securely routed via Razorpay and held until the job is marked complete by you.", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Q: Can I reschedule my booking?", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                        Text("A: Yes, you can reschedule from the booking detail screen or chat directly with your assigned pro.", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            item {
                Text("My Support Tickets", fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.padding(top = 8.dp))
            }

            items(tickets) { t ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("#${t.id}: ${t.subject}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Surface(
                                color = if (t.status == "Resolved") LoopSuccess.copy(alpha = 0.15f) else Color(0xFFFEF3C7),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    t.status,
                                    color = if (t.status == "Resolved") LoopSuccess else Color(0xFFB45309),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(t.description, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        if (t.adminReply != null) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Admin Response: ${t.adminReply}", fontSize = 11.sp, modifier = Modifier.padding(8.dp), color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
            }
        }
    }

    if (showNewTicketDialog) {
        AlertDialog(
            onDismissRequest = { showNewTicketDialog = false },
            title = { Text("Raise Support Ticket") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = subject, onValueChange = { subject = it }, label = { Text("Subject") })
                    OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Details") }, minLines = 3)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (subject.isNotBlank()) {
                            LoopRepository.createSupportTicket(subject, description)
                            showNewTicketDialog = false
                            subject = ""
                            description = ""
                        }
                    }
                ) {
                    Text("Submit")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNewTicketDialog = false }) { Text("Cancel") }
            }
        )
    }
}

// -------------------------------------------------------------
// 13. SEARCH RESULTS SCREEN
// -------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultsScreen(
    onBack: () -> Unit,
    onSelectProvider: (ServiceProvider) -> Unit,
    onSelectCategory: (ServiceCategory) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val categories by LoopRepository.categories.collectAsState()
    val providers by LoopRepository.providers.collectAsState()

    val filteredCats = categories.filter {
        it.nameEn.contains(searchQuery, ignoreCase = true) || it.nameHi.contains(searchQuery, ignoreCase = true)
    }
    val filteredPros = providers.filter {
        it.name.contains(searchQuery, ignoreCase = true) || it.categoryName.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search services or professionals...", fontSize = 13.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (filteredCats.isNotEmpty()) {
                item {
                    Text("Categories", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
                items(filteredCats) { cat ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelectCategory(cat) },
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(getCategoryIcon(cat.icon), contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(cat.nameEn, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        }
                    }
                }
            }

            if (filteredPros.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Matching Professionals", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
                items(filteredPros) { pro ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelectProvider(pro) },
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Surface(shape = CircleShape, color = Color(pro.avatarColorHex), modifier = Modifier.size(36.dp)) {
                                Box(contentAlignment = Alignment.Center) { Text(pro.name.take(1), color = Color.White) }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(pro.name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text("${pro.categoryName} • ₹${pro.hourlyRate}/hr", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        }
    }
}
