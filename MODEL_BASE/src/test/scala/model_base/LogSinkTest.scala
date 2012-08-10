/**
 * Copyright (c) 2012 The Regents of the University of California.
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the University nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */

package model_base

import org.junit._
import org.junit.Assert._
import org.scalatest._
import org.scalatest.junit.AssertionsForJUnit

import scala.collection.mutable.{ HashMap => MMap }

import org.joda.time.DateTime
import org.joda.time.Duration

class LogSinkTest extends AssertionsForJUnit {
  var sink: LogSink = _
  
  @Before def initialize() {
    sink = new LogSink()
  }
  
  @Test def test_order(): Unit = {
    sink.debug("test0")
    sink.debug("test1")
    assert("test0" === sink.buf(0).message)
    assert("test1" === sink.buf(1).message)
  }
  
  @Test def test_time(): Unit = {
    val t = DateTime.now
    sink.debug("test")
    val dur = new Duration(t, sink.buf(0).time)
    assert(dur isShorterThan Duration.standardSeconds(1))
  }
  
  @Test def test_tags(): Unit = {
    sink.info("test", tags = MMap(
      "t1" -> 1,
      "t2" -> 2
    ))
    assert(1 === sink.buf(0).tags("t1"))
  }
}
