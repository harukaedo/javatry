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

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.space.BoxSpace;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of Devil with color-box, (try if you woke up Devil in StringTest) <br>
 * Show answer by log() for question of javadoc.
 * @author jflute
 * @author your_name_here
 */
public class Step19DevilTest extends PlainTestCase {

    // ===================================================================================
    //                                                                        Devil Parade
    //                                                                        ============
    /**
     * What is the content in low space of color-box
     * of which lengths of the color is same as first place number of BigDecimal value first found in List in box spaces,
     * that the second decimal place is same as tens place of depth of the color-box
     * of which color name ends with third character of color-box that contains null as content? <br>
     * (nullを含んでいるカラーボックスの色の名前の3文字目の文字で色の名前が終わっているカラーボックスの深さの十の位の数字が小数点第二桁目になっている
     * スペースの中のリストの中で最初に見つかるBigDecimalの一の位の数字と同じ色の長さのカラーボックスの一番下のスペースに入っているものは？)
     */
    public void test_too_long() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();

        List<ColorBox> nullingBoxList = colorBoxList.stream().filter(box -> {
            return box.getSpaceList().stream().anyMatch(space -> space.getContent() == null);
        }).collect(Collectors.toList());
        log("nullingBoxList: {}", nullingBoxList.stream().map(box -> box.getColor().getColorName()).collect(Collectors.toList()));

        Set<Character> thirdCharSet = nullingBoxList.stream()
                .map(box -> box.getColor().getColorName())
                .filter(name -> name.length() >= 3) // avoid too short name
                .map(name -> name.charAt(2)) // e.g. green => e
                .collect(Collectors.toSet());
        log("thirdCharSet: {}", thirdCharSet);

        List<ColorBox> depthBoxList = colorBoxList.stream().filter(box -> {
            return thirdCharSet.stream().anyMatch(ch -> box.getColor().getColorName().endsWith(String.valueOf(ch)));
        }).collect(Collectors.toList());
        log("depthBoxList: {}",
                depthBoxList.stream()
                        .map(box -> box.getColor().getColorName() + ":" + box.getSize().getDepth())
                        .collect(Collectors.toList()));

        Set<Integer> tensPlaceSet = depthBoxList.stream()
                .map(box -> box.getSize().getDepth())
                .filter(depth -> depth >= 10)
                .map(depth -> String.valueOf(depth))
                .map(depstr -> depstr.substring(depstr.length() - 2, depstr.length() - 1)) // other way?
                .map(depstr -> Integer.valueOf(depstr))
                .collect(Collectors.toSet());
        log("tensPlaceSet: {}", tensPlaceSet);

        Integer firstPlaceNumber = colorBoxList.stream()
                .flatMap(box -> box.getSpaceList().stream())
                .filter(space -> space.getContent() instanceof List<?>)
                .map(space -> (List<?>) space.getContent())
                .flatMap(list -> list.stream())
                .filter(element -> element instanceof BigDecimal)
                .map(element -> (BigDecimal) element)
                .filter(decimal -> {
                    String moved = String.valueOf(decimal.movePointRight(2).intValue());
                    String lastStr = moved.substring(moved.length() - 1, moved.length());
                    Integer second = Integer.valueOf(lastStr);
                    return tensPlaceSet.contains(second);
                })
                .map(decimal -> {
                    String numberStr = String.valueOf(decimal.intValue());
                    String lastStr = numberStr.substring(numberStr.length() - 1, numberStr.length());
                    return Integer.valueOf(lastStr);
                })
                .findFirst()
                .orElseThrow(() -> {
                    return new IllegalStateException("Not found the specified BigDecimal: tensPlaceSet=" + tensPlaceSet);
                });
        log("firstPlaceNumber: {}", firstPlaceNumber);

        List<ColorBox> targetBoxList = colorBoxList.stream()
                .filter(box -> box.getColor().getColorName().length() == firstPlaceNumber)
                .collect(Collectors.toList());
        log("targetBoxList: {}", targetBoxList.stream().map(box -> box.getColor().getColorName()).collect(Collectors.toList()));

        List<BoxSpace> lowerSpaceList = targetBoxList.stream().map(box -> {
            List<BoxSpace> spaceList = box.getSpaceList();
            return spaceList.get(spaceList.size() - 1);
        }).collect(Collectors.toList());

        lowerSpaceList.stream().forEach(space -> log("answer: {}", space.getContent()));
    }

    // ===================================================================================
    //                                                                      Java Destroyer
    //                                                                      ==============
    /**
     * What string of toString() is BoxSize of red color-box after changing height to 160 (forcedly in this method)? <br>
     * ((このテストメソッドの中だけで無理やり)赤いカラーボックスの高さを160に変更して、BoxSizeをtoString()すると？)
     */
    public void test_looks_like_easy() {
    }

    // ===================================================================================
    //                                                                        Meta Journey
    //                                                                        ============
    /**
     * What value is returned from functional method of interface that has FunctionalInterface annotation in color-boxes? <br> 
     * (カラーボックスに入っているFunctionalInterfaceアノテーションが付与されているインターフェースのFunctionalメソッドの戻り値は？)
     */
    public void test_be_frameworker() {
    }
}
