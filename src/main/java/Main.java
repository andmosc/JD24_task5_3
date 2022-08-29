import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String fileJson = "data/Employee.json";
        String data = getDataFromFileJson(fileJson);
        List<Employee> employeeList = jsonFileToList(data);
        employeeList.forEach(System.out::println);
    }

    private static List<Employee> jsonFileToList(String data) {
        JSONParser jsonParser = new JSONParser();
        List<Employee> listEmployee = new ArrayList<>();

        try {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(data);
            for (Object item : jsonArray) {
                JSONObject jsonObject = (JSONObject) item;
                listEmployee.add(parseEmployee(jsonObject));
            }
        } catch (ParseException e) {
            System.out.println("Error parse: " + e);
        }
        return listEmployee;
    }

    private static Employee parseEmployee(JSONObject jsonObject) {
        return new Employee((long)jsonObject.get("id"), (String) jsonObject.get("firstName")
                , (String) jsonObject.get("lastName")
                , (String) jsonObject.get("country")
                , Math.toIntExact((Long) jsonObject.get("age")));
    }

    private static String getDataFromFileJson(String fileJson) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileJson))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
        }
        return builder.toString();
    }
}
