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

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.Duration

class TimeStateTest extends AssertionsForJUnit {
  var ctx: Context = _
  var st: TimeState = _

  @Before def initialize() {
    ctx = new Context(1)
    st = new TimeState(1, ctx)
  }
  
  @Test def test_update(): Unit = {
    assert(0 === st.step)
    st.update()
    assert(1 === st.step)
  }
  
  @Test def test_evolveSteps(): Unit = {
    assert(0 === st.step)
    st.evolveSteps(5)
    assert(5 === st.step)
  }
  
  @Test def test_evolveTime(): Unit = {
    assert(0 === st.step)
    st.evolveTime(Duration.standardSeconds(10))
    assert(10 === st.step)
    assert(Duration.standardSeconds(10) === st.timeElapsed)
  }
  
  @Test def test_valid(): Unit = {
    st.evolveTime(Duration.standardSeconds(10))
    assert(st.valid)
  }
  
  @Test def test_done(): Unit = {
    val formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm")
    ctx.timeBegin = formatter.parseDateTime("01/01/2012 07:00")
    ctx.timeEnd = formatter.parseDateTime("01/01/2012 07:01")
    
    st.evolveTime(Duration.standardSeconds(60))
    assert(!st.done)
    st.evolveTime(Duration.standardSeconds(1))
    assert(st.done)
  }
}
