/*
 * Copyright 2016 Christoph Böhme
 *
 * Licensed under the Apache License, Version 2.0 the "License";
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.metafacture.flowcontrol;

import org.metafacture.framework.FluxCommand;
import org.metafacture.framework.ObjectReceiver;
import org.metafacture.framework.annotations.Description;
import org.metafacture.framework.annotations.In;
import org.metafacture.framework.annotations.Out;
import org.metafacture.framework.helpers.DefaultObjectPipe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Wraps the call to the process method of the downstream module
 * in a try catch block with Exception as the catch-block's
 * argument type. This module is supposed to stop any exception
 * from downstream modules to travel further upstream. If an
 * exception is caught, a log message with log level "error" is
 * written.
 *
 * @param <T> object type
 *
 * @author Christoph Böhme
 */
@Description("passes objects through and catches exceptions.")
@In(Object.class)
@Out(Object.class)
@FluxCommand("catch-object-exception")
public final class ObjectExceptionCatcher<T> extends
        DefaultObjectPipe<T, ObjectReceiver<T>> {

    private static final Logger LOG = LoggerFactory.getLogger(ObjectExceptionCatcher.class);

    private String logPrefix;
    private boolean logStackTrace;

    public ObjectExceptionCatcher() {
        this("");
    }

    public ObjectExceptionCatcher(final String logPrefix) {
        this.logPrefix = logPrefix;
    }

    public void setLogPrefix(final String logPrefix) {
        this.logPrefix = logPrefix;
    }

    public String getLogPrefix() {
        return logPrefix;
    }

    public void setLogStackTrace(final boolean logStackTrace) {
        this.logStackTrace = logStackTrace;
    }

    public boolean isLogStackTrace() {
        return logStackTrace;
    }

    @Override
    public void process(final T obj) {
        try {
            getReceiver().process(obj);
        }
        catch (final Exception e) { // checkstyle-disable-line IllegalCatch
            LOG.error("{}'{}' while processing object: {}", logPrefix, e.getMessage(), obj);

            if (logStackTrace) {
                final StringWriter stackTraceWriter = new StringWriter();
                e.printStackTrace(new PrintWriter(stackTraceWriter));
                LOG.error("{}Stack Trace:\n{}", logPrefix, stackTraceWriter.toString());
            }
        }
    }

}
