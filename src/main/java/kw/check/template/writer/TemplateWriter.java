package kw.check.template.writer;

import kw.check.template.CheckTemplate;
import kw.check.template.generator.TemplateGenerator;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TemplateWriter {

    public static void main(String... args) {
        CheckTemplate template = new CheckTemplate();
        template.setAmount(499.00d);
        template.setAmountInWords("One thousand nd seven hundred and eighteen and  ninety nine cents onlyThirteen hundred and fifty five and sixty seven cents only");
        template.setAmountInWords("Nine billion nine million nine lakh nine thousand niasdnfiasnfda");
        template.setDate("31/01/2019");
        template.setPayee("Tejas Patel");
        System.out.println(Math.ceil((50 - 49) / 6.0));

        try {
            new TemplateGenerator().createTemplate(getUserInput());
//            new TemplateGenerator().createTemplate(template);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private static CheckTemplate getUserInput() {
        CheckTemplate template = new CheckTemplate();
        Scanner scanner = new Scanner(System.in);
        double amount;
        int userContinue;
        String date, amountInWords, payee;
        do {
            System.out.print("Enter amount: $");

            while (true) {
                try {
                    amount = scanner.nextDouble();

                    while (amount <= 0) {
                        System.out.println("\nMust enter an amount greater " +
                                "than 0.");
                        System.out.print("Please enter your amount again: $");
                        amount = scanner.nextDouble();
                    }

                    break;
                } catch (InputMismatchException e) {
                    System.out.println("\nMust enter a dollar amount.");
                    System.out.print("Please enter amount again: $");
                    scanner.nextLine();
                }
            }
            template.setAmount(amount);

            while (true) {
                try {
                    amountInWords = scanner.nextLine();

                    while (StringUtils.isBlank(amountInWords)) {
                        System.out.print("Enter amount in words: ");
                        amountInWords = scanner.nextLine();
                    }

                    break;
                } catch (InputMismatchException e) {
                    System.out.println("\nMust enter an amount in words.");
                    System.out.print("Please enter valid amount in words");
                    scanner.nextLine();
                }
            }
            template.setAmountInWords(amountInWords);

            System.out.print("Enter check date in dd/mm/yyyy format: ");

            while (true) {
                try {
                    date = scanner.nextLine();

                    while (StringUtils.isBlank(date)) {
                        System.out.println("\nMust enter date.");
                        System.out.print("Please enter valid date");
                        date = scanner.nextLine();
                    }

                    break;
                } catch (InputMismatchException e) {
                    System.out.println("\nMust enter date.");
                    System.out.print("Please enter valid date");
                    scanner.nextLine();
                }
            }
            template.setDate(date);

            System.out.print("Enter add payee name: ");

            while (true) {
                try {
                    payee = scanner.nextLine();

                    while (StringUtils.isBlank(payee)) {
                        System.out.println("\nMust enter payee name.");
                        System.out.print("Please enter valid payee name");
                        payee = scanner.nextLine();
                    }

                    break;
                } catch (InputMismatchException e) {
                    System.out.println("\nMust enter payee name.");
                    System.out.print("Please enter valid payee name");
                    scanner.nextLine();
                }
            }
            template.setPayee(payee);

            System.out.println("\nWould you like to generate the check template with the following details?\n");
            System.out.println("**************************************************************************\n");
            System.out.println(template.toString());
            System.out.println("**************************************************************************\n");
            System.out.print("Enter 1 for yes or 2 for no: ");

            while (true) {
                try {
                    userContinue = scanner.nextInt();

                    while (userContinue < 1 || userContinue > 2) {
                        System.out.println("\nMust be either 1 or 2.");
                        System.out.print("Please enter your selection " +
                                "again: ");
                        userContinue = scanner.nextInt();
                    }

                    break;
                } catch (InputMismatchException e) {
                    System.out.println("\nMust enter an integer.");
                    System.out.print("Enter 1 for yes or 2 for no: ");
                    scanner.nextLine();
                }
            }

        } while (userContinue == 2);
        return template;
    }
}
