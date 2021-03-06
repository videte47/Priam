/*
 * Copyright 2017 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.netflix.priam.backup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.netflix.priam.compress.SnappyCompression;
import com.netflix.priam.utils.SystemUtils;
import java.io.*;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import org.apache.cassandra.io.util.RandomAccessReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.xerial.snappy.SnappyInputStream;
import org.xerial.snappy.SnappyOutputStream;

@Ignore("does this test really have to generate smoke to verify correct behavior?")
public class TestCompression {

    @Before
    public void setup() throws IOException {
        File f = new File("/tmp/compress-test.txt");
        try (FileOutputStream stream = new FileOutputStream(f)) {
            for (int i = 0; i < (1000 * 1000); i++) {
                stream.write(
                        "This is a test... Random things happen... and you are responsible for it...\n"
                                .getBytes("UTF-8"));
                stream.write(
                        "The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.\n"
                                .getBytes("UTF-8"));
            }
        }
    }

    @After
    public void done() {
        File f = new File("/tmp/compress-test.txt");
        if (f.exists()) f.delete();
    }

    private void validateCompression(String compress) {
        File uncompressed = new File("/tmp/compress-test.txt");
        File compressed = new File(compress);
        assertTrue(uncompressed.length() > compressed.length());
    }

    @Test
    public void zip() throws IOException {
        BufferedInputStream source;
        try (ZipOutputStream out =
                new ZipOutputStream(
                        new BufferedOutputStream(new FileOutputStream("/tmp/compressed.zip")))) {
            byte data[] = new byte[2048];
            File file = new File("/tmp/compress-test.txt");
            FileInputStream fi = new FileInputStream(file);
            source = new BufferedInputStream(fi, 2048);
            ZipEntry entry = new ZipEntry(file.getName());
            out.putNextEntry(entry);
            int count;
            while ((count = source.read(data, 0, 2048)) != -1) {
                out.write(data, 0, count);
            }
        }
        validateCompression("/tmp/compressed.zip");
    }

    @Test
    public void unzip() throws IOException {
        ZipFile zipfile = new ZipFile("/tmp/compressed.zip");
        Enumeration e = zipfile.entries();
        while (e.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) e.nextElement();
            try (BufferedInputStream is = new BufferedInputStream(zipfile.getInputStream(entry));
                    BufferedOutputStream dest1 =
                            new BufferedOutputStream(
                                    new FileOutputStream("/tmp/compress-test-out-0.txt"), 2048)) {
                int c;
                byte d[] = new byte[2048];

                while ((c = is.read(d, 0, 2048)) != -1) {
                    dest1.write(d, 0, c);
                }
            }
        }
        String md1 = SystemUtils.md5(new File("/tmp/compress-test.txt"));
        String md2 = SystemUtils.md5(new File("/tmp/compress-test-out-0.txt"));
        assertEquals(md1, md2);
    }

    @Test
    public void snappyCompress() throws IOException {
        try (BufferedInputStream origin =
                        new BufferedInputStream(
                                new FileInputStream("/tmp/compress-test.txt"), 1024);
                SnappyOutputStream out =
                        new SnappyOutputStream(
                                new BufferedOutputStream(new FileOutputStream("/tmp/test0.snp")))) {
            byte data[] = new byte[1024];
            int count;
            while ((count = origin.read(data, 0, 1024)) != -1) {
                out.write(data, 0, count);
            }
            validateCompression("/tmp/test0.snp");
        }
    }

    @Test
    public void snappyDecompress() throws IOException {
        // decompress normally.
        try (SnappyInputStream is =
                        new SnappyInputStream(
                                new BufferedInputStream(new FileInputStream("/tmp/test0.snp")));
                BufferedOutputStream dest1 =
                        new BufferedOutputStream(
                                new FileOutputStream("/tmp/compress-test-out-1.txt"), 1024)) {

            byte d[] = new byte[1024];
            int c;
            while ((c = is.read(d, 0, 1024)) != -1) {
                dest1.write(d, 0, c);
            }

            String md1 = SystemUtils.md5(new File("/tmp/compress-test-out-1.txt"));
            String md2 = SystemUtils.md5(new File("/tmp/compress-test.txt"));
            assertEquals(md1, md2);
        }
    }

    @Test
    public void compress() throws IOException {
        SnappyCompression compress = new SnappyCompression();
        File file = new File(new File("/tmp/compress-test.txt"), "r");
        long chunkSize = 5L * 1024 * 1024;
        Iterator<byte[]> it =
                compress.compress(
                        new AbstractBackupPath.RafInputStream(RandomAccessReader.open(file)),
                        chunkSize);
        try (FileOutputStream ostream = new FileOutputStream("/tmp/test1.snp")) {
            while (it.hasNext()) {
                byte[] chunk = it.next();
                ostream.write(chunk);
            }
        }
        validateCompression("/tmp/test1.snp");
    }

    @Test
    public void decompress() throws IOException {
        SnappyCompression compress = new SnappyCompression();
        compress.decompressAndClose(
                new FileInputStream("/tmp/test1.snp"),
                new FileOutputStream("/tmp/compress-test-out-2.txt"));
        String md1 = SystemUtils.md5(new File("/tmp/compress-test.txt"));
        String md2 = SystemUtils.md5(new File("/tmp/compress-test-out-2.txt"));
        assertEquals(md1, md2);
    }
}
