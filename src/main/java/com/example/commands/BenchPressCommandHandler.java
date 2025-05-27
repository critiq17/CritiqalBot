package com.example.commands;

import com.example.entity.BenchPressLog;
import com.example.repository.BenchPressRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class BenchPressCommandHandler {

    @Inject
    BenchPressRepository benchPressRepository;

    public String handle(String[] args, Long userId) {
        if(args.length != 2) {
            return "Use format: /bench_press <weight> <reps>";
        }

        try {
            int weight = Integer.parseInt(args[0]);
            int reps = Integer.parseInt(args[1]);

            int oneMaxRep = (int) (weight * (1 + (double) reps / 30.0));

            BenchPressLog log = new BenchPressLog();
            log.telegramUserId = userId;
            log.weight = weight;
            log.reps = reps;
            log.oneMaxRep = oneMaxRep;

            return "Logged: " + weight + "kg for " + reps + " reps\n" +
                    "Your one-rep max: " + oneMaxRep + "kg";
        } catch (NumberFormatException e) {
            return "Use only number";
        }
    }
}
