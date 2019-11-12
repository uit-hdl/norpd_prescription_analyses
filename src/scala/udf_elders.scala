import org.apache.spark.sql.functions
import org.apache.spark.sql.SparkSession


/*
    Parses the death timestamp, using timestamp_from_year_and_month() to convert integer formatted year and month to a readable string format, then converts it to a unix_timestamp
*/
val parse_death_timestamp = (df: DataFrame) => df.withColumn("death_timestamp", 
    unix_timestamp(
        timestamp_from_year_and_month(
            $"year_of_death"
            ,$"month_of_death"
        ), "yyyyMM").cast(TimestampType)
   ).drop("year_of_death").drop("month_of_death")

/*
    Generates a string-timestamp given a year and month, each on integer format.
    The string is on the following format: (yyyyMM)

    y=year
    M=month

    The timestamp_from_year_and_month function has to allow year and month to be null, and in any of these cases, output null
*/
val timestamp_from_year_and_month = udf {
    (year:Integer, month:Integer) => (year, month) match{
        case (year, month) if year==null || month == null => null
        case _ => month.toString.length match {
                case 1 => year.toString+"0"+month.toString
                case 2 => year.toString+month.toString
        }
    }
}
