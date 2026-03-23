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

import org.docksidestage.bizfw.basic.objanimal.barking.BarkedSound;
import org.docksidestage.bizfw.basic.objanimal.barking.BarkingProcess;
import org.docksidestage.bizfw.basic.objanimal.loud.Loudable;

/**
 * The object for animal(動物).
 * @author jflute
 */
public abstract class Animal implements Loudable {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected int hitPoint; // is HP
    
    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Animal() {
        hitPoint = getInitialHitPoint();
    }

    protected int getInitialHitPoint() {
        return 10; // as default
    }

    // ===================================================================================
    //                                                                               Bark
    //                                                                              ======
    public BarkedSound bark() {
        return new BarkingProcess().bark(this);
    }
    
    // #1on1: Javaのprotectedの説明、サブクラスに公開、もしくは、同じpackageに公開、という二つの特徴 (2026/03/13)
    // BarkingProcess と Animal は親子関係ないので、サブクラス公開は関係ない。
    // ただ、同じpackageにあるのであれば「同じpackage公開」としてprotectedのメソッドにアクセスできる。
    // でも、barkingパッケージに移動した後のBarkingProcess側からの呼び出しでは、同じpackageじゃなくなったので呼べない。
    // packageスコープでの呼び出しは、ファイルの物理構造に依存するもので、できれば避けたい。
    // たまたま動いてるって感じで、ちょっとリファクタリングすると動かなくなる。


    // TODO done edo 修行++: これはサブクラス解決用のメソッドなので移動はできない。じゃあpublicにするか？ by jflute (2026/03/13)
    // できればpublicにせず、protectedを維持したまま、BarkingProcessを実現させたい。
    // (いったん、publicしちゃってもOK。ただとぅどぅ残したまま)
    //0319修正メモ：別パッケージから protected を直接呼べない問題に対してprotectedを公開するだけの
    // 薄いpublicのメソッド（ラッパーメソッド）を作成してgetBarkWord自体はprotectedを維持した。
    //これは、修行達成になっているのかレビューしていただけると助かります。
    //日常の業務で、あまり外で渡したくない関数やメソッドをラッパーして渡すことがあったためそこを参考にしました
    protected abstract String getBarkWord();

    public String barkWord() {
        return getBarkWord();
    }


    // ===================================================================================
    //                                                                           Hit Point
    //                                                                           =========
    protected void downHitPoint() {
        --hitPoint;
        if (hitPoint <= 0) {
            throw new IllegalStateException("I'm very tired, so I want to sleep" + getBarkWord());
        }
    }

    public void downHitPointForBark() {
        downHitPoint();
    }

    // ===================================================================================
    //                                                                               Loud
    //                                                                              ======
    @Override
    public String soundLoudly() {
        return bark().getBarkWord();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getHitPoint() {
        return hitPoint;
    }
}
