/**
 * MIT License
 *
 * Copyright (c) 2017-2018 nuls.io
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
 */
package io.nuls.network.filter.impl;

import io.nuls.core.utils.log.Log;
import io.nuls.network.message.filter.NulsMessageFilter;
import io.nuls.protocol.mesasge.NulsMessage;
import io.nuls.protocol.mesasge.NulsMessageHeader;

import java.util.HashSet;
import java.util.Set;

/**
 * @author vivi
 * @date 2017/11/21
 */
public class DefaultMessageFilter implements NulsMessageFilter {

    private Set<Integer> magicSet = new HashSet<>();

    private static DefaultMessageFilter instance = new DefaultMessageFilter();

    private DefaultMessageFilter() {

    }

    public static DefaultMessageFilter getInstance() {
        return instance;
    }

    @Override
    public boolean filter(NulsMessage message) {
        try {
            NulsMessageHeader header = message.getHeader();
            if (!magicSet.contains(header.getMagicNumber())) {
                return false;
            }
            message.verify();
            return true;
        } catch (Exception e) {
            Log.error(e);
            return false;
        }
    }

    public void addMagicNum(Integer magicNum) {
        magicSet.add(magicNum);
    }

    public void removeMagicNum(Integer magicNum) {
        magicSet.remove(magicNum);
    }


}