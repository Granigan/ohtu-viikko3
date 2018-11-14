package ohtu;

public class Submission {
    private int week;
    private int hours;
    private int[] exercises;
    private String course;

    public void setWeek(int week) {
        this.week = week;
    }

    public int getWeek() {
        return week;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getHours() {
        return hours;
    }

    public void setExercises(int[] exercises) {
        this.exercises = exercises;
    }

    public int[] getExercises() {
        return exercises;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCourse() {
        return course;
    }

    private String listTasks() {
        String list = "";
        for(int i : exercises) {
            list += "" + i + ", ";
        }
        return list.substring(0, list.length()-2) + ".";
    }
    
    @Override
    public String toString() {
        return "Kurssin " + course + ", viikolla " + week + " tehtyjä tehtäviä: " 
                + exercises.length + ". Aikaa kului " + hours 
                + " tuntia. Tehdyt tehtävät: " + listTasks();
    }
    
}