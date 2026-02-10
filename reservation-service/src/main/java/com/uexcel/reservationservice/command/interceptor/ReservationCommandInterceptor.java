package com.uexcel.reservationservice.command.interceptor;

import com.uexcel.reservationservice.command.PaymentUpdateCommand;
import com.uexcel.reservationservice.command.entity.Reservation;
import com.uexcel.reservationservice.command.repository.ReservationRepository;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.function.BiFunction;

@Component
public class ReservationCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {
     private  final ReservationRepository reservationRepository;

    public ReservationCommandInterceptor(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public @NonNull BiFunction<Integer, CommandMessage<?>,
            CommandMessage<?>> handle(@NonNull List<? extends CommandMessage<?>> messages) {
        return (index,command)->{
            if(command.getPayload() instanceof PaymentUpdateCommand paymentUpdateCommand){
                Reservation reservation = reservationRepository
                        .findByReservationId(paymentUpdateCommand.getReservationId());

                if(reservation == null) {
                    throw new CommandExecutionException("The reservation does not exist!", null);
                }

                if(LocalDate.now().isAfter(reservation.getBookedDate())){
                    throw new CommandExecutionException("The reservation has expired and due for removal!.",null);
                }
            }
            return command;
        };
    }
}
