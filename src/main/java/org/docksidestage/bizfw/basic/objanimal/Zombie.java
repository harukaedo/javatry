/*
 * Copyright 2019-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.bizfw.basic.objanimal;

import org.docksidestage.bizfw.basic.objanimal.diary.AnimalDiary;

/**
 * The object for zombie(ゾンビ).
 * @author jflute
 */
public class Zombie extends Animal {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final ZombieDiary zombieDiary = new ZombieDiary();

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Zombie() {
    }

    @Override
    protected int getInitialHitPoint() {
        return -1; // magic number for infinity hit point
    }

    public static class ZombieDiary extends AnimalDiary{

        private int breatheInCount;

        public void countBreatheIn() {
            ++breatheInCount;
        }

        public int getBreatheInCount() {
            return breatheInCount;
        }
    }

    // ===================================================================================
    //                                                                               Bark
    //                                                                              ======
    @Override
    protected AnimalDiary getAnimalDiary() {
        return zombieDiary;
    }

    @Override
    protected String getBarkWord() {
        return "uooo"; // what in English?
    }
    
    // #1on1: Diaryの階層構造ではなく、BarkingProcess自体を階層構造にしてしまうやり方 (2026/04/10)
    // o ZombieDiary: 明示的でわかりやすい拡張ポイント、一方で柔軟性は低い (追加の時に修正が多くなる)
    // o ZombieBarkingProcess: 柔軟性が高い、一方でなんでもできちゃうからわかりやすさは低くなる
    // 何を重視してどう演出するか？がまさしくコーディングデザイン。
    //@Override
    //protected BarkingProcess createBarkingProcess() {
    //    return new ZombieBarkingProcess();
    //}
    //
    //public class ZombieBarkingProcess extends BarkingProcess {
    //
    //    @Override
    //    protected void breatheIn(Animal animal, AnimalDiary animalDiary) {
    //        super.breatheIn(animal, animalDiary);
    //        zombieDiary.countBreatheIn();
    //    }
    //    
    //    @Override
    //    protected void prepareAbdominalMuscle(Animal animal) {
    //        super.prepareAbdominalMuscle(animal);
    //    }
    //}

    // ===================================================================================
    //                                                                           Hit Point
    //                                                                           =========
    @Override
    protected void downHitPoint() {
        // do nothing, infinity hit point
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public ZombieDiary getZombieDiary() {
        return zombieDiary;
    }
}
