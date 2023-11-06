package lotto.view;

import static lotto.validator.Validator.validateMainNumbersInput;
import static lotto.validator.Validator.validateNumeric;
import static lotto.view.ViewConstant.ASK_BONUS_NUMBER_MESSAGE;
import static lotto.view.ViewConstant.ASK_PAYMENT_MESSAGE;
import static lotto.view.ViewConstant.ASK_WINNING_NUMBER_MESSAGE;
import static lotto.view.ViewConstant.MAIN_NUMBER_DELIMITER;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.List;

public class InputView {

    public static int readPayment() {
        System.out.println(ASK_PAYMENT_MESSAGE);

        String payment = Console.readLine();
        validateNumeric(payment);
        return Integer.parseInt(payment);
    }

    public static List<Integer> readWinningNumbers() {
        System.out.println(ASK_WINNING_NUMBER_MESSAGE);

        String numbers = Console.readLine();
        validateMainNumbersInput(numbers);
        return toIntegerList(numbers);
    }

    public static int readBonusNumber() {
        System.out.println(ASK_BONUS_NUMBER_MESSAGE);
        String bonus = Console.readLine();
        validateNumeric(bonus);
        return Integer.parseInt(bonus);
    }

    private static List<Integer> toIntegerList(String numbers) {
        return Arrays.stream(numbers.split(MAIN_NUMBER_DELIMITER.value()))
                .map(Integer::parseInt)
                .toList();
    }
}
