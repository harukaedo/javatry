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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.docksidestage.javatry.basic.st8.St8DbFacade;
import org.docksidestage.javatry.basic.st8.St8Member;
import org.docksidestage.javatry.basic.st8.St8Withdrawal;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of Java8 functions. <br>
 * Operate as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りに実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author harukaedo
 */
public class Step08Java8FunctionTest extends PlainTestCase {

    // ===================================================================================
    //                                                                              Lambda
    //                                                                              ======
    // -----------------------------------------------------
    //                                              Callback
    //                                              --------
    /**
     * Are all the strings by log() methods in callback processes same? (yes or no) <br>
     * (コールバック処理の中で出力しているログの文字列はすべて同じでしょうか？ (yes or no))
     */
    public void test_java8_lambda_callback_basic() {
        String title = "over";

        //log(stage + ": " + title);
        // 1.クラスに名前をつけて事前に用意
        log("...Executing named class callback(!?)");
        helpCallbackConsumer(new St8BasicConsumer(title));

        //log(stage + ": " + title);
        //2.クラスに名前をつけずに、匿名クラスでその場で実装
        log("...Executing anonymous class callback");
        helpCallbackConsumer(new Consumer<String>() {
            public void accept(String stage) {
                log(stage + ": " + title);
            }
        });

        //log(stage + ": " + title);
        //3.2をさらに省略して、TSのアロー関数的な感じでメソッドが一つのみの場合、
        //new Consumer<String>() {  public void accept(String stage・・・を省略できる
        //参考：https://qiita.com/kenRp01/items/4045a7925340088bd7e3
        log("...Executing lambda block style callback");
        helpCallbackConsumer(stage -> {
            log(stage + ": " + title);
        });
        
        //log(stage + ": " + title);
        //4.3をさらに省略して、メソッドが一つのみで、処理が一行の場合、{ } と ; すら省略できる
        log("...Executing lambda expression style callback");
        helpCallbackConsumer(stage -> log(stage + ": " + title));

        // your answer? => yes。一緒になる

        // cannot reassign because it is used at callback process
        //title = "wave";
    }

    /**
     * What is order of strings by log(). (write answer as comma-separated) <br>
     * (ログに出力される文字列の順番は？ (カンマ区切りで書き出しましょう))
     */
    public void test_java8_lambda_callback_order() {
        log("harbor");
        helpCallbackConsumer(stage -> {
            log(stage);
        });
        log("lost river");
        // your answer? => harbor,broadway,dockside,hangar,lost river
        //0618解釈メモ
        //1.最初にシンプルに"harbor"を出力している
        //2-1.helpCallbackConsumerメソッドを呼び出す
        //2-2.helpCallbackConsumerメソッドの中で、"broadway"を出力している
        //2-3.helpCallbackConsumerメソッドの中で、引数として渡されたラムダ式を実行しているので、"dockside"を出力している
        //2-4.helpCallbackConsumerメソッドの中で、"hangar"を出力
        //3-1.helpCallbackConsumerメソッドの処理が終わり、元のメソッドに戻る
        //3-2.最後に"lost river"を出力している
    }

    private class St8BasicConsumer implements Consumer<String> {

        private final String title;

        public St8BasicConsumer(String title) {
            this.title = title;
        }

        //Consumer<T> のためこの形で実装する
        @Override
        public void accept(String stage) {
            log(stage + ": " + title);
        }
    }

    //Consumer<String>を受け取って、broadway,コールバック引数としてdockside,hangarの順でログ出力するメソッド
    //Consumer<String>
    /*
      public interface Consumer<T> {
      void accept(T t); ->絶対この形で実装する
    }
     */
    private void helpCallbackConsumer(Consumer<String> oneArgLambda) {
        log("broadway");
        oneArgLambda.accept("dockside");
        log("hangar");
    }

    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_java8_lambda_callback_valueRoad() {
        String label = "number";
        String sea = helpCallbackFunction(number -> {
            return label + ": " + number;//number + : number(7) →number: 7
        });
        log(sea); // your answer? => number: 7
    }

    //R apply(T t); Tを引数として受け取り、Rを返すメソッドとなる
    private String helpCallbackFunction(Function<Integer, String> oneArgLambda) {
        return oneArgLambda.apply(7);
    }

    // -----------------------------------------------------
    //                                         Convert Style
    //                                         -------------
    /**
     * Change callback style like this:
     * <pre>
     * o sea: to lambda block style
     * o land: to lambda expression style
     * o piari: to lambda block style
     * </pre>
     * (このようにコールバックスタイルを変えてみましょう:)
     * <pre>
     * o sea: BlockのLambda式に
     * o land: ExpressionのLambda式に
     * o piari: BlockのLambda式に
     * </pre>
     */
    public void test_java8_lambda_convertStyle_basic() {
        // helpCallbackSupplier(new Supplier<String>() { // sea
        //     public String get() {
        //         return "broadway";
        //     }
        // });
        //BlockのLambda式に
        helpCallbackSupplier(() -> {
            return "broadway";
        });    

        // helpCallbackSupplier(() -> { // land
        //     return "dockside";
        // });
        //ExpressionのLambda式に
        helpCallbackSupplier(() -> 
            "dockside"
        );
        //１行だけなら右のほうがいい？🤔 helpCallbackSupplier(() -> "dockside");

        //helpCallbackSupplier(() -> "hangar"); // piari
        //BlockのLambda式に
        helpCallbackSupplier(() -> {
            return "lost river";
        });
    }

    private void helpCallbackSupplier(Supplier<String> oneArgLambda) {
        String supplied = oneArgLambda.get();
        log(supplied);
    }

    // ===================================================================================
    //                                                                            Optional
    //                                                                            ========
    /**
     * Are the strings by two log() methods same? (yes or no) <br>
     * (二つのlog()によって出力される文字列は同じでしょうか？ (yes or no))
     */
    public void test_java8_optional_concept() {
        //実態は St8Member(memberId, "broadway", new St8Withdrawal(11, "music"));
        St8Member oldmember = new St8DbFacade().oldselectMember(1);
        if (oldmember != null) {
            log(oldmember.getMemberId(), oldmember.getMemberName());//1,broadway
        }
        //Optional=その値がnullかもしれない ことを表現するクラス
        //https://qiita.com/shindooo/items/815d651a72f568112910
        //このへんDBfluteでも少し触れてる
        Optional<St8Member> optMember = new St8DbFacade().selectMember(1);
        if (optMember.isPresent()) {
            St8Member member = optMember.get();
            log(member.getMemberId(), member.getMemberName());//1,broadway
        }
        // your answer? => yes
        //St8Member oldmembe・・・はnull じゃないので if に入る → log(1, "broadway")
        //Optionaljで、nullじゃないのでisPresentで中身出力　→ log(1, "broadway")
    }

    /**
     * Are the strings by two log() methods same? (yes or no) <br>
     * (二つのlog()によって出力される文字列は同じでしょうか？ (yes or no))
     */
    public void test_java8_optional_ifPresent() {
        Optional<St8Member> optMember = new St8DbFacade().selectMember(1);
        if (optMember.isPresent()) {
            St8Member member = optMember.get();
            log(member.getMemberId(), member.getMemberName());//1,broadway
        }
        optMember.ifPresent(member -> {
            log(member.getMemberId(), member.getMemberName());//1,broadway
        });
        // your answer? => yes
        //Optionalで、nullじゃないのでisPresentで中身出力　→ log(1, "broadway")
        //中身あるのでifPresent のコールバックが実行される → member に St8Member(1, "broadway") が入る → log(1, "broadway")
        //Optionalの方、nullチェックしてgetとかも自分で書いているから２とかの方がスッキリしてそう
    }

    /**
     * What string is sea, land, piari, bonvo, dstore, amba variables at the method end? <br>
     * (メソッド終了時の変数 sea, land, piari, bonvo, dstore, amba の中身は？)
     */
    public void test_java8_optional_map_flatMap() {
        St8DbFacade facade = new St8DbFacade();

        // traditional style
        St8Member oldmemberFirst = facade.oldselectMember(1);
        String sea;
        if (oldmemberFirst != null) {
            St8Withdrawal withdrawal = oldmemberFirst.oldgetWithdrawal();//11
            if (withdrawal != null) {
                sea = withdrawal.oldgetPrimaryReason();//music
                if (sea == null) {
                    sea = "*no reason1: the PrimaryReason was null";//実行されない
                }
            } else {
                sea = "*no reason2: the Withdrawal was null";//実行されない
            }
        } else {
            sea = "*no reason3: the selected Member was null";//実行されない
        }

        Optional<St8Member> optMemberFirst = facade.selectMember(1);

        // map style
        //land = music
        String land = optMemberFirst.map(mb -> mb.oldgetWithdrawal())//withdrawalがnull allowed
                .map(wdl -> wdl.oldgetPrimaryReason())//primaryReasonを箱詰め＝music
                .orElse("*no reason: someone was not present");

        // flatMap style
        //piari = music
        String piari = optMemberFirst.flatMap(mb -> mb.getWithdrawal())//Optional.ofNullable(withdrawal)でnull allowed
                .flatMap(wdl -> wdl.getPrimaryReason())//Optional.ofNullable(primaryReason)でprimaryReasonを箱詰め＝music
                .orElse("*no reason: someone was not present");

        // flatMap and map style
        //bonvo = music
        String bonvo = optMemberFirst.flatMap(mb -> mb.getWithdrawal())//Optional.ofNullable(withdrawal)でnull allowedになるので、musicをmapするしかない
                .map(wdl -> wdl.oldgetPrimaryReason())//primaryReasonを箱詰め＝music
                .orElse("*no reason: someone was not present");

        //mapとflatMapの違い
        //読むhttps://qiita.com/KevinFQ/items/97137efb2159009b60e1

        //dstore = null
        //間違い
        //正しくはdstore = *no reason: someone was not present
        //null=空になると、orElseは実行される
        String dstore = facade.selectMember(2)//会員ID２になるのでprimaryReasonはnull
                .flatMap(mb -> mb.getWithdrawal())//withdrawalがnull allowed
                .map(wdl -> wdl.oldgetPrimaryReason())//primaryReason(null)を箱詰め＝null
                .orElse("*no reason: someone was not present");

        //amba = null
        //間違い
        //正しくはamba = *no reason: someone was not present
        //null=空になると、orElseは実行される
        String amba = facade.selectMember(3)//会員ID３になるのでprimaryReasonはnull
                .flatMap(mb -> mb.getWithdrawal())
                .flatMap(wdl -> wdl.getPrimaryReason())
                .orElse("*no reason: someone was not present");

        int defaultWithdrawalId = -1;
        Integer miraco = facade.selectMember(2)
                .flatMap(mb -> mb.getWithdrawal())//会員IDのwithdrawalIDは12
                .map(wdl -> wdl.getWithdrawalId()) // ID here12をmapで箱詰め
                .orElse(defaultWithdrawalId);

        log(sea); // your answer? => music
        log(land); // your answer? => music
        log(piari); // your answer? => music
        log(bonvo); // your answer? => music
        log(dstore); // your answer? => null
        log(amba); // your answer? => null
        log(miraco); // your answer? => 12
    }

    /**
     * What string is sea variables at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_java8_optional_orElseThrow() {
        Optional<St8Member> optMember = new St8DbFacade().selectMember(2);
        St8Member member = optMember.orElseThrow(() -> new IllegalStateException("over"));//会員ID2のdocksideは存在してるのでここはスルーされる
        String sea = "the";
        try {
            String reason = member.getWithdrawal().map(wdl -> wdl.oldgetPrimaryReason()).orElseThrow(() -> {
                return new IllegalStateException("wave");//会員ID２のmapはnullとなっているのでorElseThrowが実行されてwaveが返される
            });
            sea = reason;//wave
        } catch (IllegalStateException e) {
            sea = e.getMessage();
        }
        log(sea); // your answer? => wave
    }

    // ===================================================================================
    //                                                                          Stream API
    //                                                                          ==========
    /**
     * What string is sea, land variables at the method end? <br>
     * (メソッド終了時の変数 sea, land の中身は？)
     */
    public void test_java8_stream_concept() {
        List<St8Member> memberList = new St8DbFacade().selectMemberListAll();
        List<String> oldfilteredNameList = new ArrayList<>();
        for (St8Member member : memberList) {
            if (member.getWithdrawal().isPresent()) {//withdrawalが存在する場合だけ名前をリスト化する
                oldfilteredNameList.add(member.getMemberName());
            }
        }
        String sea = oldfilteredNameList.toString();
        log(sea); // your answer? => broadway,dockside

        //処理の流れじゃ少し違うがやってることは一緒。絞り込みして、集めるかんじ
        List<String> filteredNameList = memberList.stream() //
                .filter(mb -> mb.getWithdrawal().isPresent()) //
                .map(mb -> mb.getMemberName()) //
                .collect(Collectors.toList());
        String land = filteredNameList.toString();
        log(land); // your answer? => broadway,dockside
    }

    /**
     * What string is sea, variables at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_java8_stream_map_flatMap() {
        List<St8Member> memberList = new St8DbFacade().selectMemberListAll();
        int sea = memberList.stream()
                .filter(mb -> mb.getWithdrawal().isPresent())//withdrawalが存在する場合だけ絞り込む　→ broadway,dockside
                .flatMap(mb -> mb.getPurchaseList().stream())//PurchaseListをmapして箱詰め　
                //broadway (111, 100),(112, 200),(113, 200),(114, 300)　dockside なし
                .filter(pur -> pur.getPurchaseId() > 100)//PurchaseIdが100より大きいものだけ絞り込む
                //broadway (111, 100),(112, 200),(113, 200),(114, 300)で結局全部通過
                .mapToInt(pur -> pur.getPurchasePrice())//intに変換して箱詰め
                //broadway 100,200,200,300
                .distinct()//重複を排除するhttps://kita-note.com/java-stream-distinct
                //broadway 100,200,300
                .sum();//合計
        log(sea); // your answer? => 600
    }

    // *Stream API will return at Step12 again, it's worth the wait!
}
