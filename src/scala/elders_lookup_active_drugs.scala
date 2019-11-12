/*
    NOTE: This file has not been actively worked with for a while
    An equivalent python file should exist, providing the same functions, and is likely correct 
*/



import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import spark.implicits._


def lookup_drug(active_drug_table:DataFrame, patient_id:String, timestamp:Integer):DataFrame = {
    return active_drug_table.where($"id" === patient_id)
        .where($"treatment_start" <= timestamp)
        .where($"treatment_end" >= timestamp)
}

def lookup_drugs(active_drug_table:DataFrame, patientsDF:DataFrame):DataFrame = {
    return active_drug_table.as("dd")
        .join(patientsDF.as("patdf"))
        .where(
            $"dd.id" === $"patdf.id"
            &&
            $"patdf.timestamp" >= $"dd.treatment_start"
            &&
            $"patdf.timestamp" <= $"dd.treatment_end"
          )
         .select("dd.*")
         .groupBy("id", "timestamp")
         .agg(collect_list($"drugcode").as("active_drugs"))
}

def sample_from_healthy_population(df:DataFrame, frac:Double):DataFrame = {
    df
        .sample(true, frac)
        .select(
                $"id"
                ,(rand()*365).as("timestamp").cast("int")
               )
}

def sample_from_hosp_population(df:DataFrame, frac:Double):DataFrame = {
    df.sample(frac)
}

def sample_and_get_drugs_from_both(
    dfhosp:DataFrame
    ,dfheal:DataFrame
    ,active_drug_table:DataFrame
    ,frac_hosp:Double
    ,frac_heal:Double
    ):(DataFrame, DataFrame) = {
    val hosp_pat = sample_from_hosp_population(dfhosp, frac_hosp)
    val heal_pat = sample_from_healthy_population(dfheal, frac_heal)
    val hosp_active_drugs = lookup_drugs(active_drug_table, hosp_pat)
    val heal_active_drugs = lookup_drugs(active_drug_table, heal_pat)
    return (hosp_active_drugs, heal_active_drugs)
}

println("NOTE: scala/elders_lookup_active_drugs.scala is deprecated, use python/e2_drug_lookup.py or equivalent instead")
    
                                   
                              
                                   
                                   
                                   
                                   