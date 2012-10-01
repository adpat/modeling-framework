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

package model_example;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.*;

/**
 * There's nothing to do here, so, instead, we have a quick
 * reference to junit 4. See
 * http://www.cavdar.net/2008/07/21/junit-4-in-60-seconds.
 */
public class ContextTest {
  @Test
  public void testSuccess() {
    assertEquals(1, 1);
  }

  @Before
  public void runBeforeEveryTest() {
  }

  @After
  public void runAfterEveryTest() {
  } 
  
  @Test(expected = ArithmeticException.class)
  public void divisionWithException() {
    double x = 1 / 0;
  }

  @BeforeClass
  public static void runBeforeClass() {
    // run for one time before all test cases
  }

  @AfterClass
  public static void runAfterClass() {
    // run for one time after all test cases
  }
  
  @Ignore("Not Ready to Run")
  @Test
  public void someTest() {
  }

  /*
   Test fails when the timeout period exceeds the specified
   timeout (milliseconds).
  */
  
  @Test(timeout = 1000)
  public void waitForAWhile() {
  }

  /*
    New Assertions
    Compare arrays with new assertion methods. Two arrays are equal if they have the same length and each element is equal to the corresponding element in the other array; otherwise, they're not.

    public static void assertEquals(Object[] expected, Object[] actual);
    public static void assertEquals(String message, Object[] expected, Object[] actual);
  */

  @Test
  public void listEquality() {
    List expected = new ArrayList();
    expected.add(5);

    List actual = new ArrayList();
    actual.add(5);

    assertEquals(expected, actual);
  }
}
