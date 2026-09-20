package com.example.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.*
import com.example.ui.components.*
import com.example.ui.theme.*

// -------------------------------------------------------------
// 1. PROVIDER REGISTRATION FORM
// -------------------------------------------------------------
@Composable
fun ProviderRegistrationScreen(
    onBack: () -> Unit,
    onRegistrationSubmitted: () -> Unit
) {
    val categories by LoopRepository.categories.collectAsState()
    var name by remember { mutableStateOf("Kishore Mistry") }
    var phone by remember { mutableStateOf("+91 97118 66223") }
    var selectedCategory by remember { mutableStateOf(categories.firstOrNull()) }
    var hourlyRate by remember { mutableStateOf("350") }
    var experience by remember { mutableStateOf("8") }
    var bio by remember { mutableStateOf("Skilled professional with 8+ years experience in fittings and installations.") }
    var aadharNumber by remember { mutableStateOf("4829 1928 3810") }
    var upiId by remember { mutableStateOf("kishore@okhdfcbank") }
    var isDocUploaded by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            LoopTopBar(title = "Partner Registration", subtitle = "Join Loop Service Network", showBack = true, onBackClick = onBack)
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
                Text("Personal & Contact Details", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Full Name") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Mobile Number") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
            }

            item {
                Text("Primary Service Category", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    categories.forEach { cat ->
                        val isSel = selectedCategory?.id == cat.id
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedCategory = cat },
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSel) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surface
                            ),
                            border = BorderStroke(1.dp, if (isSel) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                        ) {
                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(selected = isSel, onClick = { selectedCategory = cat })
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(cat.nameEn, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                            }
                        }
                    }
                }
            }

            item {
                Text("Rates & Experience", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = hourlyRate,
                        onValueChange = { hourlyRate = it },
                        label = { Text("Hourly Rate (₹)") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    OutlinedTextField(
                        value = experience,
                        onValueChange = { experience = it },
                        label = { Text("Experience (Yrs)") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = bio,
                    onValueChange = { bio = it },
                    label = { Text("Short Bio / Specialities") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    minLines = 2
                )
            }

            item {
                Text("Document Verification (KYC)", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = aadharNumber,
                    onValueChange = { aadharNumber = it },
                    label = { Text("Aadhaar Card Number") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = { Icon(Icons.Default.Badge, contentDescription = null) }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isDocUploaded = !isDocUploaded },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, if (isDocUploaded) LoopSuccess else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                ) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(if (isDocUploaded) Icons.Default.CheckCircle else Icons.Default.CloudUpload, contentDescription = null, tint = if (isDocUploaded) LoopSuccess else MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(if (isDocUploaded) "Aadhaar Front/Back Uploaded" else "Upload Aadhaar Photo Document", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                            Text("PDF or JPG format (Max 5MB)", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }

            item {
                Text("Bank / Payout UPI Details", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = upiId,
                    onValueChange = { upiId = it },
                    label = { Text("UPI ID (GPay / PhonePe / Paytm)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = { Icon(Icons.Default.AccountBalance, contentDescription = null) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        if (selectedCategory != null) {
                            LoopRepository.registerAsProvider(
                                name = name,
                                phone = phone,
                                category = selectedCategory!!,
                                hourlyRate = hourlyRate.toIntOrNull() ?: 300,
                                experience = experience.toIntOrNull() ?: 5,
                                bio = bio,
                                aadharNum = aadharNumber,
                                bankUpi = upiId
                            )
                            onRegistrationSubmitted()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Submit Application for Admin Review", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 2. PROVIDER DASHBOARD & HOME
// -------------------------------------------------------------
@Composable
fun ProviderDashboardScreen(
    onOpenBookings: () -> Unit,
    onOpenEarnings: () -> Unit,
    onOpenServices: () -> Unit
) {
    val providers by LoopRepository.providers.collectAsState()
    val myProvider = providers.firstOrNull() ?: MockDataProvider.serviceProviders.first()
    val bookings by LoopRepository.bookings.collectAsState()

    val pendingRequests = bookings.filter { it.status == BookingStatus.REQUESTED }
    val activeJobs = bookings.filter { it.status == BookingStatus.CONFIRMED || it.status == BookingStatus.ON_THE_WAY || it.status == BookingStatus.IN_PROGRESS }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Status Bar & Availability Toggle
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = if (myProvider.isAvailable) LoopSuccess else MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(12.dp)
                        ) {}
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (myProvider.isAvailable) "You are Online & Accepting Jobs" else "You are Offline",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                            Text(
                                text = if (myProvider.isApproved) "KYC Verified Partner" else "Pending Admin Approval",
                                fontSize = 11.sp,
                                color = if (myProvider.isApproved) LoopSuccess else LoopAccent
                            )
                        }
                    }
                    Switch(
                        checked = myProvider.isAvailable,
                        onCheckedChange = { LoopRepository.toggleProviderAvailability(myProvider.id) }
                    )
                }
            }
        }

        // Earnings & Stats Grid
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onOpenEarnings() },
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("Total Earnings", fontSize = 11.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("₹${"%.0f".format(myProvider.totalEarnings)}", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        Text("Tap to Withdraw", fontSize = 10.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                }

                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("Completed Jobs", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("${myProvider.completedJobs}", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = LoopAccent, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("${myProvider.rating} Rating", fontSize = 10.sp, color = LoopAccent, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Quick Navigation Buttons
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedButton(
                    onClick = onOpenBookings,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.FormatListBulleted, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("All Bookings", fontSize = 12.sp)
                }
                OutlinedButton(
                    onClick = onOpenServices,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Tune, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("My Services", fontSize = 12.sp)
                }
            }
        }

        // New Booking Requests (Pending Accept/Reject)
        item {
            Text(
                text = "New Booking Requests (${pendingRequests.size})",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        if (pendingRequests.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Box(modifier = Modifier.padding(24.dp), contentAlignment = Alignment.Center) {
                        Text("No pending requests right now. Stay online to receive jobs.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        } else {
            items(pendingRequests) { b ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(b.serviceName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("₹${"%.2f".format(b.totalAmount)}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Customer: ${b.userName} • ${b.userPhone}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("Address: ${b.address}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("When: ${b.date} at ${b.timeSlot}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)

                        Spacer(modifier = Modifier.height(12.dp))
                        SlideToAcceptButton(
                            onAccept = { LoopRepository.updateBookingStatus(b.id, BookingStatus.CONFIRMED) },
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(
                                onClick = { LoopRepository.cancelBooking(b.id) },
                                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                            ) {
                                Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Decline Job Request", fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }

        // Ongoing / Active Jobs
        item {
            Text(
                text = "Active Jobs in Progress (${activeJobs.size})",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        items(activeJobs) { b ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(b.serviceName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        StatusBadge(status = b.status)
                    }
                    Text("Customer: ${b.userName} • ${b.userPhone}", fontSize = 12.sp)
                    Text("Address: ${b.address}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(10.dp))

                    // Next Status Action Button
                    val nextAction = when (b.status) {
                        BookingStatus.CONFIRMED -> "Start Trip (On the Way)" to BookingStatus.ON_THE_WAY
                        BookingStatus.ON_THE_WAY -> "Start Work (In Progress)" to BookingStatus.IN_PROGRESS
                        BookingStatus.IN_PROGRESS -> "Mark Job Completed" to BookingStatus.COMPLETED
                        else -> null
                    }

                    if (nextAction != null) {
                        Button(
                            onClick = { LoopRepository.updateBookingStatus(b.id, nextAction.second) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (nextAction.second == BookingStatus.COMPLETED) LoopSuccess else MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(nextAction.first, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 3. PROVIDER EARNINGS & WALLET WITHDRAWAL
// -------------------------------------------------------------
@Composable
fun ProviderEarningsScreen(onBack: () -> Unit) {
    val providers by LoopRepository.providers.collectAsState()
    val pro = providers.firstOrNull() ?: MockDataProvider.serviceProviders.first()
    val commissionRate by LoopRepository.commissionPercentage.collectAsState()

    var showWithdrawDialog by remember { mutableStateOf(false) }
    var withdrawAmount by remember { mutableStateOf("5000") }
    var withdrawSuccessMsg by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            LoopTopBar(title = "Provider Wallet & Payouts", subtitle = "Bank Transfers via UPI", showBack = true, onBackClick = onBack)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Available Payout Balance", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("₹${"%.2f".format(pro.totalEarnings)}", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 26.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Linked UPI: ${pro.bankUpi}", color = Color.White.copy(alpha = 0.9f), fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = { showWithdrawDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = LoopAccent),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Withdraw Money to Bank", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }

            if (withdrawSuccessMsg != null) {
                Surface(
                    color = LoopSuccess.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = withdrawSuccessMsg!!,
                        color = LoopSuccess,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Commission & Pricing Terms", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• Platform commission fee: ${commissionRate.toInt()}% per completed booking.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("• Payouts processed instantly to your registered UPI ID without manual holding.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("• Razorpay gateway transaction fees are sponsored by Loop.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }

    if (showWithdrawDialog) {
        AlertDialog(
            onDismissRequest = { showWithdrawDialog = false },
            title = { Text("Withdraw to UPI") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Transferring to: ${pro.bankUpi}", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                    OutlinedTextField(
                        value = withdrawAmount,
                        onValueChange = { withdrawAmount = it },
                        label = { Text("Amount in ₹") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val amt = withdrawAmount.toDoubleOrNull() ?: 0.0
                        if (amt > 0 && LoopRepository.withdrawProviderEarnings(pro.id, amt)) {
                            withdrawSuccessMsg = "✓ Successfully transferred ₹${"%.2f".format(amt)} to ${pro.bankUpi} via IMPS UPI."
                            showWithdrawDialog = false
                        }
                    }
                ) {
                    Text("Confirm Transfer")
                }
            },
            dismissButton = {
                TextButton(onClick = { showWithdrawDialog = false }) { Text("Cancel") }
            }
        )
    }
}

// -------------------------------------------------------------
// 4. MANAGE SERVICES & PRICES
// -------------------------------------------------------------
@Composable
fun ProviderManageServicesScreen(onBack: () -> Unit) {
    val providers by LoopRepository.providers.collectAsState()
    val pro = providers.firstOrNull() ?: MockDataProvider.serviceProviders.first()
    val services by LoopRepository.services.collectAsState()
    val myServices = services.filter { it.categoryId == pro.categoryId }

    Scaffold(
        topBar = {
            LoopTopBar(title = "My Services & Prices", subtitle = pro.categoryName, showBack = true, onBackClick = onBack)
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
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Base Visiting / Hourly Rate", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text("Applies if customized repair is requested", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Text("₹${pro.hourlyRate}/hr", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }

            item {
                Text("Standard Catalog Rates", fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.padding(top = 8.dp))
            }

            items(myServices) { srv ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(srv.nameEn, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text(srv.description, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Text("₹${srv.price}", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}
