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
package org.docksidestage.bizfw.basic.buyticket;

// done edo author追加を by jflute (2025/10/03)
/**
 * @author jflute
 * @author harukaedo
 */
public class Ticket {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // done edo [いいね] 横のすらすらコメントの「チケットの残り使用可能日数」がとても良い by jflute (2025/10/03)
    // #1on1: 厳密には、JavaDocで書いたほうが丁寧ではある。
    // publicとかだったらJavaDocは必須？ by えどさん → そういう考えでいた方がGoodです。(2025/10/03)
    // 省略しちゃうこともあるけど、publicはJavaDocの費用対効果が高い(privateに比べて)、と言える。
    // (Stringは究極の費用対効果が高いクラス)
    private final int displayPrice; // written on ticket, park guest can watch this
    private boolean currentIn; // 現在入園しているかどうか
    private int restDays; // チケットの残り使用可能日数
    private boolean nightOnly; // 夜だけ使用可能なチケット
    private boolean dayTimeOnly; // お昼だけ使用可能なチケット
    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Ticket(int displayPrice) {
        this.displayPrice = displayPrice;
        this.restDays = 1;
    }

    public Ticket(int displayPrice, int days) {
        this.displayPrice = displayPrice;
        this.restDays = days;
    }

    public Ticket(int displayPrice, int days, boolean nightOnly, boolean dayTimeOnly) {
        this.displayPrice = displayPrice;
        this.restDays = days;
        this.nightOnly = nightOnly;
        this.dayTimeOnly = dayTimeOnly;
    }

    // ===================================================================================
    //                                                                             In Park
    //                                                                             =======
    public void doInPark() {
        if (restDays <= 0) {
            throw new IllegalStateException("No remaining days: displayedPrice=" + displayPrice);
        }
        if (currentIn) {
            throw new IllegalStateException("Already in park by this ticket: displayedPrice=" + displayPrice);
        }
        currentIn = true;
    }

    // done edo [いいね] outを作ったのGood by jflute (2025/10/03)
    public void doOutPark() {
        if (!currentIn) {
            throw new IllegalStateException("Not in park by this ticket: displayedPrice=" + displayPrice);
        }
        currentIn = false;
        restDays--; // 使用日数を減らす
    }


    // ========================================================================================
    //                                                                               Time logic
    //                                                                              ===========
    //-----------------------------------------
    //                                    Night
    //----------------------------------------
    /**
     * done edo notNight()くんを誰も呼び出していない (ロジックも含めて見直しを) by jflute (2025/10/15)
     * 夜だけ使えるチケットかどうかを判定する
     * @return 夜専用チケットの場合true、そうでなければfalse
     */
    public boolean isNightOnly() {
        return nightOnly;
    }

    //-----------------------------------------
    //                                    Day Time
    //----------------------------------------
    /**
     * お昼だけ使えるチケットかどうかを判定する
     * @return お昼専用チケットの場合true、そうでなければfalse
     */
    public boolean isDayTimeOnly() {
        return dayTimeOnly;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getDisplayPrice() {
        return displayPrice;
    }

    // #1on1: TwoDayを追加して、さらに、Outを追加したことで、alreadyInのニュアンスが変わりました (2025/10/03)
    // えどさんは、alreadyIn は「今入ってるかどうか？」に変えた。
    // restDays で使い切ったかどうかはわかるので、alreadyInで同じことを表現する必要がない。
    // 一方で、alreadyIn を昔の概念で使ってるプログラムがあったら、そこでデグレが発生してしまうので注意。
    // 呼び出し側を一覧化して、ニュアンスを変えても大丈夫かどうか？の確認をする。
    // alreadyIn という名前のままやっていくかどうか？
    // 「今入ってるかどうか？」を示すのに alreadyIn が適切かどうか？
    // done edo alreadyInの名前をもうちょい適切に変えてしまいましょう (リファクタリング) by jflute (2025/10/03)
    //1007 memo: alreadyInをcurrentInに変えて現在入園しているかどうかを表現するようにした
    public boolean isCurrentIn() {
        return currentIn;
    }

    public int getRestDays() {
        return restDays;
    }
}
