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

package edu.berkeley.path.model_base

import org.joda.time.Duration

class TimeState(id: Int, context: Context) extends State(id, context) {
  var step: Long = 0
  
  /**
   *  to be overridden by subclasses
   */
  def update(): Unit = {
    step += 1
  }
  
  /**
   *  Public api to move time forward a given number of steps in this state
   * instance. Should generally not be overridden. Override update instead.
   */
  def evolveSteps(steps: Int): Unit = {
    for (i <- 1 to steps) {
      update()
    }
  }
  
  /**
   *  Public api to move time forward a given number of seconds in this state
   * instance. Should generally not be overridden. Override update instead.
   */
  def evolveTime(t: Duration): Unit = {
    val t1 = timeElapsed plus t
    while (t1 isLongerThan timeElapsed) {
      update()
    }
  }

  /**
   *  Seconds since start of run.
   */
  def timeElapsed = Duration.standardSeconds(math.floor(step * context.dt).toLong)

  /**
   *  Clock time for the state.
   */
  def time = context.timeBegin plus timeElapsed
  
  /**
   *  Is the run done, based on the specified timeEnd?
   */
  def done = time isAfter context.timeEnd
  
  /**
   *  Useful to override
   */
  override def valid = step >= 0
}
