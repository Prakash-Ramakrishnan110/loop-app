package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.*
import com.example.ui.theme.*

// -------------------------------------------------------------
// 1. ONBOARDING SCREENS (Role Selection + Value Props)
// -------------------------------------------------------------
@Composable
fun AppOnboardingScreen(
    onFinish: (UserRole) -> Unit
) {
    var selectedRole by remember { mutableStateOf<UserRole?>(null) }
    var currentPage by remember { mutableIntStateOf(0) }

    data class OnboardingSlide(
        val title: String,
        val subtitle: String,
        val icon: ImageVector,
        val accentColor: Color
    )

    val slides = if (selectedRole == UserRole.CUSTOMER) {
        listOf(
            OnboardingSlide(
                title = "Find Local Pros Instantly",
                subtitle = "Connect with top-rated household specialists and event crew in your neighborhood.",
                icon = Icons.Default.NearMe,
                accentColor = Color(0xFF0284C7)
            ),
            OnboardingSlide(
                title = "Verified & Secure",
                subtitle = "All professionals are background-checked. Enjoy secure escrow payments and upfront pricing.",
                icon = Icons.Default.Shield,
                accentColor = Color(0xFF16A34A)
            )
        )
    } else {
        listOf(
            OnboardingSlide(
                title = "Find Jobs Near You",
                subtitle = "Discover available jobs in your neighborhood and get hired instantly.",
                icon = Icons.Default.Work,
                accentColor = Color(0xFFD97706)
            ),
            OnboardingSlide(
                title = "Guaranteed Payments",
                subtitle = "Get paid securely and on time directly to your bank account after every job.",
                icon = Icons.Default.AccountBalanceWallet,
                accentColor = Color(0xFF16A34A)
            )
        )
    }

    if (selectedRole == null) {
        // ROLE SELECTION SCREEN
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_loop_logo),
                contentDescription = "Loop Logo",
                modifier = Modifier.size(80.dp).clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Welcome to Loop",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "How would you like to use the app?",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
            )

            Card(
                onClick = { selectedRole = UserRole.CUSTOMER },
                modifier = Modifier.fillMaxWidth().height(100.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(shape = CircleShape, color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), modifier = Modifier.size(48.dp)) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(12.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("I want to Hire", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("Find professionals for your needs", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                onClick = { selectedRole = UserRole.PROVIDER },
                modifier = Modifier.fillMaxWidth().height(100.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(2.dp, Color(0xFFD97706).copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(shape = CircleShape, color = Color(0xFFD97706).copy(alpha = 0.1f), modifier = Modifier.size(48.dp)) {
                        Icon(Icons.Default.Handyman, contentDescription = null, tint = Color(0xFFD97706), modifier = Modifier.padding(12.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("I want to Work", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("Offer services and earn money", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    } else {
        // ONBOARDING SLIDES
        val currentSlide = slides[currentPage.coerceIn(0, slides.size - 1)]

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { onFinish(selectedRole!!) }) {
                    Text("Skip", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
            }

            // Content
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_loop_logo),
                    contentDescription = "Loop Logo",
                    modifier = Modifier.size(120.dp).clip(CircleShape)
                )
                
                Spacer(modifier = Modifier.height(40.dp))
                
                Surface(
                    shape = CircleShape,
                    color = currentSlide.accentColor.copy(alpha = 0.1f),
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        imageVector = currentSlide.icon,
                        contentDescription = null,
                        tint = currentSlide.accentColor,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = currentSlide.title,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = currentSlide.subtitle,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // Footer
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 24.dp)
                ) {
                    repeat(slides.size) { idx ->
                        val isSelected = currentPage == idx
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .height(8.dp)
                                .width(if (isSelected) 32.dp else 8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(
                                    if (isSelected) currentSlide.accentColor
                                    else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                                )
                        )
                    }
                }

                Button(
                    onClick = {
                        if (currentPage < slides.size - 1) {
                            currentPage++
                        } else {
                            onFinish(selectedRole!!)
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = currentSlide.accentColor)
                ) {
                    Text(
                        text = if (currentPage == slides.size - 1) "Get Started" else "Next",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

// Retain DualTrackOnboardingScreen alias for backwards compatibility
@Composable
fun DualTrackOnboardingScreen(
    onFinish: (UserRole) -> Unit,
    onOpenLogin: () -> Unit = { onFinish(UserRole.CUSTOMER) }
) {
    AppOnboardingScreen(onFinish = onFinish)
}

// -------------------------------------------------------------
// 2. AUTHENTICATION & LOGIN SCREEN (Phone OTP + Demo Accounts)
// -------------------------------------------------------------
@Composable
fun AuthScreen(
    initialRole: UserRole,
    onOpenOnboarding: () -> Unit,
    onLoginSuccess: () -> Unit,
    onRegistrationRequested: (UserRole, String, String) -> Unit = { _, _, _ -> }
) {
    var phone by remember { mutableStateOf("9876543210") }
    var name by remember { mutableStateOf("Rahul Sharma") }
    // Use the role passed from Onboarding
    val selectedRole = initialRole
    var showOtp by remember { mutableStateOf(false) }
    var otpCode by remember { mutableStateOf("4819") }
    var showMoreDemoRoles by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // App Header Brand
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = Color.Transparent,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_loop_logo),
                            contentDescription = "Loop Logo",
                            modifier = Modifier.fillMaxSize().clip(CircleShape)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "LOOP",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp
                            ),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Hyper-Local Services & Staffing",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Value Proposition App Tour Button
                OutlinedButton(
                    onClick = onOpenOnboarding,
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Tour App",
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("App Tour", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // =========================================================
        // PRIMARY FLOW: PHONE NUMBER ENTRY & OTP VERIFICATION
        // =========================================================
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    // State Headline
                    if (!showOtp) {
                        Text(
                            text = if (selectedRole == UserRole.CUSTOMER) "Sign In to Hire" else "Sign In to Work",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Enter your 10-digit mobile number to receive a verification OTP.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 2.dp, bottom = 14.dp)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Full Name Input
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Full Name") },
                            leadingIcon = {
                                Icon(Icons.Default.PersonOutline, contentDescription = null)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Phone Number Input with +91 Country Code
                        OutlinedTextField(
                            value = phone,
                            onValueChange = { input ->
                                val digitsOnly = input.filter { it.isDigit() }
                                if (digitsOnly.length <= 10) phone = digitsOnly
                            },
                            label = { Text("Mobile Number") },
                            prefix = {
                                Text(
                                    text = "+91 ",
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            },
                            leadingIcon = {
                                Icon(Icons.Default.Phone, contentDescription = null)
                            },
                            trailingIcon = {
                                if (phone.isNotEmpty()) {
                                    IconButton(onClick = { phone = "" }) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Clear phone input",
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone,
                                imeAction = ImeAction.Done
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("phone_input"),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        // Send OTP Action Button
                        Button(
                            onClick = {
                                if (phone.length >= 10) {
                                    showOtp = true
                                }
                            },
                            enabled = phone.length >= 10,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("send_otp_button"),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Send OTP",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "🔒 Standard SMS rates may apply. A 4-digit verification code will be dispatched to your phone.",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        // OTP VERIFICATION STATE
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Verify Mobile Number",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Text(
                                    text = "SMS SENT",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Text(
                            text = "Enter the 4-digit code sent to +91 $phone",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 2.dp, bottom = 14.dp)
                        )

                        // Visual 4-Digit Display Boxes
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            repeat(4) { index ->
                                val char = otpCode.getOrNull(index)?.toString() ?: ""
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                                    border = BorderStroke(
                                        width = if (char.isNotEmpty()) 2.dp else 1.dp,
                                        color = if (char.isNotEmpty()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                                    ),
                                    modifier = Modifier.size(54.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = char,
                                            fontSize = 22.sp,
                                            fontWeight = FontWeight.Black,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // OTP Text Input Field
                        OutlinedTextField(
                            value = otpCode,
                            onValueChange = { input ->
                                val digitsOnly = input.filter { it.isDigit() }
                                if (digitsOnly.length <= 4) otpCode = digitsOnly
                            },
                            label = { Text("Enter 4-Digit OTP") },
                            leadingIcon = {
                                Icon(Icons.Default.Lock, contentDescription = null)
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("otp_input"),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Auto-fill demo pill
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFFEF3C7),
                            border = BorderStroke(1.dp, Color(0xFFD97706).copy(alpha = 0.3f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { otpCode = "4819" }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FlashOn,
                                    contentDescription = null,
                                    tint = Color(0xFFD97706),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Tap to auto-fill demo OTP code: 4819",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFB45309)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Verify & Sign In Button
                        Button(
                            onClick = {
                                if (otpCode.length == 4) {
                                    // Navigate to Registration Screen for new users
                                    onRegistrationRequested(selectedRole, name.ifBlank { "User" }, "+91 $phone")
                                }
                            },
                            enabled = otpCode.length == 4,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("verify_otp_button"),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Verify & Sign In",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextButton(onClick = { showOtp = false }) {
                                Text(
                                    text = "← Change Mobile Number",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            TextButton(onClick = { otpCode = "4819" }) {
                                Text(
                                    text = "Resend Code",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }

        // =========================================================
        // BOTTOM SECTION: DEMO ACCOUNT BUTTONS FOR QUICK TESTING
        // =========================================================
        item {
            // Visual Divider
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                )
                Text(
                    text = "  OR QUICK TEST WITH DEMO ACCOUNTS  ",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    letterSpacing = 0.5.sp
                )
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                )
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.FlashOn,
                                contentDescription = null,
                                tint = Color(0xFFD97706),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Demo Accounts",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFDCFCE7)
                        ) {
                            Text(
                                text = "1-TAP ACCESS",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF15803D),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Text(
                        text = "Skip SMS OTP verification and test the app instantly with pre-configured profiles.",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                    )

                    // 1. DEMO BUTTON: 'Login as Customer'
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFFF0F9FF),
                        border = BorderStroke(1.5.dp, Color(0xFF0284C7).copy(alpha = 0.35f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("login_as_customer_button")
                            .clickable {
                                LoopRepository.loginAsDemo(
                                    role = UserRole.CUSTOMER,
                                    name = "Priya Sharma",
                                    phone = "+91 98765 43210",
                                    email = "priya.sharma@example.com",
                                    location = "Indiranagar, Bengaluru",
                                    walletBalance = 550.0
                                )
                                onLoginSuccess()
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = Color(0xFF0284C7),
                                modifier = Modifier.size(42.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Customer",
                                        tint = Color.White,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Login as Customer",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0369A1)
                                )
                                Text(
                                    text = "Priya Sharma • Book household repairs & event staff",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = Color(0xFF0284C7),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // 2. DEMO BUTTON: 'Login as Provider'
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFFF0FDF4),
                        border = BorderStroke(1.5.dp, Color(0xFF16A34A).copy(alpha = 0.35f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("login_as_provider_button")
                            .clickable {
                                LoopRepository.loginAsDemo(
                                    role = UserRole.PROVIDER,
                                    name = "Ramesh Kumar",
                                    phone = "+91 98234 11223",
                                    email = "ramesh.kumar@okhdfcbank",
                                    location = "Koramangala, Bengaluru",
                                    walletBalance = 3840.0
                                )
                                onLoginSuccess()
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = Color(0xFF16A34A),
                                modifier = Modifier.size(42.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Handyman,
                                        contentDescription = "Provider",
                                        tint = Color.White,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Login as Provider",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF15803D)
                                )
                                Text(
                                    text = "Ramesh Kumar • Verified Pro Partner (Plumber & Electrician)",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = Color(0xFF16A34A),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    // Optional additional roles toggle (Admin Console & Event Squad Lead)
                    Spacer(modifier = Modifier.height(8.dp))

                    TextButton(
                        onClick = { showMoreDemoRoles = !showMoreDemoRoles },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = if (showMoreDemoRoles) "▲ Hide Other Demo Roles" else "▼ More Demo Roles (Admin Console & Event Squad)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    if (showMoreDemoRoles) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(top = 6.dp)
                        ) {
                            // Event Squad Lead
                            DemoAccountCard(
                                name = "Royal Event Squad (Amit Singh)",
                                roleTag = "Event Squad Lead",
                                badgeColor = Color(0xFFD97706),
                                icon = Icons.Default.Celebration,
                                description = "Banquet Supervisor • 50+ Waiters Crew • 4.95★",
                                actionLabel = "Login as Event Lead",
                                onClick = {
                                    LoopRepository.loginAsDemo(
                                        role = UserRole.PROVIDER,
                                        name = "Royal Event Squad (Amit Singh)",
                                        phone = "+91 99001 88776",
                                        email = "royalevents@icici",
                                        location = "Outer Ring Road, Bengaluru",
                                        walletBalance = 12500.0
                                    )
                                    onLoginSuccess()
                                }
                            )

                            // Admin Console
                            DemoAccountCard(
                                name = "Loop Operations Admin",
                                roleTag = "Admin Console",
                                badgeColor = Color(0xFF7C3AED),
                                icon = Icons.Default.AdminPanelSettings,
                                description = "Ops Lead • Partner approvals, dispute tickets & commissions",
                                actionLabel = "Login as Admin",
                                onClick = {
                                    LoopRepository.loginAsDemo(
                                        role = UserRole.ADMIN,
                                        name = "Loop Ops Admin",
                                        phone = "+91 99001 12233",
                                        email = "admin@loop.com",
                                        location = "Central Hub, Bengaluru",
                                        walletBalance = 0.0
                                    )
                                    onLoginSuccess()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// HELPER COMPONENT: DEMO ACCOUNT CARD
// -------------------------------------------------------------
@Composable
fun DemoAccountCard(
    name: String,
    roleTag: String,
    badgeColor: Color,
    icon: ImageVector,
    description: String,
    actionLabel: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = badgeColor.copy(alpha = 0.15f),
                modifier = Modifier.size(44.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, tint = badgeColor, modifier = Modifier.size(22.dp))
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = badgeColor.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = roleTag,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = badgeColor,
                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.5.dp)
                        )
                    }
                }
                Text(
                    text = description,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            FilledTonalButton(
                onClick = onClick,
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = badgeColor.copy(alpha = 0.15f),
                    contentColor = badgeColor
                )
            ) {
                Text("Login", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// -------------------------------------------------------------
// 3. REGISTRATION SCREEN (After OTP)
// -------------------------------------------------------------
@Composable
fun RegistrationScreen(
    initialRole: UserRole,
    initialName: String,
    initialPhone: String,
    onRegistrationComplete: () -> Unit
) {
    if (initialRole == UserRole.CUSTOMER) {
        CustomerRegistrationWizard(initialName, initialPhone, onRegistrationComplete)
    } else {
        ProviderRegistrationWizard(initialName, initialPhone, onRegistrationComplete)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerRegistrationWizard(
    initialName: String,
    initialPhone: String,
    onComplete: () -> Unit
) {
    var step by remember { mutableIntStateOf(0) }
    
    // Form state
    var name by remember { mutableStateOf(initialName) }
    var email by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var lat by remember { mutableDoubleStateOf(0.0) }
    var lng by remember { mutableDoubleStateOf(0.0) }
    
    val totalSteps = 4
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(
            progress = (step + 1) / totalSteps.toFloat(),
            modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.height(24.dp))
        
        Crossfade(targetState = step, label = "CustomerWizard") { currentStep ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (currentStep) {
                    0 -> {
                        Text("Personal Details", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold))
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(
                            modifier = Modifier.size(100.dp).background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.PersonOutline, contentDescription = null, modifier = Modifier.size(40.dp))
                            Text("Add Photo", fontSize = 10.sp, modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 8.dp))
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Full Name") }, modifier = Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email Address") }, modifier = Modifier.fillMaxWidth())
                    }
                    1 -> {
                        Text("Exact Location", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold))
                        Text("Drag the pin to your home", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(modifier = Modifier.fillMaxWidth().height(250.dp).clip(RoundedCornerShape(12.dp))) {
                            com.example.ui.components.LeafletMapView(
                                onLocationSelected = { l, lg -> lat = l; lng = lg }
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("Apt / Suite Number") }, modifier = Modifier.fillMaxWidth())
                    }
                    2 -> {
                        Text("Service Preferences", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold))
                        Spacer(modifier = Modifier.height(16.dp))
                        val services = listOf("Cleaning", "Plumbing", "Electrical", "Handyman", "Events", "Moving")
                        var selected by remember { mutableStateOf(setOf<String>()) }
                        Column {
                            services.chunked(2).forEach { rowItems ->
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                                    rowItems.forEach { item ->
                                        FilterChip(
                                            selected = selected.contains(item),
                                            onClick = { if (selected.contains(item)) selected -= item else selected += item },
                                            label = { Text(item) },
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    3 -> {
                        Text("Payment Setup", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold))
                        Text("Add a card to book instantly", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(24.dp))
                        Card(
                            modifier = Modifier.fillMaxWidth().height(180.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            Column(modifier = Modifier.padding(24.dp).fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
                                Icon(Icons.Default.CreditCard, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.size(32.dp))
                                Text("**** **** **** 1234", fontSize = 20.sp, letterSpacing = 2.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(name.ifBlank { "Cardholder Name" }, color = MaterialTheme.colorScheme.onPrimaryContainer)
                                    Text("MM/YY", color = MaterialTheme.colorScheme.onPrimaryContainer)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {}, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)) {
                            Text("Link Card Securely")
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            if (step > 0) {
                OutlinedButton(onClick = { step-- }, modifier = Modifier.weight(1f)) { Text("Back") }
                Spacer(modifier = Modifier.width(16.dp))
            }
            Button(
                onClick = {
                    if (step < totalSteps - 1) step++ 
                    else {
                        LoopRepository.login(phone = initialPhone, name = name, role = UserRole.CUSTOMER)
                        onComplete()
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(if (step < totalSteps - 1) "Next" else "Finish")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderRegistrationWizard(
    initialName: String,
    initialPhone: String,
    onComplete: () -> Unit
) {
    var step by remember { mutableIntStateOf(0) }
    val totalSteps = 5
    
    // State
    var name by remember { mutableStateOf(initialName) }
    var email by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var profession by remember { mutableStateOf("") }
    var rate by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf(1f) }
    var availability by remember { mutableStateOf(setOf<String>()) }
    var lat by remember { mutableDoubleStateOf(0.0) }
    var lng by remember { mutableDoubleStateOf(0.0) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(
            progress = (step + 1) / totalSteps.toFloat(),
            modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.height(24.dp))
        
        Crossfade(targetState = step, label = "ProviderWizard") { currentStep ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (currentStep) {
                    0 -> {
                        Text("Professional Profile", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold))
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(
                            modifier = Modifier.size(100.dp).background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(40.dp))
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Full Name") }, modifier = Modifier.fillMaxWidth())
                        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Business Email") }, modifier = Modifier.fillMaxWidth())
                        OutlinedTextField(value = bio, onValueChange = { bio = it }, label = { Text("Short Bio") }, modifier = Modifier.fillMaxWidth().height(100.dp), maxLines = 3)
                    }
                    1 -> {
                        Text("Expertise & Pricing", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold))
                        Spacer(modifier = Modifier.height(16.dp))
                        var expanded by remember { mutableStateOf(false) }
                        val professions = listOf("Plumber", "Carpenter", "Electrician", "Event Staff", "Maid", "Driver")
                        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                            OutlinedTextField(value = profession, onValueChange = {}, readOnly = true, label = { Text("Primary Trade") }, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) }, modifier = Modifier.fillMaxWidth().menuAnchor())
                            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                professions.forEach { selectionOption ->
                                    DropdownMenuItem(text = { Text(selectionOption) }, onClick = { profession = selectionOption; expanded = false })
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Years of Experience: ${experience.toInt()}", modifier = Modifier.align(Alignment.Start))
                        Slider(value = experience, onValueChange = { experience = it }, valueRange = 0f..20f)
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(value = rate, onValueChange = { rate = it }, label = { Text("Hourly Rate ($)") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                    }
                    2 -> {
                        Text("Availability", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold))
                        Text("When do you typically work?", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(16.dp))
                        val times = listOf("Weekdays", "Weekends", "Mornings", "Afternoons", "Evenings", "Nights")
                        Column {
                            times.chunked(2).forEach { rowItems ->
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                                    rowItems.forEach { item ->
                                        FilterChip(
                                            selected = availability.contains(item),
                                            onClick = { if (availability.contains(item)) availability -= item else availability += item },
                                            label = { Text(item) },
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    3 -> {
                        Text("Service Area", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold))
                        Text("Pinpoint your base location", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(modifier = Modifier.fillMaxWidth().height(250.dp).clip(RoundedCornerShape(12.dp))) {
                            com.example.ui.components.LeafletMapView(
                                onLocationSelected = { l, lg -> lat = l; lng = lg }
                            )
                        }
                    }
                    4 -> {
                        Text("Verification", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold))
                        Text("We require ID verification to build trust.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = {},
                            modifier = Modifier.fillMaxWidth().height(60.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant, contentColor = MaterialTheme.colorScheme.onSurfaceVariant)
                        ) {
                            Icon(Icons.Default.UploadFile, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Upload Government ID or License")
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            var checked by remember { mutableStateOf(false) }
                            Checkbox(checked = checked, onCheckedChange = { checked = it })
                            Text("I consent to a background check.", fontSize = 14.sp)
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            if (step > 0) {
                OutlinedButton(onClick = { step-- }, modifier = Modifier.weight(1f)) { Text("Back") }
                Spacer(modifier = Modifier.width(16.dp))
            }
            Button(
                onClick = {
                    if (step < totalSteps - 1) step++ 
                    else {
                        LoopRepository.login(phone = initialPhone, name = name, role = UserRole.PROVIDER)
                        onComplete()
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(if (step < totalSteps - 1) "Next" else "Finish")
            }
        }
    }
}
