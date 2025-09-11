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

import org.docksidestage.unit.PlainTestCase;

/**
 * The test of if-for. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author harukaedo
 */
public class Step02IfForTest extends PlainTestCase {

    // ===================================================================================
    //                                                                        if Statement
    //                                                                        ============
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_if_basic() { // example, so begin from the next method
        int sea = 904;
        if (sea >= 904) {
            sea = 2001;
        }
        log(sea); // your answer? => 2001
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_if_else_basic() {
        int sea = 904;
        if (sea > 904) {
            sea = 2001;
        } else {
            sea = 7;
        }
        log(sea); // your answer? => 7
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_if_elseif_basic() {
        int sea = 904;
        if (sea > 904) {
            sea = 2001;
        } else if (sea >= 904) {
            sea = 7;
        } else if (sea >= 903) {
            sea = 8;
        } else {
            sea = 9;
        }
        log(sea); // your answer? => 7
    }


    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_if_elseif_nested() {
        boolean land = false;
        int sea = 904;
        //１つ目のif文
        if (sea > 904) {//1-1
            sea = 2001;
        } else if (land && sea >= 904) {//1-2
            sea = 7; 
        } else if (sea >= 903 || land) {//1-3
            sea = 8;
            if (!land) {//1-3-1
                land = true;
            } else if (sea <= 903) {//1-3-2
                sea++;
            }
        } else if (sea == 8) {//1-4
            sea++;
            land = false;
        } else {//1-5
            sea = 9;
        }
        //２つ目のif文
        if (sea >= 9 || (sea > 7 && sea < 9)) {//2-1
            sea--;
        }
        //３つ目のif文
        if (land) {//3-1
            sea = 10;
        }
        log(sea); // your answer? => 9
    }
    //0728edo【自分なりの解釈】
    //正しい答えは10。2つ目と3つ目のif文を考慮していなかった
    //1-1はfalseなので、スキップ。
    //1-２は、landがfalseのため、＆＆で全体的にfalseとなり、スキップ。
    //1-3は、seaが903以上なので、trueとなり、sea=8に変更。
        //ネストのif文について、landがfalseなので、1-3-1が実行され、land=trueに変更。2つ目のif文へ
    //2−1はsea=8で、8 >= 9はfalse,しかし、8 > 7 && 8 < 9はtrueなので、sea--が実行され、sea=7に変更。3つ目のif文へ
    //3-1はlandがtrueなので、sea=10に変更。
    // done edo [ふぉろー] まず最初に、細かく読むのではなく、if文の構造が何個あるのか？構造だけ読むと良いです by jflute (2025/07/31)
    // done jflute 1on1にて、ソースコードリーディングの基礎からの話 (2025/07/31)
    // まず「漠然読み」で構造だけ把握して、(これぞと思った)当てを付けて「フォーカス読み」。
    // ギャンブルに負けてもピンポイントでコードを読んでことは無駄にはならない。
    // その後のギャンブルも見つかるかもしれないし、しらみつぶしも速くなってる。

    // ===================================================================================
    //                                                                       for Statement
    //                                                                       =============
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_for_inti_basic() {
        List<String> stageList = prepareStageList();
        String sea = null;
        
        // #1on1: while文との比較
        //Iterator<String> iterator = stageList.iterator();
        //while (iterator.hasNext()) {
        //    ... = iterator.next();
        //}
        //for ( ; iterator.hasNext() ; ) { // 初期化と毎ループ処理がある
        //    //    ... = iterator.next();
        //}
        // ただ、int i で index で制御するのがオーソドックスな使い方
        for (int i = 0; i < stageList.size(); i++) {
            String stage = stageList.get(i);//1
            if (i == 1) {//2
                sea = stage;
            }
        }
        log(sea); // your answer? => 16
    }
    //0728edo【自分なりの解釈】
    //正しい答えはdockside。
    //prepareStageListのメゾットの中身を見ておらずprepareStageListのただの文字列を返してしまっていた
    //1はprepareStageListのインデックス番号のi番目を取得する
    //2では、iが1の時、つまり2番目の要素の文字列をseaに代入する。(indexは0から始まるため)
    // done edo [ふぉろー] 合ってます！indexって言うと基本的には0から始まるのが慣習です by jflute (2025/07/31)

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_for_foreach_basic() {
        List<String> stageList = prepareStageList();
        String sea = null;
        // #1on1: 文法用語として「拡張for文」と言われるもの (2025/08/22)
        // 普通のfor文と言ったら？こっちを指すことが多い印象。
        // 文法用語と現場用語のお話、BigDecimal の add() を例に。
        for (String stage : stageList) {//1
            sea = stage;
        }
        log(sea); // your answer? => magiclamp
    }
    //0728edo【自分なりの解釈】
    //１でstageListの要素を順番に取り出して、毎回seaに代入している。
    //最終的に、stageListの最後の要素であるmagiclampが代入される。
    // done edo [ふぉろー] OKです。まあ通常こういうプログラムを書くことはないですが^^ by jflute (2025/07/31)
    // ループの挙動をしっかり解釈できるようにというエクササイズです。

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_for_foreach_continueBreak() {
        List<String> stageList = prepareStageList();
        String sea = null;
        for (String stage : stageList) {
            if (stage.startsWith("br")) {//1
                continue;
            }
            sea = stage;//2
            if (stage.contains("ga")) {//3
                break;
            }
        }
        log(sea); // your answer? => hangar
    }
    //0728edo【自分なりの解釈】
    //1で0番目であるbroadwayがbrで始まるため、continueでスキップされる。
    //2で1番目のdocksideが代入される。
    //3でdocksideはgaを含まないため、スキップされる。
    //gaを含むhangarが来るまでループし続け、logにはhangarが出力される。
    // done edo [いいね] 詳しく制御を追うことができていてGoodです by jflute (2025/07/31)
    // continue;とbreak;がちょっとややこしい動きしますよね。

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_for_listforeach_basic() {
        List<String> stageList = prepareStageList();
        StringBuilder sb = new StringBuilder();//1
        // #1on1: for文というよりは、forEach()メソッド。
        // #1on1: コールバックとは？
        // A -> B  ::  test_        -> forEach()
        //      v                       v
        // A <- B  ::  test_(の関数) <- forEach()
        // そのコールバックで使われる関数をコールバック関数と呼ぶようになった
        // forEach()メソッドは、コールバックの仕組みを使ったfor分の代理メソッド
        // (コールバックに関しては、さらにstep8で深堀りしていく予定)
        // 1995: いんとあいのfor文 (javaの誕生)
        // 2005: 拡張for文 (普通のfor文)
        // 2015: forEach()メソッド
        stageList.forEach(stage -> {
            if (sb.length() > 0) {//2
                return;
            }
            if (stage.contains("i")) {//3
                sb.append(stage);
            }
        });
        String sea = sb.toString();
        log(sea); // your answer? => magiclamp
    }
    //0728edo【自分なりの解釈】
    //正しい回答はdockside。それぞれの行で何をしているのかあまり理解できていなかった
    //1でStringBuilderのインスタンスを生成。空の状態。
    //2でsbは0のためfalseとなり、スキップされる。
    //3でseaにiを含む文字列があれば、sbに追加される。
    //docksideはiを含むため、sbに追加される。
    //そもそもhoge.forEach(hogehoge -> {});って何
        //リストの各要素に対して、指定した処理を行うためのメソッド。
        //hogehogeは各要素を表すパラメータになるため任意のものでいい。
            //stageはstageListを省略したものだと勘違いしていた
    // done edo [ふぉろー] 一つ前のfor文と基本的には同じような挙動をするループです by jflute (2025/07/31)
    // ただ調べてもらった通り、こちらはJavaに組み込まれた文法ではなく、単なるメソッドではあります。
    // ちなみに、stageList に対して stage とあった場合、大抵は複数のstageの一つを指すことが多いです。
    // done jflute 1on1にて、forEach()メソッドのフォロー予定 (2025/07/31)

    public void test_forEach_try() {
        List<String> stageList = prepareStageList();
        String sea = null;
        stageList.forEach(stage -> {
            if (stage.startsWith("br")) {
                // ループの外という扱いなのでbreakは使えない
                //continue;
                // でも、コールバックはメソッドのようなものなので、returnは使える
                // 実質、continue;っぽい振る舞いになる
                return;
            }
            // コールバックは他のクラスのメソッドなので、ローカル変数の書き換えはできない
            // (Javaとしては、コールバックの中でローカル変数を書き換えちゃうと複雑になると考えてダメにした)
            //sea = stage;
            if (stage.contains("ga")) {
                // ループの外という扱いなのでbreakは使えない
                //break;
            }
        });
        log(sea);
        // forEach()は (for文に比べて) できないことだらけ...
        // なぜ、後から導入された？ (導入されたからには良い理由があるはず？)
        // 1995: いんとあいのfor文 (javaの誕生)
        // 2005: 拡張for文 (普通のfor文)
        // 2015: forEach()メソッド
        //
        // できないは安心安全のメリットになる。適材適所のやり方。
        // 一方で、使い分けの判断の負荷は掛かる。トレードオフ。
    }
    
    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Make list containing "a" from list of prepareStageList() and show it as log by loop. (without Stream API) <br>
     * (prepareStageList()のリストから "a" が含まれているものだけのリストを作成して、それをループで回してログに表示しましょう。(Stream APIなしで))
     */
    public void test_iffor_making() {
        // write if-for here
        List<String> stageList = prepareStageList();
        List<String> result = new ArrayList<>();
        stageList.forEach(stage -> {
            if (stage.contains("a")) { //1
                result.add(stage); //2
            }
        });
        result.forEach(this::log); //3
    }
    //0728edo【自分なりの解釈】
    //this::logは、resultの各要素をログに出力するためのメソッド参照。
    //this→このクラス（自分自身）の
    //::→メゾット参照の記号
    //log→ログに出力するメソッド。
    // done edo [いいね] できてます、素晴らしいです by jflute (2025/07/31)
    // TODO jflute 1on1にて、Lambda式とメソッドリファレンスのお話 (2025/07/31)


    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * Change foreach statement to List's forEach() (keep result after fix) <br>
     * (foreach文をforEach()メソッドへの置き換えてみましょう (修正前と修正後で実行結果が同じになるように))
     */
    
    public void test_iffor_refactor_foreach_to_forEach() {
        List<String> stageList = prepareStageList();
        String sea = null;
        for (String stage : stageList) {
            if (stage.startsWith("br")) {
                continue;
            }
            sea = stage;
            if (stage.contains("ga")) {
                break;
            }
        }
        log(sea); // should be same as before-fix
    }
    //0728edo【質問】
    //難しいのでスキップ。
    //foreachはループしているわけではないと思ったためどのように書けばいいかわかりませんでした。
    //解説していただけると嬉しいです
    // done jflute 1on1にて、forEach()書き換えエクササイズのフォロー予定 (そもそもforEach()とは？から) (2025/07/31)
    // #1on1: forEach()でできないことを以下に工夫して代替するか？
    // TODO edo 修行++: forEach()で同じ結果が返ってくるように工夫してみましょう by jflute (2025/08/22)

    //0908challenge
    public void challenge_refactor_foreach_to_forEach_answer() {
        List<String> stageList = prepareStageList();
        final String[] sea = {null};
        final boolean[] found = {false};

        stageList.forEach(stage -> {
            if(found[0]) {
                return;
            }
            if (stage.startsWith("br")) {
                return;
            }
            sea[0] = stage;
            if (stage.contains("ga")) {
                found[0] = true;
            }
        });
        log(sea[0]); // should be same as before-fix
    }   

    /**
     * Make your original exercise as question style about if-for statement. <br>
     * (if文for文についてあなたのオリジナルの質問形式のエクササイズを作ってみましょう)
     * <pre>
     * _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
     * your question here (ここにあなたの質問を):
     * countで出力されるものは？
     * _/_/_/_/_/_/_/_/_/_/
     * </pre>
     */
    public void test_iffor_yourExercise() {
        // write your code here
        List<String> stageList = prepareStageList();
        int count = 0;
        for (String stage : stageList) {
            if (stage.contains("g")){
                count++;
            }
        }
        log(count);
        // done edo [いいね] ループの挙動をしっかり把握する良いエクササイズですね！ by jflute (2025/07/31)
    }

    // ===================================================================================
    //                                                                        Small Helper
    //                                                                        ============
    private List<String> prepareStageList() {
        List<String> stageList = new ArrayList<>();
        stageList.add("broadway");
        stageList.add("dockside");
        stageList.add("hangar");
        stageList.add("magiclamp");
        return stageList;
    }
}
