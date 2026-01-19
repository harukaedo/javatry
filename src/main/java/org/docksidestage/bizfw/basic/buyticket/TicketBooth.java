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
    // done edo nightOnlyだけ定数になってるけど、やるなら他のも定数にした方が統一感がある by jflute (2025/10/15)
    // 1023 修正メモ
    // ONE_DAY_PURCHASE_QUANTITY, TWO_DAY_PURCHASE_QUANTITY, FOUR_DAY_PURCHASE_QUANTITY, NIGHT_ONLY_TWO_DAY_PURCHASE_QUANTITYを定数にした
    // done edo quantityだと、MAX_QUANTITYとの対比で、在庫数だと思ってしまう... by jflute (2025/10/15)
    // ので、一回の購入で在庫を消費する数というニュアンスがあると良いかも。
    // 1023 修正メモ
    // HOGE_PURCHASE_QUANTITYを定数にした
    // done edo せめて、QuantityとPriceを区分けするために空行を空けてみましょう by jflute (2025/10/31)
    //1104修正メモ QuantityとPriceを区分けするために空行を空け,コメントを追加して見やすくした

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

    // #1on1: コピー修正するとき、一括置換 or 手修正だけど検索で修正漏れを発見する話 (2025/10/31)
    // done edo buyメソッド、定数の利用が何回もありすぎるので、定数を変数にしてコピー時の修正箇所を減らしてみましょう by jflute (2025/10/31)
    //1104 修正メモ それぞれのquantityとticket priceをint型のquantityとpriceと定義し、修正な必要な箇所を減らした。

    // done edo タグコメントとJavaDoc整理整頓 by jflute (2025/10/15)
    // done edo タグコメント整理整頓２ (e.g. Buy Ticket, Buy Logic) by jflute (2025/10/31)
    // 1104 修正メモ OnedayやTwodayなどを大見出しで分けるのではなく、全てをBuy Ticketとして囲って
    // 日付はそれぞれコメントでわかるようにした。private　メソッドに関しても後から要件が増えた際いに追加しやすいように
    // Buy Ticket Logicとし、小見出しを追加して管理しやすいようにした
    // ===========================================================================================
    //                                                                                  Buy Ticket
    //                                                                          ==================
    // you can rewrite comments for your own language by jflute
    // e.g. Japanese
    // /**
    // * 1Dayパスポートを買う、パークゲスト用のメソッド。お釣りを返すメソッドも追加。
    // * @param handedMoney パークゲストから手渡しされたお金(金額) (NotNull, NotMinus)
    // * @throws TicketSoldOutException ブース内のチケットが売り切れだったら
    // * @throws TicketShortMoneyException 買うのに金額が足りなかったら
    // */
    //  done edo publicの方はdoBuyじゃなくてbuyでOK by jflute (2025/12/12)
    //1220 修正メモ：privateのみdoをつけてpublicとprivate見分けやすくした
    // done edo Javaだと、メソッドの先頭は小文字が慣習になっている。 by jflute (2025/12/25)
    // #1on1: TypeScriptは関数名は基本小文字っぽいのでJavaとほぼ同じ。 (2025/12/25)
    // C#は、メソッドの先頭は大文字。Go言語は大文字でpublicになる規約ベースでプログラムに影響する。
    //0106 修正メモ：メソッドの先頭を小文字に修正し、Buyからbuyに変更。参照しているところも併せて修正した
    /**
     * Buy one-day passport, method for park guest.
     * @param handedMoney The money (amount) handed over from park guest. (NotNull, NotMinus)
     * @throws TicketSoldOutException When ticket in booth is sold out.
     * @throws TicketShortMoneyException When the specified money is short for purchase.
     * @return チケットとお釣りを返す
     */
    public TicketBuyResult buyOneDayPassport(Integer handedMoney) {
        // done edo ここで売るチケット種別は、「OneDayパスポート」といい切れるのでは？ by jflute (2025/11/28)
        // (今だと、わざわざ中で infer して、TicketType.ONE_DAYを導出しているけど、
        // もうここで TicketType.ONE_DAY ベタッと指定しても良い領域ではある)
        // ということで、publicメソッド内で1:1に対応する TicketType を直接指定する方式にしてみましょう。
        //1201修正メモ TicketType.ONE_DAYを直接指定する方式にした
        // done edo もうここではチケット種別をピンポイントで指定している世界なので... by jflute (2025/12/12)
        // creat...Ticket() の中で infer する必要がないはず。(特定されているはずだから)
        // 1220修正メモ：TicketTypeを直接指定する方式にした.inferメソッドをコメントアウトした
        return doBuyTicket(handedMoney, TicketType.ONE_DAY);
    }
    
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
    public TicketBuyResult buyTwoDayPassport(Integer handedMoney) {
        return doBuyTicket(handedMoney, TicketType.TWO_DAY);
    }

    /**
     * 4Dayパスポートを買う,お釣りを返すメソッド。パークゲスト用のメソッド。
     * @param handedMoney The money (amount) handed over from park guest. (NotNull, NotMinus)
     * @throws TicketSoldOutException When ticket in booth is sold out.
     * @throws TicketShortMoneyException When the specified money is short for purchase.
     * @return チケットとお釣りを返す
     */
    public TicketBuyResult buyFourDayPassport(Integer handedMoney) {
        return doBuyTicket(handedMoney, TicketType.FOUR_DAY);
    }

    // done edo @return, ここでも "など" ってしておいたほうがいいかなと by jflute (2025/10/15)
    /**
     * 2日間の夜だけ使えるパスポートを買う,お釣りを返すメソッド。パークゲスト用のメソッド。
     * @param handedMoney The money (amount) handed over from park guest. (NotNull, NotMinus)
     * @throws TicketSoldOutException When ticket in booth is sold out.
     * @throws TicketShortMoneyException When the specified money is short for purchase.
     * @return チケットとお釣りなどを返す
     */
    public TicketBuyResult buyNightOnlyTwoDayPassport(Integer handedMoney) {
        return doBuyTicket(handedMoney, TicketType.NIGHT_ONLY_TWO_DAY);
    }

    //1104　お昼のみ使えるチケット購入のメソッドも修行問題により追加しました
    /**
     * 2日間のお昼だけ使えるパスポートを買う,お釣りを返すメソッド。パークゲスト用のメソッド。
     * @param handedMoney The money (amount) handed over from park guest. (NotNull, NotMinus)
     * @throws TicketSoldOutException When ticket in booth is sold out.
     * @throws TicketShortMoneyException When the specified money is short for purchase.
     * @return チケットとお釣りなどを返す
     */
    public TicketBuyResult buyDayTimeOnlyTwoDayPassport(Integer handedMoney) {
        return doBuyTicket(handedMoney, TicketType.DAY_TIME_ONLY_TWO_DAY);
    }

    // ===================================================================================
    //                                                                     Private Methods
    //                                                                     ===============
    // --------------------------------------------------------------
    //                                               Buy Ticket Logic
    //                          -------------------------------------
    // done edo [いいね] JavaDoc, 再利用のprivateは費用対効果高いのでGood (引数も多いのでありがたい) by jflute (2025/10/15)
    // #1on1: もうちょいまとめらるかも？まとめ過ぎもよくないかも？changeはまとめてもいいかも？ by えどさん
    // 他は引数だけで解決してるのに、changeだけそこで計算しちゃってるのが気持ち悪い by えどさん
    // calculateChange()に出した場合の計算処理の仕様変更のシミュレーションしてみた by くぼ
    // done　edo せっかくなので、calculateChange() も作ってみましょう by jflute (2025/10/15)
    // 1023 修正メモ
    // calculateChange() メソッドを作成し、お釣り金額を計算する処理を共通化した。
    // #1on1: checkという動詞のメソッド、checkは期待する結果がちょっと曖昧になる。
    // e.g. assertという動詞のメソッドであれば、assert that S+V で正しいもの(期待する状態)を書く
    //  assertQuantityExists(ticketQuantity);
    //  assertEnoughMoney(handedMoney, ticketPrice);
    // done edo ということで、checkという言葉以外の動詞を使ってみましょう by jflute (2025/10/15)
    // done edo 一方で、JavaDocのニュアンスとメソッド名のニュアンスを統一しても良いのかなと by jflute (2025/10/15)
    // 1023 修正メモ
    // validatePurchaseRequirements() メソッドとProcessPurchase() メソッドにそれぞれ名称を変更し、JavaDocのニュアンスとメソッド名のニュアンスを統一した。
    /**
     * Validate purchase requirements (sold out check and money validation).
     * @param handedMoney 手渡しされた金額
     * @param ticketPrice チケットの価格
     * @param ticketQuantity 欲しいチケットの枚数
     * @throws TicketSoldOutException チケットが売り切れている場合
     * @throws TicketShortMoneyException 買うのに金額が足りなかった場合
     */
    private void validatePurchaseRequirements(Integer handedMoney, int ticketPrice, int ticketQuantity) {
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
    private void processPurchase(int ticketPrice, int quantityUsed) {
        quantity -= quantityUsed;
        if (salesProceeds != null) { // second or more purchase
            salesProceeds = salesProceeds + ticketPrice;
        } else { // first purchase
            salesProceeds = ticketPrice;
        }
    }

    /**
     * Calculate the change.
     * @param handedMoney 手渡しされた金額
     * @param ticketPrice チケットの価格
     * @return お釣り金額
     */
    private int calculateChange(int handedMoney, int ticketPrice) {
        return handedMoney - ticketPrice;
    }

    // #1on1: privateメソッドの種類
    //
    // o 切り出して見通しをよくするためだけのprivateメソッド
    // o 特定業務の中だけで再利用するためのprivateメソッド
    // o クラス全体で再利用するためのprivateメソッド

    // done edo privateメソッド名、... by jflute (2025/11/28)
    // (追記: publicメソッドのbuyとメソッド一覧のときとか紛れてしまうので、
    // 区別しやすいようにprivateメソッドの方は先頭文字を変えるテクニック)
    // e.g. doBuyTicket(), internalBuyTicket() みたいな名前を付けるテクニックがある。
    // ぜひ、renameしてみてください。VSCodeでrenameのショートカットあると思うのでぜひ。
    //  command+P :: ファイル検索 (">" を打てばコマンド検索になる)
    //  shift+command+P :: コマンド検索 (単にファイル検索テキストボックスに ">" が追加されるだけ)
    // そこで、Rename Symbol で rename のリファクタリング機能になる。
    //1201修正メモ doBuyTicket()に修正した
    //合わせて、各Ticket Typeのメソッド名をdoBuyhogehogeTicket()に修正した

    //1117refactorメモ BiFunctionを使ってチケット作成関数を渡すようにした
    //Ticket::creatNormalTicket, Ticket::creatNightOnlyTicket, Ticket::creatDayTimeOnlyTicketを渡すようにした
    //ticketの購入を一気に共通化した

    // done edo リファクタリング前のticketCreatorが残ってる by jflute (2025/12/25)
    //0106 修正メモ：ticketCreatorを削除し,不要なJava docを削除した
    /**
     * Buy ticket with common purchase flow.
     * @param handedMoney 手渡しされた金額
     * @param ticketType チケットの種別
     * @return 購入されたチケットとお釣り金額
     */
    private TicketBuyResult doBuyTicket(Integer handedMoney, TicketType ticketType) {
        // done edo getPrice()も変数抽出して、重要な処理の行をスッキリさせましょう by jflute (2025/12/25)
        // (ぜひ、VSCodeで変数抽出のショートカット探してみてください)
        //0111修正メモ： getPrice()を変数int ticketPriceに切り出して抽出して、処理行を簡潔にした
        int usedTicketQuantity = ticketType.getUsedTicketQuantity();
        int ticketPrice = ticketType.getPrice();
        validatePurchaseRequirements(handedMoney, ticketPrice, usedTicketQuantity);
        processPurchase(ticketPrice, usedTicketQuantity);
        int change = calculateChange(handedMoney, ticketPrice);
        return new TicketBuyResult(new Ticket(ticketType), change);
    }
    
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
