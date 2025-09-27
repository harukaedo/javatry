package org.docksidestage.bizfw.basic.buyticket;

/**
 * チケット購入結果を表すクラス。
 * 購入されたチケットとお釣り金額の管理をする。
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