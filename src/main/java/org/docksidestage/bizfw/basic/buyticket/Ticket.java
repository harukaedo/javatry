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
    private final int displayPrice; // written on ticket, park guest can watch this
    private boolean currentIn; // 現在入園しているかどうか
    private int restDays; // チケットの残り使用可能日数
    private boolean nightOnly; // 夜だけ使用可能なチケット
    private boolean dayTimeOnly; // お昼だけ使用可能なチケット
    private final TicketType ticketType; // チケットの種別識別子
    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    // TODO edo mainコードで必要としているConstructorだけにしましょう。 by jflute (2025/11/28)
    // (もしくは、何かそれがわかるように区別を付ける)
    /**
     * 1日券を生成する。
     * @param displayPrice チケットに表示される価格
     */
    public Ticket(int displayPrice) {
        this(displayPrice, 1, false, false, TicketType.ONE_DAY);
    }

    /**
     * 複数日券を生成する。
     * @param displayPrice チケットに表示される価格
     * @param days 使用可能日数
     */
    public Ticket(int displayPrice, int days) {
        this(displayPrice, days, false, false, inferTicketType(days, false, false));
    }

    /**
     * 時間帯制限付き複数日券を生成する。
     * @param displayPrice チケットに表示される価格
     * @param days 使用可能日数
     * @param nightOnly 夜間のみ使用可能な場合true
     * @param dayTimeOnly 昼間のみ使用可能な場合true
     */
    public Ticket(int displayPrice, int days, boolean nightOnly, boolean dayTimeOnly) {
        this(displayPrice, days, nightOnly, dayTimeOnly, inferTicketType(days, nightOnly, dayTimeOnly));
    }

    /**
     * チケットを生成する（識別子指定版）。
     * @param displayPrice チケットに表示される価格
     * @param days 使用可能日数
     * @param nightOnly 夜間のみ使用可能な場合true
     * @param dayTimeOnly 昼間のみ使用可能な場合true
     * @param ticketType チケットの種別識別子
     */
    public Ticket(int displayPrice, int days, boolean nightOnly, boolean dayTimeOnly, TicketType ticketType) {
        this.displayPrice = displayPrice;
        this.restDays = days;
        this.nightOnly = nightOnly;
        this.dayTimeOnly = dayTimeOnly;
        this.ticketType = ticketType;
    }

    /**
     * 日数と時間帯制限からチケット種別を推測する。デフォルトは1日券
     * @param days 使用可能日数
     * @param nightOnly 夜間のみ使用可能な場合true
     * @param dayTimeOnly 昼間のみ使用可能な場合true
     * @return 推測されたチケット種別
     */
    private static TicketType inferTicketType(int days, boolean nightOnly, boolean dayTimeOnly) {
        if (days == 1) {
            return TicketType.ONE_DAY;
        } else if (days == 2) {
            if (nightOnly) {
                return TicketType.NIGHT_ONLY_TWO_DAY;
            } else if (dayTimeOnly) {
                return TicketType.DAY_TIME_ONLY_TWO_DAY;
            } else {
                return TicketType.TWO_DAY;
            }
        } else if (days == 4) {
            return TicketType.FOUR_DAY;
        } else {
            return TicketType.ONE_DAY;
        }
    }

    // ===================================================================================
    //                                                                  Ticket type method
    //                                                                         ===========
    /**
     * 通常チケット（昼夜問わず使える）
     * @param displayPrice チケットの表示価格
     * @param days 使用可能日数
     * @return 生成されたチケット
     */
    public static Ticket creatNormalTicket(int displayPrice, int days) {
        TicketType type = inferTicketType(days, false, false);
        return new Ticket(displayPrice, days, false, false, type);
    }

    /**
     * 夜専用チケット
     * @param displayPrice チケットの表示価格
     * @param days 使用可能日数
     * @return 生成されたチケット
     */
    public static Ticket creatNightOnlyTicket(int displayPrice, int days) {
        TicketType type = inferTicketType(days, true, false);
        return new Ticket(displayPrice, days, true, false, type);
    }

    /**
     * 昼専用チケット
     * @param displayPrice チケットの表示価格
     * @param days 使用可能日数
     * @return 生成されたチケット
     */
    public static Ticket creatDayTimeOnlyTicket(int displayPrice, int days) {
        TicketType type = inferTicketType(days, false, true);
        return new Ticket(displayPrice, days, false, true, type);
    }

    // ===================================================================================
    //                                                                             In Park
    //                                                                            =======
    private static final int PARK_OPEN_HOUR = 9; // パークの開始時刻
    private static final int PARK_CLOSE_HOUR = 21; // パークの閉鎖時刻
    private static final int DAY_TICKET_START_HOUR = 11; // 昼チケットの開始時刻
    private static final int NIGHT_TICKET_START_HOUR = 16; // 夜チケットの開始時刻

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
     * 通常チケット: 9-21時まで入園可能
     * 昼専用チケット: 11-16時まで入園可能
     * 夜専用チケット: 16-21時まで入園可能
     *
     * @param currentHour 現在の時刻（0-23の範囲で設定）
     * @throws IllegalStateException 時間帯が合わない場合、または営業時間外の場合
     */
    private void validateTimeRestriction(int currentHour) {
        // 営業時間外チェック（全チケット共通）
        if (currentHour < PARK_OPEN_HOUR || currentHour > PARK_CLOSE_HOUR) {
            throw new IllegalStateException("Park is closed at this hour: currentHour=" + currentHour
                    + " (Open: " + PARK_OPEN_HOUR + "-" + PARK_CLOSE_HOUR + ")");
        }

        // 昼専用チケットの時間帯チェック（11-16時）
        if (dayTimeOnly) {
            if (currentHour < DAY_TICKET_START_HOUR || currentHour >= NIGHT_TICKET_START_HOUR) {
                throw new IllegalStateException("Daytime-only ticket can only be used between "
                        + DAY_TICKET_START_HOUR + " and " + NIGHT_TICKET_START_HOUR + ": currentHour=" + currentHour);
            }
        }

        // 夜専用チケットの時間帯チェック（16-21時）
        if (nightOnly) {
            if (currentHour < NIGHT_TICKET_START_HOUR) {
                throw new IllegalStateException("Night-only ticket can only be used from "
                        + NIGHT_TICKET_START_HOUR + " onwards: currentHour=" + currentHour);
            }
        }
    }

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
