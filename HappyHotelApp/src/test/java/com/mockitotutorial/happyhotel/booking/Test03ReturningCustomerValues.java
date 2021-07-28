package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

public class Test03ReturningCustomerValues {

    private BookingService bookingService;

    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;

    @BeforeEach
    void setup() {
        // mock all dependencies
        this.paymentServiceMock = mock(PaymentService.class);
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOMock = mock(BookingDAO.class);
        this.mailSenderMock = mock((MailSender.class));

        this.bookingService =
                new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);



    }

    @Test
    void should_CountAvailablePlaces_When_OneRoomAvailable() {
        // given
        // use -> when(obj.method).thenReturn(value) create data
        when(this.roomServiceMock.getAvailableRooms())
                .thenReturn(Collections.singletonList(new Room("Room 1", 5)));
        int exp = 5;

        // when
        int act = bookingService.getAvailablePlaceCount();

        // then
        assertEquals(exp, act);

    }

    @Test
    void should_CountAvailablePlaces_When_MultipleRoomAvailable() {
        // given
        List<Room> rooms = Arrays.asList(
                new Room("Room 1", 2), new Room("Room 2", 5)
        );
        when(this.roomServiceMock.getAvailableRooms()).thenReturn(rooms);
        int exp = 7;

        // when
        int act = bookingService.getAvailablePlaceCount();

        // then
        assertEquals(exp, act);
    }
}
