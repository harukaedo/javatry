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
import org.docksidestage.bizfw.basic.buyticket.TicketBooth.TicketShortMoneyException;
import org.docksidestage.bizfw.basic.buyticket.TicketBuyResult;
import org.docksidestage.bizfw.basic.buyticket.TicketType;
import org.docksidestage.unit.PlainTestCase;

// #1on1: 命名デザイン、コメントデザイン (2025/09/05)
// プログラマーに求められるデザイン脳
// https://jflute.hatenadiary.jp/entry/20170623/desigraming
// 「gitコミットメッセージもコメントデザイン？」by えどさん => yes
// o 想像力
// o gitコミットメッセージを読む体験をする (たくさんたくさんしてください)

/**
 * The test of class. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう) <br>
 * 
 * If ambiguous requirement exists, you can determine specification that seems appropriate. <br>
 * (要件が曖昧なところがあれば、適切だと思われる仕様を決めても良いです)
 * 
 * @author jflute
 * @author harukaedo
 */
public class Step05ClassTest extends PlainTestCase {

    // ===================================================================================
    //                                                                          Helper
    //                                                                          =========
    /**
     * Create a new TicketBooth instance for testing.
     * @return New TicketBooth instance
     */
    private TicketBooth createBooth() {
        return new TicketBooth();
    }

    // ===================================================================================
    //                                                                          How to Use
    //                                                                          ==========
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_class_howToUse_basic() {
        TicketBooth booth = createBooth();
        booth.buyOneDayPassport(7400);
        int sea = booth.getQuantity();
        log(sea); // your answer? =>9
    }
    //0825自分なりの解釈
    //正しい回答は9。
    //quantityはMAX_QUANTITY（10）初期化
    //buyOneDayPassportメソッドで1枚購入されるため、9になる

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_class_howToUse_overpay() {
        TicketBooth booth = createBooth();
        booth.buyOneDayPassport(10000);
        Integer sea = booth.getSalesProceeds();
        log(sea); // your answer? => 10000
    }

    //0825自分なり解釈
    //buyOneDayPassportでsalesProceeds = handedMoney;が実行され10000がそのまま反映される


    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_class_howToUse_nosales() {
        TicketBooth booth = createBooth();
        Integer sea = booth.getSalesProceeds();
        log(sea); // your answer? => null
    }

    //0825自分なりの解釈
    //正しい回答はnull
    //TicketBooth booth = new TicketBooth();新しいTicketBoothインスタンスを作成
    //初期状態：
    //quantity = 10（チケット残数）
    //salesProceeds = null（まだ一度も売上がない）
    //Integer sea = booth.getSalesProceeds();
    //チケットを一度も購入していない状態で売上を取得
    //TicketBoothクラスのsalesProceedsフィールドはInteger型で、初期値はnull
    //何も購入していないので、売上はnullのまま

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_class_howToUse_wrongQuantity() {
        Integer sea = doTest_class_ticket_wrongQuantity();
        log(sea); // your answer? => 9
    }

    //0908自分なりの解釈
    //doTest_class_ticket_wrongQuantityメソッドを実行
    //TicketBooth booth = new TicketBooth();で新しいTicketBoothインスタンスを作成
    //int handedMoney = 7399;で7399を handedMoney に代入
    //try {
    //    booth.buyOneDayPassport(handedMoney);で7399を購入
    //} catch (TicketShortMoneyException continued) {で7399を購入
    //お金が不足している場合でも購入できてしまう

    //class修正後
    //log(sea) => 10
    //7399ではticketを購入することはできないので--quantity;にならない

    private Integer doTest_class_ticket_wrongQuantity() {
        TicketBooth booth = createBooth();
        int handedMoney = 7399;
        try {
            booth.buyOneDayPassport(handedMoney);
            fail("always exception but none");
        } catch (TicketShortMoneyException continued) {
            log("Failed to buy one-day passport: money=" + handedMoney, continued);
        }
        return booth.getQuantity();
    }

    // ===================================================================================
    //                                                                           Let's fix
    //                                                                           =========
    /**
     * Fix the problem of ticket quantity reduction when short money. (Don't forget to fix also previous exercise answers) <br>
     * (お金不足でもチケットが減る問題をクラスを修正して解決しましょう (以前のエクササイズのanswerの修正を忘れずに))
     */
    public void test_class_letsFix_ticketQuantityReduction() {
        Integer sea = doTest_class_ticket_wrongQuantity();
        log(sea); // should be max quantity, visual check here
        
        // #1on1: パズルエクササイズ (2025/09/05)
        /*
        //if (handedMoney(手渡し金額) >= ONE_DAY_PRICE(1枚の料金)) { ??
            --quantity; // 在庫を一つマイナス
        //}
        if (handedMoney(手渡し金額) < ONE_DAY_PRICE(1枚の料金)) {
            quantity++; ??
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        }
         */

        //0910自分なりの解釈
        //ticketを売る処理　if (handedMoney < ONE_DAY_PRICE) がticketの枚数を減らす処理の後に来ていたため
        //ticketがあれば無条件でticketを減らしてしまっていた。
        //if (handedMoney < ONE_DAY_PRICE)を減らす処理の前に行うことで持っているお金がticketの金額より多かった時のみ
        //ticketの枚数を減らすことができる
        
        // #1on1: 順番逆転のバグは、システムが分かれたり、クラスが分かれたりすると、隠されがち (2025/09/19)
        // 処理の流れをわかりやすく表現しておくってのも大切。
        // 以前、FizzBuzzのエクササイズで順番の大切さを痛感したことがある by えどさん
    }

    /**
     * Fix the problem of sales proceeds increased by handed money. (Don't forget to fix also previous exercise answers) <br>
     * (受け取ったお金の分だけ売上が増えていく問題をクラスを修正して解決しましょう (以前のエクササイズのanswerの修正を忘れずに))
     */
    public void test_class_letsFix_salesProceedsIncrease() {
        TicketBooth booth = createBooth();
        booth.buyOneDayPassport(10000);
        Integer sea = booth.getSalesProceeds();
        log(sea); // should be same as one-day price, visual check here
    }

        //0910自分なりの回答
        //buyOneDayPassportChangeメソッドを作成し、handMoneyがONE_DAY_PRICEより多い時はお釣りとして返し
        //売上金が正しい値になる
        
    /**
     * Make method for buying two-day passport (price is 13200). (which can return change as method return value)
     * (TwoDayPassport (金額は13200) も買うメソッドを作りましょう (戻り値でお釣りをちゃんと返すように))
     */
    public void test_class_letsFix_makeMethod_twoday() {
        TicketBooth booth = createBooth();
        int money = 14000;
        // done edo コンパイルエラーが出ています。 by jflute (2025/10/03)
        // リファクタリングなど修正を入れたら、全体がおかしくなってないか？確認する習慣を
        //1007 memo: コンパイルエラーを修正しました
        TicketBuyResult change = booth.buyTwoDayPassport(money);
        Integer sea = booth.getSalesProceeds() + change.getChange();
        log(sea); // should be same as money

        // and show two-day passport quantity here
        //0910自分なりの回答
        //TicketBooth.javaにbuyTwoDayPassportメソッドを生成し
        //2日分チケット13200円がhandmoneyよりも安いかどうかを見てチケットを売る場合は２枚ずつ減らしていく。
        //handmoneyが13200円よりも多い場合、お釣りとして返す処理を行う。
    }
    

    //0910メモ
    //TwoDayチケットの購入メソッドのロジックがあっているかわからないのでFB後取り組みます
    // #1on1: 確認してロジックOKなので、再利用エクササイズ進んじゃってOKです。
    // ↓こことつながる話でとても良い姿勢です。
    // // 別に、プルリクレビューの前にレビューしてもらっていいんだからね
    // https://jflute.hatenadiary.jp/entry/20170630/reviewbefore

    // done edo こちらのエクササイズもどこかでやってみてください。再利用メソッドを作る by jflute (2025/10/03)
    /**
     * Recycle duplicate logics between one-day and two-day by e.g. private method in class. (And confirm result of both before and after) <br>
     * (OneDayとTwoDayで冗長なロジックがあったら、クラス内のprivateメソッドなどで再利用しましょう (修正前と修正後の実行結果を確認))
     */
    public void test_class_letsFix_refactor_recycle() {
        TicketBooth booth = createBooth();
        booth.buyOneDayPassport(10000);
        log(booth.getQuantity(), booth.getSalesProceeds()); // should be same as before-fix
    }

    //1007 自分なりの回答
    //checkBuyTicketとcheckTicketQuantityメソッドを作成し、チケットが買えるかどうかのチェックとチケットが購入されたら枚数を減らす処理を
    //共通化した。
    //checkBuyTicketメソッドでチケットが買えるかどうかのチェックを行う。
    //checkTicketQuantityメソッドでチケットが購入されたら枚数を減らす処理を行う。
    //buyOneDayPassportとbuyTwoDayPassportでcheckBuyTicketとcheckTicketQuantityメソッドを呼び出し
    //再利用してrefactorした。

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Now you cannot get ticket if you buy one-day passport, so return Ticket class and do in-park. <br>
     * (OneDayPassportを買ってもチケットをもらえませんでした。戻り値でTicketクラスを戻すようにしてインしましょう)
     */
    public void test_class_moreFix_return_ticket() {
        // uncomment out after modifying the method
        // #1on1: お釣りも戻さないといけないんじゃない？というえどさんの疑念は正しい (2025/10/03)
        //1007 1dayだけお釣りも戻っていないので、TicketBuyResultクラスを戻り値として返すようにし,
        TicketBooth booth = createBooth();
        TicketBuyResult oneDayPassport = booth.buyOneDayPassport(10000);
        Ticket ticket = oneDayPassport.getTicket();
        assertEquals(TicketType.ONE_DAY, ticket.getTicketType()); // TicketTypeで確認
        log(ticket.getDisplayPrice()); // should be same as one-day price
        log(ticket.isCurrentIn()); // should be false
        ticket.doInPark(10, 0); // 営業時間内（10時0分）で入園
        log(ticket.isCurrentIn()); // should be true
    }

    //0924自分なりの回答
    //TicketBooth.javaにbuyOneDayPassportメソッドを生成し
    //Ticketクラスを戻り値として返すようにした

    /**
     * Now also you cannot get ticket if two-day passport, so return class that has ticket and change. <br>
     * (TwoDayPassportもチケットをもらえませんでした。チケットとお釣りを戻すクラスを作って戻すようにしましょう)
     */
    public void test_class_moreFix_return_whole() {
        TicketBooth booth = createBooth();
        int handedMoney = 20000;
        TicketBuyResult buyResult = booth.buyTwoDayPassport(handedMoney);
        Ticket twoDayPassport = buyResult.getTicket();
        assertEquals(TicketType.TWO_DAY, twoDayPassport.getTicketType()); // TicketTypeで確認
        int change = buyResult.getChange();
        log(twoDayPassport.getDisplayPrice() + change); // should be same as money
    }

    //0926自分なりの回答
    //TicketBuyResultを適応し、チケットとお釣りを戻すようにした
    //getTicket(でチケットを取得し、getChange()でお釣りを取得した

    /**
     * Now you can use only one in spite of two-day passport, so fix Ticket to be able to handle plural days. <br>
     * (TwoDayPassportなのに一回しか利用できません。複数日数に対応できるようにTicketを修正しましょう)
     */
    public void test_class_moreFix_usePluralDays() {
        TicketBooth booth = createBooth();
        TicketBuyResult buyResult = booth.buyTwoDayPassport(13200);
        Ticket twoDayPassport = buyResult.getTicket();
        assertEquals(TicketType.TWO_DAY, twoDayPassport.getTicketType()); // TicketTypeで確認

        // 1日目の入園、退園
        twoDayPassport.doInPark(13, 0); // 営業時間内（13時0分）で入園
        log(twoDayPassport.isCurrentIn()); // trueになる
        twoDayPassport.doOutPark();
        log(twoDayPassport.getRestDays()); // 1日使われたため、残り1日になる
        // 使用後もTicketTypeは変わらないことを確認
        assertEquals(TicketType.TWO_DAY, twoDayPassport.getTicketType());

        // 2日目の入園、退園
        twoDayPassport.doInPark(16, 0); // 営業時間内（16時0分）で入園
        log(twoDayPassport.isCurrentIn()); // trueになる
        twoDayPassport.doOutPark();
        log(twoDayPassport.getRestDays()); // 残りの1日が使われたため、残りが0となる
        // 使用後もTicketTypeは変わらないことを確認
        assertEquals(TicketType.TWO_DAY, twoDayPassport.getTicketType());
    }

    /**
     * Accurately determine whether type of bought ticket is two-day passport or not by if-statemet. (fix Ticket classes if needed) <br>
     * (買ったチケットの種別がTwoDayPassportなのかどうかをif文で正確に判定してみましょう。(必要ならTicketクラスたちを修正))
     */
    public void test_class_moreFix_whetherTicketType() {
        TicketBooth booth = createBooth();
        showTicketIfNeeds(booth.buyOneDayPassport(10000).getTicket());
        showTicketIfNeeds(booth.buyTwoDayPassport(10000).getTicket());
    }

    // uncomment when you implement this exercise
    private void showTicketIfNeeds(Ticket ticket) {
       // done edo nightも紛れてしまう by jflute (2025/10/15)
       // #1on1: 当初は問題なかったロジックが、新しいものが追加されることで影響が出るパターン。 (2025/10/15)
       // 一行も直していないところで突然不具合が出るというやっかいなケース。
       //1024 修正メモ
       //TicketクラスにisNightOnlyTicketメソッドを生成し、夜専用チケットかどうかを確認するようにした
       //showTicketIfNeedsメソッドで残り日数だけで確認するのではなく、チケットの種別を判定するようにした
        // done edo 修行++: 昼間のみの2Dayのチケットが新しく追加されたら... by jflute (2025/10/31)
        // (hint: チケットの種類の業務の柔軟性を考えるとキリがないので、ピンポイントでTwoDayPassportを識別できるように)
        // (hint2: 種別の判定、何かしら列挙されて固定数を持つものの判定。チケットに限らない。なんでも種別の判定...)
        // #1on1: 一週間以内の話をした (2025/10/31)
        // done edo 修行#: 識別子を使ってチケット種別を正確に判定できるように修正 by harukaedo (2025/11/14)
        // hint1: 識別子 e.g. えどはるか(かに座)　えどはるか(みずがめ座) => キリがない (ので、マイナンバー!?)
        // 1121修正メモ：
        // TicketTypeを新たに作り、enum定義しました
        // TicketクラスにgetTicketTypeメソッドを生成し、チケットの種別を取得するようにした
        // showTicketIfNeedsメソッドでチケットの種別を判定するようにした
        //1104　インデントがおかしくて赤くなってしまっていたので少し修正
        // 識別子（TicketType）を使ってチケット種別を正確に判定
        TicketType ticketType = ticket.getTicketType();
        
        // 一応、元のコードの感じでやるとこう:
        if (ticketType.equals(TicketType.TWO_DAY)) {
            log("two-day passport");
        }
        
        switch (ticketType) {
            case ONE_DAY:
                log("one-day passport");
                break;
            case TWO_DAY:
                log("two-day passport");
                break;
            case FOUR_DAY:
                log("four-day passport");
                break;
            case NIGHT_ONLY_TWO_DAY:
                log("night-only two-day passport");
                break;
            case DAY_TIME_ONLY_TWO_DAY:
                log("day-time only two-day passport");
                break;
            default:
                log("other");
                break;
        }
    }

    //1007 自分なりの回答
    //TicketクラスにgetRestDaysメソッドがあるため、showTicketIfNeedsメソッドでチケットの
    //残り使用可能日数を取得し、チケットの種別を判定した

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * Fix it to be able to buy four-day passport (price is 22400). <br>
     * (FourDayPassport (金額は22400) のチケットも買えるようにしましょう)
     */
    public void test_class_moreFix_wonder_four() {
        TicketBooth booth = createBooth();
        TicketBuyResult buyResult = booth.buyFourDayPassport(22400);
        Ticket fourDayPassport = buyResult.getTicket();
        log(fourDayPassport.getRestDays()); // should be same as four-day passport
        assertEquals(TicketType.FOUR_DAY, fourDayPassport.getTicketType()); // TicketTypeで確認
    }

    //1009 自分なりの回答
    //TicketBooth.javaにbuyFourDayPassportメソッドを生成し
    //4日分チケット22400円がhandmoneyよりも安いかどうかを見てチケットを売る場合は４枚ずつ減らしていく。
    //handmoneyが22400円よりも多い場合、お釣りとして返す処理を行う。

    /**
     * Fix it to be able to buy night-only two-day passport (price is 7400), which can be used at only night. <br>
     * (NightOnlyTwoDayPassport (金額は7400) のチケットも買えるようにしましょう。夜しか使えないようにしましょう)
     */
    public void test_class_moreFix_wonder_night() {
        TicketBooth booth = createBooth();
        TicketBuyResult buyResult = booth.buyNightOnlyTwoDayPassport(7400);
        Ticket nightOnlyTwoDayPassport = buyResult.getTicket();

        log("this ticket is night-only two-day passport");
        assertEquals(TicketType.NIGHT_ONLY_TWO_DAY, nightOnlyTwoDayPassport.getTicketType()); // TicketTypeで確認
        log(nightOnlyTwoDayPassport.getRestDays()); // 残りチケット日数が2日になる

        // 夜の時間帯（16:00-21:00）で入園
        nightOnlyTwoDayPassport.doInPark(17, 0); // 17時0分に入園
        log("Current in park: " + nightOnlyTwoDayPassport.isCurrentIn());
        nightOnlyTwoDayPassport.doOutPark();
        log("Current in park: " + nightOnlyTwoDayPassport.isCurrentIn());
        log("Rest days: " + nightOnlyTwoDayPassport.getRestDays()); // 2日分から1日使用
    }
        // done edo doInPark()する動作確認をしてみましょう by jflute (2025/10/15)
        //1024 修正メモ
        //TicketクラスにisNightOnlyTicketメソッドを生成し、夜専用チケットかどうかを確認するようにした
        //doInPark()とdoOutPark()の動作確認をして、残りチケット日数が1日になることを確認した

    //1010 自分なりの回答
    //TicketBooth.javaにbuyNightOnlyTwoDayPassportメソッドを生成し
    //2日分チケット7400円がhandmoneyよりも安いかどうかを見てチケットを売る場合は２枚ずつ減らしていく。
    //handmoneyが7400円よりも多い場合、お釣りとして返す処理を行う。

    // done edo "夜しか使えないようにしましょう" をやってみましょう by jflute (2025/10/31)

    //1104 修正メモ
    //if文で夜専用チケットかどうかを確認するようにし、夜専用チケットの場合はdoInPark()とdoOutPark()の動作確認をして、残りチケット日数が1日になることを確認した
    //夜のチケットでない場合は、logで"This ticket can only be used at night. "と表示されるようにした

    //以下はFB後取り組みます
    // done jflute 1on1ここまでやった (2025/10/31)

    /**
     * Test night-only ticket time restriction (should fail during daytime). <br>
     * (夜専用チケットの時間帯制限をテスト（昼間は入園できない）)
     */
    public void test_class_moreFix_wonder_night_timeRestriction() {
        TicketBooth booth = createBooth();
        TicketBuyResult buyResult = booth.buyNightOnlyTwoDayPassport(7400);
        Ticket nightOnlyTwoDayPassport = buyResult.getTicket();
        assertEquals(TicketType.NIGHT_ONLY_TWO_DAY, nightOnlyTwoDayPassport.getTicketType()); // TicketTypeで確認

        // TODO done edo 修行++: このifだと今夜かは問わないので、テストの実行が昼でもうまくいっちゃう by jflute (2025/11/14)
        // UnitTestを昼に実行したら落ちるようにしたい。
        //1117 修正メモ：Ticketクラスにテーマパークの営業時間を定義し、夜の時間帯や昼の時間帯も定義した
        //夜の時間帯外（16:00未満）は入園できないことを確認
        //夜の時間帯（16:00-21:00）は入園できることを確認

        try {
            nightOnlyTwoDayPassport.doInPark(14, 0); // 14時0分に入園を試みる
            fail("Night-only ticket should not be usable before 16:00");
        } catch (IllegalStateException e) {
            log("Expected exception: " + e.getMessage());
        }

        // 夜の時間帯（16:00-21:00）は入園できることを確認
        nightOnlyTwoDayPassport.doInPark(17, 0); // 17時0分に入園
        log("Successfully entered at night: " + nightOnlyTwoDayPassport.isCurrentIn());
    }

    /**
     * Test daytime-only ticket time restriction (should fail at night). <br>
     * (昼専用チケットの時間帯制限をテスト（夜は入園できない）)
     */
        //1117 修正メモ：Ticketクラスにテーマパークの営業時間を定義し、夜の時間帯や昼の時間帯も定義した
        //昼の時間帯外（16:00以降）は入園できないことを確認
        //昼の時間帯（11:00-16:00）は入園できることを確認
    public void test_class_moreFix_wonder_daytime_timeRestriction() {
        TicketBooth booth = createBooth();
        TicketBuyResult buyResult = booth.buyDayTimeOnlyTwoDayPassport(7400);
        Ticket dayTimeOnlyTwoDayPassport = buyResult.getTicket();
        assertEquals(TicketType.DAY_TIME_ONLY_TWO_DAY, dayTimeOnlyTwoDayPassport.getTicketType()); // TicketTypeで確認

        // 昼の時間帯外（16:00以降）は入園できないことを確認
        try {
            dayTimeOnlyTwoDayPassport.doInPark(17, 0); // 17時0分に入園チャレンジ
            fail("Daytime-only ticket should not be usable after 16:00");
        } catch (IllegalStateException e) {
            log("Expected exception: " + e.getMessage());
        }

        // 昼の時間帯（11:00-16:00）は入園できることを確認
        dayTimeOnlyTwoDayPassport.doInPark(13, 0); // 13時0分チャレンジ
        log("Successfully entered during daytime: " + dayTimeOnlyTwoDayPassport.isCurrentIn());
    }

    // ===================================================================================
    //                                                                         Bonus Stage
    //                                                                         ===========
    /**
     * Refactor if you want to fix (e.g. is it well-balanced name of method and variable?). <br>
     * (その他、気になるところがあったらリファクタリングしてみましょう (例えば、バランスの良いメソッド名や変数名になっていますか？))
     */
    //1107 修正メモ
    //①TicketクラスにcreatNormalTicket、creatNightOnlyTicket、creatDayTimeOnlyTicketメソッドを生成し、
    //通常チケット、夜専用チケット、昼専用チケットを生成するようにした
    //TicketBooth.javaでそれぞれチケットを購入するメソッドにチケットの種別を渡すようにすることで、
    //new Ticket(price, quantity, true, false) のように昼と夜どちらがtrueでfalseがわかりにくくなっているものを
    //Ticket.createNightOnlyTicket(price, quantity)とすることで明示的にしわかりやすくした
    //②Ticket.javaのコンストラクタをthisを使って呼び出すようにし、コンストラクタの中身を簡潔にした
    public void test_class_moreFix_yourRefactoring() {
        // 1. コンストラクタから直接作成したチケットの確認
        Ticket normalTicket = new Ticket(TicketType.ONE_DAY);
        log("normalTicket: " + normalTicket.getTicketType()); // 期待値：ONE_DAY
        Ticket nightOnlyTicket = new Ticket(TicketType.NIGHT_ONLY_TWO_DAY);
        log("nightOnlyTicket: " + nightOnlyTicket.getTicketType()); // 期待値：NIGHT_ONLY_TWO_DAY
        
        Ticket dayTimeOnlyTicket = new Ticket(TicketType.DAY_TIME_ONLY_TWO_DAY);
        log("dayTimeOnlyTicket: " + dayTimeOnlyTicket.getTicketType()); // 期待値：DAY_TIME_ONLY_TWO_DAY
        
        // 2. TicketBoothから購入したチケットの確認
        TicketBooth booth = createBooth();
        Ticket oneDay = booth.buyOneDayPassport(10000).getTicket();
        log("oneDay from booth: " + oneDay.getTicketType()); // 期待値：ONE_DAY
        
        Ticket nightOnlyFromBooth = booth.buyNightOnlyTwoDayPassport(8000).getTicket();
        log("nightOnlyFromBooth: " + nightOnlyFromBooth.getTicketType()); // 期待値：NIGHT_ONLY_TWO_DAY
    }

    /**
     * Write intelligent JavaDoc comments seriously on the public classes/constructors/methods of the Ticket class. <br>
     * (Ticketクラスのpublicなクラス/コンストラクター/メソッドに、気の利いたJavaDocコメントを本気で書いてみましょう)
     */
    public void test_class_moreFix_yourSuperJavaDoc() {
        // JavaDocの確認：各publicメソッドの動作確認
        Ticket oneDay = new Ticket(TicketType.ONE_DAY);
        
        // getTicketType()の確認
        log("TicketType: " + oneDay.getTicketType()); // 期待値：ONE_DAY
        
        // getDisplayPrice()の確認
        log("DisplayPrice: " + oneDay.getDisplayPrice()); // 期待値：7400
        
        // getRestDays()の確認
        log("RestDays: " + oneDay.getRestDays()); // 期待値：1
        
        // isCurrentIn()の確認（入園前）
        log("CurrentIn (before): " + oneDay.isCurrentIn()); // 期待値：false
        
        // doInPark()の確認
        oneDay.doInPark(10, 0);
        log("CurrentIn (after doInPark): " + oneDay.isCurrentIn()); // 期待値：true
        
        // doOutPark()の確認
        oneDay.doOutPark();
        log("CurrentIn (after doOutPark): " + oneDay.isCurrentIn()); // 期待値：false
        log("RestDays (after use): " + oneDay.getRestDays()); // 期待値：0
    }

    // ===================================================================================
    //                                                                         Devil Stage
    //                                                                         ===========
    /**
     * If your specification is to share inventory (quantity) between OneDay/TwoDay/...,
     * change the specification to separate inventory for each OneDay/TwoDay/.... <br>
     * (もし、OneDay/TwoDay/...で在庫(quantity)を共有する仕様になってたら、
     * OneDay/TwoDay/...ごとに在庫を分ける仕様に変えてみましょう)
     */
    public void test_class_moreFix_zonedQuantity() {
        // your confirmation code here
    }
}
