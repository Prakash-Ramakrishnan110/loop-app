package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.AppLanguage
import com.example.data.Booking
import com.example.data.BookingStatus
import com.example.data.LoopRepository
import com.example.data.UserRole
import com.example.ui.theme.*
import kotlin.math.roundToInt

@Composable
fun LoopRoleSwitcherBar(
    currentRole: UserRole,
    onSelectRole: (UserRole) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 2.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "LOOP MODES:",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                RoleTabPill(
                    label = "Customer",
                    icon = Icons.Default.Person,
                    isSelected = currentRole == UserRole.CUSTOMER,
                    onClick = { onSelectRole(UserRole.CUSTOMER) }
                )
                RoleTabPill(
                    label = "Provider",
                    icon = Icons.Default.Handyman,
                    isSelected = currentRole == UserRole.PROVIDER,
                    onClick = { onSelectRole(UserRole.PROVIDER) }
                )
                RoleTabPill(
                    label = "Admin Web",
                    icon = Icons.Default.AdminPanelSettings,
                    isSelected = currentRole == UserRole.ADMIN,
                    onClick = { onSelectRole(UserRole.ADMIN) }
                )
            }
        }
    }
}

@Composable
private fun RoleTabPill(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bgColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    val contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bgColor)
            .border(
                1.dp,
                if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                RoundedCornerShape(20.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = contentColor
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoopTopBar(
    title: String,
    subtitle: String? = null,
    showBack: Boolean = false,
    onBackClick: () -> Unit = {},
    unreadNotifs: Int = 0,
    onNotificationClick: (() -> Unit)? = null,
    currentLang: AppLanguage = AppLanguage.ENGLISH,
    onToggleLang: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        },
        navigationIcon = {
            if (showBack) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 4.dp)
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AllInclusive,
                        contentDescription = "Loop Logo",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        },
        actions = {
            // Language switch chip
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onToggleLang() }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = if (currentLang == AppLanguage.ENGLISH) "हिंदी" else "EN",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }

            if (onNotificationClick != null) {
                IconButton(onClick = onNotificationClick) {
                    BadgedBox(
                        badge = {
                            if (unreadNotifs > 0) {
                                Badge { Text("$unreadNotifs") }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Notifications"
                        )
                    }
                }
            }
            actions()
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Composable
fun StatusBadge(status: BookingStatus) {
    val (bg, fg) = when (status) {
        BookingStatus.REQUESTED -> Color(0xFFFEF3C7) to Color(0xFFB45309)
        BookingStatus.CONFIRMED -> Color(0xFFE0E7FF) to Color(0xFF4338CA)
        BookingStatus.ON_THE_WAY -> Color(0xFFE0F2FE) to Color(0xFF0369A1)
        BookingStatus.IN_PROGRESS -> Color(0xFFCFFAFE) to Color(0xFF0E7490)
        BookingStatus.COMPLETED -> Color(0xFFDCFCE7) to Color(0xFF15803D)
        BookingStatus.CANCELLED -> Color(0xFFFEE2E2) to Color(0xFFB91C1C)
    }

    Surface(
        color = bg,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = status.labelEn.uppercase(),
            color = fg,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}

@Composable
fun StarRatingBar(
    rating: Float,
    maxStars: Int = 5,
    onRatingChanged: ((Float) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        for (i in 1..maxStars) {
            val isFilled = i <= rating
            val icon = if (isFilled) Icons.Default.Star else Icons.Outlined.StarOutline
            val tint = if (isFilled) LoopAccent else MaterialTheme.colorScheme.outline

            Icon(
                imageVector = icon,
                contentDescription = "$i star",
                tint = tint,
                modifier = Modifier
                    .size(20.dp)
                    .clickable(enabled = onRatingChanged != null) {
                        onRatingChanged?.invoke(i.toFloat())
                    }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RazorpayPaymentBottomSheet(
    totalAmount: Double,
    serviceTitle: String,
    onPaymentSuccess: () -> Unit,
    onDismiss: () -> Unit
) {
    var isProcessing by remember { mutableStateOf(false) }
    var selectedMethod by remember { mutableStateOf("UPI / Google Pay") }

    ModalBottomSheet(
        onDismissRequest = { if (!isProcessing) onDismiss() },
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .navigationBarsPadding()
        ) {
            // Razorpay Header Brand
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = Color(0xFF0C2340),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("R", color = Color(0xFF528FF0), fontWeight = FontWeight.Black, fontSize = 20.sp)
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text("Razorpay Trusted", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("Secured 256-Bit Encrypted", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                Text(
                    text = "₹${"%.2f".format(totalAmount)}",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            Text("Select Payment Option", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(10.dp))

            val methods = listOf(
                "UPI (Google Pay, PhonePe, Paytm)" to Icons.Default.QrCodeScanner,
                "Credit / Debit Card (Visa, Master, RuPay)" to Icons.Default.CreditCard,
                "Net Banking (All Indian Banks)" to Icons.Default.AccountBalance,
                "Loop Cash Wallet" to Icons.Default.AccountBalanceWallet
            )

            methods.forEach { (name, icon) ->
                val isSelected = selectedMethod == name
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                        .border(
                            1.dp,
                            if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                            RoundedCornerShape(12.dp)
                        )
                        .clickable { selectedMethod = name }
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = name,
                        fontSize = 13.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    RadioButton(
                        selected = isSelected,
                        onClick = { selectedMethod = name }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (isProcessing) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Connecting to Bank Gateway...", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                Button(
                    onClick = {
                        isProcessing = true
                        // Simulate fast verification and trigger success
                        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                            isProcessing = false
                            onPaymentSuccess()
                        }, 1200)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("PAY ₹${"%.2f".format(totalAmount)} WITH RAZORPAY", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// -------------------------------------------------------------
// 1. HYPER-LOCAL TRUST & VERIFICATION ANCHORS
// -------------------------------------------------------------
@Composable
fun VerificationTrustBadge(
    text: String,
    icon: ImageVector = Icons.Default.VerifiedUser,
    tint: Color = LoopSuccess,
    modifier: Modifier = Modifier
) {
    Surface(
        color = tint.copy(alpha = 0.12f),
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(0.8.dp, tint.copy(alpha = 0.3f)),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = tint
            )
        }
    }
}

@Composable
fun MicroProximityVisualizer(
    distanceKm: Double,
    modifier: Modifier = Modifier
) {
    val estimatedMins = (distanceKm * 6).roundToInt().coerceAtLeast(8)
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Surface(
        color = Color(0xFFFEF3C7),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFFFDE68A)),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFD97706).copy(alpha = alpha))
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "⚡ Reaches in ~$estimatedMins mins (${"%.1f".format(distanceKm)} km)",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF92400E)
            )
        }
    }
}

// -------------------------------------------------------------
// 2. SHIMMER SKELETON LOADER
// -------------------------------------------------------------
@Composable
fun ShimmerCardLoader(
    modifier: Modifier = Modifier,
    height: Int = 90
) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerTranslate"
    )

    val shimmerColors = listOf(
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim, y = translateAnim)
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(height.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush)
        )
    }
}

// -------------------------------------------------------------
// 3. BOOKING CELEBRATION MODAL
// -------------------------------------------------------------
@Composable
fun BookingCelebrationDialog(
    bookingId: String,
    serviceName: String,
    providerName: String,
    onViewBooking: () -> Unit
) {
    var scaleCheck by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        scaleCheck = true
    }

    Dialog(
        onDismissRequest = onViewBooking,
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Animated Checkmark Circle
                AnimatedVisibility(
                    visible = scaleCheck,
                    enter = scaleIn(animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)) + fadeIn()
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(LoopSuccess.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(CircleShape)
                                .background(LoopSuccess),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Success",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "Booking Confirmed! 🎉",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Booking #$bookingId for $serviceName with $providerName is secured.",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.NotificationsActive,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Professional is notified and preparing to arrive.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))
                Button(
                    onClick = onViewBooking,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("Track Live Progress ➔", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 4. SLIDE-TO-ACCEPT GESTURE FOR PROVIDERS
// -------------------------------------------------------------
@Composable
fun SlideToAcceptButton(
    onAccept: () -> Unit,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val maxDragPx = with(density) { 180.dp.toPx() }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var isConfirmed by remember { mutableStateOf(false) }

    val animatedOffsetX by animateFloatAsState(
        targetValue = if (isConfirmed) maxDragPx else offsetX,
        label = "slideAnim"
    )

    Surface(
        color = LoopSuccess.copy(alpha = 0.15f),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, LoopSuccess.copy(alpha = 0.4f)),
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            // Background hint text
            Text(
                text = if (isConfirmed) "Job Accepted!" else "Slide to Accept Job ➔",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = LoopSuccess
            )

            // Draggable Knob
            Box(
                modifier = Modifier
                    .offset { IntOffset(animatedOffsetX.roundToInt(), 0) }
                    .size(44.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(LoopSuccess)
                    .draggable(
                        orientation = Orientation.Horizontal,
                        state = rememberDraggableState { delta ->
                            if (!isConfirmed) {
                                val next = (offsetX + delta).coerceIn(0f, maxDragPx)
                                offsetX = next
                                if (next >= maxDragPx * 0.85f) {
                                    isConfirmed = true
                                    offsetX = maxDragPx
                                    onAccept()
                                }
                            }
                        },
                        onDragStopped = {
                            if (!isConfirmed && offsetX < maxDragPx * 0.85f) {
                                offsetX = 0f
                            }
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isConfirmed) Icons.Default.Check else Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Slide",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

// -------------------------------------------------------------
// 5. ACTIVE BOOKING LIVE STATUS PILL (Sticky on Home Screen)
// -------------------------------------------------------------
@Composable
fun ActiveBookingStatusPill(
    booking: Booking,
    onTrack: () -> Unit,
    onCall: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onTrack() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (booking.status) {
                        BookingStatus.ON_THE_WAY -> Icons.Default.DirectionsCar
                        BookingStatus.IN_PROGRESS -> Icons.Default.Build
                        else -> Icons.Default.Schedule
                    },
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = booking.providerName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = booking.status.labelEn.uppercase(),
                            color = Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                        )
                    }
                }
                Text(
                    text = "${booking.serviceName} • In progress nearby",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                IconButton(
                    onClick = onCall,
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Call",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Button(
                    onClick = onTrack,
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    modifier = Modifier.height(34.dp)
                ) {
                    Text("Track", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 6. TRANSPARENT PRICE BREAKDOWN SHEET DIALOG (With (i) Info)
// -------------------------------------------------------------
@Composable
fun PriceBreakdownInfoDialog(
    baseAmount: Double,
    discountAmount: Double,
    taxAmount: Double,
    totalAmount: Double,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Transparent Pricing Breakdown", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Close", modifier = Modifier.size(18.dp))
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Loop believes in zero hidden fees. Every rupee is explicitly itemized:",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(14.dp))

                PriceRow(label = "Professional Service Charge", amount = baseAmount)
                PriceRow(label = "Worker Safety & Tool Insurance", amount = 19.0, note = "Included")
                if (discountAmount > 0) {
                    PriceRow(label = "Applied Promo Discount", amount = -discountAmount, isDiscount = true)
                }
                PriceRow(
                    label = "GST (18%) & Platform Convenience",
                    amount = taxAmount,
                    note = "Government compliant tax"
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Total Net Payable", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
                    Text(
                        "₹${"%.2f".format(totalAmount)}",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 17.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Got It, Close")
                }
            }
        }
    }
}

@Composable
private fun PriceRow(
    label: String,
    amount: Double,
    note: String? = null,
    isDiscount: Boolean = false
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, fontSize = 12.sp, color = if (isDiscount) LoopSuccess else MaterialTheme.colorScheme.onSurface)
            Text(
                text = "${if (isDiscount) "- " else ""}₹${"%.2f".format(kotlin.math.abs(amount))}",
                fontSize = 12.sp,
                fontWeight = if (isDiscount) FontWeight.Bold else FontWeight.Normal,
                color = if (isDiscount) LoopSuccess else MaterialTheme.colorScheme.onSurface
            )
        }
        if (note != null) {
            Text(note, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
