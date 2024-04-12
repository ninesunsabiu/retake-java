class AnnalynsInfiltration {
    public static boolean canFastAttack(boolean knightIsAwake) {
        return !knightIsAwake;
    }

    public static boolean canSpy(boolean knightIsAwake, boolean archerIsAwake, boolean prisonerIsAwake) {
        return knightIsAwake || archerIsAwake || prisonerIsAwake;
    }

    public static boolean canSignalPrisoner(boolean archerIsAwake, boolean prisonerIsAwake) {
        return prisonerIsAwake && !archerIsAwake;
    }

    public static boolean canFreePrisoner(boolean knightIsAwake, boolean archerIsAwake, boolean prisonerIsAwake, boolean petDogIsPresent) {
        return
         // - If Annalyn has her pet dog with her she can rescue the prisoner if the archer is asleep.
         //   The knight is scared of the dog and the archer will not have time to get ready before Annalyn and the prisoner can escape.
         (petDogIsPresent && !archerIsAwake) ||
         // - If Annalyn does not have her dog then she and the prisoner must be very sneaky!
         //    Annalyn can free the prisoner if the prisoner is awake and the knight and archer are both sleeping, but if the prisoner is sleeping they can't be rescued: the prisoner would be startled by Annalyn's sudden appearance and wake up the knight and archer.
         (!petDogIsPresent && prisonerIsAwake && !knightIsAwake && !archerIsAwake);
    }

}