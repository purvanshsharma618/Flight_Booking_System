package com.bookingservice.service.impl;

import com.bookingservice.model.BookingEntity;
import com.bookingservice.repository.BookingRepository;
import com.bookingservice.service.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;


import com.bookingservice.exceptions.ResourceNotFoundException;



@Service
public class BookingServiceImpl implements IBookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public void createBooking(BookingEntity booking) {
        bookingRepository.save(booking);
    }

    @Override
    public List<BookingEntity> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public List<String> getAllBookingID() {
        return bookingRepository.findAll()
                .stream()
                .map(BookingEntity::getBookingId)
                .collect(Collectors.toList());
    }

    @Override
    public BookingEntity getBookingByIdForUser(String id, String userId) throws AccessDeniedException {
        BookingEntity booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));

        System.out.println("User Id"+booking.getUserId());
        if (!booking.getUserId().equals(userId)) {
            throw new AccessDeniedException("Unauthorized access to booking with id: " + id);
        }

        return booking;
    }

    @Override
    public BookingEntity updateBookingForUser(String id, BookingEntity updatedBooking, String userId) throws AccessDeniedException {
        BookingEntity existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + id));

        if (!existingBooking.getUserId().equals(userId)) {
            throw new AccessDeniedException("Unauthorized update attempt for booking with id: " + id);
        }

        if (updatedBooking.getAge() > 0) {
            existingBooking.setAge(updatedBooking.getAge());
        }
        if (updatedBooking.getAddress() != null && !updatedBooking.getAddress().isEmpty()) {
            existingBooking.setAddress(updatedBooking.getAddress());
        }
        if (updatedBooking.getEmail() != null && !updatedBooking.getEmail().isEmpty()) {
            existingBooking.setEmail(updatedBooking.getEmail());
        }

        return bookingRepository.save(existingBooking);
    }

    @Override
    public BookingEntity deleteBookingForUser(String id, String userId) throws AccessDeniedException {
        BookingEntity booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + id));

        if (!booking.getUserId().equals(userId)) {
            throw new AccessDeniedException("Unauthorized delete attempt for booking with id: " + id);
        }

        bookingRepository.deleteById(id);
        return booking;
    }
    @Override
    public void deleteBooking(String id) {
        BookingEntity booking = getBookingById(id);
        bookingRepository.delete(booking);
    }
    @Override
    public BookingEntity getBookingById(String id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + id));
    }

    @Override
    public BookingEntity updateBooking(String id, BookingEntity updatedBooking) {
        BookingEntity existing = getBookingById(id);
        existing.setName(updatedBooking.getName());
        existing.setEmail(updatedBooking.getEmail());
        existing.setFlightId(updatedBooking.getFlightId());
        existing.setPaymentAmount(updatedBooking.getPaymentAmount());
        return bookingRepository.save(existing);
    }
}

