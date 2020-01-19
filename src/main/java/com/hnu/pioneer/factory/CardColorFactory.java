package com.hnu.pioneer.factory;

import com.hnu.pioneer.domain.CardColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardColorFactory {

    public static List<String> getColorListBySize(int listSize) {
        List<String> colorFactory = getColorList();
        List<String> colorList = new ArrayList<>(listSize);
        for (int i = 0; i < listSize; i++) {
            colorList.add(
                    colorFactory.get(
                            getRandomNumber(colorFactory.size())));
        }
        return colorList;
    }

    private static int getRandomNumber(int max) {
        Random random = new Random(System.currentTimeMillis());
        return random.nextInt(max);
    }

    private static List<String> getColorList() {
        CardColor[] colors = CardColor.values();
        List<String> colorList = new ArrayList<>(colors.length);
        for (CardColor color : colors) {
            colorList.add(color.getColor());
        }
        return colorList;
    }
}
