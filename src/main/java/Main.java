import com.github.javafaker.Faker;

import java.io.FileWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static int numbersOfEmployees = 30;
    public static List<String> TitleList = Arrays.asList("General manager", "Director",
            "Manager", "Supervisor", "Assistant manager", "Associate");
    public static List<String> LocationList = Arrays.asList("GHHDT1H7", "TH8LF9", "LDFS8F3DDS", "JGH7T");
    public static List<String> CompanyCodeList = Arrays.asList("01", "02", "03");
    public static List<String> OfficeCodeList = Arrays.asList("01", "02", "03", "04");
    public static List<String> GroupCodeList = Arrays.asList("01", "02", "03", "04", "05");
    public static List<String> SkillIdList = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    public static String employeeFilePath = "src/employees.sql";
    public static String skillsFilePath = "src/skills.sql";


    public static void main(String[] args) {
        try {
            Writer fileWriter = new FileWriter(employeeFilePath, false);
            Writer fileWriter2 = new FileWriter(skillsFilePath, false);
            for (int i = 0; i < numbersOfEmployees; i++) {
                fileWriter.write(genNewEmployee());
            }

            fileWriter2.write(genNewSkills());

            fileWriter.close();
            fileWriter2.close();
        } catch (Exception e){
            e.getStackTrace();
        }
    }
    public static String genNewSkills() {

        StringBuilder sb = new StringBuilder();

        for (int j = 0; j < numbersOfEmployees; j++) {
            for (int i = 0; i < getRandomIntBetweenRange(1, SkillIdList.size()); i++) {
                sb.append("INSERT INTO EmployeeSkills (EmployeeNumber, SkillId) VALUES (");
                sb.append(getRandomIntBetweenRange(1, numbersOfEmployees) + ",");
                sb.append(stringWrapper(getRandomItemFromList(SkillIdList)) + ");");
                sb.append("\n");
            }
        }


        return sb.toString();

    }

        public static String genNewEmployee(){
        Faker faker = new Faker();
        int isContractor = getRandomIntBetweenRange(0, 1);
        String CompanyCode = stringWrapper(getRandomItemFromList(CompanyCodeList));
        String OfficeCode = stringWrapper(getRandomItemFromList(OfficeCodeList));
        String GroupCode = stringWrapper(getRandomItemFromList(GroupCodeList));
        String LastName = stringWrapper(faker.name().lastName()); // Barton
        String FirstName = stringWrapper(faker.name().firstName()); // Emory
        String EmploymentType = stringWrapper("Salary");
        String Title = stringWrapper(getRandomItemFromList(TitleList));
        String HireDate = stringWrapper(dateBetween(LocalDate.of(1989, Month.OCTOBER, 14), LocalDate.of(2015, Month.OCTOBER, 14)).toString());
        String TerminationDate = "NULL";
        int SupervisorEmployeeNumber = getRandomIntBetweenRange(1, numbersOfEmployees);
        int YearsPriorExperience = getRandomIntBetweenRange(1, 20);
        String Email = stringWrapper(faker.name().firstName() + "@amc.com");
        String WorkPhone = stringWrapper(genPhoneNumber());
        String WorkCell = stringWrapper(genPhoneNumber());
        String LocationId = stringWrapper(getRandomItemFromList(LocationList));
        String PhotoUrl = stringWrapper("images/999.jpg");

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO Employee (isContractor, CompanyCode, OfficeCode, GroupCode, LastName, FirstName, EmploymentType, Title, HireDate, TerminationDate,SupervisorEmployeeNumber, YearsPriorExperience, Email, WorkPhone, WorkCell, LocationId, PhotoUrl)VALUES (");
        sb.append(isContractor + ",");
        sb.append(CompanyCode + ",");
        sb.append(OfficeCode + ",");
        sb.append(GroupCode + ",");
        sb.append(LastName + ",");
        sb.append(FirstName + ",");
        sb.append(EmploymentType + ",");
        sb.append(Title + ",");
        sb.append(HireDate + ",");
        sb.append(TerminationDate + ",");
        sb.append(SupervisorEmployeeNumber + ",");
        sb.append(YearsPriorExperience + ",");
        sb.append(Email + ",");
        sb.append(WorkPhone + ",");
        sb.append(WorkCell + ",");
        sb.append(LocationId + ",");
        sb.append(PhotoUrl + ");");
        sb.append("\n");

        //(isContractor, CompanyCode, OfficeCode, GroupCode, LastName, FirstName, EmploymentType, Title, HireDate, TerminationDate,SupervisorEmployeeNumber, YearsPriorExperience, Email, WorkPhone, WorkCell, LocationId, LocationId)
        //0, '01', '01', '01', 'Acme', 'Susan', 'Salary', 'President and CEO', '1995-06-01', NULL, 1, 11.2, 'acmes@acme.ca', '604-123-4567', '604-123-7654', 'GHHDT1H7', 'some-url/acmes.jpg'

        return sb.toString();

    }

    public static String stringWrapper(String str) {
        return "'" + str + "'";
    }

    public static String getRandomItemFromList(List<String> items) {
        return items.get(new Random().nextInt(items.size()));
    }

    public static LocalDate dateBetween(LocalDate startInclusive, LocalDate endExclusive) {
        long startEpochDay = startInclusive.toEpochDay();
        long endEpochDay = endExclusive.toEpochDay();
        long randomDay = ThreadLocalRandom
                .current()
                .nextLong(startEpochDay, endEpochDay);

        return LocalDate.ofEpochDay(randomDay);
    }

    public static int getRandomIntBetweenRange(int min, int max) {
        double x = (Math.random() * ((max - min) + 1)) + min;
        return (int) x;
    }

    public static String genPhoneNumber() {
        Random random = new Random();

        int area = random.nextInt(900)+100;
        int mid = random.nextInt(643)+100;
        int last = random.nextInt(9000)+1000;

        return area+"-"+mid+"-"+last;

    }


}
