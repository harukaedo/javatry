package org.docksidestage.bizfw.basic.buyticket;

// TODO edo javadocの直下の空行は、あまり一般的に開けないので削除しちゃってください by jflute (2025/10/03)
// (getterの方は空けてないので統一性のことも考えて)
// TODO edo javadoc, 「チケットとお釣り」って断定しちゃうと、後で何か追加されたときに誤解を生んじゃう by jflute (2025/10/03)
// なので、"など" とか使うと、長生きするコメントになる。
// ここで達成したいのは "チケット購入結果" という概念のイメージを沸かせること。
// なので、具体的に何の項目が管理されているか？の全部を伝えるのではなく、
// イメージを沸かせる項目だけサンプルとして紹介すれば良い。
// "など" 以外では、"とか" とか、"e.g." とか "例えば"
/**
 * チケット購入結果を表すクラス。
 * e.g. 購入されたチケットとお釣り金額の管理をする。
 * 
 * @author jflute
 * @author harukaedo
 */

public class TicketBuyResult {

    private final Ticket ticket;
    private final int change;

    /**
     * チケット購入結果を構築します。
     * 
     * @param ticket 購入されたチケット (NotNull)
     * @param change お釣り金額
     */

    public TicketBuyResult(Ticket ticket, int change) {
        this.ticket = ticket;   
        this.change = change;
    }

    // TODO edo getterの説明は冗長感あるし、なくても通じるので、@returnのみでOK by jflute (2025/10/03)
    // (そういう意味では、どストレートな Constructor も同じ)
    /**
     * 購入されたチケットを取得します。
     * 
     * @return 購入されたチケット
     */
    public Ticket getTicket() {
        return ticket;
    }

    /**
     * お釣り金額を取得します。
     * 
     * @return お釣り金額
     */
    public int getChange() {
        return change;
    }
}