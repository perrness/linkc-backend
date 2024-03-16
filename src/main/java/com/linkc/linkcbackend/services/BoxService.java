package com.linkc.linkcbackend.services;

import com.linkc.linkcbackend.domain.*;
import com.linkc.linkcbackend.repository.BoxHistoryRepository;
import com.linkc.linkcbackend.repository.BoxRepository;
import com.linkc.linkcbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class BoxService {
    @Autowired
    private final BoxRepository boxRepository;
    private final BoxHistoryRepository boxHistoryRepository;
    private final UserRepository userRepository;
    private final ParqioService parqioService;

    public BoxService(BoxRepository boxRepository, UserRepository userRepository, ParqioService parqioService, BoxHistoryRepository boxHistoryRepository) {
        this.userRepository = userRepository;
        this.boxRepository = boxRepository;
        this.parqioService = parqioService;
        this.boxHistoryRepository = boxHistoryRepository;
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
        List<Box> boxesReservedForUser = boxRepository.findBoxToBeOpenedByUserId(userId);

        return new BoxData(getAllBoxes(), boxesReservedByUser, boxesReservedForUser);
    }

    public void createBox(Box box) {
        boxRepository.insert(box);
    }

    public void reserveBox(User user, Box box, BoxReserveRequest boxReserveRequest) throws Exception {
        if (box.getStatus() != Status.available) {
            throw new Exception("Box is not available");
        }

        Optional<User> otherUser = userRepository.findByNumber(boxReserveRequest.getToBeOpenedBy());

        if (otherUser.isEmpty()) {
            throw new Exception("Reserved to is not a registered user");
        }

        box.setReservedBy(user.getId());
        box.setToBeOpenedBy(otherUser.get().getId());
        box.setStatus(Status.unavailable);
        box.setTimeBooked(LocalDateTime.now().toString());

        boxRepository.save(box);
    }

    public void openBox(User user, Box box) throws Exception {
        if (!user.getId().equalsIgnoreCase(box.getReservedBy()) && !user.getId().equalsIgnoreCase(box.getToBeOpenedBy())) {
            throw new Exception("User can not open this box");
        }

        parqioService.openBox();

        boxRepository.save(box);
        BoxHistory boxHistory = new BoxHistory();
        boxHistory.setBoxId(box.getId());
        boxHistory.setReservedBy(box.getReservedBy());
        boxHistory.setOpenedBy(user.getId());
        boxHistory.setOpenedTime(LocalDateTime.now().toString());
        boxHistory.setReservedTime(box.getTimeBooked());

        boxHistoryRepository.save(boxHistory);
    }

    public void closeReservation(User user, Box box) throws Exception{
        if (!user.getId().equalsIgnoreCase(box.getReservedBy()) && !user.getId().equalsIgnoreCase(box.getToBeOpenedBy())) {
            throw new Exception("User can not do this");
        }

        box.setStatus(Status.available);
        box.setReservedBy("");
        box.setToBeOpenedBy("");
        box.setTimeBooked("");

        boxRepository.save(box);
    }
}
