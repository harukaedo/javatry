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

/**
 * @author jflute
 * @author harukaedo
 */
public class TicketBooth {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final int MAX_QUANTITY = 10;
    private static final int ONE_DAY_PRICE = 7400; // when 2019/06/15
    private static final int TWO_DAY_PRICE = 13200; // when 2019/06/15

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // #1on1: 在庫のパターンの話、在庫を分けるのも思いついてた by えどさん (2025/09/19)
    // 「(関数とか変数とか) あんまり増やしすぎるのもよくないかな？と思って」by えどさん
    // クラスの作り方のお作法としてそれはその通りではあるが...
    // 一方で、「業務的にそうでないといけない」という要件があるのであれば、その業務を優先して考えないといけない。
    // なので、クラスの作り方のお作法の話は、厳密には「業務が同じだった場合」にこうするああするを初めて考えることができる。
    private int quantity = MAX_QUANTITY;
    private Integer salesProceeds; // null allowed: until first purchase

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBooth() {
    }

    // ===================================================================================
    //                                                                          Buy Ticket
    //                                                                          ==========
    // you can rewrite comments for your own language by jflute
    // e.g. Japanese
    // /**
    // * 1Dayパスポートを買う、パークゲスト用のメソッド。お釣りを返すメソッドも追加。
    // * @param handedMoney パークゲストから手渡しされたお金(金額) (NotNull, NotMinus)
    // * @throws TicketSoldOutException ブース内のチケットが売り切れだったら
    // * @throws TicketShortMoneyException 買うのに金額が足りなかったら
    // */
    /**
     * Buy one-day passport, method for park guest.
     * @param handedMoney The money (amount) handed over from park guest. (NotNull, NotMinus)
     * @throws TicketSoldOutException When ticket in booth is sold out.
     * @throws TicketShortMoneyException When the specified money is short for purchase.
     * @return チケットとお釣りを返す
     */

    // ============================================================================================
    //                                                                          Buy Two-day Ticket
    //                                                                          ===================
    // you can rewrite comments for your own language by jflute
    // e.g. Japanese
    // /**
    // * 2Dayパスポートを買う,お釣りを返すメソッド。パークゲスト用のメソッド。
    // * @param handedMoney パークゲストから手渡しされたお金(金額) (NotNull, NotMinus)
    // * @throws TicketSoldOutException ブース内のチケットが売り切れだったら
    // * @throws TicketShortMoneyException 買うのに金額が足りなかったら
    // * @return お釣り金額
    // */
    // done edo 戻り値があるので、JavaDocの@returnをぜひ追加してみてください by jflute (2025/09/19)
    /**
     * Buy two-day passport, method for park guest.
     * @param handedMoney The money (amount) handed over from park guest. (NotNull, NotMinus)
     * @throws TicketSoldOutException When ticket in booth is sold out.
     * @throws TicketShortMoneyException When the specified money is short for purchase.
     * @return チケットとお釣りを返す
     */

    public TicketBuyResult buyOneDayPassport(Integer handedMoney) {
        checkBuyTicket(handedMoney, ONE_DAY_PRICE, 1);
        checkTicketQuantity(ONE_DAY_PRICE, 1);
        int change = handedMoney - ONE_DAY_PRICE;
        return new TicketBuyResult(new Ticket(ONE_DAY_PRICE, 1), change);
    }

    // TODO done edo 元のコードを直しちゃってもOKです。(OneDayの統一を) by jflute (2025/10/03)
    // public int buyOneDayPassportChange(Integer handedMoney) {
    //     if (quantity <= 0) {
    //         throw new TicketSoldOutException("Sold out");
    //     }
    //     if (handedMoney < ONE_DAY_PRICE) {
    //         throw new TicketShortMoneyException("Short money: " + handedMoney);
    //     }
    //     --quantity;
    //     int change = handedMoney - ONE_DAY_PRICE;
    //     if (salesProceeds != null) { // second or more purchase
    //         salesProceeds = salesProceeds + ONE_DAY_PRICE;
    //     } else { // first purchase
    //         salesProceeds = ONE_DAY_PRICE;
    //     }
    //     return change;
    // }

    public TicketBuyResult buyTwoDayPassport(Integer handedMoney) {
        checkBuyTicket(handedMoney, TWO_DAY_PRICE, 2);
        checkTicketQuantity(TWO_DAY_PRICE, 2);
        int change = handedMoney - TWO_DAY_PRICE;
        return new TicketBuyResult(new Ticket(TWO_DAY_PRICE, 2), change);
    }

    // ===================================================================================
    //                                                                      Private Methods
    //                                                                      ===============
    /**
     * Validate purchase requirements (sold out check and money validation).
     * @param handedMoney 手渡しされた金額
     * @param ticketPrice チケットの価格
     * @param ticketQuantity 欲しいチケットの枚数
     * @throws TicketSoldOutException チケットが売り切れている場合
     * @throws TicketShortMoneyException 買うのに金額が足りなかった場合
     */
    private void checkBuyTicket(Integer handedMoney, int ticketPrice, int ticketQuantity) {
        if (quantity < ticketQuantity) {
            throw new TicketSoldOutException("Sold out");
        }
        if (handedMoney < ticketPrice) {
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        }
    }

    /**
     * Process the purchase (update quantity and sales proceeds).
     * @param ticketPrice チケットの価格
     * @param quantityUsed 使用したチケットの枚数
     */
    private void checkTicketQuantity(int ticketPrice, int quantityUsed) {
        quantity -= quantityUsed;
        if (salesProceeds != null) { // second or more purchase
            salesProceeds = salesProceeds + ticketPrice;
        } else { // first purchase
            salesProceeds = ticketPrice;
        }
    }

    //1007 自分なりの回答
    //checkBuyTicketとcheckTicketQuantityメソッドを作成し、チケットが買えるかどうかのチェックとチケットが購入されたら枚数を減らす処理を
    //共通化した。
    //checkBuyTicketメソッドでチケットが買えるかどうかのチェックを行う。
    //checkTicketQuantityメソッドでチケットが購入されたら枚数を減らす処理を行う。
    //buyOneDayPassportとbuyTwoDayPassportでcheckBuyTicketとcheckTicketQuantityメソッドを呼び出し
    //再利用してrefactorした。

    
    // ===================================================================================
    //                                                                           Exception
    //                                                                           =========
    public static class TicketSoldOutException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketSoldOutException(String msg) {
            super(msg);
        }
    }

    public static class TicketShortMoneyException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketShortMoneyException(String msg) {
            super(msg);
        }
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * 現在の在庫数を取得する
     * @return 在庫数
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * 現在の売上金額を取得する
     * @return 売上金額（まだ購入がない場合はnull）
     */
    public Integer getSalesProceeds() {
        return salesProceeds;
    }
}
