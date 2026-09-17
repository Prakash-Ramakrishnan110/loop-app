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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.*
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun AdminDashboardScreen() {
    var selectedSection by remember { mutableIntStateOf(0) }
    val sections = listOf("Overview", "Users", "Professionals", "Bookings", "Coupons", "Tickets", "Commission")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Admin Web Header Bar
        Surface(
            color = Color(0xFF0F172A),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = LoopAccent, modifier = Modifier.size(22.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("LOOP ADMIN CONSOLE", color = Color.White, fontWeight = FontWeight.Black, fontSize = 13.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = Color(0xFF334155),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("admin@loop.com", color = Color.White, fontSize = 11.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = { LoopRepository.logout() },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Default.Logout,
                            contentDescription = "Sign Out & Switch Account",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        // Section Tabs Row
        ScrollableTabRow(
            selectedTabIndex = selectedSection,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
            edgePadding = 12.dp
        ) {
            sections.forEachIndexed { index, name ->
                Tab(
                    selected = selectedSection == index,
                    onClick = { selectedSection = index },
                    text = { Text(name, fontWeight = if (selectedSection == index) FontWeight.Bold else FontWeight.Normal, fontSize = 12.sp) }
                )
            }
        }

        // Active Section Content
        when (selectedSection) {
            0 -> AdminOverviewSection()
            1 -> AdminUsersSection()
            2 -> AdminProfessionalsSection()
            3 -> AdminBookingsSection()
            4 -> AdminCouponsSection()
            5 -> AdminTicketsSection()
            6 -> AdminCommissionSection()
        }
    }
}

// -------------------------------------------------------------
// OVERVIEW SECTION (Metrics & Revenue)
// -------------------------------------------------------------
@Composable
fun AdminOverviewSection() {
    val bookings by LoopRepository.bookings.collectAsState()
    val providers by LoopRepository.providers.collectAsState()
    val commissionRate by LoopRepository.commissionPercentage.collectAsState()

    val totalRevenue = bookings.filter { it.status != BookingStatus.CANCELLED }.sumOf { it.totalAmount }
    val commissionEarned = totalRevenue * (commissionRate / 100.0)

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text("Platform Metrics Summary", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                AdminMetricCard(title = "Total Users", value = "1,420", subtitle = "+12% this week", color = LoopPrimary, modifier = Modifier.weight(1f))
                AdminMetricCard(title = "Total Pros", value = "${providers.size}", subtitle = "${providers.count { it.isApproved }} Approved", color = LoopAccent, modifier = Modifier.weight(1f))
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                AdminMetricCard(title = "Total Bookings", value = "${bookings.size}", subtitle = "Active & Past", color = Color(0xFF8B5CF6), modifier = Modifier.weight(1f))
                AdminMetricCard(title = "Gross GMV", value = "₹${"%.0f".format(totalRevenue)}", subtitle = "Razorpay Settled", color = LoopSuccess, modifier = Modifier.weight(1f))
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Admin Net Commission (${commissionRate.toInt()}%)", fontSize = 12.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        Text("₹${"%.2f".format(commissionEarned)}", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                    Icon(Icons.Default.TrendingUp, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.size(36.dp))
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Top Performing Categories", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    CategoryProgressRow("Plumber & Leakage Fix", 0.85f, "85 Bookings")
                    CategoryProgressRow("Event & Marriage Staff", 0.72f, "72 Bookings")
                    CategoryProgressRow("Electrician & Wiring", 0.64f, "64 Bookings")
                    CategoryProgressRow("Deep Cleaning Services", 0.48f, "48 Bookings")
                }
            }
        }
    }
}

@Composable
fun CategoryProgressRow(title: String, fraction: Float, label: String) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(title, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            Text(label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { fraction },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
fun AdminMetricCard(title: String, value: String, subtitle: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(title, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = color)
            Spacer(modifier = Modifier.height(2.dp))
            Text(subtitle, fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
        }
    }
}

// -------------------------------------------------------------
// USERS SECTION
// -------------------------------------------------------------
@Composable
fun AdminUsersSection() {
    val user by LoopRepository.userProfile.collectAsState()

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text("Registered Customers (1)", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(user.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(user.phone, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(user.email, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(if (user.isBlocked) "Status: BLOCKED" else "Status: Active Customer", fontSize = 11.sp, color = if (user.isBlocked) MaterialTheme.colorScheme.error else LoopSuccess, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = { LoopRepository.toggleUserBlocked(!user.isBlocked) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (user.isBlocked) LoopSuccess else MaterialTheme.colorScheme.error
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(if (user.isBlocked) "Unblock" else "Block")
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// PROFESSIONALS SECTION (Approval & KYC)
// -------------------------------------------------------------
@Composable
fun AdminProfessionalsSection() {
    val providers by LoopRepository.providers.collectAsState()

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text("Service Providers & Verification (${providers.size})", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }

        items(providers) { pro ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(pro.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("${pro.categoryName} • ${pro.phone}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (pro.isApproved) LoopSuccess.copy(alpha = 0.15f) else Color(0xFFFEF3C7)
                        ) {
                            Text(
                                if (pro.isApproved) "VERIFIED" else "PENDING KYC",
                                color = if (pro.isApproved) LoopSuccess else Color(0xFFB45309),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Aadhaar: ${pro.aadharNumber} • UPI: ${pro.bankUpi}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Earnings: ₹${"%.0f".format(pro.totalEarnings)} • ${pro.completedJobs} jobs", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        if (!pro.isApproved) {
                            Button(
                                onClick = { LoopRepository.approveProvider(pro.id) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = LoopSuccess)
                            ) {
                                Text("Approve & Verify KYC", fontSize = 12.sp)
                            }
                        } else {
                            OutlinedButton(
                                onClick = { LoopRepository.rejectOrBlockProvider(pro.id) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                            ) {
                                Text("Revoke Verification / Block", fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// BOOKINGS MANAGEMENT
// -------------------------------------------------------------
@Composable
fun AdminBookingsSection() {
    val bookings by LoopRepository.bookings.collectAsState()

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text("All Customer Bookings (${bookings.size})", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }

        items(bookings) { b ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("#${b.id}: ${b.serviceName}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        StatusBadge(status = b.status)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Customer: ${b.userName} (${b.userPhone})", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Pro: ${b.providerName} • Amount: ₹${"%.2f".format(b.totalAmount)}", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    Text("Address: ${b.address}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                    if (b.status != BookingStatus.CANCELLED && b.status != BookingStatus.COMPLETED) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(
                                onClick = { LoopRepository.cancelBooking(b.id) },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                            ) {
                                Text("Cancel & Issue Razorpay Refund", fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// COUPONS MANAGEMENT
// -------------------------------------------------------------
@Composable
fun AdminCouponsSection() {
    val coupons by LoopRepository.coupons.collectAsState()
    var showAddCouponDialog by remember { mutableStateOf(false) }
    var code by remember { mutableStateOf("") }
    var discountPercent by remember { mutableStateOf("20") }
    var maxDiscount by remember { mutableStateOf("150") }
    var minOrder by remember { mutableStateOf("300") }
    var description by remember { mutableStateOf("Special festival offer") }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Promotional Coupons (${coupons.size})", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Button(
                    onClick = { showAddCouponDialog = true },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Create Coupon", fontSize = 12.sp)
                }
            }
        }

        items(coupons) { c ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(c.code, fontWeight = FontWeight.Black, fontSize = 15.sp, color = MaterialTheme.colorScheme.primary)
                        Text(c.description, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("${c.discountPercent}% OFF • Max ₹${c.maxDiscount.toInt()} • Min order ₹${c.minOrder.toInt()}", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    }
                    IconButton(onClick = { LoopRepository.deleteCoupon(c.code) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }

    if (showAddCouponDialog) {
        AlertDialog(
            onDismissRequest = { showAddCouponDialog = false },
            title = { Text("Create New Coupon") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = code, onValueChange = { code = it }, label = { Text("Coupon Code (e.g. FESTIVE30)") })
                    OutlinedTextField(value = discountPercent, onValueChange = { discountPercent = it }, label = { Text("Discount Percentage (%)") })
                    OutlinedTextField(value = maxDiscount, onValueChange = { maxDiscount = it }, label = { Text("Max Discount Amount (₹)") })
                    OutlinedTextField(value = minOrder, onValueChange = { minOrder = it }, label = { Text("Min Order Value (₹)") })
                    OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Offer Description") })
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (code.isNotBlank()) {
                            LoopRepository.addCoupon(
                                code = code,
                                discountPercent = discountPercent.toIntOrNull() ?: 15,
                                maxDiscount = maxDiscount.toDoubleOrNull() ?: 100.0,
                                minOrder = minOrder.toDoubleOrNull() ?: 299.0,
                                description = description
                            )
                            showAddCouponDialog = false
                            code = ""
                        }
                    }
                ) {
                    Text("Create")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddCouponDialog = false }) { Text("Cancel") }
            }
        )
    }
}

// -------------------------------------------------------------
// SUPPORT TICKETS RESOLUTION
// -------------------------------------------------------------
@Composable
fun AdminTicketsSection() {
    val tickets by LoopRepository.supportTickets.collectAsState()
    var selectedTicket by remember { mutableStateOf<SupportTicket?>(null) }
    var replyText by remember { mutableStateOf("") }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text("Customer Support Complaints (${tickets.size})", fontWeight = FontWeight.Bold, fontSize = 15.sp)
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
                            shape = RoundedCornerShape(6.dp),
                            color = if (t.status == "Resolved") LoopSuccess.copy(alpha = 0.15f) else Color(0xFFFEF3C7)
                        ) {
                            Text(t.status, color = if (t.status == "Resolved") LoopSuccess else Color(0xFFB45309), fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }
                    Text(t.description, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    if (t.adminReply != null) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Current Reply: ${t.adminReply}", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            selectedTicket = t
                            replyText = t.adminReply ?: ""
                        },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(if (t.status == "Resolved") "Update Response" else "Resolve & Reply", fontSize = 11.sp)
                    }
                }
            }
        }
    }

    if (selectedTicket != null) {
        AlertDialog(
            onDismissRequest = { selectedTicket = null },
            title = { Text("Reply to Ticket #${selectedTicket!!.id}") },
            text = {
                OutlinedTextField(
                    value = replyText,
                    onValueChange = { replyText = it },
                    label = { Text("Resolution Message to Customer") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        LoopRepository.resolveTicket(selectedTicket!!.id, replyText)
                        selectedTicket = null
                    }
                ) {
                    Text("Send Resolution")
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedTicket = null }) { Text("Cancel") }
            }
        )
    }
}

// -------------------------------------------------------------
// COMMISSION & PAYMENT SETTINGS
// -------------------------------------------------------------
@Composable
fun AdminCommissionSection() {
    val commissionRate by LoopRepository.commissionPercentage.collectAsState()
    var newRate by remember { mutableStateOf(commissionRate.toString()) }
    var savedMsg by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("Commission & Payout Engine", fontWeight = FontWeight.Bold, fontSize = 15.sp)

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Platform Commission Percentage", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Text("Deducted automatically from each booking prior to provider payout transfer.", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = newRate,
                        onValueChange = { newRate = it },
                        modifier = Modifier.weight(1f),
                        suffix = { Text("%") }
                    )
                    Button(
                        onClick = {
                            val r = newRate.toDoubleOrNull() ?: 15.0
                            LoopRepository.setCommission(r)
                            savedMsg = true
                        }
                    ) {
                        Text("Save Rate")
                    }
                }
                if (savedMsg) {
                    Text("✓ Platform commission updated to $newRate%", color = LoopSuccess, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                }
            }
        }
    }
}
