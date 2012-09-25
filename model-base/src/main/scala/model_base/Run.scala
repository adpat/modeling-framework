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

import org.joda.time.DateTime
import org.joda.time.Duration

import scala.collection.mutable.{ HashMap => MMap }

/**
 * Encapsulates a single run of the computation specified by the Context.
 * The run has a State that varies over time, but with the same Run.
 * id is unique among all Run instances sharing the same Context.
 * multiple Runs can vary in Montecarlo params, distribution 
 * sampling, etc. Comparable to the FlowModelRunner of highwayFlowModel.
 */
class Run(val id:Int, val context: Context) { 
  var step: Long = 0
  
  var state: State = _;
  
  def makeState(): State = {
     new State(this)
  }
  
  /**
   * Set the initial state to either a given state or a state determined
   * by the context.
   */
  def initializeState(s: State): Unit = {
    if (s.run != this) {
      // set it, or throw?
    }
    step = 0
    state = s
  }
  
  /**
   *  Update the state by one time step;
   *  to be overridden by subclasses.
   */
  def runOnce(): State = {
    var nextState = state.update()
    step += 1
    nextState.timestamp = time
    state = nextState
    state
  }
  
  /**
   *  Public api to move time forward a given number of steps in this state
   * instance. Should generally not be overridden. Override runOnce instead.
   */
  def runForSteps(steps: Int): Unit = {
    for (i <- 1 to steps) {
      runOnce()
    }
  }
  
  /**
   *  Public api to move time forward a given number of seconds in this state
   * instance. Should generally not be overridden. Override runOnce instead.
   */
  def runForTime(t: Duration): Unit = {
    val t1 = timeElapsed plus t
    while (t1 isLongerThan timeElapsed) {
      runOnce()
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
   * there is one source for each "port" coming into the dataflow block,
   * they are indexed by port name, which is how modules are
   * connected to dataflow channels
   *
   * one type of source provides "scenario" data:
   * config, init state, and pre-defined state changes over time
   *
   * another type of source provides new data over time that is
   * not provided initially, such as sensor data
   */
  val sources = new MMap[String, Source]
  
  /**
   * there is one sink for each "port" coming out of the dataflow block,
   * they are indexed by port name, which is how modules are
   * connected to dataflow channels
   *
   * one type of sink accepts state updates or results
   * (such as density over time)
   *
   * another type of sink accepts logged program events
   *
   * another type of sink accepts program metrics
  */
  val sinks = new MMap[String, Sink]
  
  /**
   * Get a named source. This is a convenience method for java access.
   */
  def getSource(name: String): Source = {
     sources.get(name).getOrElse(null)
   }
  
  /**
   * Get a named sink. This is a convenience method for java access.
   */
  def getSink(name: String): Sink = {
     sinks.get(name).getOrElse(null)
   }
   
  def valid: Boolean = state.valid()
}
