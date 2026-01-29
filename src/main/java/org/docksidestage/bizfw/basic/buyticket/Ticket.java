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
 * テーマパークの入園チケットを表すクラス。
 * チケットの価格、残り使用可能日数、入園状態などを管理する。
 *
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
    // done edo 固定情報と(変化する)状態情報を区別するために、final付けられるところは付けておきましょう by jflute (2025/12/12)
    // done edo 変数の定義順序、変数のカテゴリを意識して並べたり、空行でカテゴリを見た目強調したり by jflute (2025/12/12)
    // (さらに、タイトルコメントを付けてあげたりするのもアリ)
    // (DBFlute の LikeSearchOption のインスタンス変数のタグコメントの例も)
    //1220修正メモ：Ticket informationとCurrent informationを分けて管理しやすいようにした
    //----------------------------------------------
    //                             Park Information
    //----------------------------------------------
    // done edo 近くに置きたい気持ちわかるけど、オーソドックスにはクラスの先頭の方で定数定義 by jflute (2025/12/25)
    // #1on1: 閉鎖はちょっとかわいそうかも。でも門のあけしめでは閉鎖という言葉も合ってる (2025/12/25)
    //0106 修正メモ：閉鎖を閉園に変更した。Park Informationというタイトルを付け
    private static final int PARK_OPEN_HOUR = 9; // パークの開始時刻
    private static final int PARK_CLOSE_HOUR = 21; // パークの閉園時刻
    private static final int DAY_TICKET_START_HOUR = 11; // 昼チケットの開始時刻
    private static final int NIGHT_TICKET_START_HOUR = 16; // 夜チケットの開始時刻
    //----------------------------------------------
    //                            Ticket Information
    //----------------------------------------------
    private final TicketType ticketType; // チケットの種別識別子
    private final int displayPrice; // written on ticket, park guest can watch this
    
    //----------------------------------------------
    //                            Current Information
    //----------------------------------------------
    private boolean currentIn; // 現在入園しているかどうか
    private int restDays; // チケットの残り使用可能日数

// e.g. jfluteの例:
//// チケット基本情報
//private final TicketType ticketType; // チケットの種別識別子
//private final int displayPrice; // written on ticket, park guest can watch this
//
//// 時間帯情報
//private final boolean nightOnly; // 夜だけ使用可能なチケット
//private final boolean dayTimeOnly; // お昼だけ使用可能なチケット
//
//// 入園状態を制御
//private boolean currentIn; // 現在入園しているかどうか
//private int restDays; // チケットの残り使用可能日数
    
    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    // done edo mainコードで必要としているConstructorだけにしましょう。 by jflute (2025/11/28)
    //1201修正メモ enum版のTicketコンストラクターのみにしました。
    // (もしくは、何かそれがわかるように区別を付ける)
    // done edo 引数、TicketTypeを指定してるなら、displayPriceとdaysなくてもOK by jflute (2025/12/12)
    //1220修正メモ: 引数をTicketTypeのみにして、displayPriceとdaysを削除した。displayPriceとdaysはTicketTypeに含まれいるgetPrice()とgetDays()メソッドで取得できる
    /**
     * チケットを生成する（識別子指定版）
     * @param ticketType チケットの種別識別子
     */
    public Ticket(TicketType ticketType) {
        this.ticketType = ticketType;
        this.displayPrice = ticketType.getPrice();
        this.restDays = ticketType.getDays();
    }

    // done edo infer, TicketBoothのとぅどぅの修正したら要らなくなっちゃうかもだけど... by jflute (2025/12/12)
    // 思い出のために、コメントアウトで残しておいてください。
    //1220修正メモ：inferメソッドを削除しました。TicketTypeを直接指定する方式にした
    // /**
    //  * 日数と時間帯制限からチケット種別を推測する。デフォルトは1日券
    //  * @param days 使用可能日数
    //  * @param nightOnly 夜間のみ使用可能な場合true
    //  * @param dayTimeOnly 昼間のみ使用可能な場合true
    //  * @return 推測されたチケット種別
    //  */
    // private static TicketType inferTicketType(int days, boolean nightOnly, boolean dayTimeOnly) {
    //     if (days == 1) {
    //         return TicketType.ONE_DAY;
    //     } else if (days == 2) {
    //         if (nightOnly) {
    //             return TicketType.NIGHT_ONLY_TWO_DAY;
    //         } else if (dayTimeOnly) {
    //             return TicketType.DAY_TIME_ONLY_TWO_DAY;
    //         } else {
    //             return TicketType.TWO_DAY;
    //         }
    //     } else if (days == 4) {
    //         return TicketType.FOUR_DAY;
    //     } else {
    //         return TicketType.ONE_DAY;
    //     }
    // }

    // ===================================================================================
    //                                                                  Ticket type method
    //                                                                         ===========
    // done edo すでに現状としては、TicketのnewはdoBuy内で統一されているから... by jflute (2025/12/25)
    // ここのstaticのFactoryメソッドたちは、テストとかそういうところでしか使われていない。
    // なので基本的にはもう消しても良いもの。思い出でコメントアウトしてもいいかも。
    //0106 修正メモ：doBuy内で担保できているためこちらはコメントアウトしましました。
    // /**
    //  * 通常チケット（昼夜問わず使える）
    //  * @return 生成されたチケット
    //  */
    // public static Ticket creatNormalTicket() {
    //     return new Ticket(TicketType.ONE_DAY);
    // }

    // /**
    //  * 夜2日券専用チケット
    //  * @return 生成されたチケット
    //  */
    // public static Ticket creatNightOnlyTicket() {
    //     return new Ticket(TicketType.NIGHT_ONLY_TWO_DAY);
    // }

    // /**
    //  * 昼2日券専用チケット
    //  * @return 生成されたチケット
    //  */
    // public static Ticket creatDayTimeOnlyTicket() {
    //     return new Ticket(TicketType.DAY_TIME_ONLY_TWO_DAY);
    // }

    // ===================================================================================
    //                                                                             In Park
    //                                                                            =======
    /**
     * パークに入園する。
     * 残り使用可能日数がない場合や、既に入園済みの場合、時間帯が合わない場合は例外をスローする。
     *
     * @param currentHour 現在の時刻（0-23の範囲）
     * @throws IllegalStateException 残り使用可能日数がない、既に入園済み、または時間帯が合わない場合
     */
    public void doInPark(int currentHour) {
        if (restDays <= 0) {
            throw new IllegalStateException("No remaining days: displayedPrice=" + displayPrice);
        }
        if (currentIn) {
            throw new IllegalStateException("Already in park by this ticket: displayedPrice=" + displayPrice);
        }
        validateTimeRestriction(currentHour);
        currentIn = true;
    }

    /**
     * 時間帯制限をチェックする。
     * 通常チケット: 9:00-20:59まで入園可能（21:00で閉園し、入園不可とする。）
     * 昼専用チケット: 11:00-15:59まで入園可能（16:00以降は夜専用チケットとなる）
     * 夜専用チケット: 16:00-20:59まで入園可能（21:00以降は不可）
     *
     * @param currentHour 現在の時刻（0-23の範囲）
     * @throws IllegalStateException 時間帯が合わない場合、または営業時間外の場合
     */
    private void validateTimeRestriction(int currentHour) {
        // #1on1: 21時10分は入園できてはいけないので、そのときは currentHour は22じゃないいけない想定ロジック (2025/12/25)
        // つまり、呼び出し側は現在日時を取得した時に21時を1分1秒でも過ぎてたら切り上げで22を導出するようにすべし。
        // 一方で、8時50分も入園できていはいけないが、今の論理で言うと9を引数で渡すことになるので、
        // 8時50分で9が来ちゃって入園できてしまう。
        // 切り捨てするとbegin側のロジックはOKだけど、end側のロジックがダメになっちゃう。
        // 逆に、切り上げだとend側のロジックはOKだけど、begin側のロジックがダメになっちゃう。
        // (一方で一方で、DayTimeOnlyの方は、両方切り捨てだと辻褄合う。NightOnlyも切り捨て)
        // done edo ↑の矛盾を調整してみてください by jflute (2025/12/25)
        //0106 修正メモ: currentMinuteを追加し、分もチェックするようにした。
        //分を追加することによって、厳格な時間帯チェックが可能になった。
        // #1on1: 一方で、hourだけで実装を済ませるためには、21:00ぴったりの入園をナシにして、
        // 昼と同じように終了の判定のロジックを統一するといいかも。
        // done edo ↑これやってみましょう。miniteの消すためというよりかは、統一性を保つため (言い訳) by jflute (2026/01/16)
        // TODO edo 三つのif文のOutOfTimeの判定が統一化されたので、そこも切り出してメソッド化してみましょう by jflute (2026/01/29)

        // 営業時間外チェック（全チケット共通）
        // 9:00より前、または21:00以降は入園不可
        int parkOpenHour = PARK_OPEN_HOUR;
        int parkCloseHour = PARK_CLOSE_HOUR;
        if (currentHour < parkOpenHour ||  currentHour >= parkCloseHour) {
            throw new IllegalStateException("Park is closed at this time: " + currentHour 
                    + " (Open: " + PARK_OPEN_HOUR + ":00-" + PARK_CLOSE_HOUR + ":00)");
        }

        // 昼専用チケットの時間帯チェック（11:00-15:59）
        // #1on1: 今のロジックだと厳密には（11:00-15:59）と言える。終わりの時間の統一性が少し気になる (2026/01/16)
        // 夜専用チケットと続いているものなので、16時ぴったりが両方入れるも変だし、まあ悪くないかも...
        // 一方で、もし昼が（11:00-15:00）という風に、夜と繋がってなくて同じロジックだったら、終わりの時間の統一性が気になる。
        if (ticketType.isDayTimeOnly()) {
            if (currentHour < DAY_TICKET_START_HOUR || currentHour >= NIGHT_TICKET_START_HOUR) {
                // done edo SQLのbetweenだと、to時間も含むニュアンスになるので、ちょっと紛らわしいかも by jflute (2026/01/16)
                // done edo メソッド切り出しエクササイズ。2つの例外throw(昼夜)をprivateメソッドで再利用してみましょう by jflute (2026/01/16)
                // throw createOutOfTimeException(...);
                createOutOfTimeException( "Daytime-only", DAY_TICKET_START_HOUR, NIGHT_TICKET_START_HOUR, currentHour);        
            }
        }

        // 夜専用チケットの時間帯チェック（16:00-20:59）
        if (ticketType.isNightOnly()) {
            if (currentHour < NIGHT_TICKET_START_HOUR || currentHour >= PARK_CLOSE_HOUR) {
                createOutOfTimeException( "Night-only", NIGHT_TICKET_START_HOUR, PARK_CLOSE_HOUR, currentHour);
            }
        }
    }

    // TODO edo createだと生成してるだけでthrowしてる感がないので、throwOutOf...() にしちゃった方がわかりやすいかなと by jflute (2026/01/29)
    //1119
    //1119修正メモ：createOutOfTimeExceptionメソッドを生成し、時間帯制限を超えた場合の例外を作成するようにした
    //Daytime-only:11:00-16:00までしか使えませんよ（疑問：untilは16:00ぴったりを除くという文脈なのか？）
    //Night-only:16:00-21:00までしか使えませんよ（疑問：untilは21:00ぴったりを除くという文脈なのか？）
    /**
     * 時間帯制限を超えた場合の例外を作成する。
     * @param ticketTypeName チケットの種類（通常、昼専用、夜専用）
     * @param startTime チケットの開始時刻
     * @param endTime チケットの終了時刻
     * @param currentHour 現在の時刻
     */
    private void createOutOfTimeException(String ticketTypeName, int startTime, int endTime, int currentHour) {
        throw new IllegalStateException(ticketTypeName + " can only be used from " + startTime + ":00 until " + endTime + ":00 " + currentHour + ":00");
    }
    
    // #1on1: SQL書いた記念パーティー！おめでとうございます。 (2026/01/16)
    // ID検索や日付絞り込み、but 日本時間で出さないとなのでTimeZone問題。

    // done edo [いいね] outを作ったのGood by jflute (2025/10/03)
    /**
     * パークから退園する。
     * 退園時に残り使用可能日数を1日減らす。
     * 入園していない状態で呼び出すと例外をスローする。
     *
     * @throws IllegalStateException 入園していない場合
     */
    public void doOutPark() {
        if (!currentIn) {
            throw new IllegalStateException("Not in park by this ticket: displayedPrice=" + displayPrice);
        }
        currentIn = false;
        restDays--; // 使用日数を減らす
    }

    // done edo もう使わなくなってしまった (by えどさん) by jflute (2025/12/25)
    //0106 修正メモ：Ticket typeの方にtime　logicを引っ越ししたので、こちらは削除しました。
    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * @return 表示価格
     */
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
    /**
     * @return 入園中の場合true、退園済みまたは未入園の場合false
     */
    public boolean isCurrentIn() {
        return currentIn;
    }

    /**
     * @return 残り使用可能日数
     */
    public int getRestDays() {
        return restDays;
    }

    /**
     * @return チケットの種別識別子
     */
    public TicketType getTicketType() {
        return ticketType;
    }
}
