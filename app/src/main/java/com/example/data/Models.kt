package com.example.data

import java.util.UUID

enum class AppLanguage {
    ENGLISH,
    HINDI
}

enum class UserRole {
    CUSTOMER,
    PROVIDER,
    ADMIN
}

enum class BookingStatus(val labelEn: String, val labelHi: String) {
    REQUESTED("Requested", "अनुरोध किया"),
    CONFIRMED("Confirmed", "स्वीकृत"),
    ON_THE_WAY("On the Way", "रास्ते में"),
    IN_PROGRESS("In Progress", "प्रगति पर"),
    COMPLETED("Completed", "पूर्ण"),
    CANCELLED("Cancelled", "रद्द")
}

data class UserProfile(
    val id: String = "user_1",
    val name: String = "Rahul Sharma",
    val phone: String = "+91 98765 43210",
    val email: String = "rahul.sharma@example.com",
    val location: String = "Indiranagar, Bengaluru",
    val isProvider: Boolean = false,
    val isBlocked: Boolean = false,
    val walletBalance: Double = 350.0
)

data class ServiceCategory(
    val id: String,
    val nameEn: String,
    val nameHi: String,
    val icon: String,
    val description: String,
    val isEventStaff: Boolean = false,
    val startingPrice: Int
)

data class ServiceItem(
    val id: String,
    val categoryId: String,
    val nameEn: String,
    val nameHi: String,
    val price: Int,
    val description: String,
    val durationMin: Int = 60
)

data class ServiceProvider(
    val id: String,
    val name: String,
    val phone: String,
    val categoryId: String,
    val categoryName: String,
    val rating: Float,
    val reviewsCount: Int,
    val hourlyRate: Int,
    val distanceKm: Double,
    val experienceYears: Int,
    val bio: String,
    val isAvailable: Boolean = true,
    val isApproved: Boolean = true,
    val aadharVerified: Boolean = true,
    val aadharNumber: String = "XXXX-XXXX-4819",
    val bankUpi: String = "provider@okhdfcbank",
    val totalEarnings: Double = 24500.0,
    val completedJobs: Int = 42,
    val avatarColorHex: Long = 0xFF2E75B6
)

data class Booking(
    val id: String = UUID.randomUUID().toString().take(8).uppercase(),
    val userId: String,
    val userName: String,
    val userPhone: String,
    val providerId: String,
    val providerName: String,
    val categoryName: String,
    val serviceName: String,
    val date: String,
    val timeSlot: String,
    val status: BookingStatus = BookingStatus.REQUESTED,
    val address: String,
    val workDescription: String,
    val isEventStaff: Boolean = false,
    val staffCount: Int = 1,
    val durationHours: Int = 2,
    val baseAmount: Double,
    val discountAmount: Double = 0.0,
    val taxAmount: Double = 0.0,
    val totalAmount: Double,
    val paymentStatus: String = "Paid via Razorpay UPI",
    val couponCode: String? = null,
    val rating: Float? = null,
    val review: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)

data class Coupon(
    val code: String,
    val discountPercent: Int,
    val maxDiscount: Double,
    val minOrder: Double,
    val description: String
)

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val bookingId: String,
    val senderName: String,
    val text: String,
    val timestamp: String,
    val isFromCustomer: Boolean
)

data class SupportTicket(
    val id: String = UUID.randomUUID().toString().take(6).uppercase(),
    val userId: String,
    val subject: String,
    val description: String,
    val status: String = "Open",
    val adminReply: String? = null,
    val createdAt: String = "Today, 10:15 AM"
)

data class AppNotification(
    val id: String = UUID.randomUUID().toString(),
    val titleEn: String,
    val titleHi: String,
    val messageEn: String,
    val messageHi: String,
    val time: String,
    val isRead: Boolean = false,
    val iconType: String = "booking"
)
