package com.zanar.hotelbooking.service.interf;

import com.zanar.hotelbooking.dto.Response;
import com.zanar.hotelbooking.entity.Booking;

public interface IBookingService {

    Response saveBooking(Long roomId, Long userId, Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response cancelBooking(Long bookingId);
}
