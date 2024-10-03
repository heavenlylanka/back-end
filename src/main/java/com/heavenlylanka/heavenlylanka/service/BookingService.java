package com.heavenlylanka.heavenlylanka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heavenlylanka.heavenlylanka.entity.Booking;
import com.heavenlylanka.heavenlylanka.repository.BookingRepository;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
