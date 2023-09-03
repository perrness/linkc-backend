package com.linkc.linkcbackend.services;

import com.linkc.linkcbackend.domain.Box;
import com.linkc.linkcbackend.domain.BoxData;
import com.linkc.linkcbackend.domain.BoxStripped;
import com.linkc.linkcbackend.repository.BoxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoxService {
    @Autowired
    private final BoxRepository boxRepository;

    public BoxService(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }

    private List<BoxStripped> getAllBoxes() {
        List<Box> allBoxes = boxRepository.findAll();

        List<BoxStripped> allBoxesData = new ArrayList<>();

        allBoxes.forEach(box -> {
            allBoxesData.add(
                new BoxStripped.builder()
                        .id(box.getId())
                        .location(box.getLocation())
                        .status(box.getStatus())
                        .build()
            );
        });

        return allBoxesData;
    }

    public BoxData getBoxes(String userId) {
        List<Box> boxesReservedByUser = boxRepository.findBoxReservedByUserId(userId);
        List<Box> boxesReservedForUser = boxRepository.findBoxReservedForUserId(userId);

        return new BoxData(getAllBoxes(), boxesReservedByUser, boxesReservedForUser);
    }
}
