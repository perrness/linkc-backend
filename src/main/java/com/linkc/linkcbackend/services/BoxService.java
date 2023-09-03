package com.linkc.linkcbackend.services;

import com.linkc.linkcbackend.domain.Box;
import com.linkc.linkcbackend.domain.User;
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

    public List<Box> getBoxes(String userId) {
        List<Box> boxesReservedByUser = boxRepository.findBoxReservedByUserId(userId);
        List<Box> boxesReservedForUser = boxRepository.findBoxReservedForUserId(userId);

        List<Box> combinedList = new ArrayList<>();
        combinedList.addAll(boxesReservedForUser);
        combinedList.addAll(boxesReservedByUser);
        return combinedList;
    }
}
