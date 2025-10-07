package org.docksidestage.bizfw.basic.buyticket;

// TODO done edo javadocの直下の空行は、あまり一般的に開けないので削除しちゃってください by jflute (2025/10/03)
// (getterの方は空けてないので統一性のことも考えて)
// TODO done edo javadoc, 「チケットとお釣り」って断定しちゃうと、後で何か追加されたときに誤解を生んじゃう by jflute (2025/10/03)
// なので、"など" とか使うと、長生きするコメントになる。
// ここで達成したいのは "チケット購入結果" という概念のイメージを沸かせること。
// なので、具体的に何の項目が管理されているか？の全部を伝えるのではなく、
// イメージを沸かせる項目だけサンプルとして紹介すれば良い。
// "など" 以外では、"とか" とか、"e.g." とか "例えば"
/**
 * チケット購入結果を表すクラス。
 * 購入されたチケットやお釣り金額などを管理する
 * 
 * @author jflute
 * @author harukaedo
 */
public class TicketBuyResult {

    private final Ticket ticket;
    private final int change;

    /**
     * @param ticket 購入されたチケット (Nullにならない)
     * @param change お釣り金額
     */
    public TicketBuyResult(Ticket ticket, int change) {
        this.ticket = ticket;   
        this.change = change;
    }

    // TODO done edo getterの説明は冗長感あるし、なくても通じるので、@returnのみでOK by jflute (2025/10/03)
    // (そういう意味では、どストレートな Constructor も同じ)
    /**
     * @return 購入されたチケット
     */
    public Ticket getTicket() {
        return ticket;
    }

    /**   
     * @return お釣り金額
     */
    public int getChange() {
        return change;
    }
}