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

package edu.berkeley.path.model_base

import org.junit._
import org.junit.Assert._
import org.scalatest._
import org.scalatest.junit.AssertionsForJUnit

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.Duration

class RunTest extends AssertionsForJUnit {
  var ctx: Context = _
  var run: Run = _
  var st: State = _

  @Before def initialize() {
    ctx = new Context(1)
    run = ctx.makeRun(1)
    run.initializeState(run.makeState())
  }
  
  @Test def test_runOnce(): Unit = {
    assert(0 === run.step)
    run.runOnce()
    assert(1 === run.step)
  }
  
  @Test def test_runForSteps(): Unit = {
    assert(0 === run.step)
    run.runForSteps(5)
    assert(5 === run.step)
  }
  
  @Test def test_runForTime(): Unit = {
    assert(0 === run.step)
    run.runForTime(Duration.standardSeconds(10))
    assert(10 === run.step)
    assert(Duration.standardSeconds(10) === run.timeElapsed)
  }
  
  @Test def test_valid(): Unit = {
    run.runForTime(Duration.standardSeconds(10))
    assert(run.valid)
  }
  
  @Test def test_done(): Unit = {
    val formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm")
    ctx.timeBegin = formatter.parseDateTime("01/01/2012 07:00")
    ctx.timeEnd = formatter.parseDateTime("01/01/2012 07:01")
    
    run.runForTime(Duration.standardSeconds(60))
    assert(!run.done)
    run.runForTime(Duration.standardSeconds(1))
    assert(run.done)
  }

  @Test def test_sources(): Unit = {
    val src = new Source
    run.sources("a") = src
    assertEquals(src, run.sources("a"))
    intercept[NoSuchElementException] {
      assertEquals(src, run.sources("b"))
    }
  }
}
