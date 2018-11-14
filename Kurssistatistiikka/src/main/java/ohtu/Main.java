package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import org.apache.http.client.fluent.Request;

public class Main {

    public static void main(String[] args) throws IOException {
        // ÄLÄ laita githubiin omaa opiskelijanumeroasi
        String studentNr = "012345678";
        if ( args.length>0) {
            studentNr = args[0];
        }

        String url = "https://studies.cs.helsinki.fi/courses/students/"+studentNr+"/submissions";

        String bodyText = Request.Get(url).execute().returnContent().asString();

//        System.out.println("json-muotoinen data:");
//        System.out.println( bodyText );

        Gson mapper = new Gson();
        Submission[] subs = mapper.fromJson(bodyText, Submission[].class);
        
        int exercisesTotal = 0;
        int hoursTotal = 0;
        
        System.out.println("Opiskelijanumeron " + studentNr + " suoritukset:\n");
        for (Submission submission : subs) {
            System.out.println(submission);
            exercisesTotal += submission.getExercises().length;
            hoursTotal += submission.getHours();
        }
        System.out.println("\nYhteensä " + exercisesTotal + " tehtävää ja " 
            + hoursTotal + " tuntia.");

    }
}