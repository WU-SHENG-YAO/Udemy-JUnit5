package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

public class Test06Matchers {

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
    void should_NotCompleteBooking_when_PriceTooHigh() {
        // given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 5), 2, true);

        when(this.paymentServiceMock.pay(any(), eq(400.0))).thenThrow(BusinessException.class);

        // when
        Executable executable = () -> bookingService.makeBooking(bookingRequest);

        // then
        assertThrows(BusinessException.class, executable);


    }
}
