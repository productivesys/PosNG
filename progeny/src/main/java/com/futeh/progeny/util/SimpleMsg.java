/*
 * Copyright (c) 2000 jPOS.org.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *    "This product includes software developed by the jPOS project 
 *    (http://www.jpos.org/)". Alternately, this acknowledgment may 
 *    appear in the software itself, if and wherever such third-party 
 *    acknowledgments normally appear.
 *
 * 4. The names "jPOS" and "jPOS.org" must not be used to endorse 
 *    or promote products derived from this software without prior 
 *    written permission. For written permission, please contact 
 *    license@jpos.org.
 *
 * 5. Products derived from this software may not be called "jPOS",
 *    nor may "jPOS" appear in their name, without prior written
 *    permission of the jPOS project.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  
 * IN NO EVENT SHALL THE JPOS PROJECT OR ITS CONTRIBUTORS BE LIABLE FOR 
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL 
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS 
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, 
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING 
 * IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the jPOS Project.  For more
 * information please see <http://www.jpos.org/>.
 */

package com.futeh.progeny.util;
import java.io.PrintStream;

import com.futeh.progeny.iso.ISOUtil;

/**
 * <p>
 * A simple general purpose loggeable message.
 * </p>
 * @author Hani S. Kirollos
 * @version $Revision$ $Date$
 */
    public class SimpleMsg
            implements Loggeable {
        String tagName;
        String msgName;
        Object msgContent;

        public SimpleMsg (String tagName, String msgName, Object msgContent) {
            this.tagName = tagName;
            this.msgName = msgName;
            this.msgContent = msgContent;
        }

        public SimpleMsg (String tagName, String msgName, byte[] msgContent) {
            this(tagName, msgName, ISOUtil.hexString(msgContent));
        }

        public SimpleMsg (String tagName, String msgName, boolean msgContent) {
            this(tagName, msgName, new Boolean(msgContent));
        }

        public SimpleMsg (String tagName, String msgName, short msgContent) {
            this(tagName, msgName, new Short(msgContent));
        }

        public SimpleMsg (String tagName, String msgName, int msgContent) {
            this(tagName, msgName, new Integer(msgContent));
        }

        public SimpleMsg (String tagName, String msgName, long msgContent) {
            this(tagName, msgName, new Long(msgContent));
        }

        /**
         * dumps message
         * @param p a PrintStream usually supplied by Logger
         * @param indent indention string, usually suppiled by Logger
         * @see Loggeable
         */
        public void dump (PrintStream p, String indent) {
            String inner = indent + "  ";
            p.print(indent + "<" + tagName);
            p.print(" name=\"" + msgName + "\"");
            p.println(">");
            if (msgContent instanceof SimpleMsg[]) {
                // dump sub messages
                for (int i = 0; i < ((SimpleMsg[])msgContent).length; i++)
                    ((SimpleMsg[])msgContent)[i].dump(p, inner);
            }
            else if (msgContent instanceof Loggeable)
                ((Loggeable)msgContent).dump(p, inner);
            else
                p.println(inner + msgContent.toString());
            p.println(indent + "</" + tagName + ">");
        }
    }