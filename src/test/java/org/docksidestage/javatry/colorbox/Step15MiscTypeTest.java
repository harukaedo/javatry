/*
 * Copyright 2019-2019 the original author or authors.
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
package org.docksidestage.javatry.colorbox;

import java.util.List;
import java.util.stream.Collectors;

import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom.FavoriteProvider;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of various type with color-box. <br>
 * Show answer by log() for question of javadoc.
 * @author jflute
 * @author jflute (as trainee)
 */
public class Step15MiscTypeTest extends PlainTestCase {

    // ===================================================================================
    //                                                                           Exception
    //                                                                           =========
    /**
     * What class name is throw-able object in color-boxes? <br>
     * (カラーボックスに入っているthrowできるオブジェクトのクラス名は？)
     */
    public void test_throwable() {
    }

    /**
     * What message is for exception that is nested by exception in color-boxes? <br>
     * (カラーボックスに入っている例外オブジェクトのネストした例外インスタンスのメッセージは？)
     */
    public void test_nestedException() {
    }

    // ===================================================================================
    //                                                                           Interface
    //                                                                           =========
    /**
     * What value is returned by justHere() of FavoriteProvider in yellow color-box? <br>
     * (カラーボックスに入っているFavoriteProviderインターフェースのjustHere()メソッドの戻り値は？)
     */
    public void test_interfaceCall() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        colorBoxList.stream()
                .flatMap(box -> box.getSpaceList().stream())
                .filter(space -> space.getContent() instanceof FavoriteProvider)
                .map(space -> (FavoriteProvider) space.getContent())
                .forEach(provider -> {
                    log("answer: {}", provider.justHere());
                });
    }

    // ===================================================================================
    //                                                                            Optional
    //                                                                            ========
    /**
     * What keyword is in BoxedStage of BoxedResort in List in beige color-box? (show "none" if no value) <br>
     * (beigeのカラーボックスに入っているListの中のBoxedResortのBoxedStageのkeywordは？(値がなければ固定の"none"という値を))
     */
    public void test_optionalMapping() {
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * What line number is makeEighthColorBox() call in getColorBoxList()? <br>
     * (getColorBoxList()メソッドの中のmakeEighthColorBox()メソッドを呼び出している箇所の行数は？)
     */
    public void test_lineNumber() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        List<Throwable> throwableList = colorBoxList.stream()
                .flatMap(box -> box.getSpaceList().stream())
                .filter(space -> space.getContent() instanceof Throwable)
                .map(space -> (Throwable) space.getContent())
                .collect(Collectors.toList());
        throwableList.forEach(throwable -> {
            StackTraceElement[] stackTrace = throwable.getStackTrace();
            boolean found = false;
            for (StackTraceElement element : stackTrace) {
                if (found) {
                    log("answer: {} in {}@{}()", element.getLineNumber(), element.getClassName(), element.getMethodName());
                    break;
                }
                if (element.getMethodName().equals("makeEighthColorBox")) {
                    found = true; // next element is just caller
                }
            }
        });
    }
}
