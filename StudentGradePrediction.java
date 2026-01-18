import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class StudentGradePrediction {

    public static void main(String[] args) {

        ArrayList<Double> studyHours = new ArrayList<>();
        ArrayList<Double> examScores = new ArrayList<>();

        // CSV dosyasını oku
        try (BufferedReader br = new BufferedReader(new FileReader("data.csv"))) {
            String line;
            br.readLine(); // başlık satırını atla

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                studyHours.add(Double.parseDouble(values[0]));
                examScores.add(Double.parseDouble(values[1]));
            }
        } catch (Exception e) {
            System.out.println("Dosya okunurken hata oluştu.");
            return;
        }

        // Linear Regression hesaplama
        double xMean = mean(studyHours);
        double yMean = mean(examScores);

        double numerator = 0;
        double denominator = 0;

        for (int i = 0; i < studyHours.size(); i++) {
            numerator += (studyHours.get(i) - xMean) * (examScores.get(i) - yMean);
            denominator += Math.pow(studyHours.get(i) - xMean, 2);
        }

        double slope = numerator / denominator;
        double intercept = yMean - slope * xMean;

        // Tahmin
        double newStudyHour = 9;
        double predictedScore = slope * newStudyHour + intercept;

        System.out.println("Çalışma Saati: " + newStudyHour);
        System.out.println("Tahmin Edilen Not: " + predictedScore);
    }

    public static double mean(ArrayList<Double> list) {
        double sum = 0;
        for (double value : list) {
            sum += value;
        }
        return sum / list.size();
    }
}
