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
package org.docksidestage.javatry.basic;

import org.docksidestage.bizfw.basic.buyticket.Ticket;
import org.docksidestage.bizfw.basic.buyticket.TicketBooth;
import org.docksidestage.bizfw.basic.objanimal.Animal;
import org.docksidestage.bizfw.basic.objanimal.BarkedSound;
import org.docksidestage.bizfw.basic.objanimal.Cat;
import org.docksidestage.bizfw.basic.objanimal.Dog;
import org.docksidestage.bizfw.basic.objanimal.Zombie;
import org.docksidestage.bizfw.basic.objanimal.loud.AlarmClock;
import org.docksidestage.bizfw.basic.objanimal.loud.Loudable;
import org.docksidestage.bizfw.basic.objanimal.runner.FastRunner;
import org.docksidestage.unit.PlainTestCase;

// #1on1: UIデザインでオブジェクト指向に触れたことがある by えどさん // (2025/10/15)
// #1on1: ちょっとフォローアップ、考え方の最初の流れだけ共通点がある話 (2025/12/12)
/**
 * The test of object-oriented. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author harukaedo
 * 
 */
public class Step06ObjectOrientedTest extends PlainTestCase {

    // ===================================================================================
    //                                                                        About Object
    //                                                                        ============
    // -----------------------------------------------------
    //                                        Against Object
    //                                        --------------
    /**
     * Fix several mistakes (except simulation) in buying one-day passport and in-park process. <br>
     * (OneDayPassportを買って InPark する処理の中で、(simulationを除いて)間違いがいくつかあるので修正しましょう)
     */
    public void test_objectOriented_aboutObject_againstObject() {
        //
        // [ticket booth info]
        //
        // simulation: actually these variables should be more wide scope
        int oneDayPrice = 7400;
        int quantity = 10;
        Integer salesProceeds = null;

        // done edo 間違い探しあと1個: その行だけ見て間違いってわかる間違いではない by jflute (2025/10/15)
        //1104 修正メモ：--quantityがお金過不足チェック前に発生していて、handmoneyが不足の場合でも減ってしまって
        //ロジックがおかしくなっていたので順番を修正。
        // すでに見つけたものであれば、displayPrice = quantity; ってその行だけで間違いが表現されている。
        //
        // [buy one-day passport]
        //
        // simulation: actually this money should be from customer
        int handedMoney = 10000;
        if (quantity <= 0) {
            throw new IllegalStateException("Sold out");
        }
        if (handedMoney < oneDayPrice) {
            throw new IllegalStateException("Short money: handedMoney=" + handedMoney);
        }
        --quantity; //1104 修正: お金のチェックを先に行ってから在庫を減らす
        salesProceeds = oneDayPrice; // 1010 修正：チケットの枚数になっていたため、チケットの価格に変更

        //
        // [ticket info]
        //
        // simulation: actually these variables should be more wide scope
        int displayPrice = oneDayPrice; //1010 修正: チケットの表示価格が枚数になっていたため価格に変更
        boolean alreadyIn = false;

        // other processes here...
        // ...
        // ...

        //
        // [do in park now!!!]
        //
        // simulation: actually this process should be called by other trigger
        if (alreadyIn) {
            throw new IllegalStateException("Already in park by this ticket: displayPrice=" + displayPrice); //1010修正: displayPriceを使用
        }
        alreadyIn = true;

        // #1on1: こういったメソッドを呼び出し側の意識 (呼ばざるを得ない状況として) (2025/10/15)
        // o int, int, int, ...みたいな引数のメソッドを呼び出すときは間違いが起きやすい
        // o 呼び出し引数のところ改行するなりして、引数の順序を明示的に意識できるようにする by えどさん
        //   (恒久的なものじゃなくても、一時的な確認でそういう風に見やすく変えるってのもアリ)
        // o 書いた後に、5秒指差し確認するかどうか？
        // o ただ、5秒指差し確認、毎回毎回やるか？だと、さすがに手間が掛かる。
        // o 経験から、(一般的に)間違いやすいポイントを知ってる、もしくは、意識している。
        //  → 危ないポイントでは、ガッと集中力を高める
        // o あと、自分が間違えやすいポイントを知ってるか？ (人に寄って間違えポイントは変わるもの)
        //  → 2回以上間違えたとき、「あっ、これが自分の間違えやすいポイントなんだ」って積み上げていく。
        //  → (自分にとって)危ないポイントでは、ガッと集中力を高める
        //
        // → 技術スキルというよりは、開発スキル (ものづくりスキル)
        //
        //
        // [final process]
        //
        saveBuyingHistory(quantity, displayPrice, salesProceeds, alreadyIn);
    }

    private void saveBuyingHistory(int quantity, int displayPrice, Integer salesProceeds, boolean alreadyIn) {
        // done edo showYourTicket()のセミコロンのところでコンパイルエラーになってるので修正しましょう by jflute (2025/10/15)
        if (alreadyIn) {
            // simulation: only logging here (normally e.g. DB insert)
            showTicketBooth(quantity, salesProceeds);
            showYourTicket(displayPrice, alreadyIn);
            //1010 修正: showTicketBoothとshowYourTicketdで定義されている引数とsaveBuyingHistory内で定義されている引数が異なっていたため修正
        }
    }

    private void showTicketBooth(int quantity, Integer salesProceeds) {
        log("Ticket Booth: quantity={}, salesProceeds={}", quantity, salesProceeds);
    }

    private void showYourTicket(int displayPrice, boolean alreadyIn) {
        log("Ticket: displayPrice={}, alreadyIn={}", displayPrice, alreadyIn);
    }

    // -----------------------------------------------------
    //                                          Using Object
    //                                          ------------
    /**
     * Read (analyze) this code and compare with the previous test method, and think "what is object?". <br>
     * (このコードを読んで(分析して)、一つ前のテストメソッドと比べて、「オブジェクトとは何か？」を考えてみましょう)
     */
    public void test_objectOriented_aboutObject_usingObject() {
        //
        // [ticket booth info]
        //
        TicketBooth booth = new TicketBooth();

        // *booth has these properties:
        //int oneDayPrice = 7400;
        //int quantity = 10;
        //Integer salesProceeds = null;

        //
        // [buy one-day passport]
        //
        // if step05 has been finished, you can use this code by jflute (2019/06/15)
        //Ticket ticket = booth.buyOneDayPassport(10000);
        booth.BuyOneDayPassport(10000); // as temporary, remove if you finished step05
        Ticket ticket = Ticket.creatNormalTicket(); // also here

        // *buyOneDayPassport() has this process:
        //if (quantity <= 0) {
        //    throw new TicketSoldOutException("Sold out");
        //}
        //if (handedMoney < oneDayPrice) {
        //    throw new TicketShortMoneyException("Short money: handedMoney=" + handedMoney);
        //}
        //--quantity;
        //salesProceeds = handedMoney;

        // *ticket has these properties:
        //int displayPrice = oneDayPrice;
        //boolean alreadyIn = false;

        // other processes here...
        // ...
        // ...

        //
        // [do in park now!!!]
        //
        ticket.doInPark(16); // 16時に入園

        // *doInPark() has this process:
        //if (alreadyIn) {
        //    throw new IllegalStateException("Already in park by this ticket: displayPrice=" + displayPrice);
        //}
        //alreadyIn = true;

        //
        // [final process]
        //
        saveBuyingHistory(booth, ticket);
    }

    private void saveBuyingHistory(TicketBooth booth, Ticket ticket) {
        if (ticket.isCurrentIn()) {
            // only logging here (normally e.g. DB insert)
            doShowTicketBooth(booth);
            doShowYourTicket(ticket);
        }
    }

    private void doShowTicketBooth(TicketBooth booth) {
        log("Ticket Booth: quantity={}, salesProceeds={}", booth.getQuantity(), booth.getSalesProceeds());
    }

    private void doShowYourTicket(Ticket ticket) {
        // done edo ログのタイトルが、alreadyIn のままになっている (潜在的な影響) by jflute (2025/10/15)
        // #1on1: リファクタリング機能で修正するのはOK, ただその後、呼び出し先に潜在的な影響がないか確認する習慣を (2025/10/15)
        log("Your Ticket: displayPrice={}, currentIn={}", ticket.getDisplayPrice(), ticket.isCurrentIn());
    }

    // TODO jflute 今度1on1にオブジェクトとは？ (2025/11/14)
    // write your memo here:
    // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
    // what is object?
    //1128　事前1on1前事前準備
    //オブジェクトとは、プログラム内で扱うデータや動作すべてを指す概念ではないかと思う。
    //私の大好きなりんごで例えてみると..
    //りんごというクラスにふじというインスタンスや王林というインスタンスが存在している。
    //インスタンスはクラスによって作られたオブジェクトでもある。
    //オブジェクトは、クラスによって作られたインスタンスであり、クラスによって作られたオブジェクトである。
    // #1on1: ふじと王林という概念自体は、クラスであって、りんごというクラスのもうちょう具象的な概念。
    // インスタンスは、そのふじや王林からnewされた実体のことを指す。
    // _/_/_/_/_/_/_/_/_/_/
    // #1on1: オブジェクトとインスタンスはかなりニアリーイコールな感じで使われているけど... (2025/12/12)
    // インスタンスにもう少しクラスの特性を意識させたものがオブジェクトと言えそう。
    // もっと抽象度の高い話をしているときに使う言葉。
    // 一方で、クラス/インスタンスはプログラミングの世界を思いっきり意識した言葉。
    // ただし、ぼくらは、それを厳密に使い分けるの難しいのでよく混ざる。
    //
    // あと、オブジェクト指向用語と、具体的なプログラミング用語の違い。
    // オブジェクト指向では、オブジェクトの属性
    // プログラミングでは、(Javaなら)インスタンス変数
    // オブジェクト指向では、オブジェクトの振る舞い
    // プログラミングでは、(Javaなら)インスタンスメソッド
    // 例えば、タグコメントの Attribute は、オブジェクト指向の属性という言葉から来ている。
    
    // TODO jflute 次回1on1, オブジェクトとは？ (2025/12/12)
    // オブジェクトという言葉の話ではなく、オブジェクトになりうるものってどんなもの？の話

    // ===================================================================================
    //                                                              Polymorphism Beginning
    //                                                              ======================
    /**
     * What string is sea and land variable at the method end? <br>
     * (メソッド終了時の変数 sea, land の中身は？)
     */
    public void test_objectOriented_polymorphism_1st_concreteOnly() {
        Dog dog = new Dog();
        BarkedSound sound = dog.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => wan
        int land = dog.getHitPoint();
        log(land); // your answer? => 7
    }
    //1206自分なりの回答
    //Dogクラスでbarkメソッドを呼び出すと、getBarkWordメソッドが呼び出され、"wan"が返される。
    //DogはAnimalを継承しているため、Animalコンストラクタが呼び出しされてanimal.bark()の処理が実行される。
    //初期値が10で各メソッドでhitPointが1ずつ減っていくため、最終的に7になる。

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_2nd_asAbstract() {
        // #1on1: is-aの関係、犬は動物である(o)、動物は犬である(x)、目覚まし時計は動物である(x)
        // dog.ote()はベルけど、animal.ote(); は呼べない
        // インスタンスはあくまでDogでote()を持ってるけど、animal扱いなので呼べない。
        //
        // #1on1: 日常でポリモーフィズムを使ってるか？ (2025/12/25)
        // ぼくらは抽象度をコントロールして会話している。
        // 抽象度の高い概念の言葉でスムーズに会話が成り立ったりしている。
        // (特に事務的な関係性の人と会話するときは)
        //
        Animal animal = new Dog();
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => wan
        int land = animal.getHitPoint();
        log(land); // your answer? => 7
    }
    //1206自分なりの回答
    //Animal animal = new Dog();でDogクラスのインスタンスをAnimal型の変数に代入している。
    //Dogクラスでbarkメソッドを呼び出すと、getBarkWordメソッドが呼び出され、"wan"が返される。
    //DogはAnimalを継承しているため、Animalコンストラクタが呼び出しされてanimal.bark()の処理が実行される。
    //初期値が10で各メソッドでhitPointが1ずつ減っていくため、最終的に7になる。

    // TODO jflute 次回1on1, ポリモーフィズムのプログラム上のメリット追っていく (2025/12/25)
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_3rd_fromMethod() {
        Animal animal = createAnyAnimal();
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => wan
        int land = animal.getHitPoint();
        log(land); // your answer? => 7
    }
    //1206自分なりの回答
    //createAnyAnimalメソッドでDogクラスのインスタンスを返す。
    //Animal animal = createAnyAnimal();でDogクラスのインスタンスをAnimal型の変数に代入している。
    //Dogクラスでbarkメソッドを呼び出すと、getBarkWordメソッドが呼び出され、"wan"が返される。
    //DogはAnimalを継承しているため、Animalコンストラクタが呼び出しされてanimal.bark()の処理が実行される。
    //初期値が10で各メソッドでhitPointが1ずつ減っていくため、最終的に7になる。

    private Animal createAnyAnimal() {
        return new Dog();
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_4th_toMethod() {
        Dog dog = new Dog();
        doAnimalSeaLand_for_4th(dog);
    }

    private void doAnimalSeaLand_for_4th(Animal animal) {
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => wan
        int land = animal.getHitPoint();
        log(land); // your answer? => 7
    }
    //1220自分なりの回答
    //doAnimalSeaLand_for_4thメソッドでAnimal型の変数を引数に取り、barkメソッドを呼び出す。
    //barkメソッドでは、getBarkWordメソッドが呼び出され、"wan"が返される。
    //getHitPointメソッドでは、hitPointが1ずつ減っていくため、最終的に7になる。

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_5th_overrideWithSuper() {
        Animal animal = new Cat();
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => nya-
        int land = animal.getHitPoint();
        log(land); // your answer? => 5
    }
    //1220自分なりの回答
    //doAnimalSeaLand_for_5thメソッドでAnimal型の変数を引数に取り、barkメソッドを呼び出す。
    //barkメソッドでは、getBarkWordメソッドが呼び出され、"nya-"が返される。
    //getHitPointメソッドでは、CatクラスでhitPointが偶数の場合は1ずつ減っていくため、8,6の時のみ減り最終的に5になる。

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_6th_overriddenWithoutSuper() {
        Animal animal = new Zombie();
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => uooo
        int land = animal.getHitPoint();
        log(land); // your answer? => -1
    }
    //1220自分なりの回答
    //doAnimalSeaLand_for_6thメソッドでAnimal型の変数を引数に取り、barkメソッドを呼び出す。
    //barkメソッドでは、getBarkWordメソッドが呼び出され、"uooo"が返される。
    //getHitPointメソッドでは、ZombieクラスでhitPointが-1になるため、最終的に-1になる。

    /**
     * What is happy if you can assign Dog or Cat instance to Animal variable? <br>
     * (Animal型の変数に、DogやCatなどのインスタンスを代入できると何が嬉しいのでしょう？)
     */
    public void test_objectOriented_polymorphism_7th_whatishappy() {
        // write your memo here:
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // what is happy?
        //同じメソッドを使って、異なるclassのインスタンスを扱えるようになる。
        //抽象的なメソッドを作ることで拡張性が高くなり汎用的に使える。
        // _/_/_/_/_/_/_/_/_/_/
    }

    // ===================================================================================
    //                                                              Polymorphism Interface
    //                                                              ======================
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_interface_dispatch() {
        Loudable loudable = new Zombie();
        String sea = loudable.soundLoudly();
        log(sea); // your answer? => 
        String land = ((Zombie) loudable).bark().getBarkWord();
        log(land); // your answer? => 
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_interface_hierarchy() {
        Loudable loudable = new AlarmClock();
        String sea = loudable.soundLoudly();
        log(sea); // your answer? => 
        boolean land = loudable instanceof Animal;
        log(land); // your answer? => 
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_interface_partImpl() {
        Animal seaAnimal = new Cat();
        Animal landAnimal = new Zombie();
        boolean sea = seaAnimal instanceof FastRunner;
        log(sea); // your answer? => 
        boolean land = landAnimal instanceof FastRunner;
        log(land); // your answer? => 
    }

    /**
     * Make Dog class implement FastRunner interface. (the method implementation is same as Cat class) <br>
     * (DogもFastRunnerインターフェースをimplementsしてみましょう (メソッドの実装はCatと同じで))
     */
    public void test_objectOriented_polymorphism_interface_runnerImpl() {
        // your confirmation code here
    }

    /**
     * What is difference as concept between abstract class and interface? <br>
     * (抽象クラスとインターフェースの概念的な違いはなんでしょう？)
     */
    public void test_objectOriented_polymorphism_interface_whatisdifference() {
        // write your memo here:
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // what is difference?
        //
        // _/_/_/_/_/_/_/_/_/_/
    }

    // ===================================================================================
    //                                                                 Polymorphism Making
    //                                                                 ===================
    /**
     * Make concrete class of Animal, which is not FastRunner, in "objanimal" package. (implementation is as you like) <br>
     * (FastRunnerではないAnimalクラスのコンクリートクラスをobjanimalパッケージに作成しましょう (実装はお好きなように))
     */
    public void test_objectOriented_polymorphism_makeConcrete() {
        // your confirmation code here
    }

    /**
     * Make interface implemented by part of Animal concrete class in new package under "objanimal" package. (implementation is as you like) <br>
     * (Animalクラスの一部のコンクリートクラスだけがimplementsするインターフェースをobjanimal配下の新しいパッケージに作成しましょう (実装はお好きなように))
     */
    public void test_objectOriented_polymorphism_makeInterface() {
        // your confirmation code here
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Extract St6MySql, St6PostgreSql (basic.st6.dbms)'s process to abstract class (as super class and sub-class) <br>
     * (St6MySql, St6PostgreSql (basic.st6.dbms) から抽象クラスを抽出してみましょう (スーパークラスとサブクラスの関係に))
     */
    public void test_objectOriented_writing_generalization_extractToAbstract() {
        // your confirmation code here
    }

    /**
     * Extract St6OperationSystem (basic.st6.os)'s process to concrete classes (as super class and sub-class) <br>
     * (St6OperationSystem (basic.st6.os) からコンクリートクラスを抽出してみましょう (スーパークラスとサブクラスの関係に))
     */
    public void test_objectOriented_writing_specialization_extractToConcrete() {
        // your confirmation code here
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * Extract Animal's bark() process as BarkingProcess class to also avoid large abstract class. <br>
     * (抽象クラス肥大化を抑制するためにも、Animalのbark()のプロセス(処理)をBarkingProcessクラスとして切り出しましょう)
     */
    public void test_objectOriented_writing_withDelegation() {
        // your confirmation code here
    }

    /**
     * Put barking-related classes, such as BarkingProcess and BarkedSound, into sub-package. <br>
     * (BarkingProcessやBarkedSoundなど、barking関連のクラスをサブパッケージにまとめましょう)
     * <pre>
     * e.g.
     *  objanimal
     *   |-barking
     *   |  |-BarkedSound.java
     *   |  |-BarkingProcess.java
     *   |-loud
     *   |-runner
     *   |-Animal.java
     *   |-Cat.java
     *   |-Dog.java
     *   |-...
     * </pre>
     */
    public void test_objectOriented_writing_withPackageRefactoring() {
        // your confirmation code here
    }

    /**
     * Is Zombie correct as sub-class of Animal? Analyze it in thirty seconds. (thinking only) <br>
     * (ゾンビは動物クラスのサブクラスとして適切でしょうか？30秒だけ考えてみましょう (考えるだけでOK))
     */
    public void test_objectOriented_zoo() {
        // write your memo here:
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // is it correct?
        //
        // _/_/_/_/_/_/_/_/_/_/
    }
}
