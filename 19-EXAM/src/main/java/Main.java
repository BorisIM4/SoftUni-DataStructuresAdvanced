import core.Expressionist;
import core.ExpressionistImpl;
import core.PackageManagerImpl;
import models.Expression;
import models.ExpressionType;
import models.Package;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Expressionist expressionist = new ExpressionistImpl();

        LocalDateTime date = LocalDateTime.of(2020, 1, 8,20, 55, 55);
        LocalDateTime date1 = LocalDateTime.of(2021, 1, 8,20, 55, 55);
        LocalDateTime date2 = LocalDateTime.of(2022, 1, 8,20, 55, 55);
        LocalDateTime date3 = LocalDateTime.of(2023, 1, 8,20, 55, 55);
        LocalDateTime date4 = LocalDateTime.of(2024, 1, 8,20, 55, 55);

        PackageManagerImpl packageManager = new PackageManagerImpl();

        Package pack = new Package("1", "Boris", "33", date);
        Package pack1 = new Package("2", "Aleks", "33", date1);
        Package pack2 = new Package("3", "Krissie", "1", date2);
        Package pack3 = new Package("4", "Borisa", "1", date3);
        Package pack4 = new Package("5", "Rumi", "75", date4);
        Package pack5 = new Package("6", "Rumi", "75", date4);


        Package pack6 = new Package("5", "Gosho", "75", date4);

        packageManager.registerPackage(pack);
        packageManager.registerPackage(pack1);
        packageManager.registerPackage(pack2);
        packageManager.registerPackage(pack3);
        packageManager.registerPackage(pack4);

        packageManager.addDependency("1", "3");
        packageManager.addDependency("1", "4");

        boolean contains = packageManager.contains(pack6);

        int size = packageManager.size();

        Iterable<Package> dependants = packageManager.getDependants(pack);

        Iterable<Package> independentPackages = packageManager.getIndependentPackages();

        System.out.println();

//        expressionist.addExpression(new Expression() {{
//            setId("1");
//            setType(ExpressionType.OPERATOR);
//            setValue("+");
//        }});
//        expressionist.addExpression(new Expression() {{
//            setId("2");
//            setType(ExpressionType.OPERATOR);
//            setValue("*");
//        }}, "1");
//        expressionist.addExpression(new Expression() {{
//            setId("3");
//            setType(ExpressionType.OPERATOR);
//            setValue("/");
//        }}, "1");
//        expressionist.addExpression(new Expression() {{
//            setId("4");
//            setType(ExpressionType.VALUE);
//            setValue("5");
//        }}, "2");
//        expressionist.addExpression(new Expression() {{
//            setId("5");
//            setType(ExpressionType.VALUE);
//            setValue("10");
//        }}, "2");
//        expressionist.addExpression(new Expression() {{
//            setId("6");
//            setType(ExpressionType.VALUE);
//            setValue("2.5");
//        }}, "3");
//        expressionist.addExpression(new Expression() {{
//            setId("7");
//            setType(ExpressionType.VALUE);
//            setValue("3.5");
//        }}, "3");
//
//        expressionist.removeExpression("7");
//
//        System.out.println(expressionist.evaluate());
    }
}