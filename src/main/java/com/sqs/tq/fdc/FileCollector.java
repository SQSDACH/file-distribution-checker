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

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

public class FileCollector implements Collector {

    @Override
    public List<FileData> collect(Path path) {
        List<FileData> data = new ArrayList<>();
        try {
            String hash = calcHash(path);
            data.add(new FileData(path.getFileName().toString(), path.toAbsolutePath().toString(), hash));
        } catch (NoSuchAlgorithmException e) {
            //
        }
        return data;
    }

    private String calcHash(Path path) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        try (InputStream is = Files.newInputStream(path); DigestInputStream dis = new DigestInputStream(is, md);) {
            while (dis.read() != -1) {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (new HexBinaryAdapter()).marshal(md.digest());
    }
}
