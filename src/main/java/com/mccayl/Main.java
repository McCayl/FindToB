package com.mccayl;

import com.mccayl.model.Book;
import com.mccayl.dto.OutputDTO;
import com.mccayl.model.enums.Side;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        String[] data = new String[]{
                "A;1;0;55;B;100;2;2",
                "A;2;0;55;B;101;3;3",
                "B;1;0;55;B;99;10;10",
                "A;2;1;55;B;101;3;0;5",
                "B;2;0;55;B;100;3;3",
                "A;1;2;55;B;100;1;1",
                "A;1;0;55;S;100;2;2",
        };
        // instrumentId -> [pricesForBuy, pricesForSale]
        Map<Integer, TreeSet<OutputDTO>[]> map = new HashMap<>(); //  0 - for buy | 1 - for sale
        TreeSet<OutputDTO> pricesB, pricesS;
        TreeSet<OutputDTO>[] prices;

        for (String str : data) {
            Book curr = new Book(str);

            if (!map.containsKey(curr.getInstrumentId())) {
                OutputDTO c = new OutputDTO(curr);
                Comparator<OutputDTO> buy = Comparator.comparingLong(OutputDTO::getPrice);
                Comparator<OutputDTO> sell = Comparator.comparingLong(OutputDTO::getPrice)
                        .reversed();
                pricesB = new TreeSet<>(buy);
                pricesS = new TreeSet<>(sell);

                if (c.getSide().equals(Side.B)) {
                    pricesB.add(c);
                } else {
                    pricesS.add(c);
                }

                prices = new TreeSet[]{pricesB, pricesS};
                map.put(c.getInstrumentId(), prices);
                System.out.println(c);
                continue;
            }

            prices = map.get(curr.getInstrumentId());

            if (curr.getSide().equals(Side.B)) {
                pricesB = prices[0];
                findTob(curr, pricesB);
            } else {
                pricesS = prices[1];
                findTob(curr, pricesS);
            }
        }
    }
    public static OutputDTO findInSetByPrice(Set<OutputDTO> set,
                                             Long price) {
        return set.stream()
                .filter(outputDTO -> outputDTO.getPrice().equals(price))
                .findFirst()
                .get();
    }

    public static void findTob(Book book,
                               TreeSet<OutputDTO> prices) {
        OutputDTO curr = new OutputDTO(book);

        // if the set is empty it literally
        // sets the top price
        if (prices.size() == 0) {
            prices.add(curr);
            System.out.println(curr);
            return;
        }

        OutputDTO top = prices.last();
        Long topPrice = top.getPrice(), currPrice = curr.getPrice();

        switch (book.getAction()) {
            case GET -> {
                if (currPrice.equals(topPrice)) {
                    prices.remove(top);
                    top = prices.last();
                    System.out.println(top);
                } else {
                    curr = findInSetByPrice(prices, book.getPrice());
                    prices.remove(curr);
                }
            }
            case SET -> {
                if (currPrice > topPrice) {
                    prices.add(curr);
                    System.out.println(curr);
                } else if (currPrice.equals(topPrice)) {
                    Integer amount = book.getAmount() + top.getAmount();
                    top.setAmount(amount);
                    System.out.println(top);
                } else {
                    prices.add(curr);
                }
            }
            case SELL -> {
                if (currPrice.equals(topPrice)) {
                    Integer amount = top.getAmount() - book.getAmount();
                    top.setAmount(amount);
                    System.out.println(top);
                } else {
                    curr = findInSetByPrice(prices, book.getPrice());
                    Integer amount = curr.getAmount() - book.getAmount();
                    curr.setAmount(amount);
                }
            }
        }
    }
}