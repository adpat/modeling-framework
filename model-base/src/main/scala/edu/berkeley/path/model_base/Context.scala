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

import org.joda.time.DateTime

/**
 * Encapsulates the requested computation, complete with a specification
 * of time parameters, data sources, data sinks. This computation may
 * actually be performed more than once, using instances of the Run 
 * class.
 *
 * The id is shared among all runs of this context (each Run has its
 * own id).
 * 
 * The context has methods to construct Runs and States.
 * 
 * Subclasses can manage additional static config, though it may be
 * better instead to pass in config via a source (such as Scenarios).
 * That way, the resulting "block" can be more flexibly connected to
 * different sources of config data, and the config could become dynamic
 * if necessary.
 * 
 * The minimum information contained in a Context object is exactly the
 * information needed before reading from sources.
 */
class Context(val id:Int) { 
  var dt: Double = 1.0
  var timeBegin = new DateTime(0)
  var timeEnd = new DateTime(0)

  /**
   * Begin time of run, the meaning of which may depend on the mode.
   */
  def getTimeBegin: DateTime = {
    this.timeBegin
  }
  
  def setTimeBegin(time: DateTime):Unit = {
    this.timeBegin = time
  }
  
  /**
   * End time of run, the meaning of which may depend on the mode.
   */
  def getTimeEnd: DateTime = {
    this.timeEnd
  }
  
  def setTimeEnd(time: DateTime):Unit = {
    this.timeEnd = time
  }
  
  def getDt(): Double = {
    this.dt
  }
  
  def setDt(dt: Double):Unit = {
    this.dt = dt
  }
  
  
  def makeRun(runId:Int): Run = {
     new Run(runId, this)
  }
}
