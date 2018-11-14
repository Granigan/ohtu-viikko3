package ohtu;

public class Course {

    private String name;
    private String fullName;
    private int[] exercises;
    private String term;
    private int year;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setExercises(int[] exercises) {
        this.exercises = exercises;
    }

    public int[] getExercises() {
        return exercises;
    }

    public int exerciseCount() {
        int count = 0;
        for (int c : exercises) {
            count += c;
        }
        return count;
    }

    @Override
    public String toString() {
        return fullName + " " + term + " " + year;
    }

}
