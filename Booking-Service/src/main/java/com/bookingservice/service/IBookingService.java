package com.bookingservice.service;

import com.bookingservice.model.BookingEntity;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;

//@Service
//public interface IBookingService {
//
//   void createBooking(BookingEntity booking); // Create Booking
//
//    List<BookingEntity> getAllBookings(); // Get All Bookings
//
//    List<String> getAllBookingID();
//
//    BookingEntity getBookingById(String id);
//
//    BookingEntity updateBooking(String id, BookingEntity updatedBooking);
//
//    void deleteBooking(String id); // Delete Booking
//}
import java.util.List;

public interface IBookingService {

  // Create Booking
  void createBooking(BookingEntity booking);

  // Get All Bookings (Admin)
  List<BookingEntity> getAllBookings();

  // Get All Booking IDs (Admin)
  List<String> getAllBookingID();

  // Get Booking by ID (Admin)
  BookingEntity getBookingById(String id);

  // Get Booking for specific User (Authorization check inside)
  BookingEntity getBookingByIdForUser(String id, String userId) throws AccessDeniedException;

  // Update Booking (Admin)
  BookingEntity updateBooking(String id, BookingEntity updatedBooking);

  // Update Booking (User-specific check)
  BookingEntity updateBookingForUser(String id, BookingEntity updatedBooking, String userId) throws AccessDeniedException;

  // Delete Booking (Admin)
  void deleteBooking(String id);

  // Delete Booking (User-specific check)
  BookingEntity deleteBookingForUser(String id, String userId) throws AccessDeniedException;
}
