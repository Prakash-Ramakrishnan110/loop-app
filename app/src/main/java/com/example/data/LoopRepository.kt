package com.example.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

object LoopRepository {

    // Current Active Role
    private val _currentRole = MutableStateFlow(UserRole.CUSTOMER)
    val currentRole: StateFlow<UserRole> = _currentRole.asStateFlow()

    // Current App Language
    private val _language = MutableStateFlow(AppLanguage.ENGLISH)
    val language: StateFlow<AppLanguage> = _language.asStateFlow()

    // Dark Mode Override (null means follow system)
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    // Authentication State
    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

    // Current User Profile
    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    // Categories
    private val _categories = MutableStateFlow(MockDataProvider.categories)
    val categories: StateFlow<List<ServiceCategory>> = _categories.asStateFlow()

    // Services
    private val _services = MutableStateFlow(MockDataProvider.serviceItems)
    val services: StateFlow<List<ServiceItem>> = _services.asStateFlow()

    // Service Providers
    private val _providers = MutableStateFlow(MockDataProvider.serviceProviders)
    val providers: StateFlow<List<ServiceProvider>> = _providers.asStateFlow()

    // Bookings
    private val _bookings = MutableStateFlow(MockDataProvider.initialBookings)
    val bookings: StateFlow<List<Booking>> = _bookings.asStateFlow()

    // Coupons
    private val _coupons = MutableStateFlow(MockDataProvider.coupons)
    val coupons: StateFlow<List<Coupon>> = _coupons.asStateFlow()

    // Chat Messages
    private val _chatMessages = MutableStateFlow(MockDataProvider.initialMessages)
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    // Support Tickets
    private val _supportTickets = MutableStateFlow(
        listOf(
            SupportTicket(
                id = "TK-101",
                userId = "user_1",
                subject = "Billing query on LP-8492",
                description = "Was coupon discount applied correctly on the final invoice?",
                status = "Resolved",
                adminReply = "Yes, ₹59.8 coupon discount was subtracted from ₹299 base amount."
            )
        )
    )
    val supportTickets: StateFlow<List<SupportTicket>> = _supportTickets.asStateFlow()

    // Notifications
    private val _notifications = MutableStateFlow(MockDataProvider.initialNotifications)
    val notifications: StateFlow<List<AppNotification>> = _notifications.asStateFlow()

    // Admin Commission Settings
    private val _commissionPercentage = MutableStateFlow(15.0) // 15%
    val commissionPercentage: StateFlow<Double> = _commissionPercentage.asStateFlow()

    fun setRole(role: UserRole) {
        _currentRole.value = role
    }

    fun switchRole(role: UserRole) {
        setRole(role)
    }

    fun setLanguage(lang: AppLanguage) {
        _language.value = lang
    }

    fun toggleDarkMode() {
        _isDarkMode.value = !_isDarkMode.value
    }

    fun login(phone: String, name: String = "Rahul Sharma", role: UserRole = UserRole.CUSTOMER) {
        _currentRole.value = role
        _userProfile.update { it.copy(phone = phone, name = name, isProvider = role == UserRole.PROVIDER) }
        _isAuthenticated.value = true
    }

    fun loginAsDemo(
        role: UserRole,
        name: String,
        phone: String,
        email: String,
        location: String,
        walletBalance: Double = 500.0
    ) {
        _currentRole.value = role
        _userProfile.value = UserProfile(
            id = "user_" + UUID.randomUUID().toString().take(6),
            name = name,
            phone = phone,
            email = email,
            location = location,
            isProvider = role == UserRole.PROVIDER,
            walletBalance = walletBalance
        )
        _isAuthenticated.value = true
    }

    fun logout() {
        _isAuthenticated.value = false
    }

    fun updateProfile(name: String, email: String, location: String) {
        _userProfile.update { it.copy(name = name, email = email, location = location) }
    }

    // Customer Book Service
    fun createBooking(
        provider: ServiceProvider,
        serviceItem: ServiceItem?,
        customServiceName: String?,
        date: String,
        timeSlot: String,
        address: String,
        workDesc: String,
        isEventStaff: Boolean,
        staffCount: Int,
        durationHours: Int,
        appliedCoupon: Coupon?
    ): Booking {
        val baseRate = (serviceItem?.price ?: provider.hourlyRate).toDouble()
        val baseAmount = if (isEventStaff) {
            baseRate * staffCount * durationHours
        } else {
            baseRate
        }

        var discount = 0.0
        if (appliedCoupon != null && baseAmount >= appliedCoupon.minOrder) {
            discount = (baseAmount * appliedCoupon.discountPercent / 100.0).coerceAtMost(appliedCoupon.maxDiscount)
        }
        val tax = (baseAmount - discount) * 0.18 // 18% GST standard in India
        val total = (baseAmount - discount + tax)

        val newBooking = Booking(
            id = "LP-" + (1000..9999).random(),
            userId = _userProfile.value.id,
            userName = _userProfile.value.name,
            userPhone = _userProfile.value.phone,
            providerId = provider.id,
            providerName = provider.name,
            categoryName = provider.categoryName,
            serviceName = serviceItem?.nameEn ?: customServiceName ?: provider.categoryName,
            date = date,
            timeSlot = timeSlot,
            status = BookingStatus.REQUESTED,
            address = address,
            workDescription = workDesc,
            isEventStaff = isEventStaff,
            staffCount = staffCount,
            durationHours = durationHours,
            baseAmount = baseAmount,
            discountAmount = discount,
            taxAmount = tax,
            totalAmount = total,
            couponCode = appliedCoupon?.code
        )

        _bookings.update { listOf(newBooking) + it }
        
        // Add push notification
        addNotification(
            titleEn = "Booking Requested",
            titleHi = "बुकिंग का अनुरोध भेजा गया",
            messageEn = "Your booking request for ${newBooking.serviceName} has been placed.",
            messageHi = "${newBooking.serviceName} के लिए आपका अनुरोध भेज दिया गया है।"
        )

        return newBooking
    }

    fun updateBookingStatus(bookingId: String, newStatus: BookingStatus) {
        _bookings.update { list ->
            list.map { if (it.id == bookingId) it.copy(status = newStatus) else it }
        }
        val b = _bookings.value.find { it.id == bookingId }
        if (b != null) {
            addNotification(
                titleEn = "Booking Status: ${newStatus.labelEn}",
                titleHi = "बुकिंग स्थिति: ${newStatus.labelHi}",
                messageEn = "Booking #${b.id} status changed to ${newStatus.labelEn}.",
                messageHi = "बुकिंग #${b.id} की स्थिति अब ${newStatus.labelHi} है।"
            )
        }
    }

    fun cancelBooking(bookingId: String) {
        updateBookingStatus(bookingId, BookingStatus.CANCELLED)
    }

    fun rateBooking(bookingId: String, rating: Float, review: String) {
        _bookings.update { list ->
            list.map { if (it.id == bookingId) it.copy(rating = rating, review = review) else it }
        }
    }

    // Provider Registration
    fun registerAsProvider(
        name: String,
        phone: String,
        category: ServiceCategory,
        hourlyRate: Int,
        experience: Int,
        bio: String,
        aadharNum: String,
        bankUpi: String
    ) {
        val newProvider = ServiceProvider(
            id = "prov_" + UUID.randomUUID().toString().take(5),
            name = name,
            phone = phone,
            categoryId = category.id,
            categoryName = category.nameEn,
            rating = 5.0f,
            reviewsCount = 0,
            hourlyRate = hourlyRate,
            distanceKm = 0.5,
            experienceYears = experience,
            bio = bio,
            isAvailable = true,
            isApproved = false, // Goes to Admin verification queue!
            aadharVerified = false,
            aadharNumber = aadharNum,
            bankUpi = bankUpi,
            totalEarnings = 0.0,
            completedJobs = 0,
            avatarColorHex = 0xFF2E75B6
        )
        _providers.update { listOf(newProvider) + it }
        _userProfile.update { it.copy(isProvider = true) }
        _currentRole.value = UserRole.PROVIDER

        addNotification(
            titleEn = "Provider Application Submitted",
            titleHi = "पार्टनर आवेदन जमा हुआ",
            messageEn = "Your registration as a service professional is pending admin verification.",
            messageHi = "सेवा प्रदाता के रूप में आपका आवेदन व्यवस्थापक सत्यापन के अधीन है।"
        )
    }

    fun toggleProviderAvailability(providerId: String) {
        _providers.update { list ->
            list.map { if (it.id == providerId) it.copy(isAvailable = !it.isAvailable) else it }
        }
    }

    fun withdrawProviderEarnings(providerId: String, amount: Double): Boolean {
        var success = false
        _providers.update { list ->
            list.map {
                if (it.id == providerId && it.totalEarnings >= amount) {
                    success = true
                    it.copy(totalEarnings = it.totalEarnings - amount)
                } else it
            }
        }
        return success
    }

    // Chat
    fun sendMessage(bookingId: String, senderName: String, text: String, isCustomer: Boolean) {
        val newMsg = ChatMessage(
            bookingId = bookingId,
            senderName = senderName,
            text = text,
            timestamp = "Just now",
            isFromCustomer = isCustomer
        )
        _chatMessages.update { it + newMsg }
    }

    // Support Tickets
    fun createSupportTicket(subject: String, description: String) {
        val ticket = SupportTicket(
            userId = _userProfile.value.id,
            subject = subject,
            description = description
        )
        _supportTickets.update { listOf(ticket) + it }
    }

    fun resolveTicket(ticketId: String, reply: String) {
        _supportTickets.update { list ->
            list.map { if (it.id == ticketId) it.copy(status = "Resolved", adminReply = reply) else it }
        }
    }

    // Admin Controls
    fun approveProvider(providerId: String) {
        _providers.update { list ->
            list.map { if (it.id == providerId) it.copy(isApproved = true, aadharVerified = true) else it }
        }
    }

    fun rejectOrBlockProvider(providerId: String) {
        _providers.update { list ->
            list.map { if (it.id == providerId) it.copy(isApproved = false) else it }
        }
    }

    fun toggleUserBlocked(isBlocked: Boolean) {
        _userProfile.update { it.copy(isBlocked = isBlocked) }
    }

    fun setCommission(rate: Double) {
        _commissionPercentage.value = rate
    }

    fun addCoupon(code: String, discountPercent: Int, maxDiscount: Double, minOrder: Double, description: String) {
        val c = Coupon(code.uppercase(), discountPercent, maxDiscount, minOrder, description)
        _coupons.update { listOf(c) + it }
    }

    fun deleteCoupon(code: String) {
        _coupons.update { list -> list.filterNot { it.code == code } }
    }

    fun addCategory(nameEn: String, nameHi: String, icon: String, description: String, startPrice: Int, isEventStaff: Boolean = false) {
        val cat = ServiceCategory(
            id = "cat_" + UUID.randomUUID().toString().take(6),
            nameEn = nameEn,
            nameHi = nameHi,
            icon = icon,
            description = description,
            isEventStaff = isEventStaff,
            startingPrice = startPrice
        )
        _categories.update { it + cat }
    }

    fun addNotification(titleEn: String, titleHi: String, messageEn: String, messageHi: String) {
        val notif = AppNotification(
            titleEn = titleEn,
            titleHi = titleHi,
            messageEn = messageEn,
            messageHi = messageHi,
            time = "Just now"
        )
        _notifications.update { listOf(notif) + it }
    }
}
