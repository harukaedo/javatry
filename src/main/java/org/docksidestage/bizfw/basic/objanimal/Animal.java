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
    //                                                                              breath
    //    
    //0319修正メモ：ペンギンとゾンビが、super.breatheinでAnimal共通処理となったいた。
    // AnimalにはもうbreatheIn本体はないのでbreatheInをBarkingProcess側で呼ぶためのトリガーを用意してあげて
    // 各々の動物で上書きしてあげるように変更                                                           ======
    // TODO edo 修行++: breatheInForBark()を呼び出している人が誰もいない by jflute (2026/03/27)
    // 確かに、BarkingProcessがbreathIn()の中でこれを呼べば成立するはず。
    // でも、protectedだから、BarkingProcessから呼ぶことはできない。
    // publicなら実現できるが、結局カプセル化の話に戻ってきちゃう。
    // 逆にいうと、downHitPoint()のpublicが解決すればこっちも同時に解決するかも。
    //
    // 一方で、こっちは別のやり方でも解決できる。downHitPoint()が解決してなくても、こっちだけ解決することできる。
    // hint1: オブジェクト指向はもっと自由。
    // hint2: ザ・オブジェクト指向 (階層構造をどう作るか？どこで作るか？は自由)
    // hint3: ↑はAnimalだけのものじゃない (例えば、ZombieDiaryとPenguinDiaryがAnimalDiaryを継承するとか)
    //
    protected void breatheInForBark(){
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


    // done edo 修行++: これはサブクラス解決用のメソッドなので移動はできない。じゃあpublicにするか？ by jflute (2026/03/13)
    // できればpublicにせず、protectedを維持したまま、BarkingProcessを実現させたい。
    // (いったん、publicしちゃってもOK。ただとぅどぅ残したまま)
    //0319修正メモ：別パッケージから protected を直接呼べない問題に対してprotectedを公開するだけの
    // 薄いpublicのメソッド（ラッパーメソッド）を作成してgetBarkWord自体はprotectedを維持した。
    //これは、修行達成になっているのかレビューしていただけると助かります。
    //日常の業務で、あまり外で渡したくない関数やメソッドをラッパーして渡すことがあったためそこを参考にしました
    // #1on1: そもそもpublicにしたくない理由は？ (元々protectedで隠されていた理由は？) (2026/03/27)
    // downHitPoint()の方を参照。
    // TODO edo 修行++: getBarkWord(), downHitPoint()に比べてこっちは比較的楽に実現(解決)できます by jflute (2026/03/27)
    // hint1: すでに知ってる文法レベルの組み合わせで解決できる。
    protected abstract String getBarkWord();

    public String barkWord() {
        return getBarkWord();
    }


    // ===================================================================================
    //                                                                           Hit Point
    //                                                                           =========
    // #1on1: そもそもpublicにしたくない理由は？ (元々protectedで隠されていた理由は？) (2026/03/27)
    // 内部処理を公開してしまうと、内部都合で内部処理を変更する時に影響範囲が大きくなってしまう。
    // getBarkWord()に比べて、むやみに呼ばれるとAnimalはどうなっちゃう？
    // getBarkWord()はまだ情報が漏れるだけ(それもよくないけど)...
    // downHitPoint()はむやみに体力減らされてしまう...何も行動をしてないのに体力が減るみたいなこともありえる。
    //
    //  getBarkWord(): select
    //  downHitPoint(): update // 状態が変わる
    //
    // publicだと内部状態を壊れてしまう (壊される可能性が出てきてしまう)
    // publicで (BarkingProcess以外の) 他のクラスから呼ばれてしまうことを避けたい。
    //
    // オブジェクト指向のカプセル化の本質。
    //
    protected void downHitPoint() {
        --hitPoint;
        if (hitPoint <= 0) {
            throw new IllegalStateException("I'm very tired, so I want to sleep" + getBarkWord());
        }
    }

    // #1on1: ラッパー自体は悪くないけど、ラッパー自体がpublicだと、結局呼ばれてしまう (2026/03/27)
    // TODO edo 修行#: downHitPoint()のラッパーもpublicじゃないようにしたい by jflute (2026/03/27)
    // hint1: ラッパーがメソッドだったりクラスだったり、色々な形式の可能性はある (2026/03/27)
    // hint2: Animal自身とBarkingProcessだけ (Animalが許してるクラスだけ) が呼べるような状態にしたい (2026/03/27)
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
