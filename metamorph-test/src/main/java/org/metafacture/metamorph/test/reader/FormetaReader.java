/*
 * Copyright 2013, 2014, 2016 Deutsche Nationalbibliothek
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

package org.metafacture.metamorph.test.reader;

import org.metafacture.formeta.FormetaDecoder;
import org.metafacture.formeta.FormetaRecordsReader;

/**
 * Reads and decodes a <i>Formeta</i> data stream.
 *
 * @author Markus Geipel
 *
 */
public class FormetaReader extends ReaderBase {

    /**
     * Creates an instance of {@link FormetaReader}.
     */
    public FormetaReader() {
        super(new FormetaRecordsReader(), new FormetaDecoder());
    }

}
