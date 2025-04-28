// ProgramTracker Class
// This class will help calculate the progress of the user based on their workouts and goals and generate reports

// Import Libraries
import java.io.Serializable;
import java.time.LocalDateTime;

public class ProgressTracker implements Serializable {
    private static final long serialVersionUID = 1L;

    // Calculate and return in order most common workout types in a given range
    public String[] getMostCommonWorkoutTypes(Workout[] workouts, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        int[] typeCount = new int[Workout.WORKOUT_TYPES.length];
        for (Workout workout : workouts) {
            if (workout.getDateTime().isAfter(startDateTime) && workout.getDateTime().isBefore(endDateTime)) {
                for (int i = 0; i < Workout.WORKOUT_TYPES.length; i++) {
                    if (workout.getType().equals(Workout.WORKOUT_TYPES[i])) {
                        typeCount[i]++;
                    }
                }
            }
        }

        // Sort the workout types based on their counts
        String[] sortedTypes = new String[Workout.WORKOUT_TYPES.length];
        for (int i = 0; i < Workout.WORKOUT_TYPES.length; i++) {
            sortedTypes[i] = Workout.WORKOUT_TYPES[i] + ": " + typeCount[i];
        }
        return sortedTypes;
    }

    // Calculate the total duration of workouts in a given range
    public int getTotalWorkoutDurationInRange(Workout[] workouts, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        int totalDuration = 0;
        for (Workout workout : workouts) {
            if (workout.getDateTime().isAfter(startDateTime) && workout.getDateTime().isBefore(endDateTime)) {
                totalDuration += workout.getDuration();
            }
        }
        return totalDuration;
    }
    // Calculate and return the average duration of workouts in a given range
    public double getAverageWorkoutDurationInRange(Workout[] workouts, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        int totalDuration = 0;
        int count = 0;
        for (Workout workout : workouts) {
            if (workout.getDateTime().isAfter(startDateTime) && workout.getDateTime().isBefore(endDateTime)) {
                totalDuration += workout.getDuration();
                count++;
            }
        }
        return count == 0 ? 0 : (double) totalDuration / count;
    }


    // Calculate and return the total calories burned in a given range
    public int getTotalCaloriesBurnedInRange(Workout[] workouts, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        int totalCalories = 0;
        for (Workout workout : workouts) {
            if (workout.getDateTime().isAfter(startDateTime) && workout.getDateTime().isBefore(endDateTime)) {
                totalCalories += workout.getCaloriesBurned();
            }
        }
        return totalCalories;
    }
    // Calculate and return the average calories burned per day in a given range
    public double getAverageCaloriesBurnedInRange(Workout[] workouts, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        int totalCalories = 0;
        for (Workout workout : workouts) {
            if (workout.getDateTime().isAfter(startDateTime) && workout.getDateTime().isBefore(endDateTime)) {
                totalCalories += workout.getCaloriesBurned();
            }
        }
        long daysInRange = java.time.Duration.between(startDateTime.toLocalDate().atStartOfDay(), endDateTime.toLocalDate().atStartOfDay()).toDays() + 1;
        return daysInRange == 0 ? 0 : (double) totalCalories / daysInRange;
    }

    // Number of steps taken in a given range
    public int getStepsTakenInRange(Workout[] workouts, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        int totalSteps = 0;
        for (Workout workout : workouts) {
            if (workout.getDateTime().isAfter(startDateTime) && workout.getDateTime().isBefore(endDateTime)) {
                totalSteps += workout.getSteps();
            }
        }
        return totalSteps;
    }
    // Calculate and return the average steps taken in a given range
    public double getAverageStepsTakenInRange(Workout[] workouts, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        int totalSteps = 0;
        for (Workout workout : workouts) {
            if (workout.getDateTime().isAfter(startDateTime) && workout.getDateTime().isBefore(endDateTime)) {
                totalSteps += workout.getSteps();
            }
        }
        long daysInRange = java.time.Duration.between(startDateTime.toLocalDate().atStartOfDay(), endDateTime.toLocalDate().atStartOfDay()).toDays() + 1;
        return daysInRange == 0 ? 0 : (double) totalSteps / daysInRange;
    }

    // Calculate and return the total number of workouts in a given range
    public int getTotalWorkoutsInRange(Workout[] workouts, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        int count = 0;
        for (Workout workout : workouts) {
            if (workout.getDateTime().isAfter(startDateTime) && workout.getDateTime().isBefore(endDateTime)) {
                count++;
            }
        }
        return count;
    }
    // Calculate and return the average number of workouts in a given range
    public double getAverageWorkoutsInRange(Workout[] workouts, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        int count = 0;
        for (Workout workout : workouts) {
            if (workout.getDateTime().isAfter(startDateTime) && workout.getDateTime().isBefore(endDateTime)) {
                count++;
            }
        }
        long daysInRange = java.time.Duration.between(startDateTime.toLocalDate().atStartOfDay(), endDateTime.toLocalDate().atStartOfDay()).toDays() + 1;
        return daysInRange == 0 ? 0 : (double) count / daysInRange;
    }

    // Calculate water intake in a given range
    public double getWaterIntakeInRange(Water[] waterIntakes, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        double totalWater = 0;
        for (Water water : waterIntakes) {
            if (water.getDateTime().isAfter(startDateTime) && water.getDateTime().isBefore(endDateTime)) {
                totalWater += water.getAmount();
            }
        }
        return totalWater;
    }
    // Calculate and return the average water intake in a given range
    public double getAverageWaterIntakeInRange(Water[] waterIntakes, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        double totalWater = 0;
        for (Water water : waterIntakes) {
            if (water.getDateTime().isAfter(startDateTime) && water.getDateTime().isBefore(endDateTime)) {
                totalWater += water.getAmount();
            }
        }
        long daysInRange = java.time.Duration.between(startDateTime.toLocalDate().atStartOfDay(), endDateTime.toLocalDate().atStartOfDay()).toDays() + 1;
        return daysInRange == 0 ? 0 : totalWater / daysInRange;
    }

    // Calculate and return the total sleep minutes in a given range
    public int getTotalSleepMinutesInRange(Sleep[] sleepRecords, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        int totalSleep = 0;
        for (Sleep sleep : sleepRecords) {
            if (sleep.getDuration() > 0 && sleep.getDateTime().isAfter(startDateTime) && sleep.getDateTime().isBefore(endDateTime)) {
                totalSleep += sleep.getDuration();
            }
        }
        return totalSleep;
    }
    // Calculate and return the average sleep minutes in a given range
    public double getAverageSleepMinutesInRange(Sleep[] sleepRecords, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        int totalSleep = 0;
        for (Sleep sleep : sleepRecords) {
            if (sleep.getDuration() > 0 && sleep.getDateTime().isAfter(startDateTime) && sleep.getDateTime().isBefore(endDateTime)) {
                totalSleep += sleep.getDuration();
            }
        }
        long daysInRange = java.time.Duration.between(startDateTime.toLocalDate().atStartOfDay(), endDateTime.toLocalDate().atStartOfDay()).toDays() + 1;
        return daysInRange == 0 ? 0 : (double) totalSleep / daysInRange;
    }

    // Calculate the avrage weight in a given range
    public double getAverageWeightInRange(Weight[] weights, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        double totalWeight = 0;
        int count = 0;
        for (Weight weight : weights) {
            if (weight.getDateTime().isAfter(startDateTime) && weight.getDateTime().isBefore(endDateTime)) {
                totalWeight += weight.getLogedWeight();
                count++;
            }
        }
        return count == 0 ? 0 : totalWeight / count;
    }
    // Calculate the average weight change in a given range
    public double getWeightChangeInRange(Weight[] weights, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        double initialWeight = Double.MAX_VALUE;
        double finalWeight = Double.MIN_VALUE;
        for (Weight weight : weights) {
            if (weight.getDateTime().isAfter(startDateTime) && weight.getDateTime().isBefore(endDateTime)) {
                if (weight.getLogedWeight() < initialWeight) {
                    initialWeight = weight.getLogedWeight();
                }
                if (weight.getLogedWeight() > finalWeight) {
                    finalWeight = weight.getLogedWeight();
                }
            }
        }
        return finalWeight - initialWeight;
    }
    
}