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
package org.docksidestage.bizfw.debug;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.docksidestage.bizfw.debug.sorter.BubbleSorter;
import org.docksidestage.bizfw.debug.sorter.QuickSorter;
import org.docksidestage.bizfw.debug.sorter.SelectionSorter;
import org.docksidestage.bizfw.debug.sorter.Sorter;
import org.docksidestage.bizfw.debug.sorter.WordSorter;

/**
 * @author zaya
 * @author jflute
 */
public class WordAssort extends WordSorter {

    public WordAssort() {
        words = new WordPool().getWords();
    }

    @Override
    public List<Word> sort() {
        List<Sorter<Word>> sorters = Arrays.asList(new BubbleSorter(words), new SelectionSorter(words), new QuickSorter());
        int i = new Random().nextInt(sorters.size());
        return sorters.get(i).sort();
    }

    @Override
    public List<Word> sort(List<Word> list) {
        List<Sorter<Word>> sorters = Arrays.asList(new BubbleSorter(), new SelectionSorter(), new QuickSorter(list));
        int i = new Random().nextInt(sorters.size());
        return sorters.get(i).sort(list);
    }
}
