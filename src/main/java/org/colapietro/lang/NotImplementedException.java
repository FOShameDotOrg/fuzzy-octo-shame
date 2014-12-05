/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.colapietro.lang;

import javax.annotation.Nullable;

/**
 * <p>Thrown to indicate that a block of code has not been implemented.
 * This exception supplements <code>UnsupportedOperationException</code>
 * by providing a more semantically rich description of the problem.</p>
 *
 * <p><code>NotImplementedException</code> represents the case where the
 * author has yet to implement the logic at this point in the program.
 * This can act as an exception based TODO tag. </p>
 *
 * <pre>
 * public void foo() {
 *   try {
 *     // do something that throws an Exception
 *   } catch (Exception ex) {
 *     // don't know what to do here yet
 *     throw new NotImplementedException("TODO", ex);
 *   }
 * }
 * </pre>
 *
 * This class was originally from Commons Lang and was added to the library in Lang 2.0, removed in 3.0, and
 * added back in 3.2.
 *
 * I have taken it and put it under my org.colapietro.lang.* package structure. These comments are prominent notice
 * that I changed this file.
 *
 * @author Peter Colapietro
 * @since 0.1.7
 * @version $Id$
 */
public class NotImplementedException extends UnsupportedOperationException {

    /**
     *
     */
    private static final long serialVersionUID = 20141122L;

    /**
     *
     */
    private final String code;

    /**
     * Constructs a NotImplementedException.
     *
     * @param message description of the exception
     * @since 0.1.7
     */
    public NotImplementedException(final String message) {
        this(message, (String) null);
    }

    /**
     * Constructs a NotImplementedException.
     *
     * @param cause cause of the exception
     * @since 0.1.7
     */
    public NotImplementedException(final Throwable cause) {
        this(cause, null);
    }

    /**
     * Constructs a NotImplementedException.
     *
     * @param message description of the exception
     * @param cause cause of the exception
     * @since 0.1.7
     */
    public NotImplementedException(final String message, final Throwable cause) {
        this(message, cause, null);
    }

    /**
     * Constructs a NotImplementedException.
     *
     * @param message description of the exception
     * @param code code indicating a resource for more information regarding the lack of implementation
     * @since 0.1.7
     */
    public NotImplementedException(final String message, @Nullable final String code) {
        super(message);
        this.code = code;
    }

    /**
     * Constructs a NotImplementedException.
     *
     * @param cause cause of the exception
     * @param code code indicating a resource for more information regarding the lack of implementation
     * @since 0.1.7
     */
    public NotImplementedException(final Throwable cause, @Nullable final String code) {
        super(cause);
        this.code = code;
    }

    /**
     * Constructs a NotImplementedException.
     *
     * @param message description of the exception
     * @param cause cause of the exception
     * @param code code indicating a resource for more information regarding the lack of implementation
     * @since 0.1.7
     */
    public NotImplementedException(final String message, final Throwable cause, @Nullable final String code) {
        super(message, cause);
        this.code = code;
    }

    /**
     * Obtain the not implemented code. This is an unformatted piece of text intended to point to
     * further information regarding the lack of implementation. It might, for example, be an issue
     * tracker ID or a URL.
     *
     * @return a code indicating a resource for more information regarding the lack of implementation
     */
    @Nullable
    public String getCode() {
        return this.code;
    }
}