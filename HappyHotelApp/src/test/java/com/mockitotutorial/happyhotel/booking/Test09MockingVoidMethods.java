package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.internal.stubbing.answers.DoesNothing;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class Test09MockingVoidMethods {

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
    void should_ThrowException_when_MailNotReady() {
        // given
        BookingRequest bookingRequest =
                new BookingRequest("1", LocalDate.of(2020, 1, 1),
                        LocalDate.of(2020, 1, 5), 2, false);

        // doThrow 指定在某物件執行某方法後，拋出指定異常
        doThrow(new BusinessException()).when(mailSenderMock).sendBookingConfirmation(any());

        // when
        Executable executable = () -> bookingService.makeBooking(bookingRequest);

        // then
        assertThrows(BusinessException.class, executable);
    }

    @Test
    void should_NotThrowException_when_MailNotReady() {
        // given
        BookingRequest bookingRequest =
                new BookingRequest("1", LocalDate.of(2020, 1, 1),
                        LocalDate.of(2020, 1, 5), 2, false);

        // doNothing 指定在某物件執行某方法
        doNothing().when(mailSenderMock).sendBookingConfirmation(any());
//        doThrow(new BusinessException()).when(mailSenderMock).sendBookingConfirmation(any());

        // when
        Executable executable = () -> bookingService.makeBooking(bookingRequest);

        // then
        assertDoesNotThrow(executable);

    }
}
