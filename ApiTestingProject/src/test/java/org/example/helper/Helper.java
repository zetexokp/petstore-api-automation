package org.example.helper;
import java.util.Random;

import model.Pet;
import java.util.List;

public class Helper {

    public static Pet createDefaultPet() {
        return new Pet(
                new Random().nextInt(8999) + 1000,
                new Pet.Category(
                        1,
                        "DINOZAUR"),
                "Kuzia", List.of(
                        "UrlPhoto1",
                        "UrlPhoto2"
        ), List.of(
                new Pet.Tag(1, "friendly1"),
                new Pet.Tag(2, "friendly2"),
                new Pet.Tag(3, "friendly3"),
                new Pet.Tag(4, "friendly4"),
                new Pet.Tag(5, "friendly5"),
                new Pet.Tag(6, "friendly6")
        ), "available");
    }
}