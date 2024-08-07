package com.mccayl.model;

import com.mccayl.model.enums.Action;
import com.mccayl.model.enums.Side;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {
    String userId;
    String clorderId;
    Action action;
    Integer instrumentId;
    Side side;
    Long price;
    Integer amount;
    Integer amountRest;
    public Book(String s) {
        String[] data = s.split(";");
        this.userId = data[0];
        this.clorderId = data[1];
        this.action = Action.values()[Integer.parseInt(data[2])];
        this.instrumentId = Integer.parseInt(data[3]);
        this.side = Side.valueOf(data[4]);
        this.price = Long.parseLong(data[5]);
        this.amount = Integer.parseInt(data[6]);
        this.amountRest = Integer.parseInt(data[7]);
    }
}
