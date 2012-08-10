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

import org.joda.time.DateTime

import scala.collection.mutable.{ HashMap => MMap }

/**
 * Encapsulates the requested computation, complete with a specification
 * of time parameters, data sources, data sinks. This computation may
 * actually be performed more than once, using instances of the State 
 * class.
 *
 * The id is shared among all runs of this context (each run has its
 * own State with its own id).
 */
class Context(val id:Int) { 
  var dt: Double = 1.0
  var timeBegin = new DateTime(0)
  var timeEnd = new DateTime(0)
  
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
}
