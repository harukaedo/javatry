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

import java.io.File;
import java.io.IOException;

import org.docksidestage.bizfw.basic.supercar.SupercarClient;
import org.docksidestage.javatry.basic.st7.St7BasicExceptionThrower;
import org.docksidestage.javatry.basic.st7.St7ConstructorChallengeException;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of variable. <br>
 * Operate as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りに実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author harukaedo
 */
public class Step07ExceptionTest extends PlainTestCase {

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_exception_basic_catchfinally() {
        St7BasicExceptionThrower thrower = new St7BasicExceptionThrower();
        StringBuilder sea = new StringBuilder();
        try {
            thrower.land();
            sea.append("dockside");
        } catch (IllegalStateException e) {
            sea.append("hangar");
        } finally {
            sea.append("broadway");
        }
        log(sea); // your answer? =>hangarbroadway
    }
    //0413自分なりの回答
    //thrower.land();が呼ばれてSt7BasicExceptionThrower内でprivate void onemanまで進む。
    //ここではIllegalStateExceptionが例外としてthrowされるのでtryは実行されない。
    //catchでsea.append("hangar");が実行される
    //finallyは必ず実行され、hangarbroadwayとなる。

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_exception_basic_message() {
        St7BasicExceptionThrower thrower = new St7BasicExceptionThrower();
        String sea = null;
        try {
            thrower.land();
            fail("no way here");
        } catch (IllegalStateException e) {
            sea = e.getMessage();
        }
        log(sea); // your answer? =>oneman at showbase
    }
    //0413自分なりの回答
    //同じく、thrower.land();が呼ばれてSt7BasicExceptionThrower内でprivate void onemanまで進む。
    //IllegalStateExceptionが例外としてthrowされるのでtryは実行されない。
    //catchでIllegalStateException側のイベントとしてstringをメッセージとして受け取る
    //(例外オブジェクトのコンストラクタに渡された文字列を取得)

    /**
     * What class name and method name and row number cause the exception? (you can execute and watch logs) <br>
     * (例外が発生したクラス名とメソッド名と行番号は？(実行してログを見て良い))
     */
    public void test_exception_basic_stacktrace() {
        St7BasicExceptionThrower thrower = new St7BasicExceptionThrower();
        try {
            thrower.land();
            fail("no way here");
        } catch (IllegalStateException e) {
            log(e);
        }
        // your answer? => St7BasicExceptionThrower,oneman,40
    }
    //0413自分なりの回答
    //クラス自体はsrc/test/java/org/docksidestage/javatry/basic/st7/St7BasicExceptionThrower.javaの
    //St7BasicExceptionThrower
    //例外をIllegalStateExceptionとして流すメソッドはonemanであり、例外自体は40行目で流している

    // ===================================================================================
    //                                                                           Hierarchy
    //                                                                           =========
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_exception_hierarchy_Runtime_instanceof_RuntimeException() {
        Object exp = new IllegalStateException("mystic");
        boolean sea = exp instanceof RuntimeException;
        log(sea); // your answer? => true
    }
    //0413自分なりの回答
    //IllegalStateExceptionはclass IllegalStateException extends RuntimeException
    //となっているためRuntimeExceptionを継承していることになっている
    //そのため、回答もtrueになる。

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_exception_hierarchy_Runtime_instanceof_Exception() {
        Object exp = new IllegalStateException("mystic");
        boolean sea = exp instanceof Exception;
        log(sea); // your answer? => true
    }
    //0413自分なりの回答
    //上の問題と同様、IllegalStateExceptionはclass IllegalStateException extends RuntimeException
    //となっているためRuntimeExceptionを継承していることになっている
    //さらにRuntimeExceptionはRuntimeException extends Exceptionとなっているため
    //結局はExceptionも継承してると言える。
    //the 階層構造


    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_exception_hierarchy_Runtime_instanceof_Error() {
        Object exp = new IllegalStateException("mystic");
        boolean sea = exp instanceof Error;
        log(sea); // your answer? => false
    }
    //0413自分なりの回答
    //Errorはclass Error extends ThrowableとなっていてIllegalStateException自体は同じThrowableを継承しているが
    // 別の継承のツリーにいるため継承しているとは言えない。
    //そのため回答もfalseになる。

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_exception_hierarchy_Runtime_instanceof_Throwable() {
        Object exp = new IllegalStateException("mystic");
        boolean sea = exp instanceof Throwable;
        log(sea); // your answer? => true
    }
    //0413自分なりの回答
    //IllegalStateExceptionは先ほどtest_exception_hierarchy_Runtime_instanceof_Exceptionにて
    //回答したException extends Throwableとなっていて、これも階層構造になっているので結局継承していることになる
    //階層構造

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_exception_hierarchy_Throwable_instanceof_Exception() {
        Object exp = new Throwable("mystic");
        boolean sea = exp instanceof Exception;
        log(sea); // your answer? => false
    }
    //0413自分なりの回答
    //Exception extends Throwableとなっているが、ExceptionはThrowableの子になるようなものなので
    //Throwable自体がExceptionを継承しているわけじゃない。
    //下記だったらtrueだった
    /*
    public void test_exception_hierarchy_Throwable_instanceof_Exception() {
        Object exp = new Exception("mystic");
        boolean sea = exp instanceof Throwable;
        log(sea); 
    }
     */

    // ===================================================================================
    //                                                                         NullPointer
    //                                                                         ===========
    /**
     * What variable (is null) causes the NullPointerException? And what row number? (you can execute and watch logs) <br>
     * (NullPointerが発生する変数(nullだった変数)と、発生する行番号は？(実行してログを見ても良い))
     */
    public void test_exception_nullpointer_basic() {
        try {
            String sea = "mystic";
            String land = sea.equals("mystic") ? null : "oneman";
            String lowerCase = land.toLowerCase();
            log(lowerCase);
        } catch (NullPointerException e) {
            log(e);
        }
        // your answer? => null.nullだった変数はland, 177行目
    }
    //0413自分なりの回答
    //三項演算子の書き方。『条件 ? A : B』
    //条件式が正しい場合はA（真）を取るためここでnullが値として入る
    //業務でもたまに使うけど、いつもどっちだったっけ？ってなる😅
    // done edo [ふぉろー] 確かに。一応順番イメージ true/false って感じではあるのかな。 by jflute (2026/04/26)
    // 一方で、三項演算子は乱用しない方が良いものという感覚ではある。シンプルな場面なら見やすいけど、込み入ってくると見づらい。
    // #1on1: if ... else ... で考えるといいかも (2026/05/08)

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_exception_nullpointer_headache() {
        try {
            String sea = "mystic";
            String land = !!!sea.equals("mystic") ? null : "oneman";
            String piari = !!!sea.equals("mystic") ? "plaza" : null;
            int sum = land.length() + piari.length();//6+0
            log(sum);
        } catch (NullPointerException e) {
            log(e);
        }
        // your answer? => 6
    }
    //0413
    //正しい回答　null
    //条件式の取る方は正しかったが、nullは文字の長さにはならないので、そもそも
    //計算に辿り着かなくて結局nullで終わってしまう。
    //計算に行かないためcatchに回されてnull扱いで終わる

    /**
     * Refactor to immediately understand what variable (is null) causes the NullPointerException by row number in stack trace. <br>
     * (どの変数がNullPointerを引き起こしたのか(nullだったのか)、スタックトレースの行番号だけでわかるようにリファクタリングしましょう)
     */
    public void test_exception_nullpointer_refactorCode() {
        try {
            String sea = "mystic";
            String land = !!!sea.equals("mystic") ? null : "oneman";//oneman,6
            String piari = !!!sea.equals("mystic") ? "plaza" : null;//null
            int sum = land.length() + piari.length();//計算が成り立たないのでcatchへ
            log(sum);
        } catch (NullPointerException e) {
            // done edo これだと結局、どっちの変数がnullだったのかわからない by jflute (2026/04/26)
            log(e);//ここでnull
        }
    }

    public void test_exception_nullpointer_refactorCode_refactor() {
        try {
            String sea = "mystic";
            String land = !sea.equals("mystic") ? null : "oneman";//oneman,6
            String piari = !sea.equals("mystic") ? "plaza" : null;//null
            // #1on1: 行を分けたことで、スタックトレースの行から変数を特定できるようになった (2026/05/08)
            // ただ、これを常にやるかといったらなかなか面倒なのでやったりやらなかったりではある。
            // ここでは、スタックトレースの行番号からコードを追う習慣を学んでもらうことの方が大事。
            int landLength = land.length(); //6
            int piariLength = piari.length(); //null -> この時点で、nullになるのでどの変数でNullPointerを引き起こしたのかがわかるようになる
            int sum = landLength + piariLength;//ぱっとみはどっちがnullかこれだけだとわからない。
            log(sum);
        } catch (NullPointerException e) {
            log(e);//ここでnull
        }
    }
    //0428 修正メモ：
    //まず、条件式の前にある『!!!』は、否定の否定の否定で、結局は『!』と同じ意味になっていて進次郎構文みたいになっていたので修正してわかりやすくした
    //さらに、nullがどっちの変数で発生しているのかがわからない状態だったので、変数を分けて、最後にlengthとlemgthを足すことで
    // どこでNullPointerExceptionが発生しているのかがわかるようにした。

    // ===================================================================================
    //                                                                   Checked Exception
    //                                                                   =================
    /**
     * Show canonical path of new java.io.File(".") by log(), and if I/O error, show message and stack-trace instead <br>
     * (new java.io.File(".") の canonical path を取得してログに表示、I/Oエラーの時はメッセージとスタックトレースを代わりに表示)
     */
    public void test_exception_checkedException_basic() {
        File file = new File("."); //今いるディレクトリを指す
        try {
            String canonicalPath = file.getCanonicalPath(); //今いる正規化されたパスを取得する
            log(canonicalPath);
        } catch (IOException e) {
            log(e.getMessage(), e);
        }
    }
    //0428 memo
    //I/Oエラーとは?
    //https://qiita.com/pitan109/items/c9910edddc007126df41
    //I/Oエラーは、Input/Outputのエラーで、ファイルの読み書きやネットワーク通信などの入出力処理で発生するエラーのこと。
    //エラーが起きてしまった場合、メッセージとスタックトレースを代わりに表示とのことだったので
    //catchブロックで、IOExceptionが発生したときに、e.getMessage()でエラーメッセージを取得し、eを渡すことでスタックトレースも表示するようにした。

    // #1on1: チェック例外とは？ (2026/05/08)
    // throwされた例外をcatchする義務が生まれる (or さらに呼び出し側にthrowsでたらい回しにする)
    // それをやらないと、コンパイルエラーになる。catchの強制力のある機能。
    // 一見便利そうな機能ではある。catchのし忘れを未然に防いでくれる。
    // ただ、Javaでこの機能、ぜんぜん流行ってない。なぜ？
    // 家の鍵の話。
    // 頭の上の隕石の話。
    // 確率の違い。
    // 隕石ぐらいのレベルのところで、チェック例外がたくさん使われていたら...面倒でしょうがない。
    // 既存のチェック例外が、けっこうそれに違い場面でたくさん使われていて、印象とても悪い。
    // (他にも複合的に色々な要因はあるだろうけど)
    // TicketSoldOutException は本来チェック例外でも良さそうな場面だけど...
    // 流行ってないから使ってもわりとみんなビックリするし、まあいいやって感じなる。
    // (世の中、便利でも時々しか使わないものだと、使い方を忘れたりとかで使わなくなっちゃうものある)
    //
    // チェック例外を覚える話というより、
    // 例外ハンドリングをするのか？しないのか？の判断基準の要因を学んで欲しい。
    // 確率の違いで、ぼくらは「まあいいや」になったりする。

    // done jflute 次回1on1ここから (2026/05/08)
    // ===================================================================================
    //                                                                               Cause
    //                                                                               =====
    /**
     * What string is sea variable in the catch block?
     * And What is exception class name displayed at the last "Caused By:" of stack trace? <br>
     * (catchブロックの変数 sea, land の中身は？また、スタックトレースの最後の "Caused By:" で表示されている例外クラス名は？)
     */
    public void test_exception_cause_basic() {
        String sea = "mystic";
        String land = "oneman";
        try {
            throwCauseFirstLevel();
            fail("always exception but none");
        } catch (IllegalStateException e) {
            Throwable cause = e.getCause();
            sea = cause.getMessage();
            land = cause.getClass().getSimpleName();
            log(sea); // your answer? => Failed to call the second help method: symbol= - 1
            log(land); // your answer? => IllegalArgumentException
            log(e); // your answer? => NumberFormatException
        }
    }

    // #1on1: 例外が例外を保持できるようになっている (2026/05/19)
    // それぞれのレイヤーごとのニュアンスにどんどん差し代わっている。
    // それぞれのレイヤーごとの関連データの情報を保持して表示することができる。
    private void throwCauseFirstLevel() { //1段階
        int symbol = Integer.MAX_VALUE - 0x7ffffffe; //2147483647 - 2147483647 = 1
        // 16進数直す(15*16^7+15*16^6+15*16^5+15*16^4+15*16^3+15*16^2+15*16^1+14*16^0)
        try {
            throwCauseSecondLevel(symbol); // => throwCauseSecondLevel(1)
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Failed to call the second help method: symbol=" + symbol, e);
        }
    }

    private void throwCauseSecondLevel(int symbol) { //2段階
        try {
            --symbol;
            symbol--;
            if (symbol < 0) {
                throwCauseThirdLevel(symbol); // => throwCauseThirdLevel(-1)
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Failed to call the third help method: symbol=" + symbol, e); //これが発生
        }
    }

    private void throwCauseThirdLevel(int symbol) { //3段階
        if (symbol < 0) {
            Integer.valueOf("piari"); //symbolが-1のため、Integer.valueOf("piari")でNumberFormatExceptionが発生する
        }
    }

    // ===================================================================================
    //                                                                         Translation
    //                                                                         ===========
    /**
     * Execute this test and read exception message and write situation and cause on comment for non-programmer. <br>
     * テストを実行して例外メッセージを読んで、プログラマーでない人にもわかるように状況と原因をコメント上に書きましょう。
     */
    public void test_exception_translation_debugChallenge() {
        try {
            new SupercarClient().buySupercar();
            fail("always exception but none");
        } catch (RuntimeException e) {
            log("*No hint here for training.", e);
            // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
            // What happens? Write situation and cause here. (何が起きた？状況と原因をここに書いてみましょう)
            // - - - - - - - - - -
            //[状況]
            //顧客から注文を受け、販売業者が工場に発注→工場で部品を組み立て→完成したスーパーカーを顧客に納品する処理の一連の流れを追っていくコードになっている。
            //顧客が出した注文に対して、ネジ工場の工程で「このネジは作れません」というエラーが発生し、
            //車の組み立てを完了できなかった。
            //
            //[原因]
            //1.販売店から製造工場へ発注する際、顧客の要望とは異なるハンドルのカタログ番号で発注してしまった。
            //2.その種類のハンドルに使うネジは、現在は製造中止となっており、ネジ工場では生産できなくなっていた。
            //3.そのため、ネジ工場で「このネジは作れません」というエラーが発生し、車の組み立てを完了できなかった。
            // _/_/_/_/_/_/_/_/_/_/
            // #1on1: SuperCarの流れの確認、Good (2026/05/19)
            // Causeの活用のお話。それを踏まえて、次のエクササイズやってみてください。
        }
    }

    /**
     * Improve exception handling in supercar's classes to understand the situation
     * by only exception information as far as possible. <br>
     * できるだけ例外情報だけでその状況が理解できるように、Supercarのクラスたちの例外ハンドリングを改善しましょう。
     */
    //0525修正メモ
    //SpecialScrewManufacturerでscrewSpec をメッセージに入れることで、どのスペックのネジが作れなかったのかがわかるようにした。
    //SupercarSteeringWheelManufacturerで、ネジの製造に失敗する可能性があるため、例外処理を追加し、どのハンドルのカタログ番号で発注したのか、どのスペックのネジが作れなかったのかがわかるようにした。
    //SupercarManufacturerで、ハンドルの製造に失敗する可能性があるため、例外処理を追加し、どのハンドルのカタログ番号で発注したのか、どのスペックのネジが作れなかったのかがわかるようにした。
    //SupercarDealerで、販売店から製造工場へ発注する際、顧客の要望とは異なるハンドルのカタログ番号で発注してしまった可能性があるため、例外処理を追加し、どのハンドルのカタログ番号で発注したのかがわかるようにした。

    //例外メッセージ
    /*
    2026-05-25 18:18:32,272 [main] DEBUG (PlainTestCase@log():705) - <<< Step07ExceptionTest.test_exception_translation_improveChallenge() >>>
    2026-05-25 18:18:32,273 [main] DEBUG (PlainTestCase@log():709) - *No hint here for training.
    java.lang.RuntimeException: Failed to order supercar because of manufacturing failure: clientRequirement=steering wheel is like sea
	at org.docksidestage.bizfw.basic.supercar.SupercarDealer.orderSupercar(SupercarDealer.java:41)
	at org.docksidestage.bizfw.basic.supercar.SupercarClient.buySupercar(SupercarClient.java:34)
	at org.docksidestage.javatry.basic.Step07ExceptionTest.test_exception_translation_improveChallenge(Step07ExceptionTest.java:396)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at junit.framework.TestCase.runTest(TestCase.java:168)
	at org.docksidestage.unit.PlainTestCase.runTest(PlainTestCase.java:98)
	at junit.framework.TestCase.runBare(TestCase.java:134)
	at junit.framework.TestResult$1.protect(TestResult.java:110)
	at junit.framework.TestResult.runProtected(TestResult.java:128)
	at junit.framework.TestResult.run(TestResult.java:113)
	at junit.framework.TestCase.run(TestCase.java:124)
	at junit.framework.TestSuite.runTest(TestSuite.java:243)
	at junit.framework.TestSuite.run(TestSuite.java:238)
	at org.junit.internal.runners.JUnit38ClassRunner.run(JUnit38ClassRunner.java:83)
	at org.junit.runner.JUnitCore.run(JUnitCore.java:157)
	at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:73)
	at com.intellij.rt.junit.IdeaTestRunner$Repeater$1.execute(IdeaTestRunner.java:38)
	at com.intellij.rt.execution.junit.TestsRepeater.repeat(TestsRepeater.java:11)
	at com.intellij.rt.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:35)
	at com.intellij.rt.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:244)
	at com.intellij.rt.junit.JUnitStarter.main(JUnitStarter.java:65)
    Caused by: java.lang.RuntimeException: Failed to make supercar because of steering wheel manufacturing failure: catalogKey=piari, steeringWheelId=3
	at org.docksidestage.bizfw.basic.supercar.SupercarManufacturer.makeSupercar(SupercarManufacturer.java:39)
	at org.docksidestage.bizfw.basic.supercar.SupercarDealer.orderSupercar(SupercarDealer.java:31)
	... 23 common frames omitted
    Caused by: java.lang.RuntimeException: Failed to make steering wheel because of screw manufacturing failure: steeringWheelId=3, screwSpec=ScrewSpec:{\(^_^)/}
	at org.docksidestage.bizfw.basic.supercar.SupercarSteeringWheelManufacturer.makeSteeringWheel(SupercarSteeringWheelManufacturer.java:46)
	at org.docksidestage.bizfw.basic.supercar.SupercarManufacturer.makeSupercar(SupercarManufacturer.java:36)
	... 24 common frames omitted
    Caused by: org.docksidestage.bizfw.basic.screw.exception.ScrewCannotMakeBySpecException: Cannot make special screw because the kawaii face is already unsupported: screwSpec=ScrewSpec:{\(^_^)/}
	at org.docksidestage.bizfw.basic.screw.SpecialScrewManufacturer.makeSpecialScrew(SpecialScrewManufacturer.java:31)
	at org.docksidestage.bizfw.basic.supercar.SupercarSteeringWheelManufacturer.makeSteeringWheel(SupercarSteeringWheelManufacturer.java:43)
	... 25 common frames omitted
     */
    // #1on1: しっかり例外の翻訳ができていて、デバッグ情報も充実している (2026/06/05)
    // RuntimeExecption祭りはちょい雑だけど、まあご愛嬌。
    public void test_exception_translation_improveChallenge() {
        try {
            new SupercarClient().buySupercar(); // you can fix the classes
            fail("always exception but none");
        } catch (RuntimeException e) {
            log("*No hint here for training.", e);
        }
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Fix terrible (YABAI in Japanese) exception handling. (you can modify exception class) <br>
     * (やばい例外ハンドリングがあるので修正しましょう (例外クラスを修正してOK))
     */
    public void test_exception_writing_constructorChallenge() {
        try {
            helpSurprisedYabaiCatch();
        } catch (St7ConstructorChallengeException e) {
            log("Thrown by help method", e); // should show also "Caused-by" information
        }
    }

    private void helpSurprisedYabaiCatch() {
        try {
            helpThrowIllegalState();
        } catch (IllegalStateException e) {
            //0525修正メモ eを渡すことで、helpThrowIllegalStateで発生したIllegalStateExceptionが原因として保持されるようにした。
            //IllegalStateExceptionではimportantValueが何だったかが書いてあるはずなのに、catch側でそのeを繋がずに、
            //新しい例外を投げてしまうことで元の例外スタックトレースも消えてしまう
            //catch 側で cause として渡す
            // #1on1: 例外の握りつぶしの話 (2026/06/05)
            throw new St7ConstructorChallengeException("Failed to do something.", e);
        }
    }

    private void helpThrowIllegalState() {
        if (true) { // simulate something illegal
            String importantValue = "dummy"; // important to debug
            throw new IllegalStateException("something illegal: importantValue=" + importantValue);
        }
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * What is the concept difference between Exception and Error? Write it on comment. <br>
     * (ExceptionとErrorのコンセプトの違いはなんでしょうか？コメント上に書きましょう)
     */
    public void test_exception_zone_differenceExceptionError() {
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // Write here. (ここに書いてみましょう)
        // - - - - - - - - - -
        //ExceptionとErrorは、throw/catchできる仕組みは同じ。
        //何が違うか？
        //Exception（例外）
        //想定内のトラブル。アプリケーションレベルでの異常になるため、プログラム上で想定してあげて適切に処理してあげる必要がある。
        //例）ファイルが見つからない、不正な引数が入ってくる、ユーザーの入力が不正など。
        //
        //Error（エラー）
        //想定外のトラブル。システムレベルでの異常になるため、プログラム上で想定してあげることが難しい。基本的には、プログラム上で処理することはできない。
        //例）メモリ不足、スタックオーバーフローなど。
        //
        //車の運転で例えると....
        //Exceptionは、車の運転中に起こるトラブルで、例えば、ガソリンが切れた、タイヤがパンクしたなどなど...
        // これらは、事前に想定しておくことができるため、適切な対処をすることができる！
        //Errorは、車の運転中に起こる想定外のトラブルで、例えば、ブレーキが完全に故障した、エンジンが突然停止したなど...
        //事前に想定しておくことが難しいので制御しようがない！
        // _/_/_/_/_/_/_/_/_/_/
        
        // #1on1: エラーにはダメというニュアンス、例外はただの例外(正常なレアケース、エラーかまだわからない) (2026/06/05)
        // throwする瞬間は、その事象がエラーかどうかは、立場的にわからない。ので、例外としてthrow。
        // catchする人は全体像を知っているので、その人がエラーなのか？正常なレアケースなのか？判断。
        // エラーハンドリング自体は、ログ出したりとかで、Errorクラスをthrowするとかないので、Errorクラスを目にすることがない。
        // throwされている途中はまだ例外(まだエラーかどうかわからない状態)なので、throwされるのはException。
        // JavaのErrorクラスは、throwする瞬間からダメというのが判断できるものばかり。
        //
        // 他の言語だと、ErrorがExceptionを継承するものも。Exceptionが抽象概念。
        // Python, Ruby, TypeScript, Go
        // 使ってる言葉が違うだけで、やってることみんな同じ。
    }
}
