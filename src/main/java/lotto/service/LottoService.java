package lotto.service;

import static lotto.LottoRule.LOTTO_NUMBER_COUNT;
import static lotto.LottoRule.LOTTO_NUMBER_END;
import static lotto.LottoRule.LOTTO_NUMBER_START;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lotto.domain.BonusNumber;
import lotto.domain.Lotto;
import lotto.domain.MainNumbers;
import lotto.domain.Prize;
import lotto.domain.PrizeCounter;
import lotto.domain.WinningNumbers;
import lotto.dto.LottoTicket;

public class LottoService {

    private List<Lotto> lottos;
    private MainNumbers mainNumbers;
    private BonusNumber bonusNumber;
    private WinningNumbers winningNumbers;

    public void init(int size) {
        List<Lotto> lottos = new ArrayList<>();
        while (lottos.size() < size) {
            lottos.add(new Lotto(createLottoNumbers()));
        }
        init(lottos);
    }

    public void init(List<Lotto> lottos) {
        this.lottos = lottos;
    }

    public void initMainNumbers(List<Integer> mainNumbers) {
        this.mainNumbers = new MainNumbers(mainNumbers);
    }

    public void initBonusNumber(int bonus) {
        this.bonusNumber = new BonusNumber(this.mainNumbers.toList(), bonus);
    }

    public void initWinningNumbers() {
        this.winningNumbers = new WinningNumbers(this.mainNumbers, this.bonusNumber);
    }

    public List<LottoTicket> tickets() {
        return lottos.stream().map(Lotto::toLottoTicket).toList();
    }

    private List<Integer> createLottoNumbers() {
        List<Integer> numbers = Randoms.pickUniqueNumbersInRange(LOTTO_NUMBER_START.value(), LOTTO_NUMBER_END.value(),
                LOTTO_NUMBER_COUNT.value());
        numbers.sort(Comparator.naturalOrder());
        return numbers;
    }


    public PrizeCounter countPrize() {
        PrizeCounter prizeCounter = new PrizeCounter();
        lottos.stream()
                .map(this::findPrize)
                .filter(p -> p.rank() > 0)
                .map(Enum::name)
                .forEach(prizeCounter::addCount);
        return prizeCounter;
    }

    private Prize findPrize(Lotto lotto) {
        int nMatchingMainNumber = (int) lotto.countMatchingMainNumbers(winningNumbers);
        boolean isBonusMatched = lotto.isBonusMatched(winningNumbers);
        return Prize.findFrom(nMatchingMainNumber, isBonusMatched);
    }

}
