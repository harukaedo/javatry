package org.docksidestage.bizfw.basic.objanimal;

import org.docksidestage.bizfw.basic.objanimal.barking.BarkedSound;
/**
 * @author harukaedo
 */

public class BarkingProcess {
    public BarkedSound bark(Animal animal) {
        animal.breatheIn();
        animal.prepareAbdominalMuscle();
        String barkWord = animal.getBarkWord();
        return animal.doBark(barkWord);
    }
}
