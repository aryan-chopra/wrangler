/*
 *  Copyright © 2017-2019 Cask Data, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 */

package io.cdap.directives.aggregates;

import io.cdap.cdap.api.annotation.Description;
import io.cdap.cdap.api.annotation.Name;
import io.cdap.cdap.api.annotation.Plugin;
import io.cdap.wrangler.api.Arguments;
import io.cdap.wrangler.api.Directive;
import io.cdap.wrangler.api.DirectiveExecutionException;
import io.cdap.wrangler.api.DirectiveParseException;
import io.cdap.wrangler.api.ExecutorContext;
import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.api.TransientVariableScope;
import io.cdap.wrangler.api.annotations.Categories;
import io.cdap.wrangler.api.parser.ByteSize;
import io.cdap.wrangler.api.parser.ColumnName;
import io.cdap.wrangler.api.parser.Identifier;
import io.cdap.wrangler.api.parser.TimeDuration;
import io.cdap.wrangler.api.parser.TokenType;
import io.cdap.wrangler.api.parser.UsageDefinition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A directive that aggregates byte size and time duration across all input
 * rows.
 *
 * This directive calculates the total of specified byte and duration columns
 * across all rows and stores them as a new row with total values.
 * 
 * The results are stored as transient variables and also returned as a single
 * result row.
 */

@Plugin(type = Directive.TYPE)
@Name(TimeAndByteDirective.NAME)
@Categories(categories = { "transient"})
@Description("Aggregates time and byte values")
public class TimeAndByteDirective implements Directive {
    public static final String NAME = "aggregate-stats";

    private static final String SOURCE_BYTE_SIZE = "byte_size";
    private static final String SOURCE_TIME_DURATION = "time_duration";
    private static final String TARGET_TOTAL_SIZE = "total_size";
    private static final String TARGET_TOTAL_DURATION = "total_duration";
    private static final String TARGET_BYTE_UNITS = "target_byte_units";
    private static final String TARGET_TIME_UNITS = "target_time_units";

    private String sourceByteSizeColumn;
    private String sourceTimeDurationColumn;
    private String targetTotalSizeColumn;
    private String targetTotalDurationColumn;

    @Override
    public UsageDefinition define() {
        UsageDefinition.Builder builder = new UsageDefinition.Builder(NAME);

        // Define input and output argument types.
        builder.define(SOURCE_BYTE_SIZE, TokenType.COLUMN_NAME);
        builder.define(SOURCE_TIME_DURATION, TokenType.COLUMN_NAME);
        builder.define(TARGET_TOTAL_SIZE, TokenType.IDENTIFIER);
        builder.define(TARGET_TOTAL_DURATION, TokenType.IDENTIFIER);

        return builder.build();
    }

    @Override
    public void initialize(Arguments args) throws DirectiveParseException {
        if (args.size() < 4) {
            throw new DirectiveParseException("Missing arguments");
        }

        if (args.value(SOURCE_BYTE_SIZE) == null) {
            throw new DirectiveParseException("Missing input");
        }

        // Extract argument values from the input and assign to internal fields.
        this.sourceByteSizeColumn = ((ColumnName) args.value(SOURCE_BYTE_SIZE)).value();
        this.sourceTimeDurationColumn = ((ColumnName) args.value(SOURCE_TIME_DURATION)).value();
        this.targetTotalSizeColumn = ((Identifier) args.value(TARGET_TOTAL_SIZE)).value();
        this.targetTotalDurationColumn = ((Identifier) args.value(TARGET_TOTAL_DURATION)).value();
    }

    @Override
    public List<Row> execute(List<Row> rows, ExecutorContext context) throws DirectiveExecutionException {
        // Initialize transient variables for global aggregation.
        if (context.getTransientStore().getVariables().contains("total_bytes") == false) {
            context.getTransientStore().set(TransientVariableScope.GLOBAL, "total_bytes", 0L);
        }
        if (context.getTransientStore().getVariables().contains("total_duration") == false) {
            context.getTransientStore().set(TransientVariableScope.GLOBAL, "total_duration", 0L);
        }

        // Iterate through all rows and compute cumulative byte size and duration.
        for (Row row : rows) {
            ByteSize byteSize = new ByteSize(row.getValue(this.sourceByteSizeColumn).toString());
            TimeDuration timeDuration = new TimeDuration(row.getValue(this.sourceTimeDurationColumn).toString());

            long currentBytes = context.getTransientStore().get("total_bytes");
            long currentDuration = context.getTransientStore().get("total_duration");

            // Update the cumulative totals in transient store.
            context.getTransientStore().set(TransientVariableScope.GLOBAL, "total_bytes",
                    currentBytes + byteSize.getBytes());
            context.getTransientStore().set(TransientVariableScope.GLOBAL, "total_duration",
                    currentDuration + timeDuration.getTime());
        }

        if (context.getTransientStore().get("is_last") == null) {
            return Collections.emptyList();
        }

        // Fetch the aggregated values from the transient store.
        long totalBytes = context.getTransientStore().get("total_bytes");
        long totalDuration = context.getTransientStore().get("total_duration");

        // Create a new result row with the total aggregated values.
        Row resultRow = new Row();
        resultRow.add(this.targetTotalSizeColumn, totalBytes);
        resultRow.add(this.targetTotalDurationColumn, totalDuration);

        // Return a single-row list containing the result row.
        return Collections.singletonList(resultRow);
    }

    @Override
    public void destroy() {
        System.out.println("Destroying");
    }
}
