package com.example.data

object MockDataProvider {

    val categories = listOf(
        ServiceCategory(
            id = "cat_plumber",
            nameEn = "Plumber",
            nameHi = "प्लंबर",
            icon = "plumbing",
            description = "Pipe fitting, leakage repair & bathroom installations",
            startingPrice = 299
        ),
        ServiceCategory(
            id = "cat_electrician",
            nameEn = "Electrician",
            nameHi = "इलेक्ट्रीशियन",
            icon = "flash_on",
            description = "Wiring, appliance repair, switchboard & fan fitting",
            startingPrice = 249
        ),
        ServiceCategory(
            id = "cat_cleaning",
            nameEn = "Deep Cleaning",
            nameHi = "सफाई सेवा",
            icon = "cleaning_services",
            description = "Full home deep clean, bathroom & kitchen sanitize",
            startingPrice = 499
        ),
        ServiceCategory(
            id = "cat_carpenter",
            nameEn = "Carpenter",
            nameHi = "बढ़ई",
            icon = "construction",
            description = "Furniture repair, door lock installation & assembly",
            startingPrice = 349
        ),
        ServiceCategory(
            id = "cat_painter",
            nameEn = "Painter",
            nameHi = "पेंटर",
            icon = "format_paint",
            description = "Interior/exterior wall painting & waterproofing",
            startingPrice = 799
        ),
        ServiceCategory(
            id = "cat_ac",
            nameEn = "AC Technician",
            nameHi = "एसी तकनीशियन",
            icon = "ac_unit",
            description = "AC servicing, gas charging, filter wash & installation",
            startingPrice = 499
        ),
        ServiceCategory(
            id = "cat_event_staff",
            nameEn = "Event & Marriage Staff",
            nameHi = "इवेंट एवं विवाह स्टाफ",
            icon = "groups",
            description = "Professional Waiters, Helpers, Supplier Boys & Function cleaners",
            isEventStaff = true,
            startingPrice = 450
        )
    )

    val serviceItems = listOf(
        ServiceItem("srv_1", "cat_plumber", "Water Tap Leakage Repair", "नल रिसाव मरम्मत", 299, "Fix tap leakage and seal washer replacement", 45),
        ServiceItem("srv_2", "cat_plumber", "Washbasin & Sink Unblock", "बेसिन एवं सिंक अनब्लॉक", 399, "Chemical unblock and debris cleaning", 60),
        ServiceItem("srv_3", "cat_plumber", "Toilet Flush Tank Installation", "फ्लश टैंक इंस्टॉलेशन", 599, "Fitting and internal mechanism overhaul", 90),
        
        ServiceItem("srv_4", "cat_electrician", "Switchboard / Socket Repair", "स्विचबोर्ड मरम्मत", 249, "Fault detection and replacement of up to 2 switches", 30),
        ServiceItem("srv_5", "cat_electrician", "Ceiling Fan Installation", "सीलिंग फैन फिटिंग", 299, "Safe mounting and regulator check", 45),
        ServiceItem("srv_6", "cat_electrician", "MCB Tripping Fix", "एमसीबी ट्रिपिंग सुधार", 499, "Short circuit testing and wiring load balancing", 60),

        ServiceItem("srv_7", "cat_cleaning", "Bathroom Deep Cleaning", "बाथरूम डीप क्लीनिंग", 499, "Hard water stain removal & tile buffing", 90),
        ServiceItem("srv_8", "cat_cleaning", "Full Home Sanitization", "सम्पूर्ण घर सैनिटाइजेशन", 1499, "2BHK full vacuuming and surface disinfection", 180),

        ServiceItem("srv_9", "cat_carpenter", "Door Lock / Latch Repair", "दरवाजा लॉक रिपेयर", 349, "Cylinder realignment & lubrication", 45),
        ServiceItem("srv_10", "cat_carpenter", "Bed / Table Assembly", "फर्नीचर असेंबली", 699, "Complete unboxing and tightening", 120),

        ServiceItem("srv_11", "cat_painter", "Wall Touch-up & Patch", "दीवार टच-अप", 799, "Putty filling and color matching for 1 wall", 120),
        ServiceItem("srv_12", "cat_ac", "AC Foam Jet Service", "एसी जेट सर्विसिंग", 549, "High pressure indoor coil & outdoor wash", 60),

        // Event Staff Items
        ServiceItem("srv_event_1", "cat_event_staff", "Function Waiters (Uniformed)", "वर्दीधारी वेटर", 450, "Experienced catering & serving staff per person/hour", 60),
        ServiceItem("srv_event_2", "cat_event_staff", "Kitchen & Catering Helpers", "किचन एवं खान-पान सहायक", 400, "Vegetable chopping, dish setup & buffet management", 60),
        ServiceItem("srv_event_3", "cat_event_staff", "Supplier Boys (Runner)", "सप्लायर बॉयज़", 420, "Material supply & refilling trays continuously", 60),
        ServiceItem("srv_event_4", "cat_event_staff", "Post-Event Cleaning Crew", "समारोह उपरांत सफाई दल", 500, "Waste segregation, hall sweeping & mopping", 60)
    )

    val serviceProviders = listOf(
        ServiceProvider(
            id = "prov_1",
            name = "Ramesh Kumar",
            phone = "+91 98234 11223",
            categoryId = "cat_plumber",
            categoryName = "Plumber",
            rating = 4.8f,
            reviewsCount = 128,
            hourlyRate = 299,
            distanceKm = 1.8,
            experienceYears = 8,
            bio = "Certified plumber with 8+ years experience in premium fixtures, PPR pipe jointing, and leak detection.",
            isAvailable = true,
            isApproved = true,
            aadharVerified = true,
            bankUpi = "ramesh.kumar@okhdfcbank",
            totalEarnings = 38400.0,
            completedJobs = 94,
            avatarColorHex = 0xFF2E75B6
        ),
        ServiceProvider(
            id = "prov_2",
            name = "Vikas Verma",
            phone = "+91 98112 33445",
            categoryId = "cat_electrician",
            categoryName = "Electrician",
            rating = 4.9f,
            reviewsCount = 210,
            hourlyRate = 249,
            distanceKm = 2.4,
            experienceYears = 6,
            bio = "Govt ITI certified electrician. Expert in inverter wiring, smart home switchboards & short circuit resolution.",
            isAvailable = true,
            isApproved = true,
            aadharVerified = true,
            bankUpi = "vikas.verma@paytm",
            totalEarnings = 46800.0,
            completedJobs = 142,
            avatarColorHex = 0xFF1F4E79
        ),
        ServiceProvider(
            id = "prov_3",
            name = "Sunita Devi & Team",
            phone = "+91 98770 55667",
            categoryId = "cat_cleaning",
            categoryName = "Deep Cleaning",
            rating = 4.7f,
            reviewsCount = 85,
            hourlyRate = 499,
            distanceKm = 3.1,
            experienceYears = 5,
            bio = "Specialized in eco-friendly steam cleaning, mechanized single-disc scrubbing and kitchen de-greasing.",
            isAvailable = true,
            isApproved = true,
            aadharVerified = true,
            bankUpi = "sunita.clean@sbi",
            totalEarnings = 31200.0,
            completedJobs = 68,
            avatarColorHex = 0xFF22C55E
        ),
        ServiceProvider(
            id = "prov_4",
            name = "Royal Event Squad (Amit Singh)",
            phone = "+91 99001 88776",
            categoryId = "cat_event_staff",
            categoryName = "Event & Marriage Staff",
            rating = 4.95f,
            reviewsCount = 312,
            hourlyRate = 450,
            distanceKm = 1.2,
            experienceYears = 9,
            bio = "Ready crew of 50+ trained banquet waiters, kitchen boys and supplier runners for weddings, sangeet and corporate parties.",
            isAvailable = true,
            isApproved = true,
            aadharVerified = true,
            bankUpi = "royalevents@icici",
            totalEarnings = 92000.0,
            completedJobs = 188,
            avatarColorHex = 0xFFF59E0B
        ),
        ServiceProvider(
            id = "prov_5",
            name = "Deepak AC Solutions",
            phone = "+91 98450 77112",
            categoryId = "cat_ac",
            categoryName = "AC Technician",
            rating = 4.6f,
            reviewsCount = 74,
            hourlyRate = 499,
            distanceKm = 4.0,
            experienceYears = 7,
            bio = "Specialized in Daikin, LG, Voltas, Hitachi inverter split ACs and copper piping.",
            isAvailable = true,
            isApproved = true,
            aadharVerified = true,
            bankUpi = "deepak.ac@axisbank",
            totalEarnings = 22400.0,
            completedJobs = 52,
            avatarColorHex = 0xFF0284C7
        ),
        ServiceProvider(
            id = "prov_6",
            name = "Kishore Mistry",
            phone = "+91 97118 66223",
            categoryId = "cat_carpenter",
            categoryName = "Carpenter",
            rating = 4.75f,
            reviewsCount = 92,
            hourlyRate = 349,
            distanceKm = 2.9,
            experienceYears = 11,
            bio = "Master carpenter skilled in modular kitchen alignment, Godrej lock installations and custom wardrobes.",
            isAvailable = true,
            isApproved = false, // Pending admin approval test case
            aadharVerified = false,
            bankUpi = "kishore.carpenter@ybl",
            totalEarnings = 0.0,
            completedJobs = 0,
            avatarColorHex = 0xFFD97706
        )
    )

    val coupons = listOf(
        Coupon("LOOP50", 20, 100.0, 300.0, "Get 20% OFF up to ₹100 on home repairs"),
        Coupon("EVENT15", 15, 500.0, 1500.0, "Get 15% OFF up to ₹500 on event staff bookings"),
        Coupon("WELCOME", 25, 150.0, 250.0, "First booking discount of 25% up to ₹150"),
        Coupon("FLAT100", 10, 100.0, 499.0, "Flat ₹100 savings on deep cleaning & AC")
    )

    val initialBookings = listOf(
        Booking(
            id = "LP-8492",
            userId = "user_1",
            userName = "Rahul Sharma",
            userPhone = "+91 98765 43210",
            providerId = "prov_1",
            providerName = "Ramesh Kumar",
            categoryName = "Plumber",
            serviceName = "Water Tap Leakage Repair",
            date = "Today",
            timeSlot = "02:00 PM - 03:00 PM",
            status = BookingStatus.ON_THE_WAY,
            address = "Flat 402, Green Glen Heights, Indiranagar, Bengaluru",
            workDescription = "Kitchen mixer tap dripping continuously from main spout.",
            isEventStaff = false,
            staffCount = 1,
            durationHours = 1,
            baseAmount = 299.0,
            discountAmount = 59.8,
            taxAmount = 43.0,
            totalAmount = 282.2,
            couponCode = "LOOP50",
            createdAt = System.currentTimeMillis() - 3600000
        ),
        Booking(
            id = "LP-7311",
            userId = "user_1",
            userName = "Rahul Sharma",
            userPhone = "+91 98765 43210",
            providerId = "prov_4",
            providerName = "Royal Event Squad (Amit Singh)",
            categoryName = "Event & Marriage Staff",
            serviceName = "Function Waiters (Uniformed)",
            date = "Tomorrow",
            timeSlot = "06:00 PM - 11:00 PM",
            status = BookingStatus.CONFIRMED,
            address = "Shubh Palace Banquet Hall, 100ft Road, Bengaluru",
            workDescription = "Wedding Reception dinner service for 250 guests. Need punctual uniformed boys.",
            isEventStaff = true,
            staffCount = 6,
            durationHours = 5,
            baseAmount = 13500.0,
            discountAmount = 500.0,
            taxAmount = 2340.0,
            totalAmount = 15340.0,
            couponCode = "EVENT15",
            createdAt = System.currentTimeMillis() - 86400000
        ),
        Booking(
            id = "LP-6120",
            userId = "user_1",
            userName = "Rahul Sharma",
            userPhone = "+91 98765 43210",
            providerId = "prov_2",
            providerName = "Vikas Verma",
            categoryName = "Electrician",
            serviceName = "Switchboard / Socket Repair",
            date = "12 Sep 2026",
            timeSlot = "11:00 AM - 12:00 PM",
            status = BookingStatus.COMPLETED,
            address = "Flat 402, Green Glen Heights, Indiranagar, Bengaluru",
            workDescription = "Living room main switchboard spark issue.",
            isEventStaff = false,
            staffCount = 1,
            durationHours = 1,
            baseAmount = 249.0,
            discountAmount = 0.0,
            taxAmount = 44.8,
            totalAmount = 293.8,
            rating = 5.0f,
            review = "Very quick and professional. Brought his own tester and high quality wire.",
            createdAt = System.currentTimeMillis() - 432000000
        )
    )

    val initialNotifications = listOf(
        AppNotification(
            titleEn = "Professional On The Way!",
            titleHi = "प्रोफेशनल रास्ते में हैं!",
            messageEn = "Ramesh Kumar is heading towards your location for Tap Repair (LP-8492).",
            messageHi = "रमेश कुमार आपके पते की ओर रवाना हो चुके हैं (LP-8492)।",
            time = "10 mins ago"
        ),
        AppNotification(
            titleEn = "Booking Confirmed",
            titleHi = "बुकिंग स्वीकृत हुई",
            messageEn = "Royal Event Squad has accepted your booking for Tomorrow 6:00 PM.",
            messageHi = "रॉयल इवेंट स्क्वाड ने कल शाम 6:00 बजे की बुकिंग स्वीकार कर ली है।",
            time = "2 hours ago"
        ),
        AppNotification(
            titleEn = "Cashback Credited",
            titleHi = "कैशबैक मिला",
            messageEn = "₹50 referral bonus credited to your Loop Wallet.",
            messageHi = "₹50 का रेफरल बोनस आपके लूप वॉलेट में जमा कर दिया गया है।",
            time = "Yesterday"
        )
    )

    val initialMessages = listOf(
        ChatMessage(
            bookingId = "LP-8492",
            senderName = "Ramesh Kumar",
            text = "Namaste sir, I have started from Indiranagar junction. Will reach in 10-12 minutes.",
            timestamp = "01:45 PM",
            isFromCustomer = false
        ),
        ChatMessage(
            bookingId = "LP-8492",
            senderName = "Rahul Sharma",
            text = "Sure Ramesh ji, the security will let you in at Gate 2.",
            timestamp = "01:47 PM",
            isFromCustomer = true
        )
    )
}
