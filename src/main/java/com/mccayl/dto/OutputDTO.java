package com.mccayl.dto;

import com.mccayl.model.Book;
import com.mccayl.model.enums.Side;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OutputDTO {
    Integer instrumentId;
    Side side;
    Long price;
    Integer amount;
    public OutputDTO(Book book) {
        this.instrumentId = book.getInstrumentId();
        this.side = book.getSide();
        this.price = book.getPrice();
        this.amount = book.getAmount();
    }

    @Override
    public String toString() {
        return String.format("%d;%s;%d;%d",
                instrumentId, side.name(), price, amount);
    }
}
