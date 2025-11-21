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
 * チケットの種別を表す識別子。
 * チケットの種類を明確に識別するために使用する。
 *
 * @author jflute
 * @author harukaedo
 */
public enum TicketType {
    /** 1日券 */
    ONE_DAY,
    /** 2日券 */
    TWO_DAY,
    /** 4日券 */
    FOUR_DAY,
    /** 夜専用2日券 */
    NIGHT_ONLY_TWO_DAY,
    /** 昼専用2日券 */
    DAY_TIME_ONLY_TWO_DAY
}

