package com.example.rdfcarrentals.util;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClockUtil {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy - MM - dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh : mm : ss a");

    public static void startClock(Label lblDate, Label lblTime) {
        lblDate.setText(LocalDateTime.now().format(DATE_FORMATTER));

        Timeline clock = new Timeline(
                new KeyFrame(Duration.ZERO, event -> {
                    lblTime.setText(LocalDateTime.now().format(TIME_FORMATTER));
                }),
                new KeyFrame(Duration.seconds(1))
        );

        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
}

