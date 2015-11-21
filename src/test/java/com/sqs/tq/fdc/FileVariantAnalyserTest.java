/*******************************************************************************
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 SQS Software Quality Systems AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/
package com.sqs.tq.fdc;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class FileVariantAnalyserTest {

    @Test
    public void shouldGenerateJsonOutput() throws Exception {
        List<FileData> fdata = new ArrayList<>();
        fdata.add(new FileData("data.txt", "a/data.txt", "1"));
        fdata.add(new FileData("data.txt", "a/b/data.txt", "1"));
        fdata.add(new FileData("data.txt", "a/c/data.txt", "2"));

        FileVariantAnalyser fva = new FileVariantAnalyser();
        ObjectNode reportData = fva.analyse(fdata);

        assertEquals(Fixtures.expected, reportData);
    }

}
