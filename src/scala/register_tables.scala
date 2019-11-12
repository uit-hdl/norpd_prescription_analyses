import org.apache.spark.sql.SparkSession

/*
    register_tables ...
    Makes datasets available through the sparkSQL session
    args:
        spark:spark session
        warehouse:base directory where data is stored
*/
def register_tables(spark:SparkSession, warehouse:String){
    
    register_prescription(spark,warehouse)
    
    register_elders(spark, warehouse)
    
    register_npr_elders(spark,warehouse)
    
    spark.sql("show tables").show(false)
    println("done!")
}


def register_prescription(spark:SparkSession, warehouse:String){
    val prescription_prep = "prescription/"
    val ppath = warehouse+prescription_prep
    val fileext = "" //used to be .parquet
    println("===Prescription dataset===")
    
    println("registering patients")
    val patients = spark.read.parquet(ppath+"patients"+fileext)
        patients.createOrReplaceTempView("prescription_patients")
    
    println("registering prescriptions")
    val prescriptions = spark.read.parquet(ppath+"prescriptions"+fileext)
        prescriptions.createOrReplaceTempView("prescription_prescriptions")
    
    println("registering drugs")
    val drugs = spark.read.parquet(ppath+"drugs"+fileext)
        drugs.createOrReplaceTempView("prescription_drugs")
    
    println("ok!")
}


def register_hospitalization(spark:SparkSession, warehouse:String){
    println("Warning: This function will be deprecated soon")
    println("Use register_elders() instead")
    println("register_hospitalization() will later be used to register the hospitalized portion of the elders dataset")
    register_elders(spark, warehouse)
}

def register_npr_elders(spark:SparkSession,warehouse:String){
    val npr_elders_dir="npr_elders"
    val path = warehouse+npr_elders_dir
    println("===NPR Elders===")
    spark.read.parquet(path)
        .createOrReplaceTempView("npr_elders")
    println("ok!")
}

def register_elders(spark:SparkSession, warehouse:String){
    val elders_prep = "elders/"
    val elders_path = warehouse+elders_prep
    val fileext=""
    val all_prep="all"
    val patients_prep = "patients"
    val prescriptions_prep = "prescriptions"
    val drugs_prep = "drugs"
    
    println("===Hospitalization dataset===")
    
    println("registering all")
    spark.read.parquet(elders_path+all_prep+fileext)
        .createOrReplaceTempView("elders")
    
    println("registering patients")
    spark.read.parquet(elders_path+patients_prep+fileext)
        .createOrReplaceTempView("elders_patients")
    
    println("registering prescriptions")
    spark.read.parquet(elders_path+prescriptions_prep+fileext)
        .createOrReplaceTempView("elders_prescriptions")
    
    println("registering drugs")
    spark.read.parquet(elders_path+drugs_prep+fileext)
        .createOrReplaceTempView("elders_drugs")
    
    println("ok!")
}