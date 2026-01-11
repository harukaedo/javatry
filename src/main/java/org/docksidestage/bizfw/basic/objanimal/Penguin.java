package org.docksidestage.bizfw.basic.objanimal;

import org.docksidestage.bizfw.basic.objanimal.swimmer.Swimmable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The object for penguin(ペンギン).
 * @author harukaedo
 */
public class Penguin extends Animal implements Swimmable {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final Logger logger = LoggerFactory.getLogger(Penguin.class);

    // ===================================================================================
    //                                                                          Attribute
    //                                                                          ==========
    protected final PenguinDiary penguinDiary = new PenguinDiary();
    
    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Penguin() {
    }

    @Override
    protected int getInitialHitPoint() {
        return +2;
    }

    public static class PenguinDiary {
        private int breatheInCount;

        public void countBreatheIn() {
            --breatheInCount;
        }

        public int getBreatheInCount() {
            return breatheInCount;
        }
    }

    // ===================================================================================
    //                                                                               Bark
    //                                                                              ======
    @Override
    protected void breatheIn() {
        super.breatheIn();
        penguinDiary.countBreatheIn();
    }

    @Override
    protected String getBarkWord() {
        return "aww,aww";
    }     
    

    // ===================================================================================
    //                                                                             swimmer
    //                                                                              ======
    @Override
    public void swim() {
        logger.debug("...Swimming now");
    }

    // ===================================================================================
    //                                                                           Hit Point
    //                                                                           =========
    @Override
    protected void downHitPoint() {
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public PenguinDiary getPenguinDiary() {
        return penguinDiary;
    }    
}
