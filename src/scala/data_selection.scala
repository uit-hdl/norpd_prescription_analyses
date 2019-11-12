import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import spark.implicits._

//Encode timestamp as difference between jan 1st, 2013 and date of prescription
def unhospitalized_selector (patients:DataFrame) : DataFrame = {
    return patients.select( 
        $"id"
        , $"birthyear"
        , $"drugcode"
        , $"timestamp".as("date")
        , $"gender"
        //, $"timestamp".cast("integer")
    ).withColumn("diffdate", datediff($"date", lit("2013-01-01")))
    //.withColumn("diffdate", ($"timestamp"/(60*60*24)).cast("integer"))    
}

def hospitalized_selector (patients:DataFrame) : DataFrame = {
    return patients.select(
        $"id"
        , $"birthyear"
        , $"drugcode"
        , $"diff_utleveringdato"
        , $"gender"
    )
}

def npr_selector (hospitalizations:DataFrame) : DataFrame = {
    return hospitalizations.select("diffdager_inn","diffdager_ut", "id")
}

println("Registered functions: unhospitalized_selector, hospitalized_selector,  npr_selector")
println("ok")