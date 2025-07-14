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

import java.math.BigDecimal;

import org.docksidestage.unit.PlainTestCase;

// TODO edo 事務的なレビューですが、javatry研修では以下のようにjavadocのauthorをお願いします by jflute (2025/07/14)
// // ハンズオンのコーディングポリシー - 3. 最低限のクラスJavaDoc
// https://dbflute.seasar.org/ja/tutorial/handson/review/codingpolicy.html#minjavadoc
// ここでは、your_name_here のところを修正すればOKです。
/**
 * The test of variable. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author your_name_here
 */
public class Step01VariableTest extends PlainTestCase {

    // ===================================================================================
    //                                                                      Local Variable
    //                                                                      ==============
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_variable_basic() { // example, so begin from the next method
        String sea = "mystic";
        log(sea); // your answer? => mystic
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_initial() {
        String sea = "mystic";
        Integer land = 8;
        String piari = null;
        String dstore = "mai";
        sea = sea + land + piari + ":" + dstore;
        log(sea); // your answer? => mystic8：mai
        //正しい出力はmystic8null:mai
        //Javaでは、+ 演算子を使って文字列連結を行う際、null 値は文字列 "null" に変換される
        //null を空文字として扱いたい場合は、三項演算子を使って (piari != null ? piari : "") のような処理が必要になる
        //三項演算子（ternary operator）は、条件に基づいて2つの値のうち1つを選択する演算子です。?: の記号を使用する
        //<例>条件式 ? 真の場合の値 : 偽の場合の値
        // TODO edo [いいね] 思考コメントありがとうございます。わかりやすいです。 by jflute (2025/07/14)
        // 昔のインターネットサービスの画面では、よく画面に「こんにちは、nullさん」とか表示されてました。
        // さすがに最近は見なくなりましたが、メールではわりと最近でもメール文章に null って表示されてたことありました。
        // 一方で、ログにとかに出力するときは null って表示されるのでわかりやすいという面はあります。
        // まあ、些細な点ですが、言語によってはこういうところの挙動が変わったりします。
        // ちなみに JavaScript だと、"null" どころか "undefined" とか表示されることがあります。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_reassigned_basic() {
        String sea = "mystic";
        String land = "oneman";
        sea = land;
        land = land + "'s dreams";
        log(sea); // your answer? => oneman'sdreams
        //正しい出力はoneman
        //sea = land; の時点で、sea は land が参照している文字列オブジェクト "oneman" を参照するようになる
        //その後、land = land + "'s dreams"; で、land は新しい文字列 "oneman's dreams" を参照するようになる
        //しかし sea は元の "oneman" を参照し続ける
        // TODO edo [いいね] 参照という言葉が適切でとても良いです。 by jflute (2025/07/14)
        // 変数は実体そのものではなく実体を参照するものなので、sea = land の一瞬は sea と land が同じものを参照します。
        // (オブジェクト型(参照型)であればの話)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_reassigned_int() {
        int sea = 94;
        int land = 415;
        sea = land;
        land++;
        log(sea); // your answer? => 415
        //seaは元のlandを参照し続けるため、landがインクリメントされていてもseaには影響しないという認識であってますか？
        // TODO edo [へんじ] ++のインクリメントも単純に land = land + 1 を省略した記法と言えるので... by jflute (2025/07/14)
        // そういう意味では、一つ前のエクササイズとあまり要点は変わりません。
        // 一方で、ちょっと内部的に違うのは、int はプリミティブ型と言われる値そのものが変数に入っているようなイメージなので、
        // sea = land; の一瞬は同じ値ではありますが、さっきの String と違って同じ実体を参照しているわけではなく、
        // 415という数値がそのままコピーされたような感じです。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_reassigned_BigDecimal() {
        BigDecimal sea = new BigDecimal(94);
        BigDecimal land = new BigDecimal(415);
        sea = land;
        sea = land.add(new BigDecimal(1));
        sea.add(new BigDecimal(1));
        log(sea); // your answer? => 416
        //sea.add(new BigDecimal(1));はsea自体に+1しているため95になっていてseaはlandを参照するため、land自体に+1して416になるという認識であっていますか？
        // TODO edo 厳密には sea は途中で415インスタンスに差し替わっているので95は発生しません。 by jflute (2025/07/14)
        // ちょっと一行一行の補足です:
        //  BigDecimal sea = new BigDecimal(94); // sea変数が、「94 の BigDecimalインスタンス」を参照する
        //  BigDecimal land = new BigDecimal(415); // land変数が、「415 の BigDecimalインスタンス」を参照する
        //
        //  sea = land; // sea変数の参照先が、land変数の参照先と同じものになる (415 の BigDecimalインスタンス)
        //              // この時点で、 94 の BigDecimal インスタンスは誰からも参照されなくなる
        //
        //  sea = land.add(new BigDecimal(1)); // land変数を経由して「415 の BigDecimalインスタンス」の add() を呼び出す
        //                                     // 引数は「1 のBigDecimalインスタンス」が指定
        //                                     // add() は自分自身と引数を足したものを戻すので...
        //                                     // sea は「416 の BigDecimalインスタンス」を参照
        //                                     // land が参照している「415 の BigDecimalインスタンス」はそのまま
        //
        //  sea.add(new BigDecimal(1));        // この行は少し自分で考えてみましょう。(宿題)
        //
        // これを元に考えてみて、また疑問に思ったことなどあったらぜひここで書いて質問してください(^^。
        
        // TODO jflute 1on1のときに、add()の挙動から、immutableの話までフォローする予定 (2025/07/14)
        // ↑これはくぼ用のtodoということでそのまま残しておいてください。
    }

    // ===================================================================================
    //                                                                   Instance Variable
    //                                                                   =================
    private String instanceBroadway;
    private int instanceDockside;
    private Integer instanceHangar;
    private String instanceMagiclamp;

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_default_String() {
        String sea = instanceBroadway;
        log(sea); // your answer? => instanceBroadway
        //正しい回答はnull
        //Javaでは、インスタンス変数は明示的に初期化されていない場合、自動的にデフォルト値で初期化される
        // プリミティブ型
        //int intValue;        // デフォルト値: 0
        //boolean boolValue;   // デフォルト値: false
        //double doubleValue;  // デフォルト値: 0.0
        //char charValue;      // デフォルト値: '\u0000'
        //オブジェクト型
        //String stringValue;  // デフォルト値: null
        //Integer integerValue; // デフォルト値: null
        //Object objectValue;  // デフォルト値: null

    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_default_int() {
        int sea = instanceDockside;
        log(sea); // your answer? => 0
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_default_Integer() {
        Integer sea = instanceHangar;
        log(sea); // your answer? => null
        //integerはオブジェクト型のため、初期値はnullになる
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_via_method() {
        instanceBroadway = "bbb"; //1
        instanceMagiclamp = "magician";//2
        helpInstanceVariableViaMethod(instanceMagiclamp);//3
        String sea = instanceBroadway + "|" + instanceDockside + "|" + instanceHangar + "|" + instanceMagiclamp;//4
        log(sea); // your answer? => bbb|||magician
        //正しい回答はbigband|1|null|magician
        //インスタンス変数はnullで設定されているが、1,2にてbbbとmagicianを設定
        //3でメゾットを呼び出し、引数とてinstanceMagiclampを渡す
        //helpInstanceVariableViaMethodでは,,,?
        //private void helpInstanceVariableViaMethod(String instanceMagiclamp) {
        //       // 注意：このinstanceMagiclampはパラメータ（メソッド専用の変数）
        //      // 同じ名前でもインスタンス変数とは別物！
        //
        //       instanceBroadway = "bigband";    // インスタンス変数を"bigband"に変更
        //
        //       ++instanceDockside;              // インスタンス変数をnull→1に変更
        //                                       // Integer型のnullに++すると1になる
        //
        //       instanceMagiclamp = "burn";      // パラメータ（メソッド専用）を変更
        //                                       // インスタンス変数は変更されない！
        //                                       // メソッド終了時にパラメータは消える
        //   }
        //一度使用されたメソッドのパラメーターはメモリ効率のため、削除される。
        // TODO edo [いいね] 引数の instanceMagiclamp は「メソッド専用の変数」というの良いですね by jflute (2025/07/14)
        // そう、ここはたまたま同じ名前の変数が、あっちとこっちで2個あるみたいな感じです。
        // メソッドを呼び出すとき、変数が参照している先のインスタンスがhelpメソッドに引き渡されますが、
        // 変数自体は引き渡されるわけじゃなく、あくまでhelpメソッド側が用意した引数変数で受け取るという感じです。
        // TODO edo [してき] instanceDockside は Integer ではなく int 型なので、元々 0 が入ってます by jflute (2025/07/14)
        // なので、「Integer型のnullに++すると1になる」ではなく「int型のデフォルト0に++すると1になる」が正解ですね。
        // Integer型なのは、instanceHangarの方で、hangarは特にhelpメソッドの中では何もしていないので、
        // デフォルトnullそのままが+連結されて "null" という文字列に変換されるわけですね。
    }

    private void helpInstanceVariableViaMethod(String instanceMagiclamp) {
        instanceBroadway = "bigband";
        ++instanceDockside;
        instanceMagiclamp = "burn";
    }

    // ===================================================================================
    //                                                                     Method Argument
    //                                                                     ===============
    // -----------------------------------------------------
    //                                 Immutable Method-call
    //                                 ---------------------
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_method_argument_immutable_methodcall() {
        String sea = "harbor";
        int land = 415;
        helpMethodArgumentImmutableMethodcall(sea, land);
        log(sea); // your answer? => harbor
        //受け取るものとしては、新しく作られたsea.concatではなく、元々のseaのため、回答はharborになるという認識であってますか
        // TODO edo [へんじ] そうですね、testメソッド側のsea変数と、helpメソッド側のsea変数は、変数自体は別物で... by jflute (2025/07/14)
        // でも、参照している Stringインスタンスは同じものです。
        // でもでも、StringインスタンスはBigDecimalと同じように自分自身のインスタンスは変化させない特徴のクラスなので、
        // concat()してもseaが参照している "harbor" インスタンス自体は何も変わらないわけですね。
        // そして、concat() で新しく作った "harbor416" という文字列は、一瞬だけ生成されて誰にも受け取ってもらえず終了...
    }

    private void helpMethodArgumentImmutableMethodcall(String sea, int land) {
        ++land;
        String landStr = String.valueOf(land); // is "416"
        sea.concat(landStr);
    }

    // -----------------------------------------------------
    //                                   Mutable Method-call
    //                                   -------------------
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_method_argument_mutable_methodcall() {
        StringBuilder sea = new StringBuilder("harbor");
        int land = 415;
        helpMethodArgumentMethodcall(sea, land);
        log(sea); // your answer? => 6
        //正しい回答はharbor416。文字列の長さを返すものだと思っていた。
        //sea.append(land);でseaに416を追加
        //++land で land が 416 になる
        //sea.append(land) で数値 416 が文字列として追加される
        //StringBuilder は可変なので元のオブジェクトが変更される
        // TODO edo [ふぉろー] (^^)、ちょっと違ったところで勘違いしてしまったようですね。 by jflute (2025/07/14)
        // まあ、大事なのは「可変」であるというところですね。ここはオブジェクト(インスタンス)自体が変化するので...
        // helpメソッドの中のsea変数が別物だとしても参照するインスタンスが同じで、その唯一のものをhelp内で変化させたということで。
    }
    // TODO jflute [memo] いったんレビューここまで (2025/07/15)

    private void helpMethodArgumentMethodcall(StringBuilder sea, int land) {
        ++land;
        sea.append(land);
    }

    // -----------------------------------------------------
    //                                   Variable Assignment
    //                                   -------------------
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_method_argument_variable_assignment() {
        StringBuilder sea = new StringBuilder("harbor");
        int land = 415;
        helpMethodArgumentVariable(sea, land);
        log(sea); // your answer? => harbor
    }

    private void helpMethodArgumentVariable(StringBuilder sea, int land) {
        ++land;
        String seaStr = sea.toString(); // is "harbor"
        sea = new StringBuilder(seaStr).append(land);
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Define variables as followings:
     * <pre>
     * o local variable named sea typed String, initial value is "mystic"
     * o local variable named land typed Integer, initial value is null
     * o instance variable named piari typed int, without initial value
     * o show all variables by log() as comma-separated
     * </pre>
     * (変数を以下のように定義しましょう):
     * <pre>
     * o ローカル変数、名前はsea, 型はString, 初期値は "mystic"
     * o ローカル変数、名前はland, 型はInteger, 初期値は null
     * o インスタンス変数、名前はpiari, 型はint, 初期値なし
     * o すべての変数をlog()でカンマ区切りの文字列で表示
     * </pre>
     */
    public void test_variable_writing() {
        // define variables here
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * Make your original exercise as question style about variable. <br>
     * (変数についてあなたのオリジナルの質問形式のエクササイズを作ってみましょう)
     * <pre>
     * _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
     * your question here (ここにあなたの質問を):
     * 
     * _/_/_/_/_/_/_/_/_/_/
     * </pre>
     */
    public void test_variable_yourExercise() {
        // write your code here
    }
}
