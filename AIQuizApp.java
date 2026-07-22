import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class AIQuizApp {
    static final String API_KEY = System.getenv("GROQ_API_KEY");
    static final String MODEL = "llama-3.1-8b-instant";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Groq Java Quiz Generator");
        System.out.print("Enter Java topic: ");
        String topic = input.nextLine();

        String quiz = generateQuiz(topic);

        String[] questions = new String[3];
        String[][] options = new String[3][4];
        String[] answers = new String[3];

        try {
            readQuizText(quiz, questions, options, answers);
        } catch (Exception e) {
            System.out.println("AI gave an unexpected format, so demo questions will be used.");
            readQuizText(demoQuiz(topic), questions, options, answers);
        }

        int score = 0;

        for (int i = 0; i < questions.length; i++) {
            System.out.println();
            System.out.println("Question " + (i + 1));
            System.out.println(questions[i]);
            System.out.println("A. " + options[i][0]);
            System.out.println("B. " + options[i][1]);
            System.out.println("C. " + options[i][2]);
            System.out.println("D. " + options[i][3]);

            System.out.print("Your answer A/B/C/D: ");
            String userAnswer = input.nextLine().toUpperCase();

            if (userAnswer.equals("A") || userAnswer.equals("B") ||
                    userAnswer.equals("C") || userAnswer.equals("D")) {

                if (userAnswer.equals(answers[i])) {
                    System.out.println("Correct!");
                    score++;
                } else {
                    System.out.println("Wrong. Correct answer is " + answers[i]);
                }

            } else {
                System.out.println("Invalid answer. Marked wrong.");
            }
        }

        System.out.println();
        System.out.println("Final score: " + score + " out of " + questions.length);

        input.close();
    }

    static String generateQuiz(String topic) {
        if (API_KEY == null || API_KEY.isEmpty()) {
            System.out.println("Set the GROQ_API_KEY environment variable. Demo questions will be used.");
            return demoQuiz(topic);
        }

        try {
            String prompt = "Create 3 beginner Java multiple choice questions about " + topic + ".\n"
                    + "Return exactly this format only.\n"
                    + "Do not use markdown, bullets, numbering, or explanations.\n\n"
                    + "Q: question\n"
                    + "A: option\n"
                    + "B: option\n"
                    + "C: option\n"
                    + "D: option\n"
                    + "ANSWER: A\n\n";

            String json = "{"
                    + "\"model\":\"" + MODEL + "\","
                    + "\"messages\":[{"
                    + "\"role\":\"user\","
                    + "\"content\":\"" + escapeJson(prompt) + "\""
                    + "}]"
                    + "}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.groq.com/openai/v1/chat/completions"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return getJsonValue(response.body(), "content");
            }

            System.out.println("Groq API returned an error:");
            System.out.println(response.body());

        } catch (Exception e) {
            System.out.println("Groq API call failed.");
        }

        return demoQuiz(topic);
    }

    // Keep the rest of your methods unchanged:
    // demoQuiz, readQuizText, escapeJson, getJsonValue
}
