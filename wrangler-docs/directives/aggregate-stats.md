# Aggregate stats of byte and time data provided in a record
The AGGREGATE_STATS directive aggregates the values of the columns 
containing byte data and time durations, and outputs a new row
containing the results in the specified units, or Mega Bytes and
seconds by default.

## Syntax
```
aggregate-stats <input_bytes_column_name> <input_time_column_name> <output_bytes_column_name> [output_byte_units] <output_time_column_name> [output_time_unit] [aggregation_type]
```
* <input_bytes_column_name> is used to specify the name of the column containing byte data in the record.
* <input_time_column_name> is used to specify the name of the column containing time durations in the record.
* <output_byte_column_name> is used to specify the name of the column containing output of aggregated byte data.
* [output_byte_units] is used to specify the units for aggregated byte data (optional, MB by default).
* <output_time_column_name> is used to specify the name of the column containing output of aggregated time data in the record.
* [output_time_unit] is used to specify the units for aggregated time data (optional, seconds by default).
* [aggregation_time] is used to specify the type of aggregation performed. Permitted values are "sum" and "average" (case insensitive)
  (optional, sum by default)

## Usage Notes

This directive outputs a single row containing both aggregated time and byte result.
