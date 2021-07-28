package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Test04MultipleThenReturnCalls {

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
    void should_CountAvailablePlaces_When_CalledMultipleTimes() {
        // given
        List<Room> rooms = Arrays.asList(
                new Room("Room 1", 5)
        );
        when(this.roomServiceMock.getAvailableRooms())
                .thenReturn(rooms)
                .thenReturn(Collections.emptyList());
        int expFirst = 5;
        int expSecond = 0;

        // when
        int actFirst = bookingService.getAvailablePlaceCount();
        int actSecond = bookingService.getAvailablePlaceCount();

        // then
        assertAll(
                () -> assertEquals(expFirst, actFirst),
                () -> assertEquals(expSecond, actSecond)
        );
    }


}
