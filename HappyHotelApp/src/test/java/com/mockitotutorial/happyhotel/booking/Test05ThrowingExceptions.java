package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

public class Test05ThrowingExceptions {

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
    void should_ThrowException_when_NoRoomAvailable() {
        // given
        BookingRequest bookingRequest =
                new BookingRequest("1", LocalDate.of(2020, 1, 1),
                        LocalDate.of(2020, 1, 5), 2, false);

        BookingRequest bookingRequest2 =
                new BookingRequest("2", LocalDate.of(2020, 1, 1),
                        LocalDate.of(2020, 1, 5), 2, false);

        // make an exception result after methodCalled
        when(this.roomServiceMock.findAvailableRoomId(bookingRequest))
                .thenThrow(BusinessException.class);
        // when
        Executable executable = () -> bookingService.makeBooking(bookingRequest2);

        // then
        assertThrows(BusinessException.class, executable);



    }
}
