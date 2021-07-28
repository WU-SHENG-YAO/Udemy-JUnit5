package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.LenientStubber;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class Test13StrictSubbing {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private PaymentService paymentServiceMock;
    @Mock
    private RoomService roomServiceMock;
    @Spy
    private BookingDAO bookingDAOMock;
    @Mock
    private MailSender mailSenderMock;
    @Captor
    private ArgumentCaptor<Double> doubleCaptor;

    @Test
    void should_InvokePayment_When_Prepaid() {
        // given
        BookingRequest bookingRequest =
                new BookingRequest("1", LocalDate.of(2020, 1, 1),
                        LocalDate.of(2020, 1, 5), 2, false);
//        given(paymentServiceMock.pay(any(), anyDouble())).willReturn("1");
        lenient().when(paymentServiceMock.pay(any(), anyDouble())).thenReturn("1");
        // when
        bookingService.makeBooking(bookingRequest);

        // then
        // no exception is thrown


    }




}
