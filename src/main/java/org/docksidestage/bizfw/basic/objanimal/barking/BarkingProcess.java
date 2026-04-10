package org.docksidestage.bizfw.basic.objanimal.barking;

import org.docksidestage.bizfw.basic.objanimal.Animal;
import org.docksidestage.bizfw.basic.objanimal.diary.AnimalDiary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author harukaedo
 */

public class BarkingProcess {
    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final Logger logger = LoggerFactory.getLogger(BarkingProcess.class);

    public BarkedSound bark(Animal animal, String barkWord, AnimalDiary animalDiary) {
        breatheIn(animal, animalDiary);
        prepareAbdominalMuscle(animal);
        return doBark(animal,barkWord);
    }

    // done edo コメント見ると、barkingのための息継ぎなので、barking専用と考えて良い by jflute (2026/03/13)
    //0319 修正メモ：breatheIn,prepareAbdominalMuscle,doBarkは BarkingProcessの一部であるため
    // BarkingProcessに引っ越した。引っ越したことによりそれぞれが各animalのメソッドを引数で受ける形になったため
    //animal.hogehogeからhogehoge(animal)に変更。
    //Animal自身には、getBarkWord() と downHitPoint() のような、動物自身の状態や差分に関わる責務を残した
    protected void breatheIn(Animal animal, AnimalDiary animalDiary) { // actually depends on barking
        logger.debug("...Breathing in for barking"); // dummy implementation
        // TODO edo ZombieDiaryでbreatheInForBark()がオーバーライドされてないので結局空振りしてる by jflute (2026/04/10)
        // #1on1: ザ・仕組みって感じ。スーパードミノの仕掛けが途中で途切れたみたいな感じ。 (2026/04/10)
        animalDiary.breatheInForBark();
        animal.downHitPointForBark();
    }

    protected void prepareAbdominalMuscle(Animal animal) { // also actually depends on barking
        logger.debug("...Using my abdominal muscle for barking"); // dummy implementation
        animal.downHitPointForBark();
    }

    // done edo これは確実に、barking専用メソッドなので、Processの一部にしちゃっていいかなと by jflute (2026/03/13)
    protected BarkedSound doBark(Animal animal,String barkWord) {
        animal.downHitPointForBark();
        return new BarkedSound(barkWord);
    }

}
