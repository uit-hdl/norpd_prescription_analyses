//TODO: Define some user-defined functions for transformation of fields here


//produce an UDF that transforms a 5-level ATC to N-level?
def takeNatcLevels(n:Int) = {
    val t = Array( 1
                  ,3
                  ,4 
                  ,5 
                  ,7)
    val takeNlevels = (x:String) => x.slice(0,t(n-1))
    udf(takeNlevels)
}

//UDFs for transformation from 5-level to 4-level ATC codes
val take4levelsUDF = udf( (x:String)=>x.slice(0,5) )



