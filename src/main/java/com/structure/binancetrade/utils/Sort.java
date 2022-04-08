package com.structure.binancetrade.utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

/*/*from   ww  w .  j ava 2s.  c o m*/
    /*Copyright 2008-2010 Gephi
     Authors : Eduardo Ramos <eduramiba@gmail.com>
    Website : http://www.gephi.org

    This file is part of Gephi.

    DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

    Copyright 2011 Gephi Consortium. All rights reserved.

    The contents of this file are subject to the terms of either the GNU
    General Public License Version 3 only ("GPL") or the Common
    Development and Distribution License("CDDL") (collectively, the
    "License"). You may not use this file except in compliance with the
    License. You can obtain a copy of the License at
    http://gephi.org/about/legal/license-notice/
    or /cddl-1.0.txt and /gpl-3.0.txt. See the License for the
    specific language governing permissions and limitations under the
    License.  When distributing the software, include this License Header
    Notice in each file and include the License files at
    /cddl-1.0.txt and /gpl-3.0.txt. If applicable, add the following below the
    License Header, with the fields enclosed by brackets [] replaced by
    your own identifying information:
    "Portions Copyrighted [year] [name of copyright owner]"

    If you wish your version of this file to be governed by only the CDDL
    or only the GPL Version 3, indicate your decision by adding
    "[Contributor] elects to include this software in this distribution
    under the [CDDL or GPL Version 3] license." If you do not indicate a
    single choice of license, a recipient has the option to distribute
    your version of this file under either the CDDL, the GPL Version 3 or
    to extend the choice of license to its licensees as provided above.
    However, if you add GPL Version 3 code and therefore, elected the GPL
    Version 3 license, then the option applies only if the new code is
    made subject to such option by the copyright holder.

    Contributor(s):

    Portions Copyrighted 2011 Gephi Consortium.
    */
public class Sort {
    /**
     * <p>Calculate median of various numbers as a BigDecimal.</p>
     * <p>The elements can't be null.</p>
     * <p>The elements don't need to be sorted.</p>
     * @param numbers Not null numbers to calculate median
     * @return Median as a BigDecimal
     */
    public static BigDecimal median(Number[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return null;
        }

        BigDecimal[] bigDecimalNumbers = numbersArrayToSortedBigDecimalArray(numbers);
        return median(bigDecimalNumbers);
    }

    /**
     * <p>Calculate median of various numbers as a BigDecimal.</p>
     * <p>The elements can't be null.</p>
     * <p>The elements don't need to be sorted.</p>
     * @param numbers Not null numbers to calculate median
     * @return Median as a BigDecimal
     */
    public static BigDecimal median(Collection<Number> numbers) {
        return median(numbers.toArray(new Number[0]));
    }

    private static BigDecimal median(final BigDecimal[] bigDecimalNumbers) {
        return median(bigDecimalNumbers, 0, bigDecimalNumbers.length);
    }

    private static BigDecimal median(final BigDecimal[] bigDecimalNumbers,
                                     final int start, final int end) {
        final int size = end - start;

        if (size % 2 == 1) {
            return bigDecimalNumbers[start + (size + 1) / 2 - 1];
        } else {
            BigDecimal result = bigDecimalNumbers[start + (size) / 2 - 1];
            result = result.add(bigDecimalNumbers[start + (size) / 2]);
            return result.divide(BigDecimal.valueOf(2));
        }
    }

    /**
     * <p>Takes an array of numbers of any type combination and returns
     * an array with their BigDecimal equivalent numbers.</p>
     * @return BigDecimal array
     */
    private static BigDecimal[] numbersArrayToSortedBigDecimalArray(
            Number[] numbers) {
        if (numbers == null) {
            return null;
        }
        BigDecimal[] result = new BigDecimal[numbers.length];
        Number number;
        for (int i = 0; i < result.length; i++) {
            number = numbers[i];
            if (number != null) {
                result[i] = new BigDecimal(number.toString());
            }
        }
        Arrays.sort(result);
        return result;
    }
}
