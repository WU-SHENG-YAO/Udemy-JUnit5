package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class Test14StaticMethods {

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
    void should_CalculateCorrectPrice() {
        try (MockedStatic<CurrencyConverter> mockedConverter
                     = mockStatic(CurrencyConverter.class)){
            // given
            BookingRequest bookingRequest =
                    new BookingRequest("1", LocalDate.of(2020, 1, 1),
                            LocalDate.of(2020, 1, 5), 2, false);

            double expected = 400.0;
            mockedConverter.when(() -> CurrencyConverter.toEuro(anyDouble()))
                    .thenReturn(400.0);
            // when
            double actual = bookingService.calculatePriceEuro(bookingRequest);

            // then
            assertEquals(expected, actual);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }




}
