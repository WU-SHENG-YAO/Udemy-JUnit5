package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class Test12BDD {

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
    void should_CountAvailablePlaces_When_OneRoomAvailable() {
        // given
        given(this.roomServiceMock.getAvailableRooms())
                .willReturn(Collections.singletonList(new Room("Room 1", 5)));
        int exp = 5;

        // when
        int act = bookingService.getAvailablePlaceCount();

        // then
        assertEquals(exp, act);

    }

    @Test
    void should_InvokePayment_When_Prepaid() {
        // given
        BookingRequest bookingRequest =
                new BookingRequest("1", LocalDate.of(2020, 1, 1),
                        LocalDate.of(2020, 1, 5), 2, true);

        // when
        bookingService.makeBooking(bookingRequest);

        // then
        then(paymentServiceMock).should(times(1)).pay(bookingRequest, 400.0);
        verifyNoMoreInteractions(paymentServiceMock);


    }




}
