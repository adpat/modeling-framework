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

import scala.collection.mutable.ArrayBuffer
import org.joda.time.DateTime
import scala.collection.mutable.{ HashMap => MMap }

object LogLevel extends Enumeration {
  type LogLevel = Int // is there a better way?

  val Debug = 0
  val Info  = 1
  val Warn  = 2
  val Error = 3
  val Fatal = 4
}
import LogLevel._

class LogEntry(lev: LogLevel, msg: String, t: DateTime, tg: MMap[String, Any]) {
  val level: LogLevel = lev
  val message: String = msg
  val time: DateTime = t
  
  /**
   * For example, client code may wish to use these tags:
   * 
   * type of process: computation type
   *   (estimation or prediction, for example)
   * type of data: density or speed
   * commit_id: git commit id
   * 
   * 
   * These tags will be filled in by the other layers:
   * 
   * host, process, dataflow id, context id, state id
   * 
   * TBD: file, line, method
   */
  val tags: MMap[String, Any] = tg
}

class LogSink extends Sink {
  val buf = ArrayBuffer[LogEntry]()
  
  def put(entry: LogEntry): Unit = {
    buf += entry
  }
  
  def putLogEntry(message: String, tags: MMap[String, Any], level: LogLevel): Unit = {
    val time = DateTime.now // or should this be utc?
    val entry = new LogEntry(level, message, time, tags)
    put(entry)
  }
  
  def debug(message: String, tags: MMap[String, Any] = null): Unit = {
    putLogEntry(message, tags, LogLevel.Debug)
  }
  
  def info(message: String, tags: MMap[String, Any] = null): Unit = {
    putLogEntry(message, tags, LogLevel.Info)
  }
  
  def warn(message: String, tags: MMap[String, Any] = null): Unit = {
    putLogEntry(message, tags, LogLevel.Warn)
  }
  
  def error(message: String, tags: MMap[String, Any] = null): Unit = {
    putLogEntry(message, tags, LogLevel.Error)
  }
  
  def fatal(message: String, tags: MMap[String, Any] = null): Unit = {
    putLogEntry(message, tags, LogLevel.Fatal)
  }
}
