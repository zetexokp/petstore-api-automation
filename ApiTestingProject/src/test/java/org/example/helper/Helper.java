package org.example.helper;

import model.Order;
import model.Pet;

import java.util.List;
import java.util.Random;

public class Helper {

    private static final Random RANDOM = new Random();

    // ================= PET =================

    public static Pet createDefaultPet() {
        return new Pet(
                RANDOM.nextLong(1000, 10000),
                new Pet.Category(
                        1L,
                        "DINOZAUR"
                ),
                "Kuzia",
                List.of("UrlPhoto1", "UrlPhoto2"),
                List.of(
                        new Pet.Tag(1L, "friendly1"),
                        new Pet.Tag(2L, "friendly2"),
                        new Pet.Tag(3L, "friendly3"),
                        new Pet.Tag(4L, "friendly4"),
                        new Pet.Tag(5L, "friendly5"),
                        new Pet.Tag(6L, "friendly6")
                ),
                "available"
        );
    }

    // ================= ORDER =================

    public static Order createDefaultOrder() {
        return new Order(
                RANDOM.nextLong(1000, 10000),
                RANDOM.nextLong(1000, 10000),
                RANDOM.nextInt(1, 10),
                "2026-06-12T06:44:07.930Z",
                "placed",
                true
        );
    }
}