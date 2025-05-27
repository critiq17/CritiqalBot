package com.example.commands;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BjuCommanderHandler {

    public String handle(String[] args, Long chatId) {
        if(args.length != 2) {
            return "Correct format: /bju <your weight> <mass/cut>";
        }

        try {
            double weight = Double.parseDouble(args[0]);
            String goal = args[1].toLowerCase();

            double protein, fat, carbs;

            switch (goal) {
                case "mass":
                    protein = weight * 2.0;
                    fat = weight * 1.0;
                    carbs = weight * 5.0;
                    break;
                case "cut":
                    protein = weight * 2.2;
                    fat = weight * 0.8;
                    carbs = weight * 2.0;
                    break;
                default:
                    return "Use mass or cut";
            }

            int kcal = (int) (protein * 4 + fat * 9 + carbs * 4);

            return String.format(
                    "Bju for %s (%s kg):\n Protein: %.1f g\n Fat: %.1f g\n Carbs: %.1f g\n Kcal: %d kcal",
                    goal.equals("mass") ? "mass" : "cut",
                    weight, protein, fat, carbs, kcal
            );
        } catch (NumberFormatException e) {
            return "Weight number";
        }
    }
}
