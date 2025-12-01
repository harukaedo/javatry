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

// #1on1: enumについて。列挙型と言われる。
// Javaだと、Java5 (2005年くらい) から導入されました。
// Javaの歴史は、1995年からなので、30年の歴史で10年目あたりで導入された。
// e.g.
//  public class TicketType {
//      public static final TicketType ONE_DAY = new TicketType(1);
//      public static final TicketType TWO_DAY = new TicketType(2);
//      public static final TicketType FOUR_DAY = new TicketType(4);
//      ...
//      private TicketType(int days) { // インスタンス紛れが起きないように Constructor が private
//          ...
//      }
//  }
//
//  if (TicketType.ONE_DAY == ticketType)) { // oneDayだったら
//  }
//
//  ↓これはダメ (だから、Constructorがprivate)
//  TicketType ticketType = new TicketType(1); // oneDayのつもり新しいインスタンス
//  if (TicketType.ONE_DAY == ticketType)) { // oneDayだったら → これ一致しない (別インスタンス)
//  }
//
// なので、enumは「インスタンスの数を制限した、ただのクラス」とも言える。
// 少なくともJavaにおいては、ほぼほぼその通りで、他のクラスと同じように Object を継承している。
// あと、インスタンス変数も作れる、メソッドも作れる。コンストラクタも作れるけどprivateのみ。
// ちょっと制限のあるクラスみたいな感じ。
//
// enumは、種別をインスタンスで表現をしているクラス。
// なので、インスタンスが勝手に作られると破綻する。
/**
 * チケットの種別を表す識別子。
 * チケットの種類を明確に識別するために使用する。
 *
 * @author jflute
 * @author harukaedo
 */
public enum TicketType {
    /** 1日券 */
    ONE_DAY(1, 7400, 1),
    /** 2日券 */
    TWO_DAY(2, 13200, 2),
    /** 4日券 */
    FOUR_DAY(4, 22400, 4),
    /** 夜専用2日券 */
    NIGHT_ONLY_TWO_DAY(2, 7400, 2),
    /** 昼専用2日券 */
    DAY_TIME_ONLY_TWO_DAY(2, 7400, 2);

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** 使用可能日数 */
    private final int days;
    /** チケットの価格 */
    private final int price;
    /** 購入時に在庫を消費する枚数 */
    private final int purchaseQuantity;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    /**
     * チケット種別を生成する。
     * @param days 使用可能日数
     * @param price チケットの価格
     * @param purchaseQuantity 購入時に在庫を消費する枚数
     */
    private TicketType(int days, int price, int purchaseQuantity) {
        this.days = days;
        this.price = price;
        this.purchaseQuantity = purchaseQuantity;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * @return 使用可能日数
     */
    public int getDays() {
        return days;
    }

    /**
     * @return チケットの価格
     */
    public int getPrice() {
        return price;
    }

    /**
     * @return 購入時に在庫を消費する枚数
     */
    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }
}

