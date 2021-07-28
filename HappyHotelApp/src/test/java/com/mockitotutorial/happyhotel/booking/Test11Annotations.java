package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class Test11Annotations {

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
    void should_PayCorrectPrice_When_InputOK() {
        // given
        BookingRequest bookingRequest =
                new BookingRequest("1", LocalDate.of(2020, 1, 1),
                        LocalDate.of(2020, 1, 5), 2, true);

        // when
        bookingService.makeBooking(bookingRequest);

        // then
        assertAll(
                () -> verify(paymentServiceMock).pay(bookingRequest, 400.0),
                () -> {
                    verify(paymentServiceMock, times(1))
                            .pay(eq(bookingRequest), doubleCaptor.capture());
                    double captureArgument = doubleCaptor.getValue();
                    System.out.println(captureArgument);
                    assertEquals(400.0, captureArgument);
                }
        );
    }

    @Test
    void should_PayCorrectPrices_When_MultipleCalls() {
        // given
        BookingRequest bookingRequest =
                new BookingRequest("1", LocalDate.of(2020, 1, 1),
                        LocalDate.of(2020, 1, 5), 2, true);
        BookingRequest bookingRequest2 =
                new BookingRequest("1", LocalDate.of(2020, 1, 1),
                        LocalDate.of(2020, 1, 2), 2, true);

        List<Double> expectedValues = Arrays.asList(400.0, 100.0);
        // when
        bookingService.makeBooking(bookingRequest);
        bookingService.makeBooking(bookingRequest2);

        // then

        verify(paymentServiceMock, times(2)).pay(any(), doubleCaptor.capture());
        List<Double> captureArgument = doubleCaptor.getAllValues();
        System.out.println(captureArgument);
        assertEquals(expectedValues, captureArgument);

    }


}
