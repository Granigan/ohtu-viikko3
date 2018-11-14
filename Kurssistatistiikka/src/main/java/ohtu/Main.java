package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import org.apache.http.client.fluent.Request;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Main {

    public static void main(String[] args) throws IOException {
        // ÄLÄ laita githubiin omaa opiskelijanumeroasi
        String studentNr = "012345678";
        if (args.length > 0) {
            studentNr = args[0];
        }

        Gson mapper = new Gson();

        String url = "https://studies.cs.helsinki.fi/courses/students/" + studentNr + "/submissions";
        String bodyText = Request.Get(url).execute().returnContent().asString();

        Submission[] subs = mapper.fromJson(bodyText, Submission[].class);

        url = "https://studies.cs.helsinki.fi/courses/courseinfo";
        bodyText = Request.Get(url).execute().returnContent().asString();

//        System.out.println("json-muotoinen data:");
//        System.out.println( bodyText );
        Course[] courses = mapper.fromJson(bodyText, Course[].class);

        int exercisesDone = 0;
        int hoursTotal = 0;
        int subsTotal = 0;

        System.out.println("Opiskelijanumero " + studentNr + "\n\n");

        for (Course course : courses) {
            System.out.println(course.toString() + "\n");

            for (Submission sub : subs) {
                if (course.getName().equals(sub.getCourse())) {
                    exercisesDone += sub.getExercises().length;
                    hoursTotal += sub.getHours();
                    
                    System.out.println("Viikko " + sub.getWeek() + ":");
                    System.out.println("\tTehtyjä tehtäviä "
                            + sub.getExercises().length + "/"
                            + course.getExercises()[sub.getWeek()] + ". Aikaa kului "
                            + sub.getHours() + "h. Tehdyt tehtävät: "
                            + sub.listTasks());
                }
            }
            System.out.println("\nYhteensä: " + exercisesDone + "/"
                    + course.exerciseCount() + " tehtävää ja " + hoursTotal
                    + " tuntia.\n");
            exercisesDone = 0;
            hoursTotal = 0;

            String courseUrl = "https://studies.cs.helsinki.fi/courses/" + course.getName() + "/stats";
            String courseText = Request.Get(courseUrl).execute().returnContent().asString();

            JsonParser parser = new JsonParser();
            JsonObject parsittuData = parser.parse(courseText).getAsJsonObject();

            for (int i = 1; i < 20; i++) { // 20 AS MAX NUMBER OF WEEKS
                if (parsittuData.getAsJsonObject("" + i) != null) {
                    JsonObject week = parsittuData.getAsJsonObject("" + i);
                    exercisesDone += week.get("exercise_total").getAsInt();
                    hoursTotal += week.get("hour_total").getAsInt();
                    subsTotal += week.get("students").getAsInt();
                }
            }

            System.out.println("Kurssilla yhteensä " + subsTotal
                    + " palautusta, palautettuja tehtäviä " + exercisesDone
                    + " kpl, aikaa käytetty yhteensä " + hoursTotal
                    + " tuntia.\n\n");
            
            exercisesDone = 0;
            hoursTotal = 0;
            subsTotal = 0;
            
        }
    }
}
